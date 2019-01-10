package com.solstice.spring_assessment.dto;

import java.util.List;

public class UserDetails {

    private Long userId;
    private String fullName;
    private List<AccountInfo> accountInfoList;

    private UserDetails() {}

    public UserDetails(Long userId, String fullName, List<AccountInfo> accountInfoList) {
        this.userId = userId;
        this.fullName = fullName;
        this.accountInfoList = accountInfoList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<AccountInfo> getAccountInfoList() {
        return accountInfoList;
    }

    public void setAccountInfoList(List<AccountInfo> accountInfoList) {
        this.accountInfoList = accountInfoList;
    }

}
