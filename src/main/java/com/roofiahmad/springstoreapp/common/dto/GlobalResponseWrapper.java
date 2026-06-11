package com.roofiahmad.springstoreapp.common.dto;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalResponseWrapper implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object>{

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (ApiResponseWrapper.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }

        if (Resource.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {


        String path = request.getURI().getPath();

        if (body instanceof ApiResponseWrapper ||  body == null || path.startsWith("/files") || path.startsWith("/actuator")  ) {
            return body;
        }


        return new ApiResponseWrapper<>(true, "Operation completed successfully.", body);
    }
}