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
		vehiculeService.save(vehicule);
	}

	/*
	 * @RequestMapping(method = RequestMethod.PUT, value = "/vehicle/{id}") public
	 * void updateVehicule(@PathVariable Integer vehiculeId, @RequestBody Vehicle
	 * vehicule) { Vehicle vehicule = getVehicule(vehiculeId); // update // save }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/vehicle")
	public List<Vehicle> getAllVehicule() {
		List<Vehicle> vehiculeList = vehiculeService.getAllVehicules();
		return vehiculeList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicule/caserne/{caserneId}")
	public List<Vehicle> getAllVehiculeCaserne(@PathVariable Integer caserneId) {
		List<Vehicle> vehiculeList = vehiculeService.getAllVehicules();
		List<Vehicle> caserneVehiculeList = new ArrayList<Vehicle>();
		for (int i = 0; i < vehiculeList.size(); i++) {
			Vehicle vehicule = vehiculeList.get(i);
			if (vehicule.getFacilityRefID() == caserneId) {
				caserneVehiculeList.add(vehicule);

			}

		}
		return caserneVehiculeList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicle/{id}")
	public Vehicle getVehicule(@PathVariable Integer vehiculeId) {
		Vehicle h = vehiculeService.getVehicule(vehiculeId);
		return h;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/vehicle/{id}")
	public void deleteVehicule(@PathVariable Integer vehiculeId) {
		Vehicle h = vehiculeService.getVehicule(vehiculeId);
		vehiculeService.delete(h);
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