package com.paymentsystem.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class MerchantCsvBean extends UsernameCsvBean {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "status")
    private String status;
}
