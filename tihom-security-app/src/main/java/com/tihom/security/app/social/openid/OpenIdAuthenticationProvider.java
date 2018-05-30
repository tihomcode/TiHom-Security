package com.tihom.security.app.social.openid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * OpenId身份验证提供程序
 * @author TiHom
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;

    /**
     * 去数据库查数据的类
     */
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        Set<String> providerUserIds = new HashSet<>();
        //去传进来的token里面把providerId和openId拿出来
        providerUserIds.add((String) authenticationToken.getPrincipal());
        //去数据库查providerId和openId,能查出来就调loadUserByUserId方法
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);

        if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        //读出用户信息
        UserDetails user = userDetailsService.loadUserByUserId(userId);

        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //重新组装token返回回去
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}
