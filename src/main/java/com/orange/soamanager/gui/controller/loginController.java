package com.orange.soamanager.gui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.orange.soamanager.gui.model.User;



@Controller
public class loginController {
	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	  
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String Login(WebRequest request) {
    	User user = new User();
    	user.setLogin(request.getParameter("username"));
    	user.setMotDePasse(request.getParameter("password"));
    	restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	String result = restTemplate.postForObject("http://localhost:9090/Utilisateur/Authentification?Authorization=Basic dXNlcjpwYXNz", user, String.class);
    	System.out.println("Result : " + result);
    	System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("password"));
    	if (result.equals("OK")) {
    		return "redirect:home.html";
    
    	}else {
    		System.out.println("Nom d'utilisateur ou mot de passe incorrect !");
    		return "redirect:index.html";
    	}
    }
}
