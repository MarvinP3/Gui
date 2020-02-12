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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.orange.soamanager.gui.model.Composant;
import com.orange.soamanager.gui.model.ComposantDTO;
import com.orange.soamanager.gui.model.User;



@Controller
public class ComposantControllerIHM {
	@Autowired
	@Qualifier("composantRestTemplate")
	private RestTemplate composantRestTemplate;
	
	
	@Bean
	public RestTemplate composantRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
    
	@RequestMapping(value="/AjouterComp", method=RequestMethod.POST)
    public String saveComp (WebRequest request) {
    	Composant composant = new Composant();
    	composant.setNom(request.getParameter("name"));
    	composant.setDescription(request.getParameter("description"));
    	composant.setNomDuServeur(request.getParameter("nameServer"));
    	composant.setIpDuServeur(request.getParameter("IpServer"));
    	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
    	String result = composantRestTemplate.postForObject("http://localhost:9292/Composant", composant, String.class);
    	System.out.println("Result : " + result);
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("description"));
    	System.out.println(request.getParameter("nameServer"));
    	System.out.println(request.getParameter("IpServer"));
    	if (result.equals("OK")) {
    		return "SuccessComposant.html";
    	}else {
    		return"redirect:Erreur.html";
    	}
	}
    	@PostMapping("/ModifierComp/{id}")
        public String modifComp (@PathVariable("id") long id, @Valid Composant composant, 
      		  BindingResult result, Model model, HttpServletResponse response) {
        	
        	
        	
        	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	Map<String, String> param = new HashMap<String, String>();
            param.put("id",""+composant.getId());
            HttpEntity<Composant> requestEntity = new HttpEntity<Composant>(composant);
            HttpEntity<String> responseForUserService = composantRestTemplate.exchange("http://localhost:9292/Composant/"+id, HttpMethod.PUT, requestEntity, String.class, param);
        	
        	//String resultat = userRestTemplate.pupostForObject("http://localhost:9290/Utilisateur/"+id, user, String.class);
        	System.out.println("Result : " + response);
        	System.out.println(responseForUserService.getBody());
        	
//        	System.out.println(request.getParameter("firstname"));
//        	System.out.println(request.getParameter("name"));
//        	System.out.println(request.getParameter("username"));
//        	System.out.println(request.getParameter("password"));
//        	System.out.println(request.getParameter("function"));
//        	System.out.println(request.getParameter("email"));
        	if (responseForUserService.getBody().equals(" Un composant modifié")) {
        		return "SuccessComposant.html";
        	}else {
        		return"redirect:Erreur.html";
        	}
        	}
        	
        	
        	
         	
    	@PostMapping("/SupprimerComp")
        public String suppComp (WebRequest request, @RequestParam List<String> id) {
        	Composant composant = new Composant();
        	//String id = request.getParameter("id");
        	System.out.println("ID : " + id);
        	composant.setNom(request.getParameter("name"));
        	composant.setDescription(request.getParameter("description"));
        	composant.setNomDuServeur(request.getParameter("nameServer"));
        	composant.setIpDuServeur(request.getParameter("IpServer"));
         	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	for (String string : id) {
        		composantRestTemplate.delete("http://localhost:9292/Composant/"+string);
    		}
        	System.out.println(request.getParameter("name"));
        	System.out.println(request.getParameter("description"));
        	System.out.println(request.getParameter("nameServer"));
        	System.out.println(request.getParameter("IpServer"));
        	return "SuccessComposant.html";
    	}
    	@GetMapping ("/Composant")
        public String ListComposant ( Model model) {
    	 	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	model.addAttribute("result", result);
            return "TabListComposant";    	
	}
    	@GetMapping ("/ComposantModif")
        public String ListComposantSave ( Model model) {
    	 	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	ComposantDTO composantdto = new ComposantDTO(result);
        	model.addAttribute("result", result);
            return "TabListComposantModif";
    	}
    	@GetMapping ("/ComposantSupp")
        public String ListComposantSupp ( Model model) {
    	 	composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	model.addAttribute("result", result);
            return "TabListComposantSupp";
    	}
    	@GetMapping ("/LoadComposantForModification")
        public String LoadUserForModification(WebRequest request, @RequestParam String id, Model model) {
    		System.out.println(id);
    		composantRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "pass"));
        	Composant composant = composantRestTemplate.getForObject("http://localhost:9292/Composant/"+id, Composant.class);
        	//UserDTO userdto = new UserDTO(result);
        	System.out.println(composant.getNom());
        	model.addAttribute("composant", composant);
            return "FormulaireModificationComp";
    	}
}
