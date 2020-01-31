package com.orange.soamanager.gui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.orange.soamanager.gui.model.Composant;
import com.orange.soamanager.gui.model.ComposantDTO;



@Controller
public class ComposantControllerIHM {
	@Autowired
	private RestTemplate composantRestTemplate;
	
	@Qualifier
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
    	String result = composantRestTemplate.postForObject("http://localhost:9292/Composant", composant, String.class);
    	System.out.println("Result : " + result);
    	System.out.println(request.getParameter("name"));
    	System.out.println(request.getParameter("description"));
    	System.out.println(request.getParameter("nameServer"));
    	System.out.println(request.getParameter("IpServer"));
    	if (result.equals("OK")) {
    		return "TabListComposant";
    	}else {
    		return"redirect:Erreur.html";
    	}
	}
    	@PostMapping("/ModifierComp")
        public String modifComp (WebRequest request) {
        	Composant composant = new Composant();
        	composant.setNom(request.getParameter("name"));
        	composant.setDescription(request.getParameter("description"));
        	composant.setNomDuServeur(request.getParameter("nameServer"));
        	composant.setIpDuServeur(request.getParameter("IpServer"));
        	String result = composantRestTemplate.postForObject("http://localhost:9292/Composant/{id}", composant, String.class);
        	System.out.println("Result : " + result);
        	System.out.println(request.getParameter("name"));
        	System.out.println(request.getParameter("description"));
        	System.out.println(request.getParameter("nameServer"));
        	System.out.println(request.getParameter("IpServer"));
        	if (result.equals("OK")) {
        		return "TabListComposant";
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
       
        	for (String string : id) {
        		composantRestTemplate.delete("http://localhost:9292/Composant/"+string);
    		}
        	System.out.println(request.getParameter("name"));
        	System.out.println(request.getParameter("description"));
        	System.out.println(request.getParameter("nameServer"));
        	System.out.println(request.getParameter("IpServer"));
        	return "TabListComposant";
    	}
    	@GetMapping ("/Composant")
        public String ListComposant ( Model model) {
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	model.addAttribute("result", result);
            return "TabListComposant";    	
	}
    	@GetMapping ("/ComposantModif")
        public String ListComposantSave ( Model model) {
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	ComposantDTO composantdto = new ComposantDTO(result);
        	model.addAttribute("result", composantdto);
            return "TabListComposantModif";
    	}
    	@GetMapping ("/ComposantSupp")
        public String ListComposantSupp ( Model model) {
        	List<Composant> result = composantRestTemplate.getForObject("http://localhost:9292/Composant", List.class);
        	model.addAttribute("result", result);
            return "TabListComposantSupp";
    	}

}
