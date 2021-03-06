package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main05 {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // INSERT
//        em.getTransaction().begin();
//
//        User user = new User("user3", "password3", "some4@email.cc");
//        User user1 = new User("user4", "password4", "some4@email.cc");
//        em.persist(user);
//        em.persist(user1);
//
//        em.getTransaction().commit();
//
//        em.close();

        // SELECT
//        System.out.println("User with id 1");
//        User user = em.find(User.class, 1L);
//        System.out.println(user);
//
//        // HQL, JPQL
//        System.out.println("All users in table");
//        List<User> userList = em.createQuery("from User", User.class)
//                .getResultList();
//        System.out.println(userList);
//
//        System.out.println("User with name 'user3'");
//        Object user3 = em.createQuery("from User u where u.username = :username")
//                .setParameter("username", "user3")
//                .getSingleResult();
//        System.out.println(user3);
//
//        // SQL
//        userList = em.createNativeQuery("select * from users", User.class)
//                .getResultList();
//        System.out.println(userList);
//
//        em.createNamedQuery("userByName")
//                .setParameter("username", "user3")
//                .getSingleResult();
//
//        em.close();

        // UPDATE
//        User user = em.find(User.class, 1L);
//
//        em.getTransaction().begin();
//
//        user.setPassword("fdssdfsdfsdf");
//
//        em.getTransaction().commit();
//
//        List<User> userList = em.createQuery("from User", User.class)
//                .getResultList();
//        System.out.println(userList);
//
//        em.close();

        // DELETE
        em.getTransaction().begin();

//        User user = em.find(User.class, 5L);
//        if (user != null) {
//            em.remove(user);
//        }

        em.createQuery("delete from User where username=:username")
                .setParameter("username", "user2")
                .executeUpdate();

        em.getTransaction().commit();

        List<User> userList = em.createQuery("from User", User.class)
                .getResultList();
        System.out.println(userList);

        em.close();
    }
}
