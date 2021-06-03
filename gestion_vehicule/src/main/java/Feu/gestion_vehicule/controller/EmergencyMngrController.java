package Feu.gestion_vehicule.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.service.VehiculeService;
import Feu.gestion_vehicule.model.Caserne;
import Feu.gestion_vehicule.model.Vehicle;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmergencyMngrController {
	@Autowired
	VehiculeService vehiculeService;
	@Autowired
	CaserneService caserneService;

	@RequestMapping(method = RequestMethod.POST, value = "/vehicle")
	public void createVehicule(@RequestBody Vehicle vehicule) {
		// TODO
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/vehicle/{id}")
	public void updateVehicule(@PathVariable Integer vehiculeId, @RequestBody Vehicle vehicule) {
		VehicleDto vehicule = getVehicule(vehiculeId);
		//update
		//save
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicle")
	public List<Vehicle> getAllVehicule() {
		List<Integer> idList = vehiculeService.getVehiculeList();
		List<Vehicle> vehiculeList = new ArrayList<Vehicle>();
		for (Integer id : idList) {
			Vehicle vehicule = vehiculeService.getVehicule(id);
			if (vehicule != null) {
				vehiculeList.add(vehicule);
			}
			return vehiculeList;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicle/{id}")
	public Vehicle getVehicule(@PathVariable Integer vehiculeId) {
		Vehicle h = vehiculeService.getVehicule(vehiculeId);
		return h;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/vehicle/{id}")
	public void deleteVehicule(@PathVariable Integer vehiculeId, Caserne caserne) {
		caserne.removeVehicule(vehiculeId);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/caserne/{id}")
	public Caserne getCaserne(@PathVariable Integer caserneId) {
		Caserne h = caserneService.getCaserne(caserneId);
		return h;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/caserne")
	public List<Caserne> getAllCaserne() {
		List<Caserne> caserneList = caserneService.getAllCaserne();
		return caserneList;
	}



}