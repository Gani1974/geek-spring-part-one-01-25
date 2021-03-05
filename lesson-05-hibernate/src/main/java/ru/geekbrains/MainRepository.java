package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.persistence.EntityManagerFactory;

public class MainRepository {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        UserRepository repository = new UserRepository(emFactory);

        //repository.insert(new User("user333", "adasdada", "sadasdasd"));
        User user = new User("user3", "adasdada", "sadasdasd");
        user.setId(2L);
        user.setUsername("111222333");
        repository.update(user);

        repository.findAll().forEach(System.out::println);
    }
}
