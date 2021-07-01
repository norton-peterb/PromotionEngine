package org.engine.promotion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PromotionEngineConfiguration {
    private final String promotionRulesFile;

    public PromotionEngineConfiguration(@Value("${promotion.rules.file}") String promotionRulesFile) {
        this.promotionRulesFile = promotionRulesFile;
    }

    public String getPromotionRulesFile() {
        return promotionRulesFile;
    }
}
