package SQLEngine;

import SQLRequests.*;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class SQLEngineImpl implements SQLEngine {

    private final SessionFactory sessionFactory;
    private final SQLFactory sqlFactory;

    public SQLEngineImpl(SQLFactory sqlFactory){
        this.sqlFactory = sqlFactory;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had troubles building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
    }

    private SQLKeyword saveKeyword(SQLKeyword keyword, Session session){
        var temp = session.createQuery("from SQLKeyword where value=:v").setParameter("v", keyword.getValue()).uniqueResultOptional();
        if (temp.isPresent())
            return (SQLKeyword) temp.get();
        session.save(keyword);
        return keyword;
    }

    private SQLQuestion saveQuestion(SQLQuestion question, Session session){
        session.save(question);
        return question;
    }

    private SQLAnswer saveAnswer(SQLAnswer answer, Session session){
        var temp = session.createQuery("from SQLAnswer where value=:v").setParameter("v", answer.getValue()).uniqueResultOptional();
        if (temp.isPresent())
            return (SQLAnswer) temp.get();
        session.save(answer);
        return answer;
    }

    private SQLQuestionAnswer saveQuestionAnswer(SQLQuestionAnswer qa, Session session){
        var temp = session.createQuery("from SQLQuestionAnswer where question=:q and answer=:a")
                .setParameter("q", qa.getQuestion()).setParameter("a", qa.getAnswer()).uniqueResultOptional();
        if (temp.isPresent())
            return (SQLQuestionAnswer) temp.get();
        session.save(qa);
        return qa;
    }

    @Override
    public void add(ServerQuestion q, ServerAnswer a) {
        SQLQuestion question = sqlFactory.createQuestion(q);
        SQLAnswer answer = sqlFactory.createAnswer(a);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        question = saveQuestion(question, session);
        answer = saveAnswer(answer, session);
        SQLQuestionAnswer qa = new SQLQuestionAnswer();
        qa.setQuestion(question);
        qa.setAnswer(answer);
        qa = saveQuestionAnswer(qa, session);
        Long pos = 0L;
        for (String keywordValue: q.getKeys()){
            SQLKeyword keyword = new SQLKeyword();
            keyword.setValue(keywordValue);
            keyword = saveKeyword(keyword, session);
            SQLQuestionKeyword qk = new SQLQuestionKeyword();
            qk.setQuestion(question);
            qk.setKeyword(keyword);
            qk.setPosition(pos);
            session.save(qk);
            pos++;
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SQLQuestionAnswer> query(List<Long> questionIds) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SQLQuestionAnswer> result = session.createQuery("from SQLQuestionAnswer where question.id in (:q)").setParameter("q", questionIds).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SQLQuestionKeyword> getKeywords(List<String> keywords) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SQLQuestionKeyword> result = session.createQuery
                        ("from SQLQuestionKeyword qk where qk.keyword.value in (:l)")
                .setParameterList("l", keywords).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
