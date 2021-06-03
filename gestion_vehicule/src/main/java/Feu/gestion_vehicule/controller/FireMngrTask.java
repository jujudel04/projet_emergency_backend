package Feu.gestion_vehicule.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;

import Feu.gestion_vehicule.model.Caserne;
import Feu.gestion_vehicule.model.Vehicle;
import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.service.VehiculeService;

@Component
public class FireMngrTask {
	@Autowired
	VehiculeService vehiculeservice;

	@Autowired
	CaserneService caserneservice;

	List<FireDto> fireList = new ArrayList<FireDto>();

	// fire task management
	@Scheduled(fixedRate = 1000)
	public void manageFire() {
		// get all fire with Rest call
		FireDto[] newList = getAllFire();
		List<FireDto> tempfireList = new ArrayList<FireDto>(this.fireList);
		List<FireDto> newfireList = new ArrayList<FireDto>();

		// detect new fire and ended fire
		for (int i = 0; i < tempfireList.size(); i++) {
			FireDto fire = tempfireList.get(i);
			if (tempfireList.contains(fire)) {
				// already managed fire
				tempfireList.remove(i);
			} else {
				// new fire
				newfireList.add(fire);
			}
		}
		// for new fire affect a Vehicule
		for (FireDto fire : newfireList) {
			intervention(fire);
		}
		// for ended return vehicule to casern.
		for (FireDto fire : tempfireList) {
			List<Vehicle> fireVehicules = vehiculeservice.getVehiculeListOnFire(fire.id);
			for (Vehicle v : fireVehicules) {
				retourCaserne(v, fire);
			}
		}

		this.fireList = new ArrayList<FireDto>(fireList);
	}

	public void intervention(FireDto fire) {
		Vehicle vehicule = getVehicleForIntervention(fire);
		if (vehicule != null) {
			vehicule.setLat(fire.getLat());
			vehicule.setLon(fire.getLon());
			vehicule.setLiquidQuantity(vehicule.getLiquidQuantity() - 10);
			vehicule.setFuel(vehicule.getFuel() - 10);
			vehiculeservice.save(vehicule);
		}
	}

	public Vehicle getVehicleForIntervention(FireDto fire) {
		Caserne caserne = getCaserneproche(fire);
		List<Vehicle> listVehic = vehiculeservice.getVehiculeInCaserne(caserne.getId());
		for (Vehicle vehicule : listVehic) {
			if (!vehicule.isInIntervention() && vehicule.getLiquidQuantity() >= 0 && vehicule.getFuel() >= 0) {
				return vehicule;
			}
		}
		return null;
	}

	public Caserne getCaserneproche(FireDto fire) {
		for (int i = 0; i < 5; i++) {
			Caserne caserne = caserneservice.getCaserne(i);
			if (((caserne.getLon() + 1000 >= fire.getLon()) || (caserne.getLon() - 1000 <= fire.getLon()))
					&& ((caserne.getLat() + 1000 >= fire.getLat()) || (caserne.getLat() - 1000 <= fire.getLat()))) {
				return caserne;
			}
		}
		return null;
	}

	public void retourCaserne(Vehicle vehicule, FireDto fire) {
		Caserne caserne = getCaserneproche(fire);
		vehicule.setLat(caserne.getLat());
		vehicule.setLon(caserne.getLon());
		vehicule.setFuel(50);
		vehicule.setLiquidQuantity(50);
		vehicule.returnToCaserne();
		vehiculeservice.save(vehicule);
	}

	private FireDto[] getAllFire() {
		String allfireurl = "http://localhost:8080/access/sim/fire";
		RestTemplate rescardTemplate = new RestTemplate();
		ResponseEntity<FireDto[]> response = rescardTemplate.getForEntity(allfireurl, FireDto[].class);
		FireDto[] list = response.getBody();
		return list;
	}

	private class FireDto {

		private Integer id;
		private String type;
		private float intensity;
		private float range;
		private double lon;
		private double lat;

		public FireDto() {
		}

		public FireDto(Integer id, String type, float intensity, float range, double lon, double lat) {
			super();
			this.id = id;
			this.type = type;
			this.intensity = intensity;
			this.range = range;
			this.lon = lon;
			this.lat = lat;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public float getIntensity() {
			return intensity;
		}

		public void setIntensity(float intensity) {
			this.intensity = intensity;
		}

		public float getRange() {
			return range;
		}

		public void setRange(float range) {
			this.range = range;
		}

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

	}

}
