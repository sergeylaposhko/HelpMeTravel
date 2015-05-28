package ua.laposhko.hmt.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Sergey Laposhko
 */
@SuppressWarnings("deprecation")
public class HibernateConfig {
    private static ServiceRegistry serviceRegistry;

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
