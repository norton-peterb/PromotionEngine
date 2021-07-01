package org.engine.promotion.rules;

import org.engine.promotion.schema.PromotionRules;

public interface PromotionRulesLoader {
    PromotionRules loadPromotionRules() throws PromotionRulesLoaderException;
}
