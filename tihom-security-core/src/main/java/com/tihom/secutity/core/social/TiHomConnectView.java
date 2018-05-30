package com.tihom.secutity.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author TiHom
 */
public class TiHomConnectView extends AbstractView {
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
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        //将结果写成字符串返回
        if(model.get("connection")==null){
            httpServletResponse.getWriter().write("<h3>解绑成功</h3>");
        } else {
            //有connection字段证明是绑定操作
            httpServletResponse.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}
