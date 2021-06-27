package com.ecommerce.web.jwt.response;

import com.ecommerce.web.exception.GeneralException;

public class JwtException extends GeneralException {

    public JwtException(JwtMessage errorMessage) {
        super(errorMessage.getDesc());
    }

    public JwtException(String errorMessage) {
        super(errorMessage);
    }
}
