package com.tihom.secutity.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author TiHom
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
