package Feu.gestion_vehicule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Feu.gestion_vehicule.model.Vehicle;
import Feu.gestion_vehicule.repository.VehiculeRepository;

@Service
public class VehiculeService {
	@Autowired
	VehiculeRepository vehiculerepository;
	
	
	VehiculeService(){
		
	}
	
	public List<Vehicle> getAllVehicules() {
		List<Vehicle> result = new ArrayList<Vehicle>();
		vehiculerepository.findAll().forEach(result::add);
		return result;
	}
	public Vehicle getVehicule(Integer id) {
		Optional<Vehicle> hOpt = vehiculerepository.findById(id);
		if (hOpt.isPresent()) {
			return hOpt.get();
		}
		return null;
	}
	
	public List<Vehicle> getVehiculeListOnFire(Integer fireId) {
		return vehiculerepository.findByFireId(fireId);
	}
	
	public List<Vehicle> getVehiculeInCaserne(Integer carserneId) {
		return vehiculerepository.findByFacilityRefId(carserneId);
	}
	
	public void save(Vehicle h) {
		vehiculerepository.save(h);
	}

}
