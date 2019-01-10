package com.solstice.spring_assessment.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cbu;
    private BigDecimal amount;
    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    public Account() {
    }

    public Account(Long id, String cbu, BigDecimal amount, AccountTypeEnum accountType) {
        this.id = id;
        this.cbu = cbu;
        this.amount = amount;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account1 = (Account) o;
        return Objects.equals(id, account1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cbu, amount, accountType);
    }

}
