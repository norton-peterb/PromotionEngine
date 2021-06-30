package org.engine.promotion.controller;

import org.engine.promotion.model.Order;
import org.engine.promotion.model.OrderTotal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromotionController {

    @PostMapping(path = "order",consumes = "application/json",produces = "application/json")
    public OrderTotal computeOrderTotal(Order order) {
        OrderTotal orderTotal = new OrderTotal();
        orderTotal.setTotal(100);
        return orderTotal;
    }
}
