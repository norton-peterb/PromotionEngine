package org.engine.promotion.comparator;

import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.SKUQuantity;

import java.util.List;

public class PromotionItemComparatorImpl implements PromotionItemComparator {

    private boolean compareQuantities(SKUQuantity skuQuantity, List<SKUQuantity> quantityList) {
        for(SKUQuantity skuQuantityB : quantityList) {
            if(skuQuantityB.getQuantity().equals(skuQuantity.getQuantity())
                && skuQuantityB.getSku().equals(skuQuantity.getSku())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean compare(PromotionItem itemA, PromotionItem itemB) {
        if(itemA == null && itemB == null) {
            return true;
        } else if(itemA == null || itemB == null) {
            return false;
        }
        if(!(itemA.getPrice().equals(itemB.getPrice()))) {
            return false;
        }
        for(SKUQuantity skuQuantityA : itemA.getSkuQuantity()) {
            if(!(compareQuantities(skuQuantityA,itemB.getSkuQuantity()))) {
                return false;
            }
        }
        return true;
    }
}
