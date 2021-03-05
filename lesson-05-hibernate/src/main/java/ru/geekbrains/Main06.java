package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Contact;
import ru.geekbrains.persist.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main06 {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        // INSERT for one to many
        em.getTransaction().begin();

//        User user = new User("user2", "password2", "user2@mail.com");
//        em.persist(user);

        User user = new User("user222", "password2", "user2@mail.com");
        user.setId(1L);
        em.merge(user);
        em.getTransaction().commit();
//
//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(new Contact("home phone", "(095)343-15-62", user));
//        contacts.add(new Contact("work phone", "(095)233-56-55", user));
//        contacts.add(new Contact("mobile phone", "(916)998-65-27", user));
//        contacts.add(new Contact("home address", "Russia, Moscow, Tverskaja", user));
//
//        contacts.forEach(em::persist);
//
//        em.getTransaction().commit();

        // SELECT for one to many
//        User user = em.find(User.class, 1L);
//        user.getContacts().forEach(System.out::println);
//
//        List<Contact> contacts = em.createQuery(
//                "select c from User u " +
//                        "inner join Contact c on u.id = c.user.id " +
//                        "where c.type = 'mobile phone'", Contact.class)
//                .getResultList();
//
//        contacts.forEach(System.out::println);
//
//        List<String> usernames = em.createQuery(
//                "select new java.lang.String(u.username) from User u " +
//                        "inner join Contact c on u.id = c.user.id " +
//                        "where c.type = 'mobile phone'", String.class)
//                .getResultList();
//
//        System.out.println(usernames);

        em.close();
    }
}
