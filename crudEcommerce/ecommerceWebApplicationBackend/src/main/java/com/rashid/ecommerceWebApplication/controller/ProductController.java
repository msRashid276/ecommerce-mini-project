package com.rashid.ecommerceWebApplication.controller;

import com.rashid.ecommerceWebApplication.model.Product;
import com.rashid.ecommerceWebApplication.repository.ProductRepo;
import com.rashid.ecommerceWebApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable int id){

        Product product = productService.getProductsById(id);
        if(product !=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){

        System.out.println(product);
        try {
            Product productAdded = productService.addProduct(product,imageFile);
            return new ResponseEntity<>(productAdded,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id ){
        Product product = productService.getProductsById(id);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }


    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile){


        try{
            Product productUpdated = productService.updateProduct(id,product,imageFile);
            if (productUpdated!=null){
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
            }

        }
       catch (IOException e){
           return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
       }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

           Product product = productService.getProductsById(id);
            if (product!=null){
                productService.deleteProduct(id);
                return new ResponseEntity<>("Deleted",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Product not Found",HttpStatus.BAD_REQUEST);
            }

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println(keyword);
       List<Product> product = productService.searchProducts(keyword);
           return new ResponseEntity<>(product,HttpStatus.OK);

    }


}
