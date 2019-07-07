package com.example.demo.service;

import com.example.demo.dao.adminMapper;
import com.example.demo.entity.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class adminServiceImpl implements adminService{

    @Autowired
    private adminMapper mapper;

    public boolean checkUser(admin ad){
        if (mapper.getAdmin(ad)>0){
            return true;
        }else {
            return false;
        }
    }

}
