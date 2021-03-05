package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<ProductRepr> findWithFilter(String nameFilter,BigDecimal minPrice,
                                 BigDecimal maxPrice,
                                 Integer page,
                                 Integer size,
                                 String sortField);

    List<ProductRepr> findAll();
//    List<ProductRepr> findAll(Specification<Product> spec);

    Optional<ProductRepr> findById(Long id);

    void save(ProductRepr product);

    void delete(Long id);

}
