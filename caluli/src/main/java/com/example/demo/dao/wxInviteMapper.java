package com.example.demo.dao;

import com.example.demo.entity.wxInvite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface wxInviteMapper {

    public void insertInviteRecord(wxInvite invite);
}
