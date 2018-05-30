package com.tihom.secutity.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author TiHom
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
