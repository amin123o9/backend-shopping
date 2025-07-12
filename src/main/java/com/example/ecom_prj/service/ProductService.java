package com.example.ecom_prj.service;

import com.example.ecom_prj.model.Product;
import com.example.ecom_prj.repo.Productrepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private Productrepo repo;
    public List<Product> getAllProducts() {
        List <Product> a=repo.findAll();

        return a;

    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImagename(imageFile.getOriginalFilename());
        product.setImagetype(imageFile.getContentType());
        product.setImagedata(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) {
        Optional<Product> existingProductOpt = repo.findById(id);

        if (!existingProductOpt.isPresent()) {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }

        Product existingProduct = existingProductOpt.get();

        // Update basic fields
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        // Add other fields you want to update

        // Handle image if present
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                existingProduct.setImagename(imageFile.getOriginalFilename());
                existingProduct.setImagetype(imageFile.getContentType());
                existingProduct.setImagedata(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to process image file", e);
            }
        }

        return repo.save(existingProduct);
    }



    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {

    }
}
