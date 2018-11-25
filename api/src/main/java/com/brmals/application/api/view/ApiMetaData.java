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
public class ApiMetaData implements Serializable {
    private Boolean status;
    private String description;
}
