package org.engine.promotion.comparator;

import org.engine.promotion.schema.PromotionItem;

public interface PromotionItemComparator {
    boolean compare(PromotionItem itemA,PromotionItem itemB);
}
