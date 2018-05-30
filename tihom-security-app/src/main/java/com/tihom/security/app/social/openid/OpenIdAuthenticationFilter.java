package com.tihom.security.app.social.openid;

import com.tihom.secutity.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TiHom
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    protected OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,"POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !httpServletRequest.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported:"+httpServletRequest.getMethod());
        }
        String openid = obtainOpenId(httpServletRequest);
        String providerId = obtainProviderId(httpServletRequest);

        if(openid==null){
            openid = "";
        }
        if(providerId==null){
            providerId = "";
        }

        openid = openid.trim();
        providerId = providerId.trim();

        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid,providerId);

        setDetails(httpServletRequest,authRequest);
        //交给AuthenticationManager校验
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取openId
     */
    protected String obtainOpenId(HttpServletRequest request){
        return request.getParameter(openIdParameter);
    }

    /**
     * 获取providerId
     */
    protected String obtainProviderId(HttpServletRequest request){
        return request.getParameter(providerIdParameter);
    }

    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getOpenIdParameter() {
        return openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }
}
