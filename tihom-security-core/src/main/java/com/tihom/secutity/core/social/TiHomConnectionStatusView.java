package com.tihom.secutity.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TiHom
 */
@Component("connect/status")
public class TiHomConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 渲染视图
     * @param model
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //获取connection信息即绑定信息
        Map<String,List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        //这个map告诉前台微信绑定了没,QQ绑定了没等等
        Map<String,Boolean> result = new HashMap<>();
        for(String key : connections.keySet()){
            result.put(key,CollectionUtils.isNotEmpty(connections.get(key)));
        }
        //返回的类型
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //将结果写成字符串返回
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
