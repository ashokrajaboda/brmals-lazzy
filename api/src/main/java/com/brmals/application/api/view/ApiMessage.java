package com.brmals.application.api.view;

import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class ApiMessage implements Serializable {
    private ApiMessageType messageType;
    private Integer code;
    private String message;
}
