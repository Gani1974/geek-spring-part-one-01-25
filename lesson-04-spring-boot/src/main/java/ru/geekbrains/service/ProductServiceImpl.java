package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

//    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductRepr> findWithFilter(String nameFilter,
                                        BigDecimal minPrice,
                                        BigDecimal maxPrice,
                                        Integer page,
                                        Integer size,
                                        String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (nameFilter != null && !nameFilter.isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.minPriceFilter(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.maxPriceFilter(maxPrice));
        }
        if (sortField != null && !sortField.isBlank()) {
            return productRepository.findAll(spec, PageRequest.of(
                    page, size, Sort.by(sortField)))
                    .map(ProductRepr::new);
        }
        return productRepository.findAll(spec, PageRequest.of(page, size))
                .map(ProductRepr::new);
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductRepr::new);
    }

    @Transactional
    @Override
    public void save(ProductRepr product) {
        Product productToSave = new Product(product);
        productRepository.save(productToSave);
        if(product.getId() == null){
            product.setId(productToSave.getId());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
