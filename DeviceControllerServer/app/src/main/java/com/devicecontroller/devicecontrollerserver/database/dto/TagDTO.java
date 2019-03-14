package com.devicecontroller.devicecontrollerserver.database.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TagDTO {

    @NonNull
    private Integer tag;
    @NonNull
    private String name;
    @NonNull
    private Integer status;

}
