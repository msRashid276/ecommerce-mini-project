package com.rashid.ecommerceWebApplication.repository;

import com.rashid.ecommerceWebApplication.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE "+
        "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Product> searchProducts(String keyword);
}
