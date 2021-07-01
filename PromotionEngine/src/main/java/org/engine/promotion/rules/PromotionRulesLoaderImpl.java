package org.engine.promotion.rules;

import org.engine.promotion.comparator.PromotionItemComparator;
import org.engine.promotion.config.PromotionEngineConfiguration;
import org.engine.promotion.schema.PromotionItem;
import org.engine.promotion.schema.PromotionRules;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class PromotionRulesLoaderImpl implements PromotionRulesLoader {

    private final PromotionItemComparator promotionItemComparator;
    private final PromotionEngineConfiguration configuration;

    public PromotionRulesLoaderImpl(PromotionItemComparator promotionItemComparator, PromotionEngineConfiguration configuration) {
        this.promotionItemComparator = promotionItemComparator;
        this.configuration = configuration;
    }

    private void validatePromotionRules() throws IOException, SAXException {
        try (InputStream inputStream = getClass().getResourceAsStream("/PromotionEngine.xsd")) {
            SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
            Schema schema = schemaFactory.newSchema(new StreamSource(inputStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(configuration.getPromotionRulesFile())));
        }
    }

    private PromotionRules unmarshallPromotionRules() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PromotionRules.class);
        return (PromotionRules) jaxbContext.createUnmarshaller().unmarshal(
                new File(configuration.getPromotionRulesFile()));
    }

    private boolean validatePromotionItems(PromotionRules promotionRules) {
        for(PromotionItem promotionItemA : promotionRules.getPromotionItem()) {
            for(PromotionItem promotionItemB : promotionRules.getPromotionItem()) {
                if(promotionItemA != promotionItemB) {
                    if(promotionItemComparator.compare(promotionItemA,promotionItemB)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public PromotionRules loadPromotionRules() throws PromotionRulesLoaderException {
        try {
            validatePromotionRules();
            PromotionRules promotionRules = unmarshallPromotionRules();
            if(!validatePromotionItems(promotionRules)) {
                throw new PromotionRulesLoaderException(
                        "Promotion Rules contain duplicate Promotion with different price");
            }
            return promotionRules;
        } catch (IOException exception) {
            throw new PromotionRulesLoaderException("IOException",exception);
        } catch (SAXException exception) {
            throw new PromotionRulesLoaderException("SAXException",exception);
        } catch (JAXBException exception) {
            throw new PromotionRulesLoaderException("JAXBException",exception);
        }
    }
}
