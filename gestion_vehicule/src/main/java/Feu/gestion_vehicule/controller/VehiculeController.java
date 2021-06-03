package Feu.gestion_vehicule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;

import Feu.gestion_vehicule.model.Caserne;
import Feu.gestion_vehicule.model.VehicleDto;
import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.service.VehiculeService;

@Controller
public class VehiculeController {
	@Autowired
	VehiculeService vehiculeservice;

	@Autowired
	CaserneService caserneservice;


	public VehicleDto getVehicleForIntervention(Integer fireId) {
		Caserne caserne = getCaserneproche(fireId);
		List<Integer> listVehicId = caserne.getVehiculeList();
		for (int i = 0; i < listVehicId.size(); i++) {
			Integer vehicId = listVehicId.get(i);
			VehicleDto vehicule = vehiculeservice.getVehicule(vehicId);
			if ((vehicule.getLiquidQuantity() >= 0) && (vehicule.getFuel() >= 0)) {
				return vehicule;
			} else {
				retourCaserne(vehicule);
			}

		}
		return null;
	}

	public Caserne getCaserneproche(@RequestBody Integer fireId) {
		FireDto fire = getFire(fireId);
		for (int i = 0; i < 5; i++) {
			Caserne caserne = caserneservice.getCaserne(i);
			if (((caserne.getLon() + 1000 >= fire.getLon()) || (caserne.getLon() - 1000 <= fire.getLon()))
					&& ((caserne.getLat() + 1000 >= fire.getLat()) || (caserne.getLat() - 1000 <= fire.getLat()))) {
				return caserne;
			}
		}
		return null;
	}

	public void retourCaserne(VehicleDto vehicule) {
		Caserne caserne = getCaserneproche(vehicule.getId());
		vehicule.setLat(caserne.getLat());
		vehicule.setLon(caserne.getLon());
		vehicule.setFuel(50);
		vehicule.setLiquidQuantity(50);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/vehicle/{id}")
	public void intervention(@RequestBody Integer fireId) {
		VehicleDto vehicule = getVehicleForIntervention(fireId);
		FireDto fire = getFire(fireId);
		vehicule.setLat(fire.getLat());
		vehicule.setLon(fire.getLon());
		vehicule.setLiquidQuantity(vehicule.getLiquidQuantity() - 10);
		vehicule.setFuel(vehicule.getFuel() - 10);

	}

	private FireDto getFire(Integer fireId) {
		String fireurl = "http://localhost:8081/fire";
		RestTemplate rescardTemplate = new RestTemplate();
		FireDto fire = rescardTemplate.getForObject(fireurl, FireDto.class, fireId);
		return fire;
	}

	public class FireDto {

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
