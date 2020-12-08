package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//dao层负责的不是读就是写（增删改查）
@Mapper
public interface PaymentDao {
    public int create(Payment payment);//写
    public Payment getPaymentById(@Param("id") Long id);//读
}
