package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component//这个注解是为springboot容器访问到的
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "---PaymentFallbackService fall back-paymentInfo_OK,55555";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "---PaymentFallbackService fall back-paymentInfo_TimeOut,55555";
    }
}
