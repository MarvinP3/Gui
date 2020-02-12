package com.orange.soamanager.gui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Qualifier("userRestTemplate")
	private RestTemplate userRestTemplate;
	
	@Bean
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
	@PostMapping("/Modifier/{id}")
    public String modif (@PathVariable("id") long id, @Valid User user, 
    		  BindingResult result, Model model, HttpServletResponse response) {
//    	User user = new User();
//    	user.setNom(request.getParameter("firstname"));
//    	user.setPrenom(request.getParameter("name"));
//    	user.setLogin(request.getParameter("username"));
//    	user.setMotDePasse(request.getParameter("password"));
//    	user.setFonction(request.getParameter("function"));
//    	user.setMail(request.getParameter("email"));
//    	System.out.println(request.getParameter("firstname"));
//    	System.out.println(request.getParameter("name"));
//    	System.out.println(request.getParameter("username"));
//    	System.out.println(request.getParameter("password"));
//    	System.out.println(request.getParameter("function"));
//    	System.out.println(request.getParameter("email"));
    	userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	Map<String, String> param = new HashMap<String, String>();
        param.put("id",""+user.getId());
        HttpEntity<User> requestEntity = new HttpEntity<User>(user);
        HttpEntity<String> responseForUserService = userRestTemplate.exchange("http://localhost:9090/Utilisateur/"+id, HttpMethod.PUT, requestEntity, String.class, param);
    	
    	//String resultat = userRestTemplate.pupostForObject("http://localhost:9290/Utilisateur/"+id, user, String.class);
    	System.out.println("Result : " + response);
    	System.out.println(responseForUserService.getBody());
    	
//    	System.out.println(request.getParameter("firstname"));
//    	System.out.println(request.getParameter("name"));
//    	System.out.println(request.getParameter("username"));
//    	System.out.println(request.getParameter("password"));
//    	System.out.println(request.getParameter("function"));
//    	System.out.println(request.getParameter("email"));
    	if (responseForUserService.getBody().equals(" Un utilisateur modifié")) {
    		return "Success.html";
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
    	return "Success.html";
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
        	model.addAttribute("result", result);
            return "TabListUserModif";
    	}
    	@GetMapping ("/LoadUserForModification")
        public String LoadUserForModification(WebRequest request, @RequestParam String id, Model model) {
    		System.out.println(id);
    		userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	User user = userRestTemplate.getForObject("http://localhost:9090/Utilisateur/"+id, User.class);
        	//UserDTO userdto = new UserDTO(result);
        	System.out.println(user.getNom());
        	model.addAttribute("user", user);
            return "FormulaireModification";
    	}
    	@GetMapping ("/UtilisateurSupp")
        public String ListUserSupp ( Model model) {
    		userRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<User> result = userRestTemplate.getForObject("http://localhost:9090/Utilisateur", List.class);
        	model.addAttribute("result", result);
            return "TabListUserSupp";
    	}
}
