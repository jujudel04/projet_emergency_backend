package Feu.gestion_vehicule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.model.Caserne;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CaserneController {

	@Autowired
	CaserneService caserneService;

	@RequestMapping(method = RequestMethod.GET, value = "/caserne/{id}")
	public Caserne getCaserne(@PathVariable Integer caserneId) {
		Caserne h = caserneService.getCaserne(caserneId);
		return h;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/caserne")
	public List<Caserne> getAllCaserne() {
		List<Caserne> caserneList = caserneService.getAllCaserne();
		return caserneList;
	}

}