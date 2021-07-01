package org.engine.promotion.processor;

import org.engine.promotion.model.Order;
import org.engine.promotion.model.OrderItem;
import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.SKUItem;
import org.engine.promotion.schema.SKUQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PromotionProcessorTest {

    private static final PromotionProcessor PROMOTION_PROCESSOR
            = new PromotionProcessorImpl();

    @ParameterizedTest
    @MethodSource("testArgsTotalCost")
    public void testTotalCost(String scenarioName,Order order,int expectedTotal) throws PromotionProcessorException {
        Assertions.assertEquals(expectedTotal,PROMOTION_PROCESSOR.calculateTotal(order,skuItemList()));
    }

    @ParameterizedTest
    @MethodSource("testArgsPromotionItem1")
    public void testTotalPromotionItem1(String scenarioName,Order order,int expectedTotal) throws PromotionProcessorException {
        Assertions.assertEquals(expectedTotal,
                PROMOTION_PROCESSOR.calculateApplyPromotion(order,promotionItem1(),skuItemList()));
    }

    @ParameterizedTest
    @MethodSource("testArgsPromotionItem2")
    public void testTotalPromotionItem2(String scenarioName,Order order,int expectedTotal) throws PromotionProcessorException {
        Assertions.assertEquals(expectedTotal,
                PROMOTION_PROCESSOR.calculateApplyPromotion(order,promotionItem2(),skuItemList()));
    }

    @ParameterizedTest
    @MethodSource("testArgsPromotionItem3")
    public void testTotalPromotionItem3(String scenarioName,Order order,int expectedTotal) throws PromotionProcessorException {
        Assertions.assertEquals(expectedTotal,
                PROMOTION_PROCESSOR.calculateApplyPromotion(order,promotionItem3(),skuItemList()));
    }

    private static Stream<Arguments> testArgsTotalCost() {
        return Stream.of(
                Arguments.of("ScenarioA",scenarioA(),100),
                Arguments.of("ScenarioB",scenarioB(),420),
                Arguments.of("ScenarioC",scenarioC(),335)
        );
    }

    private static Stream<Arguments> testArgsPromotionItem1() {
        return Stream.of(
                Arguments.of("ScenarioA",scenarioA(),100),
                Arguments.of("ScenarioB",scenarioB(),400),
                Arguments.of("ScenarioC",scenarioC(),315)
        );
    }

    private static Stream<Arguments> testArgsPromotionItem2() {
        return Stream.of(
                Arguments.of("ScenarioA",scenarioA(),100),
                Arguments.of("ScenarioB",scenarioB(),390),
                Arguments.of("ScenarioC",scenarioC(),305)
        );
    }

    private static Stream<Arguments> testArgsPromotionItem3() {
        return Stream.of(
                Arguments.of("ScenarioA",scenarioA(),100),
                Arguments.of("ScenarioB",scenarioB(),420),
                Arguments.of("ScenarioC",scenarioC(),330)
        );
    }

    private PromotionItem promotionItem1() {
        PromotionItem promotionItem = new PromotionItem();
        promotionItem.setPrice(130);
        SKUQuantity skuQuantity = new SKUQuantity();
        skuQuantity.setSku("A");
        skuQuantity.setQuantity(3);
        promotionItem.getSkuQuantity().add(skuQuantity);
        return promotionItem;
    }

    private PromotionItem promotionItem2() {
        PromotionItem promotionItem = new PromotionItem();
        promotionItem.setPrice(45);
        SKUQuantity skuQuantity = new SKUQuantity();
        skuQuantity.setSku("B");
        skuQuantity.setQuantity(2);
        promotionItem.getSkuQuantity().add(skuQuantity);
        return promotionItem;
    }

    private PromotionItem promotionItem3() {
        PromotionItem promotionItem = new PromotionItem();
        promotionItem.setPrice(30);
        SKUQuantity skuQuantity = new SKUQuantity();
        skuQuantity.setSku("C");
        skuQuantity.setQuantity(1);
        promotionItem.getSkuQuantity().add(skuQuantity);
        SKUQuantity skuQuantity1 = new SKUQuantity();
        skuQuantity1.setSku("D");
        skuQuantity1.setQuantity(1);
        promotionItem.getSkuQuantity().add(skuQuantity1);
        return promotionItem;
    }

    private List<SKUItem> skuItemList() {
        List<SKUItem> skuItemList = new ArrayList<>();
        SKUItem skuItemA = new SKUItem();
        skuItemA.setSku("A");
        skuItemA.setPrice(50);
        skuItemList.add(skuItemA);
        SKUItem skuItemB = new SKUItem();
        skuItemB.setSku("B");
        skuItemB.setPrice(30);
        skuItemList.add(skuItemB);
        SKUItem skuItemC = new SKUItem();
        skuItemC.setSku("C");
        skuItemC.setPrice(20);
        skuItemList.add(skuItemC);
        SKUItem skuItemD = new SKUItem();
        skuItemD.setSku("D");
        skuItemD.setPrice(15);
        skuItemList.add(skuItemD);
        return skuItemList;
    }

    private static Order scenarioA() {
        Order order = new Order();
        order.setItems(new ArrayList<>());
        OrderItem orderItemA = new OrderItem();
        orderItemA.setSku("A");
        orderItemA.setQuantity(1);
        order.getItems().add(orderItemA);
        OrderItem orderItemB = new OrderItem();
        orderItemB.setSku("B");
        orderItemB.setQuantity(1);
        order.getItems().add(orderItemB);
        OrderItem orderItemC = new OrderItem();
        orderItemC.setSku("C");
        orderItemC.setQuantity(1);
        order.getItems().add(orderItemC);
        return order;
    }

    private static Order scenarioB() {
        Order order = new Order();
        order.setItems(new ArrayList<>());
        OrderItem orderItemA = new OrderItem();
        orderItemA.setSku("A");
        orderItemA.setQuantity(5);
        order.getItems().add(orderItemA);
        OrderItem orderItemB = new OrderItem();
        orderItemB.setSku("B");
        orderItemB.setQuantity(5);
        order.getItems().add(orderItemB);
        OrderItem orderItemC = new OrderItem();
        orderItemC.setSku("C");
        orderItemC.setQuantity(1);
        order.getItems().add(orderItemC);
        return order;
    }

    private static Order scenarioC() {
        Order order = new Order();
        order.setItems(new ArrayList<>());
        OrderItem orderItemA = new OrderItem();
        orderItemA.setSku("A");
        orderItemA.setQuantity(3);
        order.getItems().add(orderItemA);
        OrderItem orderItemB = new OrderItem();
        orderItemB.setSku("B");
        orderItemB.setQuantity(5);
        order.getItems().add(orderItemB);
        OrderItem orderItemC = new OrderItem();
        orderItemC.setSku("C");
        orderItemC.setQuantity(1);
        order.getItems().add(orderItemC);
        OrderItem orderItemD = new OrderItem();
        orderItemD.setSku("D");
        orderItemD.setQuantity(1);
        order.getItems().add(orderItemD);
        return order;
    }
}
