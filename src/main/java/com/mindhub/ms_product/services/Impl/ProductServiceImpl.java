package com.mindhub.ms_product.services.Impl;

import com.mindhub.ms_product.Repositories.ProductRepository;
import com.mindhub.ms_product.dtos.ProductDTO;
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
    public ProductDTO getProduct(Long id) throws Exception {
        return productMapper.productToDTO(findById(id));
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
    public Product updateProduct(Long id, ProductDTO updatedProduct) throws Exception {
        Product productToUpdate = findById(id);
        return save(productMapper.updateProductToEntity(productToUpdate, updatedProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        deleteById(id);
    }

    @Override
    public Product findById(Long id) throws Exception {
        return (productRepository.findById(id).orElseThrow(() -> new Exception("Not Found.")));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
