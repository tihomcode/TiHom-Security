package com.tihom.security.browser.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tihom.secutity.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 抽象的session失效处理器
 * @author TiHom
 */
public class AbstractSessionStrategy {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 跳转的url
	 */
	private String destinationUrl;

	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 跳转前是否创建新的session
	 */
	private boolean createNewSession = true;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public AbstractSessionStrategy(String invalidSessionUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = invalidSessionUrl;
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (createNewSession) {
			request.getSession();
		}

		String sourceUrl = request.getRequestURI();
		String targetUrl;

		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			targetUrl = destinationUrl+".html";
			logger.info("session失效,跳转到"+targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else{
			String message = "session已失效";
			if(isConcurrency()){
				message = message + "，有可能是并发登录导致的";
			}
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(message)));
		}
		
	}

	/**
	 * session失效是否是并发导致的
	 */
	protected boolean isConcurrency() {
		return false;
	}


	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}
	
}
