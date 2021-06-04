package Feu.gestion_vehicule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Feu.gestion_vehicule.controller.FireMngrTask.FireDto;
import Feu.gestion_vehicule.model.Vehicle;
import Feu.gestion_vehicule.repository.VehiculeRepository;

@Service
public class VehiculeService {
	@Autowired
	VehiculeRepository vehiculerepository;
	
	
	VehiculeService(){
		
	}
	
	public Vehicle[] getAllVehicules() {
		String allVehiculeUrl="http://localhost:8081/vehicle";
		RestTemplate rescardTemplate = new RestTemplate();
		ResponseEntity<Vehicle[]> response = rescardTemplate.getForEntity(allVehiculeUrl, Vehicle[].class);
		Vehicle[] list = response.getBody();
		return list;		
	}
	public Vehicle getVehicule(Integer id) {
		String VehiculeUrl="http://localhost:8081/vehicle/{id}";
		RestTemplate rescardTemplate = new RestTemplate();
		ResponseEntity<Vehicle> response = rescardTemplate.getForEntity(VehiculeUrl, Vehicle.class);
		Vehicle vehicule=response.getBody();
		return vehicule;
		}

	
		/*
		 * public List<Vehicle> getVehiculeListOnFire(Integer fireId) { return
		 * vehiculerepository.findByFireId(fireId); }
		 * 
		 * public List<Vehicle> getVehiculeInCaserne(Integer carserneId) { return
		 * vehiculerepository.findByFacilityRefId(carserneId); }
		 */
	
	public void save(Vehicle h) {
		String VehiculeUrl="http://localhost:8081/vehicle/{id}";
		Map < String, Integer > params = new HashMap < String, Integer > ();
        params.put("id", h.getId());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(VehiculeUrl, h, params);
		
	}
	public void delete(Vehicle h) {
		String VehiculeUrl="http://localhost:8081/vehicle/{id}";
		RestTemplate restTemplate = new RestTemplate();
		String entityUrl = VehiculeUrl +h.getId();
		restTemplate.delete(entityUrl);
	}
}
