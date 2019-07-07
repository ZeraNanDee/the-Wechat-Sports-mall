package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.wxProductMapper;
import com.example.demo.entity.wxProduct;

@Service("wxProductService")
public class wxProductServiceImpl implements wxProductService{
	@Autowired
	private wxProductMapper wxProductMapper;
	@Override
	public List<wxProduct> selectProduct() {
		List<wxProduct> wxProduct=wxProductMapper.selectProduct();
		return wxProduct;
	}

	public List<wxProduct>getAllProduct(){
		List<wxProduct> wxProduct=wxProductMapper.getAllProduct();
		return wxProduct;
	}

	public void updatePcount(wxProduct product){
		wxProductMapper.updatePcount(product);
	}

	public List<wxProduct> getProduct(int productId){
		List<wxProduct> wxProduct=wxProductMapper.getProduct(productId);
		return wxProduct;
	}
	public void updateProduct(wxProduct product){
		wxProductMapper.updateProduct(product);
	}
}
