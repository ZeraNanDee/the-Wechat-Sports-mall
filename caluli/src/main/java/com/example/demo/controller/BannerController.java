package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.wxProduct;
import com.example.demo.service.wxProductService;

@RestController
@RequestMapping("/image")
public class BannerController {
	@Autowired
	private wxProductService wxProductService;
	@GetMapping("/banner")
	public List<wxProduct>getBanner(){
		List<wxProduct>wxProduct=wxProductService.selectProduct();
		
		return wxProduct;
		
	}

}
