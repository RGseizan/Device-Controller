package com.devicecontroller.devicecontrollerserver.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ClientDTO {

    @NonNull
    private Integer tag;
    @NonNull
    private String name;
    @NonNull
    private String url_icon;
    @NonNull
    private String last_ip;
    @NonNull
    private Integer rights;

}
