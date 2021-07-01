package org.engine.promotion.processor;

import org.engine.promotion.model.Order;
import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.SKUItem;

import java.util.List;

public interface PromotionProcessor {
    int calculateTotal(Order order, List<SKUItem> skuItemList) throws PromotionProcessorException;
    int calculateApplyPromotion(Order order, PromotionItem promotionItem,List<SKUItem> skuItemList)
        throws PromotionProcessorException;
}
