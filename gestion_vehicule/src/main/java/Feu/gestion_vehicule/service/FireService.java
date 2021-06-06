package Feu.gestion_vehicule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Feu.gestion_vehicule.model.Caserne;
import Feu.gestion_vehicule.model.VehiculeInIntervention;
import Feu.gestion_vehicule.service.dto.FireDto;
import Feu.gestion_vehicule.service.dto.FireTypeDto;
import Feu.gestion_vehicule.service.dto.VehicleDto;

@Service
public class FireService {

	List<VehiculeInIntervention> vehiculeList = new ArrayList<VehiculeInIntervention>();

	@Autowired
	VehiculeService vehiculeservice;

	@Autowired
	CaserneService caserneService;
	
	FireService(){
	}
	
	public void updateVehicleList() {
		VehicleDto[]  vehiculeList = vehiculeservice.getAllVehicules();
		List<VehiculeInIntervention> foundVehicle = new ArrayList<VehiculeInIntervention>();
		// detect new vehicle and deleted one
		for (int i = 0; i < vehiculeList.length; i++) {
			VehicleDto v = vehiculeList[i];
			VehiculeInIntervention foundVi = null;
			for (VehiculeInIntervention vi : this.vehiculeList) {
				if (vi.getVehiculeId() == v.getId()) {
					foundVi = vi;
					break;
				}
			}
			
			if (foundVi != null) {
				foundVehicle.add(foundVi);
			} else {
				//new vehicle get a caserne.
				Caserne c = this.getCaserneproche(v.getLon(), v.getLat());
				if (c !=null) {
					VehiculeInIntervention vi = new VehiculeInIntervention();
					vi.setVehiculeId(v.getId());
					vi.setCaserneId(c.getId());
					foundVehicle.add(vi);
				}
			}
		}

		this.vehiculeList = foundVehicle;
	}

	public FireDto[] getAllFire() {
		String allfireurl = "http://localhost:8081/fire";
		RestTemplate rescardTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> response = rescardTemplate.getForEntity(allfireurl, FireDto[].class);
		FireDto[] list = response.getBody();
		return list;
	}
	
	public List<VehiculeInIntervention> getVehiculeInInteventionList() {
		return this.vehiculeList;
	}
	
	public boolean isFireManaged(Integer fireId) {
		for (VehiculeInIntervention v : this.vehiculeList) {
			if (v.getFireId() == fireId) {
				return true;
			}
		}
		return false;
	}

	public void retourCaserne(Integer retviId) {
		for (VehiculeInIntervention vi : this.vehiculeList) {
			if (vi.getVehiculeId() == retviId) {
				vi.returnToCasern();
				Caserne caserne = caserneService.getCaserne(vi.getCaserneId());
				VehicleDto vehicule = vehiculeservice.getVehicule(vi.getVehiculeId());
				if (caserne != null && vehicule != null) {
			
					//update in simulator
					vehicule.setLat(caserne.getLat());
					vehicule.setLon(caserne.getLon());
					vehicule.setFuel(50);
					vehicule.setLiquidQuantity(50);
					vehiculeservice.save(vehicule);	
				}
				break;
			}
		}
	}

	public void intervention(FireDto fire) {
		VehicleDto vehicule = this.getVehicleForIntervention(fire);
		if (vehicule != null) {
			//update service vehicle state
			for (VehiculeInIntervention vi : this.vehiculeList) {
				if (vi.getVehiculeId() == vehicule.getId()) {
					vi.affectFire(fire.getId());
					break;
				}
			}		
			vehicule.setLat(fire.getLat());
			vehicule.setLon(fire.getLon());
			vehicule.setLiquidQuantity(vehicule.getLiquidQuantity() - 10);
			vehicule.setFuel(vehicule.getFuel() - 10);
			vehiculeservice.save(vehicule);
		}
	}

	private VehicleDto getVehicleForIntervention(FireDto fire) {
		//get a caserne
		Caserne caserne = getCaserneproche(fire.getLon(), fire.getLat());
		if (caserne != null) {
			//get all caserne vehicule.
			List<VehiculeInIntervention> listVehic = this.getVehiculeInCaserne(caserne.getId());
			//get a vehicule that isn't in intervention.
			for (VehiculeInIntervention vi : listVehic) {
				if (!vi.isInIntervention()) {
					VehicleDto vehicule = vehiculeservice.getVehicule(vi.getVehiculeId());
					if (vehicule.getLiquidQuantity() >= 0 && vehicule.getFuel() >= 0) {
						return vehicule;						
					}
				}
			}	
		}
		return null;
	}

	private Caserne getCaserneproche(double lon, double lat) {
		 List<Caserne> caserneList = caserneService.getAllCaserne();
		for (Caserne caserne : caserneList) {
			if (((caserne.getLon() + 1000 >= lon) || (caserne.getLon() - 1000 <= lon))
					&& ((caserne.getLat() + 1000 >= lat) || (caserne.getLat() - 1000 <= lat))) {
				return caserne;
			}
		}
		//return the first caserne found if no near
		if (caserneList.size() > 0) {
			return caserneList.get(0);
		}
		return null;
	}
	
	private  List<VehiculeInIntervention> getVehiculeInCaserne(Integer caserneId) {
		List<VehiculeInIntervention> vehicleInCaserneList = new ArrayList<VehiculeInIntervention>();
		for (VehiculeInIntervention vi : this.vehiculeList) {
			if (vi.getCaserneId() == caserneId) {
				vehicleInCaserneList.add(vi);
			}
		}
		return vehicleInCaserneList;
	}
}
