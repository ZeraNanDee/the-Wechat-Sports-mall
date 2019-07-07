package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.wxStep;

public interface wxStepService {
	
	public void insertStep(wxStep step);
	
//	public boolean isexistUserid(Integer userid);
	public List<Integer> selectStepRecord();

}
