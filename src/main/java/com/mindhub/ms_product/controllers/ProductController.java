package com.mindhub.ms_product.controllers;

import com.mindhub.ms_product.Repositories.ProductRepository;
import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.mappers.ProductMapper;
import com.mindhub.ms_product.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) throws Exception {
        ProductDTO product = productMapper.productToDTO(productRepository.findById(id).orElseThrow(() -> new Exception("Not Found.")));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> products = productMapper.productListToDTO(productRepository.findAll());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProduct) {
        Product newProductEntity = productRepository.save(productMapper.productToEntity(newProduct));
        return new ResponseEntity<>(newProductEntity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) throws Exception {
        //validate ID
        //validate entries for put
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new Exception("Not Found."));
        Product updatedProductToEntity = productMapper.updateProductToEntity(productToUpdate, updatedProduct);
        return new ResponseEntity<>(updatedProductToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }
}
