package com.mindhub.ms_product.services;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.models.Product;

import java.util.List;

public interface ProductService extends GenericService<Product>{

    ProductDTO getProduct(Long id) throws Exception;

    List<ProductDTO> getAllProducts();

    Product createProduct(ProductDTO newProduct);

    Product updateProduct(Long id, ProductDTO updatedProduct) throws Exception;

    void deleteProduct(Long id);
}
