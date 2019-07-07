package com.example.demo.controller;

import com.example.demo.entity.wxProduct;
import com.example.demo.service.wxProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Products")
public class ProductsController {

    @Autowired
    private wxProductService wxProductService;

    @GetMapping("/getProducts")
    public List<wxProduct> getProducts(){
        List<wxProduct>wxProduct=wxProductService.getAllProduct();

        return wxProduct;

    }

}
