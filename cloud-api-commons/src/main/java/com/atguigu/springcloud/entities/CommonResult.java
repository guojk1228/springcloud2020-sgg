package com.atguigu.springcloud.entities;
//返回给前端通用的实体串
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//全参
@NoArgsConstructor//无参
public class CommonResult<T> { //泛型：如果装的payment 返回payment,装的order 返回order
    private Integer code;//200表示成功
    private String message;
    private T   data;

    public CommonResult(Integer code,String message){
    this(code,message,null);}//2个参数

}
