package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.BadRequestException;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

    @Tag(name = "Product resource API", description = "API to manipulate Product resource ...")
    @CrossOrigin(origins = "http://localhost:63342")
    @RestController
    @RequestMapping("/api/v1/product")
    public class ProductResource {

        private final ProductService productService;

        @Autowired
        public ProductResource(ProductService productService) {
            this.productService = productService;
        }

        @GetMapping(path = "/all", produces = "application/json")
        public List<ProductRepr> findAll() {
            return productService.findAll().stream()
                    .collect(Collectors.toList());
        }

        @GetMapping(path = "/{id}")
        public ProductRepr findById(@PathVariable("id") Long id) {
            ProductRepr productRepr = productService.findById(id)
                    .orElseThrow(NotFoundException::new);
            return productRepr;
        }

        @GetMapping("filter")
        public Page<ProductRepr> listPage(
                @RequestParam("nameFilter") Optional<String> nameFilter,
                @RequestParam("minPrice") Optional<BigDecimal> minPrice,
                @RequestParam("maxPrice") Optional<BigDecimal> maxPrice,
                @Parameter(example = "1") @RequestParam("page") Optional<Integer> page,
                @RequestParam("size") Optional<Integer> size,
                @RequestParam("sortField") Optional<String> sortField) {

            return productService.findWithFilter(
                    nameFilter.orElse(null),
                    minPrice.orElse(null),
                    maxPrice.orElse(null),
                    page.orElse(1) - 1,
                    size.orElse(3),
                    sortField.orElse(null)
            );
        }

        @PostMapping(consumes = "application/json")
        public ProductRepr create(@RequestBody ProductRepr productRepr) {
            if (productRepr.getId() != null) {
                throw new BadRequestException();
            }
            productService.save(productRepr);
            return productRepr;
        }

        @PutMapping(consumes = "application/json")
        public void update(@RequestBody ProductRepr productRepr) {
            if (productRepr.getId() == null) {
                throw new BadRequestException();
            }
            productService.save(productRepr);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable("id") Long id) {
            productService.delete(id);
        }

        @ExceptionHandler
        public ResponseEntity<String> notFoundException(NotFoundException ex) {
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler
        public ResponseEntity<String> badRequestException(BadRequestException ex) {
            return new ResponseEntity<>("Bad request", HttpStatus.NOT_FOUND);
        }
    }
