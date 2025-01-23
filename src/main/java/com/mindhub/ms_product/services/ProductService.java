package com.mindhub.ms_product.services;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.models.Product;

import java.util.List;

public interface ProductService extends GenericService<Product>{

    ProductDTO getProduct(Long id) throws NotFoundException;

    List<ProductDTO> getAllProducts();

    Product createProduct(ProductDTO newProduct);

    Product updateProduct(Long id, ProductDTO updatedProduct) throws NotFoundException;

    void deleteProduct(Long id) throws NotFoundException;
}
