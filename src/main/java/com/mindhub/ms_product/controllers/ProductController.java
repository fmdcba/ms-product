package com.mindhub.ms_product.controllers;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.exceptions.NotFoundException;
import com.mindhub.ms_product.exceptions.NotValidArgumentException;
import com.mindhub.ms_product.models.Product;
import com.mindhub.ms_product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get product by id", description = "Return product if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return product, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> getProduct(@PathVariable long id) throws NotValidArgumentException, NotFoundException {
        isValidId(id);
        ProductDTO product = productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Return all products in DB")
        @ApiResponse(responseCode = "200", description = "Return list of products, and http code status OK")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Return product if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return created product, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Indicating the field that cause the error")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProduct) throws NotValidArgumentException {
        validateEntries(newProduct);
        Product newProductEntity = productService.createProduct(newProduct);
        return new ResponseEntity<>(newProductEntity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Return updated product if ID is valid and exists in DB")
        @ApiResponse(responseCode = "200", description = "Return updated product, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException {
        validateEntries(updatedProduct);
        Product updatedProductToEntity = productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>(updatedProductToEntity, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch product", description = "Return patched product if ID is valid and exists in DB")
    @ApiResponse(responseCode = "200", description = "Return patched product, and http code status OK")
    @ApiResponse(responseCode = "400", description = "Error msg Bad request: Pointing out that not all fields can be null or empty")
    @ApiResponse(responseCode = "404", description = "Error msg: Not found")
    public ResponseEntity<?> patchProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) throws NotFoundException, NotValidArgumentException {
        validateEmptyEntries(updatedProduct);
        Product updatedProductToEntity = productService.patchProduct(id, updatedProduct);
        return new ResponseEntity<>(updatedProductToEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Return msg of operation completed successfully")
        @ApiResponse(responseCode = "200", description = "Return msg: Product deleted successfully, and http code status OK")
        @ApiResponse(responseCode = "400", description = "Error msg Bad request: Invalid ID")
        @ApiResponse(responseCode = "404", description = "Error msg: Not found")
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
            throw new NotValidArgumentException("Name cannot be empty or null.");
        }
    }

    public void isValidDescription(String description) throws NotValidArgumentException {
        if (description == null || description.isBlank()) {
            throw new NotValidArgumentException("Description cannot be empty or null.");
        }
    }

    public void isValidPrice(Double price) throws NotValidArgumentException {
        if (price == null) {
            throw new NotValidArgumentException("Price cannot be null.");
        }
    }

    public void isValidStock(Integer stock) throws NotValidArgumentException {
        if (stock == null || stock <= 0) {
            throw new NotValidArgumentException("Stock has to be at least 1.");
        }
    }

    public void validateEntries(ProductDTO product) throws NotValidArgumentException {
        isValidName(product.getName());
        isValidDescription(product.getDescription());
        isValidPrice(product.getPrice());
        isValidStock(product.getStock());
    }

    public void validateEmptyEntries(ProductDTO product) throws NotValidArgumentException {
        if (product.getName() == null && product.getDescription() == null && product.getPrice() == null && product.getStock() == null) {
            throw new NotValidArgumentException("When patching product at least one field must not be null or empty.");
        }
    }
}
