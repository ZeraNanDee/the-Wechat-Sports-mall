package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.wxStepMapper;
import com.example.demo.entity.wxStep;

@Service("wxStepService")
public class wxStepServiceImpl implements wxStepService {
	
	@Autowired
	private wxStepMapper stepMapper;
	
 public void insertStep(wxStep step) {
	 stepMapper.insertStep(step);
 }
 
 public List<Integer> selectStepRecord() {
	return stepMapper.selectStepRecord();
	 
 }
 
// public boolean isexistUserid(Integer userid)
// {
//	 if (stepMapper.isexistUserid(userid)>1) {
//		return true;
//	}else {
//		 return false;
//	}
// }
// 
 
}
