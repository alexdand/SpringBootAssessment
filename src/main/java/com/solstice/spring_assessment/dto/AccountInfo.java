package com.solstice.spring_assessment.dto;

import java.math.BigDecimal;

public class AccountInfo {

    private Long accountId;
    private BigDecimal amount;
    private String accountType;

    private AccountInfo() {}

    public AccountInfo(Long accountId, BigDecimal amount, String accountType) {
        this.accountId = accountId;
        this.amount = amount;
        this.accountType = accountType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
