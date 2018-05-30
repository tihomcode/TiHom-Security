package com.tihom.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tihom.dto.User;
import com.tihom.security.app.social.AppSignUpUtils;
import com.tihom.secutity.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tihom
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/regist")
    public void regist(User user, HttpServletRequest request){
        //不管是注册用户还是绑定用户,都会拿到一个用户唯一标识
        String userId = user.getUsername();
//        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
        appSignUpUtils.doPostSignUp(new ServletWebRequest(request),userId);
        //省略了用户注册和绑定的增删改查
    }

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user/* Authentication authentication*/,HttpServletRequest request)
            throws Exception {
//        return SecurityContextHolder.getContext().getAuthentication();
        //Spring会自动去SecurityContext对象里去找
//        return authentication;
        //获取请求头里面Authentication的值
        String header = request.getHeader("Authentication");
        String token = StringUtils.substringAfter(header,"bearer ");
        //JWT的解析器,把jwtToken转化成了claims对象
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        String company = (String) claims.get("company");
        //要用日志工具输出,不要用sout
        System.out.println("-->"+company);
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors){

        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors){

        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(objectError -> {
//                FieldError fieldError = (FieldError) objectError;
//                String message = fieldError.getField() +" "+ objectError.getDefaultMessage();
                System.out.println(objectError.getDefaultMessage());
            }
            );
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation("用户查询服务")
    public List<User> query(@RequestParam String username){
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam("用户id") @PathVariable String id){
        System.out.println("进入getInfo服务");
        //返回对象或对象的集合spring会直接转换成json的字符串给前台
        User user = new User();
        user.setUsername("tom");
        return user;
    }



}
