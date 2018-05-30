package com.tihom.service.impl;

import com.tihom.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author TiHom
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello"+name;
    }
}
