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
import Feu.gestion_vehicule.model.VehiculeInIntervention;
import Feu.gestion_vehicule.service.CaserneService;
import Feu.gestion_vehicule.service.FireService;
import Feu.gestion_vehicule.service.VehiculeService;
import Feu.gestion_vehicule.service.dto.FireDto;
import Feu.gestion_vehicule.service.dto.VehicleDto;

@Component
public class FireMngrTask {
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	FireService fireService;
	
	@Autowired
	CaserneService caserneservice;

	// fire task management
	@Scheduled(fixedRate = 1000)
	public void manageFire() {
		//first update managed vehicle
		fireService.updateVehicleList();
		
		// get all fire with Rest call
		FireDto[] fireList = fireService.getAllFire();
		List<FireDto> newfireList = new ArrayList<FireDto>();
		List<VehiculeInIntervention>  vehiculeInInterList = fireService.getVehiculeInInteventionList();
		//List of currently managed fire
		List<Integer> currentFire = new ArrayList<Integer>();
		for (VehiculeInIntervention v : vehiculeInInterList) {
			currentFire.add(v.getFireId());
		}

		// detect new fire and ended fire
		for (int i = 0; i < fireList.length; i++) {
			FireDto fire = fireList[i];
			if (fireService.isFireManaged(fire.getId())) {
				// already managed fire
				currentFire.remove(i);
			} else {
				// new fire
				newfireList.add(fire);
			}
		}
		// for new fire affect a Vehicule
		for (FireDto fire : newfireList) {
			fireService.intervention(fire);
		}
		// for ended return vehicule to casern.
		for (Integer fireId : currentFire) {
			for (VehiculeInIntervention v : vehiculeInInterList) {
				if (v.getFireId() == fireId) {
					fireService.retourCaserne(v.getVehiculeId());
				}
			}
		}
	}


}
