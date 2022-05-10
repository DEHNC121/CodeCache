package SQLEngine;

import SQLRequests.*;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
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
        var temp = session.createQuery(
                "from SQLKeyword " +
                        "where value=:v")
                .setParameter("v", keyword.getValue())
                .uniqueResultOptional();
        if (temp.isPresent())
            return (SQLKeyword) temp.get();
        session.save(keyword);
        return keyword;
    }

    private SQLKeywordPosition saveKeywordPosition(SQLKeywordPosition keywordPosition, Session session){
        keywordPosition.setKeyword(saveKeyword(keywordPosition.getKeyword(), session));
        var temp = session.createQuery(
                "from SQLKeywordPosition" +
                        " where keyword =:k and position =:p")
                .setParameter("k", keywordPosition.getKeyword())
                .setParameter("p", keywordPosition.getPosition())
                .uniqueResultOptional();
        if (temp.isPresent())
            return (SQLKeywordPosition) temp.get();
        session.save(keywordPosition);
        return keywordPosition;
    }

    private SQLQuestion saveQuestion(SQLQuestion question, Session session){
        HashSet<SQLKeywordPosition> tempSet = new HashSet<>();
        for (SQLKeywordPosition keywordPosition: question.getKeywords()){
            tempSet.add(saveKeywordPosition(keywordPosition, session));
        }
        question.setKeywords(tempSet);

        var temp = session.createQuery(
                "from SQLQuestion " +
                        "where keywords=:k")
                .setParameter("k", question.getKeywords())
                .uniqueResultOptional();
        if (temp.isPresent())
            return (SQLQuestion) temp.get();
        session.save(question);
        return question;
    }

    private SQLAnswer saveAnswer(SQLAnswer answer, Session session){
        var temp = session.createQuery(
                "from SQLAnswer " +
                        "where value=:v")
                .setParameter("v", answer.getValue())
                .uniqueResultOptional();
        if (temp.isPresent())
            return (SQLAnswer) temp.get();
        session.save(answer);
        return answer;
    }

    @Override
    public void add(ServerQuestion q, ServerAnswer a) {
        SQLQuestion question = sqlFactory.createQuestion(q);
        SQLAnswer answer = sqlFactory.createAnswer(a);
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        answer = saveAnswer(answer, session);
        question = saveQuestion(question, session);
        question.getAnswers().add(answer);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SQLQuestionAnswers> query(List<Long> questionIds) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SQLQuestionAnswers> result = session.createQuery(
                "select new SQLRequests.SQLQuestionAnswers(question, answers)" +
                        " from SQLQuestion question join question.answers answers" +
                        " where question.id in (:q)")
                .setParameter("q", questionIds)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SQLQuestionKeyword> getKeywords(List<String> keywords) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SQLQuestionKeyword> result = session.createQuery(
                "select new SQLRequests.SQLQuestionKeyword(question, keywords.keyword, keywords.position) " +
                        "from SQLQuestion question join question.keywords keywords " +
                        "where keywords.keyword.value in (:l)")
                .setParameterList("l", keywords)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
