package org.engine.promotion.rules;

import org.engine.promotion.comparator.PromotionItemComparator;
import org.engine.promotion.comparator.PromotionItemComparatorImpl;
import org.engine.promotion.config.PromotionEngineConfiguration;
import org.engine.promotion.schema.PromotionRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PromotionRulesLoaderTest {

    private static final PromotionItemComparator COMPARATOR =
            new PromotionItemComparatorImpl();

    @ParameterizedTest
    @MethodSource("testArgs")
    public void test(String testFile,boolean exceptionThrown) throws PromotionRulesLoaderException {
        PromotionRulesLoader promotionRulesLoader = promotionRulesLoader(testFile);
        if(exceptionThrown) {
            Assertions.assertThrows(PromotionRulesLoaderException.class, promotionRulesLoader::loadPromotionRules);
        } else {
            PromotionRules promotionRules = promotionRulesLoader.loadPromotionRules();
            Assertions.assertNotNull(promotionRules);
        }
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("IAMMISSING",true),
                Arguments.of("src/test/resources/xml/InvalidXML.xml",true),
                Arguments.of("src/test/resources/xml/DuplicatePromotion.xml",true),
                Arguments.of("src/test/resources/xml/AnotherDupPromotion.xml",true),
                Arguments.of("src/test/resources/xml/ValidRules.xml",false)
        );
    }

    private static PromotionRulesLoader promotionRulesLoader(String xmlFile) {
        PromotionEngineConfiguration configuration = new PromotionEngineConfiguration(xmlFile);
        return new PromotionRulesLoaderImpl(COMPARATOR,configuration);
    }
}
