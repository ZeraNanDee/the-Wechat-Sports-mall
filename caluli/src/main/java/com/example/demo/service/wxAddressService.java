package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.wxAddress;

public interface wxAddressService {
	
	public List<wxAddress> getAddress(String openid);
	
	public void insertAddress(wxAddress address);

	public void deleteAddress(int addressid);

	public void updateAddress(wxAddress address);
}
