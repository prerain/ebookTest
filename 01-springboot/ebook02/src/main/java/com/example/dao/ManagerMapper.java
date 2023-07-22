package com.example.dao;

//只写方法，不写方法体--就是dao层

import com.example.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository("ManagerMapper")
public interface ManagerMapper {

    List<Manager> findByManagername(@Param("managerName") String managerName);
    //查询所有管理员
    List<Manager> findAll();
    List<Manager> findBykeyword(@Param("keyword")String keyword);
}
