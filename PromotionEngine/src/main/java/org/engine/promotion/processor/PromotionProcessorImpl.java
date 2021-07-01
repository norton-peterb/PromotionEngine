package org.engine.promotion.processor;

import org.engine.promotion.model.Order;
import org.engine.promotion.model.OrderItem;
import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.SKUItem;
import org.engine.promotion.schema.SKUQuantity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionProcessorImpl implements PromotionProcessor {

    private Map<String,Integer> skuPriceMap(List<SKUItem> skuItemList) {
        final Map<String,Integer> skuPriceMap = new HashMap<>();
        skuItemList.forEach(
                skuItem -> skuPriceMap.put(skuItem.getSku(),skuItem.getPrice())
        );
        return skuPriceMap;
    }

    private Map<String,Integer> orderQuantityMap(Order order) {
        final Map<String,Integer> orderQuantityMap = new HashMap<>();
        order.getItems().forEach(
                orderItem -> {
                    if(orderQuantityMap.containsKey(orderItem.getSku())) {
                        Integer quantity = orderQuantityMap.get(orderItem.getSku());
                        orderQuantityMap.put(orderItem.getSku(),quantity + orderItem.getQuantity());
                    } else {
                        orderQuantityMap.put(orderItem.getSku(),orderItem.getQuantity());
                    }
                }
        );
        return orderQuantityMap;
    }

    @Override
    public int calculateTotal(Order order, List<SKUItem> skuItemList) throws PromotionProcessorException {
        final Map<String,Integer> skuPriceMap = skuPriceMap(skuItemList);
        return calculateTotal(order,skuPriceMap);
    }

    private int calculateTotal(Order order,Map<String,Integer> skuPriceMap) throws PromotionProcessorException {
        int total = 0;
        for(OrderItem orderItem : order.getItems()) {
            Integer price = skuPriceMap.get(orderItem.getSku());
            if(price == null) {
                throw new PromotionProcessorException("Unrecognised SKU " + orderItem.getSku());
            }
            total += (orderItem.getQuantity() * price);
        }
        return total;
    }

    private int calculatePromotionItemNormalCost(PromotionItem promotionItem,Map<String,Integer> skuPriceMap)
            throws PromotionProcessorException {
        int cost = 0;
        for(SKUQuantity skuQuantity : promotionItem.getSkuQuantity()) {
            Integer skuCost = skuPriceMap.get(skuQuantity.getSku());
            if(skuCost == null) {
                throw new PromotionProcessorException("Promotion SKU " + skuQuantity.getSku() + " does not have a " +
                        "standard price");
            }
            cost += skuCost * skuQuantity.getQuantity();
        }
        return cost;
    }

    @Override
    public int calculateApplyPromotion(Order order, PromotionItem promotionItem, List<SKUItem> skuItemList)
            throws PromotionProcessorException {
        final Map<String,Integer> skuPriceMap = skuPriceMap(skuItemList);
        final Map<String,Integer> orderQuantityMap = orderQuantityMap(order);
        final int discount = calculatePromotionItemNormalCost(promotionItem,skuPriceMap)
                - promotionItem.getPrice();
        int appliedPromotions = -1;
        for(SKUQuantity skuQuantity : promotionItem.getSkuQuantity()) {
            Integer orderQuantity = orderQuantityMap.get(skuQuantity.getSku());
            if(orderQuantity != null) {
                int promotionTotal = orderQuantity / skuQuantity.getQuantity();
                if(appliedPromotions == -1 || appliedPromotions < promotionTotal) {
                    appliedPromotions = promotionTotal;
                }
            } else {
                appliedPromotions = 0;
            }
        }
        return calculateTotal(order,skuPriceMap) - (appliedPromotions * discount);
    }
}
