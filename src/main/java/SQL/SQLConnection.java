package SQL;

import SQL.Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SQLConnection {

    private static SQLConnection connection;

    private SQLConnection()
    {

    }

    public static SQLConnection getConnection()
    {
        if(connection == null)
        {
            connection = new SQLConnection();
        }

        return connection;
    }

    public static Session GetSession()
    {
        SessionFactory sessionFactory  = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        return sessionFactory.getCurrentSession();
        // КАК ЗАКРЫТЬ ?
    }
}
