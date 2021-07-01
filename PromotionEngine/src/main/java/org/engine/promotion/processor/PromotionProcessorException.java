package org.engine.promotion.processor;

public class PromotionProcessorException extends Exception {
    public PromotionProcessorException(String message) {
        super(message);
    }

    public PromotionProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
