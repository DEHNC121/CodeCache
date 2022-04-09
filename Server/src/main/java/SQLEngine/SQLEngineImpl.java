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

    //TODO: duplicates
    @Override
    public void add(ServerQuestion q, ServerAnswer a) {
        SQLQuestion question = sqlFactory.createQuestion();
        SQLAnswer answer = sqlFactory.createAnswer(a.getValue());
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(question);
        session.save(answer);
        SQLQuestionAnswer qa = new SQLQuestionAnswer();
        qa.setQuestion(question);
        qa.setAnswer(answer);
        session.save(qa);
        Long pos = 0L;
        for (String keyword: q.getKeys()){
            SQLKeyword k = new SQLKeyword();
            k.setValue(keyword);
            session.save(k);
            SQLQuestionKeyword qk = new SQLQuestionKeyword();
            qk.setQuestion(question);
            qk.setKeyword(k);
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
