package SQLRequests;

import SQLEngine.SQLEngine;
import SQLRequests.*;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;
import SQLEngine.EngineQuestionAnswer;
import SQLEngine.EngineQuestionKeyword;
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

    public SQLEngineImpl(SQLFactory sqlFactory) {
        this.sqlFactory = sqlFactory;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had troubles building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }

    private SQLKeyword saveKeyword(SQLKeyword keyword, Session session) {
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

    private SQLKeywordPosition saveKeywordPosition(SQLKeywordPosition keywordPosition, Session session) {
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

    private SQLQuestion saveQuestion(SQLQuestion question, Session session) {
        HashSet<SQLKeywordPosition> tempSet = new HashSet<>();
        for (SQLKeywordPosition keywordPosition : question.getKeywords()) {
            tempSet.add(saveKeywordPosition(keywordPosition, session));
        }
        question.setKeywords(tempSet);

        var temp = session.createQuery(
                        "select q from SQLQuestion q join q.keywords x " +
                                "where x in (:k)")
                .setParameterList("k", question.getKeywords())
                .list();
        for (var q : temp) {
            if (((SQLQuestion) q).getKeywords().equals(question.getKeywords()))
                return (SQLQuestion) q;
        }
        session.save(question);
        return question;
    }

    private SQLAnswer saveAnswer(SQLAnswer answer, Session session) {
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
    public List<EngineQuestionAnswer> query(List<Long> questionIds) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<EngineQuestionAnswer> result = session.createQuery(
                        "select new SQLEngine.EngineQuestionAnswer(question, answers) " +
                                "from SQLQuestion question join question.answers answers " +
                                "where question.id in (:q)")
                .setParameter("q", questionIds)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EngineQuestionKeyword> getKeywords(List<String> keywords) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<EngineQuestionKeyword> result = session.createQuery(
                        "select new SQLEngine.EngineQuestionKeyword(question, keywords.keyword, keywords.position) " +
                                "from SQLQuestion question join question.keywords keywords " +
                                "where keywords.keyword.value in (:l)")
                .setParameterList("l", keywords)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public int remove(ServerQuestion serverQuestion, ServerAnswer serverAnswer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        var tempQuestion = session.createQuery(
                        "from SQLQuestion where id = :qId")
                .setParameter("qId", serverQuestion.getId())
                .uniqueResultOptional();
        if (tempQuestion.isEmpty() || !((SQLQuestion) tempQuestion.get()).getFull().equals(serverQuestion.getValue())) {
            session.getTransaction().commit();
            session.close();
            return -1;
        }

        var tempAnswer = session.createQuery(
                        "from SQLAnswer where id = :aId")
                .setParameter("aId", serverAnswer.getId())
                .uniqueResultOptional();
        if (tempAnswer.isEmpty() || !((SQLAnswer) tempAnswer.get()).getValue().equals(serverAnswer.getValue())) {
            session.getTransaction().commit();
            session.close();
            return -1;
        }

        SQLQuestion question = (SQLQuestion) tempQuestion.get();
        question.getAnswers().remove((SQLAnswer) tempAnswer.get());

        session.getTransaction().commit();
        session.close();
        return 0;
    }
}
