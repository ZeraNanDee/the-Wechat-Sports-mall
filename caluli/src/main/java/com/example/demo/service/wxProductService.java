package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.wxProduct;

public interface wxProductService {
	public List<wxProduct>selectProduct();//轮播图产品

	public List<wxProduct>getAllProduct();//购买的产品

	public void updatePcount(wxProduct product);//更新库存

	public List<wxProduct> getProduct(int productId);//按ID查询商品展示给管理员

	public void updateProduct(wxProduct product);//管理员修改商品
}
