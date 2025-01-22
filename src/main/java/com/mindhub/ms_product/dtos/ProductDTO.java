package com.mindhub.ms_product.dtos;

import com.mindhub.ms_product.models.Product;

public class ProductDTO {

    private Long id;

    private String name, description;

    private Double price;

    private Integer stock;

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        stock = product.getStock();
    }

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
