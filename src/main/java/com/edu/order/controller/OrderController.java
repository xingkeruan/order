package com.edu.order.controller;

import com.edu.order.domain.Order;
import com.edu.order.service.impl.OrderService;
import com.edu.order.vo.ResultVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @Value("${router.dish.url}")
    private String dishUrl;

    private Gson gson = new Gson();

    @PostMapping("/addDishToOrder/{dishid}")
    public ResultVO<Order> addDishToOrder(@PathVariable long dishid){
        String email="email";
        ResultVO<Order> resultVO =  new ResultVO<Order>();

        Order order =orderService.addDishToOrder(email,dishid);
        //add to order
        resultVO.setCode(0);
        resultVO.setData(order);
        return resultVO;
    }
    @PostMapping("/placeOrder")
    public ResultVO<Order> placeOrder(){
        String email="email";
        ResultVO<Order> resultVO =  new ResultVO<Order>();

        Order order  = orderService.getDraftOrderByEmail(email);
        if(order==null){
            resultVO.setCode(1);
            resultVO.setMsg("there is no dish ordered");
        }else{
            order.setStatus("SUCCESS");
            ResponseEntity<String> responseEntity = request(dishUrl,gson.toJson(order));
            ResultVO<Double> resultVO1= (ResultVO<Double>) this.JSONToObject(responseEntity.getBody(),ResultVO.class);

            orderService.updateOrder(order);
            resultVO.setCode(0);
            resultVO.setMsg("success");
        }
        resultVO.setData(order);
        return resultVO;
    }

    public static String beanToJSONString(Object bean) {
        return new Gson().toJson(bean);
    }
    public static Object JSONToObject(String json,Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
    }
    private ResponseEntity<String> request(String authUrl, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        return restTemplate.postForEntity(authUrl, formEntity, String.class);
    }
}
