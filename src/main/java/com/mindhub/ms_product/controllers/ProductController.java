package com.mindhub.ms_product.controllers;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.exceptions.NotValidArgumentException;
import com.mindhub.ms_product.models.Product;
import com.mindhub.ms_product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id) throws NotValidArgumentException, NotFoundException {
        isValidId(id);
        ProductDTO product = productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProduct) {
        Product newProductEntity = productService.createProduct(newProduct);
        return new ResponseEntity<>(newProductEntity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException {
        validateEntries(updatedProduct);
        Product updatedProductToEntity = productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>(updatedProductToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws NotValidArgumentException, NotFoundException {
        isValidId(id);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }

    public void isValidId(Long id) throws NotValidArgumentException {
        if (id == null || id <= 0) {
            throw new NotValidArgumentException("Invalid ID");
        }
    }

    public void isValidName(String name) throws NotValidArgumentException {
        if (name == null || name.isBlank()) {
            throw new NotValidArgumentException("Name cannot be empty.");
        }
    }

    public void isValidDescription(String description) throws NotValidArgumentException {
        if (description == null || description.isBlank()) {
            throw new NotValidArgumentException("Description cannot be empty.");
        }
    }

    public void isValidPrice(Double price) throws NotValidArgumentException {
        if (price == null) {
            throw new NotValidArgumentException("Price cannot be null.");
        }
    }

    public void isValidStock(Integer stock) throws NotValidArgumentException {
        if (stock == null || stock <= 0) {
            throw new NotValidArgumentException("Stock has to be at least 1");
        }
    }

    public void validateEntries(ProductDTO product) throws NotValidArgumentException {
        isValidName(product.getName());
        isValidDescription(product.getDescription());
        isValidPrice(product.getPrice());
        isValidStock(product.getStock());
    }
}
