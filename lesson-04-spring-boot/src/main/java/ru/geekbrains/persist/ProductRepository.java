package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findProductByNameLike(String name);

//    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    Optional<Product> findProductByName(String name);

    @Query("select p from Product p " +
            "where (p.name like :name or :name is null) and " +
            "      (p.price >= :minPrice or :minPrice is null) and " +
            "      (p.price <= :maxPrice or :maxPrice is null)")
    List<Product> findWithFilter(@Param("name") String nameFilter,
                              @Param("minPrice") BigDecimal minPrice,
                              @Param("maxPrice") BigDecimal maxPrice);
}
