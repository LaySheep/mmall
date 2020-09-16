package com.wy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String index(){
        return "redirect:/productCategory/list";
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
      return url;
    }
}
