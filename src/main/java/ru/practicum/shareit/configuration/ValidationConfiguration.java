package ru.practicum.shareit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.shareit.util.validation.FieldValidator;
import ru.practicum.shareit.util.validation.ParamValidator;
import ru.practicum.shareit.util.validation.annotation.NotEmpty;
import ru.practicum.shareit.util.validation.impl.AnnotationBasedParamValidatorImpl;
import ru.practicum.shareit.util.validation.impl.NotEmptyValidatorImpl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ValidationConfiguration {

    @Bean
    public ParamValidator getParamValidator() {
        Map<Class<? extends Annotation>, FieldValidator> validatorMap = new HashMap<>();
//        validatorMap.put(RegExp.class, new RegularExpressionValidatorImpl());
        validatorMap.put(NotEmpty.class, new NotEmptyValidatorImpl());
        return new AnnotationBasedParamValidatorImpl(validatorMap);
    }
}