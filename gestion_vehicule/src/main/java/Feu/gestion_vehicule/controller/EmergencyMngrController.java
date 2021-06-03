package Feu.gestion_vehicule.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.service.VehiculeService;
import Feu.gestion_vehicule.model.Caserne;
import Feu.gestion_vehicule.model.VehicleDto;


import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmergencyMngrController {
	@Autowired
	VehiculeService vehiculeService;
	@Autowired
	CaserneService caserneService;

	@RequestMapping(method = RequestMethod.POST, value = "/vehicle")
	public void addVehicule(@RequestBody Caserne caserne, Integer vehiculeid) {
		caserne.addVehicule(vehiculeid);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicle")
	public List<VehicleDto> getAllVehicule(@RequestBody Caserne caserne) {
		List<Integer> idList = caserne.getVehiculeList();
		List<VehicleDto> vehiculeList = new ArrayList<VehicleDto>();
		for (Integer id : idList) {
			VehicleDto vehicule = vehiculeService.getVehicule(id);
			if (vehicule != null) {
				vehiculeList.add(vehicule);
			}
			return vehiculeList;
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/vehicle/{id}")
	public VehicleDto getVehicule(@RequestBody Integer vehiculeId ){
		VehicleDto h=vehiculeService.getVehicule(vehiculeId);
		return h;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/vehicle/{id}")
	public void deleteVehicule(@RequestBody Integer vehiculeId,Caserne caserne ){
		caserne.removeVehicule(vehiculeId);
		
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/caserne/{id}")
	public Caserne getCaserne(@RequestBody Integer caserneId ){
		Caserne h=caserneService.getCaserne(caserneId);
		return h;
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/caserne")
	public List<Caserne> getAllCaserne(){
		List<Caserne> caserneList = caserneService.getAllCaserne();
			return caserneList;
	}
	/*
	 * @RequestMapping(method = RequestMethod.PUT, value = "/vehicle/{id}") public
	 * void changeAttribute(@RequestBody Integer vehiculeId) { VehicleDto
	 * vehicule=getVehicule(vehiculeId); }
	 */
	
	
	
	
	
}