package com.mindhub.ms_product.services.Impl;

import com.mindhub.ms_product.config.JwtUtils;
import com.mindhub.ms_product.exceptions.NotAuthorizedException;
import com.mindhub.ms_product.exceptions.NotValidArgumentException;
import com.mindhub.ms_product.repositories.ProductRepository;
import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.mappers.ProductMapper;
import com.mindhub.ms_product.models.Product;
import com.mindhub.ms_product.services.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ProductDTO getProduct(Long id) throws NotFoundException {
        try {
            return productMapper.productToDTO(findById(id));
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() throws NotFoundException {
        try {
            List<ProductDTO> productList = productMapper.productListToDTO(productRepository.findAll());
            validateProductListIsEmpty(productList);
            return productList;
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO newProduct) throws NotValidArgumentException, NotAuthorizedException {
        try {
            validateIsAdmin();
            validateAlreadyExistsByName(newProduct.getName());
            Product savedProduct = save(productMapper.productToEntity(newProduct));
            return productMapper.productToDTO(savedProduct);
        } catch (NotValidArgumentException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException, NotAuthorizedException {
        try {
            validateIsAdmin();
            Product productToUpdate = findById(id);
            validateAlreadyExistsByName(updatedProduct.getName());
            Product savedProduct = save(productMapper.updateProductToEntity(productToUpdate, updatedProduct));
            return productMapper.productToDTO(savedProduct);
        } catch (NotFoundException | NotValidArgumentException | NotAuthorizedException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    @Transactional
    public ProductDTO patchProduct(Long id, ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException {
        try {
            Product productToUpdate = findById(id);
            validateAlreadyExistsByName(updatedProduct.getName());
            Product savedProduct = (productMapper.patchProductToEntity(productToUpdate, updatedProduct));
            return productMapper.productToDTO(savedProduct);
        } catch (NotFoundException | NotValidArgumentException e) {
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }


    @Override
    @Transactional
    public void deleteProduct(Long id) throws NotFoundException, NotAuthorizedException {
        try {
            validateIsAdmin();
            existsById(id);
            deleteById(id);
        } catch (NotFoundException | NotAuthorizedException e){
            log.warn(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Override
    public Product findById(Long id) throws NotFoundException {
        return (productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not Found.")));
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
    public void existsById(Long id) throws NotFoundException {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found.");
        }
    }

    private void validateAlreadyExistsByName(String name) throws NotValidArgumentException {
        if (productRepository.existsByName(name)) {
            throw new NotValidArgumentException("Product with name: " + name + "  already exists.");
        }
    }

    private void validateProductListIsEmpty(List<ProductDTO> allProducts) throws NotFoundException {
        if (allProducts.toArray().length == 0) {
            throw new NotFoundException("No products found to show.");
        }
    }

    private void validateIsAdmin() throws NotAuthorizedException {
        String token = jwtUtils.getJwtToken();
        String userRole = jwtUtils.extractRole(token);

        if (!userRole.equals("ADMIN")) {
            throw new NotAuthorizedException("You are not authorized to access this resource.");
        }
    }
}
