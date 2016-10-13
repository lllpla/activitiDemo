package com.drpeng.service;

import com.drpeng.dao.UserMapper;
import com.drpeng.entity.User;
import com.drpeng.entity.UserExample;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by lifei13 on 2016/10/13.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserExample userExample;
    /**
     *
     * @param execution
     */

    public void addUser(DelegateExecution execution){

        User user = new User();
        //用户姓名
        //String userName = (String) execution.getVariable("userName");
        user.setName("lifei");
        user.setState("0");
        user.setCreateDate("201610");
        user.setTel("17012312312");

        //保存对象到流程
        execution.setVariable("userObj",user);
        execution.setVariable("userName","lifei");
    }

    public void saveUser(DelegateExecution execution){
        User user = (User)execution.getVariable("userObj");
        String userName = (String) execution.getVariable("userName");

        //1.查出是否有重名，如果有重名 则修改用户名
        userExample.clear();
        userExample.or()
                .andNameEqualTo(userName);
        List<User> userList= userMapper.selectByExample(userExample);
        //2.修改用户名+UUID
        if (userList != null && userList.size() != 0){
            UUID uuid = UUID.randomUUID();
            userName = userName+ uuid;
            user.setName(userName);
        }
        userMapper.insert(user);

    }

}
