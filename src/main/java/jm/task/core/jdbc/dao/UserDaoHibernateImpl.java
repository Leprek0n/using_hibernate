package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS usershibernate " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
        try {
            util.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String sql = "DROP table usershibernate";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
        try {
            util.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Util util = new Util();
        Transaction transaction = null;
       Session session = util.getSessionFactory().openSession();
       transaction = session.beginTransaction();
       session.save(user);
       transaction.commit();
       session.close();


    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        Session session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers()  {
        Util util = new Util();
        Transaction transaction = null;
        Session session = util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        transaction.commit();
        session.close();
        try {
            util.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        Session session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String sql = "TRUNCATE usershibernate";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();

    }
}
