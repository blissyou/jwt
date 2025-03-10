package org.example.auth.oauth2.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal) {

        if(principal != null){
            return "Hello,"+principal.getAttribute("name")+"!";
        }
        return "Hello Guest";
    }
}
