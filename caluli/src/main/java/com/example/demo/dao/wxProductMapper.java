package com.example.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.wxProduct;

@Repository
@Mapper
public interface wxProductMapper {
		public List<wxProduct> selectProduct() ;

	    public List<wxProduct>getAllProduct();

	    public void updatePcount(wxProduct product);

	    public List<wxProduct> getProduct(int productId);

	    public void updateProduct(wxProduct product);
}
