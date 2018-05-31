package com.tihom.security.rbac.repository.support;

import org.springframework.core.convert.converter.Converter;

/**
 * @author TiHom
 *
 */
public interface Domain2InfoConverter<T, I> extends Converter<T, I> {
	
}
