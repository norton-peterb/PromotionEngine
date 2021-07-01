package org.engine.promotion.comparator;

import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.SKUQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PromotionItemComparatorTest {
    private static final PromotionItemComparator COMPARATOR
            = new PromotionItemComparatorImpl();

    @ParameterizedTest
    @MethodSource("testArgs")
    public void test(PromotionItem itemA,PromotionItem itemB,Boolean expectedResult) {
        Assertions.assertEquals(expectedResult,
                COMPARATOR.compare(itemA,itemB));
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(promotionItemA(),promotionItemA(),true),
                Arguments.of(promotionItemA(),promotionItemB(),false),
                Arguments.of(promotionItemA(),promotionItemC(),true),
                Arguments.of(promotionItemB(),promotionItemC(),false),
                Arguments.of(null,null,true),
                Arguments.of(promotionItemA(),null,false),
                Arguments.of(null,promotionItemB(),false)
        );
    }

    private static PromotionItem promotionItemA() {
        PromotionItem promotionItemA = new PromotionItem();
        SKUQuantity skuQuantityA = new SKUQuantity();
        skuQuantityA.setQuantity(2);
        skuQuantityA.setSku("A");
        promotionItemA.getSkuQuantity().add(skuQuantityA);
        promotionItemA.setPrice(100);
        return promotionItemA;
    }

    private static PromotionItem promotionItemB() {
        PromotionItem promotionItemB = new PromotionItem();
        SKUQuantity skuQuantityB1 = new SKUQuantity();
        skuQuantityB1.setQuantity(1);
        skuQuantityB1.setSku("C");
        SKUQuantity skuQuantityB2 = new SKUQuantity();
        skuQuantityB2.setQuantity(1);
        skuQuantityB2.setSku("D");
        promotionItemB.getSkuQuantity().add(skuQuantityB1);
        promotionItemB.getSkuQuantity().add(skuQuantityB2);
        promotionItemB.setPrice(45);
        return promotionItemB;
    }

    private static PromotionItem promotionItemC() {
        PromotionItem promotionItemC = new PromotionItem();
        SKUQuantity skuQuantityC = new SKUQuantity();
        skuQuantityC.setQuantity(2);
        skuQuantityC.setSku("A");
        promotionItemC.getSkuQuantity().add(skuQuantityC);
        promotionItemC.setPrice(90);
        return promotionItemC;
    }
}
