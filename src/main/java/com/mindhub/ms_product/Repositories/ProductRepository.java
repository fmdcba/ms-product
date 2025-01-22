package com.mindhub.ms_product.Repositories;

import com.mindhub.ms_product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
