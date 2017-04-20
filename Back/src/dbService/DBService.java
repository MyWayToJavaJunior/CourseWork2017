package dbService;

import accounts.UserDataSet;
import dao.DialogDao;
import dao.UserDao;
import dataset.DialogDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBService {
    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getMySqlConfig();
        this.sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfig() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(DialogDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
        configuration.setProperty("hibernate.connection.username", "homestead");
        configuration.setProperty("hibernate.connection.password", "secret");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        configuration.setProperty("hibernate.connection.CharSet", "utf8");
        configuration.setProperty("hibernate.connection.characterEncoding", "utf8");
        configuration.setProperty("hibernate.connection.useUnicode", "true");

        return configuration;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void addUser(UserDataSet userDataSet) {
        Session session = sessionFactory.openSession();
        Transaction thx = session.beginTransaction();
        UserDao userDao = new UserDao(session);
        userDao.add(userDataSet);
        thx.commit();
        session.close();
    }

    public UserDataSet getUser(Long id) {
        Session session = sessionFactory.openSession();
        UserDao userDao = new UserDao(session);
        UserDataSet user = userDao.get(id);
        session.close();
        return user;
    }

    public UserDataSet getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        UserDao userDao = new UserDao(session);
        UserDataSet user = userDao.getByLogin(login);
        session.close();
        return user;
    }

    public List<DialogDataSet> getAllDialog() {
        Session session = sessionFactory.openSession();
        DialogDao dialogDao = new DialogDao(session);
        List<DialogDataSet> list = dialogDao.getAll();
        session.close();
        return list;
    }

    public void addDialog(DialogDataSet dialogDataSet) {
        Session session = sessionFactory.openSession();
        Transaction thx = session.beginTransaction();
        DialogDao dialogDao = new DialogDao(session);
        dialogDao.add(dialogDataSet);
        thx.commit();
        session.close();
    }
}
