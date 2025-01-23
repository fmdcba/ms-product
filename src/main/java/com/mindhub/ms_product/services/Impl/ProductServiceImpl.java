package com.mindhub.ms_product.services.Impl;

import com.mindhub.ms_product.repositories.ProductRepository;
import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.mappers.ProductMapper;
import com.mindhub.ms_product.models.Product;
import com.mindhub.ms_product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDTO getProduct(Long id) throws NotFoundException {
        if (existsById(id)) {
            return productMapper.productToDTO(findById(id));
        } else {
            throw new NotFoundException("Not found.");
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return  productMapper.productListToDTO(productRepository.findAll());
    }

    @Override
    public Product createProduct(ProductDTO newProduct) {
        return save(productMapper.productToEntity(newProduct));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO updatedProduct) throws NotFoundException {
        if (existsById(id)) {
            Product productToUpdate = findById(id);
            return save(productMapper.updateProductToEntity(productToUpdate, updatedProduct));
        } else {
            throw new NotFoundException("Not found.");
        }
    }

    @Override
    public void deleteProduct(Long id) throws NotFoundException {
        if (existsById(id)) {
            deleteById(id);
        } else {
            throw new NotFoundException("Not found.");
        }
    }

    @Override
    public Product findById(Long id) throws NotFoundException {
        return (productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found.")));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
