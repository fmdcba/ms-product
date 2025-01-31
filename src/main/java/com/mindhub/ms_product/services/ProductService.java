package com.mindhub.ms_product.services;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotAuthorizedException;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.exceptions.NotValidArgumentException;
import com.mindhub.ms_product.models.Product;

import java.util.List;

public interface ProductService extends GenericService<Product>{

    ProductDTO getProduct(Long id) throws NotFoundException;

    List<ProductDTO> getAllProducts() throws NotFoundException;

    ProductDTO createProduct(ProductDTO newProduct) throws NotValidArgumentException, NotAuthorizedException;

    ProductDTO updateProduct(Long id, ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException, NotAuthorizedException;

    ProductDTO patchProduct(Long id, ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException;

    void deleteProduct(Long id) throws NotFoundException, NotAuthorizedException;
}
