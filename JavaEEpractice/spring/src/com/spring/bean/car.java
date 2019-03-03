package com.spring.bean;

import org.springframework.stereotype.Controller;

@Controller
public class car {
    private String color;
    private String name;

    public car(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public car() {
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
