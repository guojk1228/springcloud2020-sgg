package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.ws.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j//日志
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;//注意导包为import org.springframework.cloud.client.discovery.DiscoveryClient;

    //只传给前端CommonResult，不需要前端了解其他的组件
    @PostMapping(value = "/payment/create")//写操作，就用post请求
    public CommonResult create(@RequestBody Payment payment){
        int result=paymentService.create(payment);
        log.info("*****插入结果："+result);
        if(result > 0){
            return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")//读操作，就用get请求
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment=paymentService.getPaymentById(id);
        //log.info("*****插入结果："+ payment+"\t"+"hahahahahha");
        if(payment !=null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else{
           return new CommonResult(444,"没有对应记录，查询Id:"+id,null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public  Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element:services){
            log.info("****element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());

        }
        return this.discoveryClient;
    }
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //暂停几秒钟线程,故意测试为之
        try{ TimeUnit.SECONDS.sleep(3); }catch (Exception e){ e.printStackTrace(); }
        return serverPort;
    }
}
