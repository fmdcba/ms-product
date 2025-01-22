package com.mindhub.ms_product.mappers;

import com.mindhub.ms_product.dtos.ProductDTO;
import com.mindhub.ms_product.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductDTO productToDTO (Product product) {
        return new ProductDTO(product);
    }

    public List<ProductDTO> productListToDTO(List<Product> products) {
        return products.stream().map(product -> new ProductDTO(product)).toList();
    }

    public Product productToEntity (ProductDTO product){
        return new Product (product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }

    public Product updateProductToEntity (Product productToUpdate, ProductDTO updatedProduct) {
        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setDescription(updatedProduct.getDescription());
        productToUpdate.setPrice(updatedProduct.getPrice());
        productToUpdate.setStock(updatedProduct.getStock());

        return productToUpdate;
    }
}
