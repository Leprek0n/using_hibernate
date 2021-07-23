package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS usershibernate " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();
        String sql = "DROP table usershibernate";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();


    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "TRUNCATE usershibernate";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();

    }
}
