package com.paymentsystem.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class UsernameCsvBean {

    @CsvBindByName(column = "username")
    String username;

    @CsvBindByName(column = "password")
    String password;
}
