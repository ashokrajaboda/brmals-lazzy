package com.brmals.application.api.view;

public enum ApiMessageType {
    SUCCESS("Success"),WARNING("Warning"),ERROR("Error");
    String value;
    ApiMessageType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
