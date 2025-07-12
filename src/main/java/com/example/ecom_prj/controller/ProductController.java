package com.example.ecom_prj.controller;

import com.example.ecom_prj.model.Product;
import com.example.ecom_prj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product=service.getProductById(id);
        if(product!=null)
        return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProducts(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1=service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id){
        Product product=service.getProductById(id);
        byte [] imageFile=product.getImagedata();

        return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImagetype())).body(imageFile);
}

@PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1=service.updateProduct(id,product,imageFile);
        if(product1!=null)
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("failed to update",HttpStatus.INTERNAL_SERVER_ERROR);
}

@DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.getProductById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("failed to delete",HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

@GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        System.out.println("Searching for "+keyword);
    List<Product> products=service.searchProducts(keyword);
    return new ResponseEntity<>(products,HttpStatus.OK);
}

}
