package org.engine.promotion.controller;

import org.engine.promotion.model.Order;
import org.engine.promotion.model.OrderTotal;
import org.engine.promotion.processor.PromotionProcessor;
import org.engine.promotion.processor.PromotionProcessorException;
import org.engine.promotion.rules.PromotionRulesLoader;
import org.engine.promotion.rules.PromotionRulesLoaderException;
import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.PromotionRules;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class PromotionController {

    private final PromotionRulesLoader promotionRulesLoader;
    private final PromotionProcessor promotionProcessor;

    private PromotionRules promotionRules;

    public PromotionController(PromotionRulesLoader promotionRulesLoader, PromotionProcessor promotionProcessor) {
        this.promotionRulesLoader = promotionRulesLoader;
        this.promotionProcessor = promotionProcessor;
    }

    @PostConstruct
    public void init() throws PromotionRulesLoaderException {
        promotionRules = promotionRulesLoader.loadPromotionRules();
    }

    @PostMapping(path = "order",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public OrderTotal computeOrderTotal(@RequestBody Order order) {
        try {
            int total = promotionProcessor.calculateTotal(order, promotionRules.getSkuItem());
            for(PromotionItem promotionItem : promotionRules.getPromotionItem()) {
                int promTotal = promotionProcessor.calculateApplyPromotion(order,promotionItem,
                        promotionRules.getSkuItem());
                if(promTotal < total) {
                    total = promTotal;
                }
            }
            OrderTotal orderTotal = new OrderTotal();
            orderTotal.setTotal(total);
            return orderTotal;
        } catch (PromotionProcessorException exception) {
            throw new InternalServerException(exception.getMessage());
        }
    }
}
