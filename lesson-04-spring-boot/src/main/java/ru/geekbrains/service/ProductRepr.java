package ru.geekbrains.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.User;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// DTO
public class ProductRepr {

        private Long id;

        @NotEmpty
        private String name;

        private String description;

        //@NotEmpty
        private BigDecimal price;

    public ProductRepr() {
    }

    public ProductRepr(String name) {
            this.name = name;
        }

    public ProductRepr(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRepr productRepr = (ProductRepr) o;
        return id.equals(productRepr.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
