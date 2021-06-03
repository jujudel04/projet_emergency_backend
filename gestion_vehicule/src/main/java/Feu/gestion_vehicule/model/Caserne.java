package Feu.gestion_vehicule.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CASERNE")
public class Caserne {
	@Id
	@GeneratedValue
	private Integer id;
	private String vehicules;
	private double lon;
	private double lat;

	public Caserne() {
		super();
	}

	public Caserne(Integer id, String vehicules, double lat, double lon) {
		super();
		this.id = id;
		this.vehicules = vehicules;
		this.lat = lat;
		this.lon = lon;

	}

	public List<Integer> getVehiculeList() {
		if (this.vehicules.length() > 0) {
			List<String> list = Arrays.asList(this.vehicules.split(":"));

			return list.stream().map(Integer::parseInt).collect(Collectors.toList());
		} else {
			ArrayList<Integer> toto = new ArrayList<Integer>();
			return toto;
		}
	}

	public void addVehicule(Integer vehicule_id) {
		this.vehicules = this.vehicules + id + ":";
	}

	public void removeVehicule(Integer vehicule_id) {
		List<Integer> listc = this.getVehiculeList();
		listc.remove(vehicule_id);
		this.vehicules = "";
		for (Integer id : listc) {
			this.addVehicule(id);
		}
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicules() {
		return vehicules;
	}

	public void setVehicules(String vehicules) {
		this.vehicules = vehicules;
	}

	@Override
	public String toString() {
		return "Caserne [" + this.id + "]: vehicules_attribué:" + this.vehicules;
	}

}
