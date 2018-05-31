package com.tihom.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tihom.secutity.core.properties.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * APP环境下认证成功处理器
 * @author TiHom
 */
@Component("tiHomAuthenticationSuccessHandler")
public class TiHomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");

        String header = httpServletRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        //抽取并且解码请求头里的字符串
        String[] tokens = this.extractAndDecodeHeader(header, httpServletRequest);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //通过clientId获取clientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails==null){
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在"+clientId);
        }else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配"+clientSecret);
        }

        //map是存储authentication内属性的,因为我们这里自带authentication,所以传空map即可
        TokenRequest tokenRequest = new TokenRequest
                (MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"custom");

        //clientDetails和tokenRequest合成OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //oAuth2Request和authentication合成OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        //拿认证去获取令牌
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //将authentication这个对象转成json格式的字符串
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(token));
    }

        private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
            byte[] base64Token = header.substring(6).getBytes("UTF-8");

            byte[] decoded;
            try {
                decoded = Base64.decode(base64Token);
            } catch (IllegalArgumentException var7) {
                throw new BadCredentialsException("Failed to decode basic authentication token");
            }

            String token = new String(decoded,"UTF-8");
            int delim = token.indexOf(":");
            if (delim == -1) {
                throw new BadCredentialsException("Invalid basic authentication token");
            } else {
                return new String[]{token.substring(0, delim), token.substring(delim + 1)};
            }
        }
}
