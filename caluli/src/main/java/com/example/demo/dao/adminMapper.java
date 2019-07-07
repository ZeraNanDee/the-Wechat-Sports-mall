package com.example.demo.dao;

import com.example.demo.entity.admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface adminMapper
{
   public Integer getAdmin(admin ad);
}
