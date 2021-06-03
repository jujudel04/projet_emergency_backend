package Feu.gestion_vehicule.repository;

import org.springframework.data.repository.CrudRepository;

import Feu.gestion_vehicule.model.VehicleDto;


public interface VehiculeRepository extends CrudRepository<VehicleDto, Integer> {

}
