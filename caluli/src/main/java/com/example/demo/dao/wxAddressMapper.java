package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.wxAddress;

@Repository
@Mapper
public interface wxAddressMapper {
	
	public List<wxAddress> getAddress(String openid);
	
	public void insertAddress(wxAddress address);
	
	public void deleteAddress(int addressid);

	public void updateAddress(wxAddress address);
}
