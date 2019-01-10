package com.solstice.spring_assessment.helper;

import com.solstice.spring_assessment.model.AccountTypeEnum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Quotations {

    private static Map quotations = new HashMap<String, BigDecimal>();

    static {
        quotations.put("ARS", new BigDecimal("0.027"));
        quotations.put("USD", new BigDecimal("37"));
    }

    public static BigDecimal convertAmount(BigDecimal amount, AccountTypeEnum originType, AccountTypeEnum destinyType) {
        if (originType.name().equals(destinyType.name())) {
            return amount;
        }
        BigDecimal coefficient = (BigDecimal) quotations.get(originType.name());
        return amount.multiply(coefficient);
    }

}
