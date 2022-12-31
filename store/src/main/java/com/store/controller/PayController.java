package com.store.controller;

import com.store.entity.AlipayBean;
import com.store.service.PaymentService;
import com.store.util.IdentityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pay")
public class PayController {
    private PaymentService service;
    public PayController(@Autowired PaymentService service){
        this.service = service;
    }
    @RequestMapping(path = "/ali",method = RequestMethod.POST)
    public String pay(@RequestBody AlipayBean payment){
        try {
            payment.setOut_trade_no(IdentityUtil.generateSequenceNo());
            String s = service.toAlipay(payment);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    @RequestMapping(path="/notify")
    public void notifyPayment(){
        //挂载到服务器上后此处进行验签和修改订单状态
    }
}
