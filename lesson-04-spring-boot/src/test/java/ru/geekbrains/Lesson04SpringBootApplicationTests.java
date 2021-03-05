package ru.geekbrains;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.persist.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Lesson04SpringBootApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {
//        List<User> resultList = em.createQuery("select u " +
//                " from User u " +
//                "where (u.username like :username or :username is null) and " +
//                "      (u.age >= :minAge or :minAge is null) and " +
//                "      (u.age <= :maxAge or :maxAge is null)", User.class).getResultList();


        List<User> resultList = findWithFilter(null, null, null);

        resultList.forEach(System.out::println);
    }

    List<User> findWithFilter(String usernameFilter,
                              Integer minAge,
                              Integer maxAge) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (usernameFilter != null && !usernameFilter.isBlank()) {
            predicates.add(cb.like(root.get("username"), usernameFilter));
        }
        if (minAge != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("age"), minAge));
        }
        if (maxAge != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("age"), maxAge));
        }

        return em.createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
                .getResultList();
    }

}
