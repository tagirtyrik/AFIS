package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Ксю on 26.03.2016.
 */
public class HibernateUtils {

    private static SessionFactory ourSessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Session currSession;

    private static void createSession(){
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session s = ourSessionFactory.openSession();
        currSession = s;
    }
    public static Session getSession(){
        if (currSession==null||!currSession.isOpen()){
            createSession();
        }
        return currSession;
    }
}
