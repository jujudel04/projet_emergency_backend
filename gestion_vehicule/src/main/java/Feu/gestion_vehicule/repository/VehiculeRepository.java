package Feu.gestion_vehicule.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import Feu.gestion_vehicule.model.Vehicle;


public interface VehiculeRepository extends CrudRepository<Vehicle, Integer> {
	public List<Vehicle> findByFireId(Integer fireId);
	public List<Vehicle> findByFacilityRefId(Integer fireId);
}
