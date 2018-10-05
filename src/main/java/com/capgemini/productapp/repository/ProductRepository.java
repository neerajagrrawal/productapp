package com.capgemini.productapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.productapp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
