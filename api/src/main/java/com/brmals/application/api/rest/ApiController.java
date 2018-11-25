package com.brmals.application.api.rest;

import com.brmals.application.api.view.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController("help")
public class ApiController {

    @Autowired
    private MessageSource messageSource;
    @GetMapping(value = "about",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ApiResponse> about(Locale locale) {
        return ResponseEntity.ok(ApiResponse.builder().data(messageSource.getMessage("app.name",null,locale)).build());
    }
}
