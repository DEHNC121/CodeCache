package SQLEngine;

import SQLRequests.SQLAnswer;
import SQLRequests.SQLEngine;
import SQLRequests.SQLFactory;
import SQLRequests.SQLQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class SQLEngineImpl implements SQLEngine {

    private final SessionFactory sessionFactory;

    public SQLEngineImpl(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
    }

    @Override
    public void add(SQLQuestion q, SQLAnswer a) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(q);
        session.save(a);
        SQLQuestionAnswer qa = new SQLQuestionAnswer();
        qa.setQuestion(q);
        qa.setAnswer(a);
        session.save(qa);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SQLAnswer> query(SQLQuestion q) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SQLAnswer> result = session.createQuery("from SQLQuestionAnswer where question=:q").setParameter("q", q).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
