package com.brmals.application.api.view;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class ApiResponse<T> implements Serializable {
    private ApiMetaData metaData;
    private T data;
    private Collection<ApiMessage> messages;
}
