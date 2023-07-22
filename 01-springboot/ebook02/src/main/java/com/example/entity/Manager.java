package com.example.entity;

import lombok.Data;

@Data
public class Manager {
    private int managerId;
    private String managerName;
    private String managerPassword;
    private String managerState;
    private int managerPoint;

    public Manager(String managerName, String managerPassword) {
        this.managerName = managerName;
        this.managerPassword = managerPassword;
    }

}
