package com.example.demo.service;


import com.example.demo.dao.wxInviteMapper;
import com.example.demo.entity.wxInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxInviteService")
public class wxInviteServiceImpl implements wxInviteService {

    @Autowired
    private wxInviteMapper inviteMapper;

    public void insertInviteRecord(wxInvite invite){
        inviteMapper.insertInviteRecord(invite);
    }

}
