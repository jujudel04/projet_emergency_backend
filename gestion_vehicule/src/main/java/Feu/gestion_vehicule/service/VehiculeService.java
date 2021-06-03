package Feu.gestion_vehicule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Feu.gestion_vehicule.model.VehicleDto;
import Feu.gestion_vehicule.repository.VehiculeRepository;

@Service
public class VehiculeService {
	@Autowired
	VehiculeRepository vehiculerepository;
	
	
	VehiculeService(){
		
	}
	
	public List<VehicleDto> getAllVehicules() {
		List<VehicleDto> result = new ArrayList<VehicleDto>();
		vehiculerepository.findAll().forEach(result::add);
		return result;
	}
	public VehicleDto getVehicule(Integer id) {
		Optional<VehicleDto> hOpt = vehiculerepository.findById(id);
		if (hOpt.isPresent()) {
			return hOpt.get();
		}
		return null;
	}
	
	public void save(VehicleDto h) {
		vehiculerepository.save(h);
	}

}
