package com.tihom.security.browser.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * @author TiHom
 */
public class TiHomInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public TiHomInvalidSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		onSessionInvalid(request, response);
	}

}
