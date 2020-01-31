package com.orange.soamanager.gui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.orange.soamanager.gui.model.User;
import com.orange.soamanager.gui.model.UserDTO;

@Controller
public class UserControllerIHM {
	
	@Autowired
	private RestTemplate userRestTemplate;
	
	public RestTemplate userRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@RequestMapping(value="/Ajouter", method=RequestMethod.POST)
    public String save (WebRequest request) {
    	User user = new User();
    	user.setNom(request.getParameter("firstname"));
    	user.setPrenom(request.getParameter("name"));
    	user.setLogin(request.getParameter("username"));
    	user.setMotDePasse(request.getParameter("password"));
    	user.setFonction(request.getParameter("function"));
    	user.setMail(request.getParameter("email"));
    	userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	String result = userRestTemplate.postForObject("http://localhost:9090/Utilisateur", user, String.class);
    	System.out.println("Result : " + result);
    	System.out.println(request.getParameter("firstname"));
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("password"));
    	System.out.println(request.getParameter("function"));
    	System.out.println(request.getParameter("email"));
    	if (result.equals("OK")) {
    		return "TabListUser";
    	}else {
    		return"redirect:Erreur.html";
    	}
    	}
	@PostMapping("/Modifier")
    public String modif (WebRequest request) {
    	User user = new User();
    	user.setNom(request.getParameter("firstname"));
    	user.setPrenom(request.getParameter("name"));
    	user.setLogin(request.getParameter("username"));
    	user.setMotDePasse(request.getParameter("password"));
    	user.setFonction(request.getParameter("function"));
    	user.setMail(request.getParameter("email"));
    	System.out.println(request.getParameter("firstname"));
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("password"));
    	System.out.println(request.getParameter("function"));
    	System.out.println(request.getParameter("email"));
    	userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	String result = userRestTemplate.postForObject("http://localhost:9090/Utilisateur/{id}", user, String.class);
    	System.out.println("Result : " + result);
    	System.out.println(request.getParameter("firstname"));
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("password"));
    	System.out.println(request.getParameter("function"));
    	System.out.println(request.getParameter("email"));
    	if (result.equals("OK")) {
    		return "TabListUser";
    	}else {
    		return"redirect:Erreur.html";
    	}
    	}
	@PostMapping("/Supprimer")
    public String supp (WebRequest request, @RequestParam List<String> id) {
    	User user = new User();
    	//String id = request.getParameter("id");
    	System.out.println("ID : " + id);
    	user.setNom(request.getParameter("firstname"));
    	user.setPrenom(request.getParameter("name"));
    	user.setLogin(request.getParameter("username"));
    	user.setMotDePasse(request.getParameter("password"));
    	user.setFonction(request.getParameter("function"));
    	user.setMail(request.getParameter("email"));
    	userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	for (String string : id) {
    		userRestTemplate.delete("http://localhost:9090/Utilisateur/"+string);
		}
    	
    	//userRestTemplate.delete("http://localhost:9090/Utilisateur/"+id, user);
    	//System.out.println("Result : " + result);
    	System.out.println(request.getParameter("firstname"));
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("password"));
    	System.out.println(request.getParameter("function"));
    	System.out.println(request.getParameter("email"));
//    	if (result.equals("OK")) {
//    		return "TabListUser";
//    	}else {
//    		return"redirect:Erreur.html";
//    	}
    	return "TabListUser";
	}
	
//	 @RequestMapping(value = { "/", "/home2" })
//     public @ResponseBody ModelAndView home() {
//         System.out.println("sdasasas");
//         ModelAndView modelAndView = new ModelAndView();
//         modelAndView.setViewName("home");
//         return modelAndView;
//     }

    	@GetMapping ("/Utilisateur")
        public String ListUser ( Model model) {
    		userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<User> result = userRestTemplate.getForObject("http://localhost:9090/Utilisateur", List.class);
        	model.addAttribute("result", result);
            return "TabListUser";    	
	}
    	@GetMapping ("/UtilisateurModif")
        public String ListUserSave ( Model model) {
    		userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<User> result = userRestTemplate.getForObject("http://localhost:9090/Utilisateur", List.class);
        	UserDTO userdto = new UserDTO(result);
        	model.addAttribute("result", userdto);
            return "TabListUserModif";
    	}
    	@GetMapping ("/UtilisateurSupp")
        public String ListUserSupp ( Model model) {
    		userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<User> result = userRestTemplate.getForObject("http://localhost:9090/Utilisateur", List.class);
        	model.addAttribute("result", result);
            return "TabListUserSupp";
    	}
}
