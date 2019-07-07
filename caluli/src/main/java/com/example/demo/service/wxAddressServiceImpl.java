package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.wxAddressMapper;
import com.example.demo.entity.wxAddress;

@Service("wxAddressService")
public class wxAddressServiceImpl implements wxAddressService {


	@Autowired
	private wxAddressMapper addressMapper;
	
	public List<wxAddress> getAddress(String openid) {
		
		List<wxAddress> address=addressMapper.getAddress(openid);
		return address;
	}
	
	public void insertAddress(wxAddress address) {
		addressMapper.insertAddress(address);
	}

	public void deleteAddress(int addressid){
		addressMapper.deleteAddress(addressid);
	}

	public void updateAddress(wxAddress address){
		addressMapper.updateAddress(address);
	}

}
