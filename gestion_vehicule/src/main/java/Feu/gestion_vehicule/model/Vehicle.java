package Feu.gestion_vehicule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VEHICLE")
public class Vehicle {
	
	public static final int CREW_MEMBER_START_VALUE=-1;
	@Id
	@GeneratedValue
	private Integer id;
	private double lon;
	private double lat;
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE", length = 30)
	private VehicleType type;
	private float efficiency; // need all crew member to reach full efficiency value between 0 and 10
	@Enumerated(EnumType.STRING)
	@Column(name="LIQUID_TYPE", length = 30)
	private LiquidType liquidType; // type of liquid effective to type of fire
	private float liquidQuantity; // total quantity of liquid
	private float liquidConsumption; // per second when use
	private float fuel;		// total quantity of fuel
	private float fuelConsumption; // per km
	private int crewMember;
	private int crewMemberCapacity;
	
	//for fire management
	private Integer facilityRefId; //Always set to a caserne
	private Integer fireId; //set to 0 for no fire. If not 0 the vehicule is affected to a fire.
	
	public Vehicle() {
		crewMember= CREW_MEMBER_START_VALUE;
		liquidType=LiquidType.ALL;
	}

	public Vehicle(int id,double lon, double lat,VehicleType type, float efficiency,
			LiquidType liquidType, float liquidQuantity, float liquidConsumption, float fuel,
			float fuelConsumption, int crewMember, Integer facilityRefID) {
		super();
		this.id=id;
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.efficiency = efficiency;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.liquidConsumption = liquidConsumption;
		this.fuel = fuel;
		this.fuelConsumption = fuelConsumption;
		this.crewMember = crewMember;
		this.crewMemberCapacity =type.getVehicleCrewCapacity();
		this.facilityRefId = facilityRefID;
		this.fireId = 0;
	}

	public void returnToCaserne() {
		this.fireId = 0;
	}
	
	public void intervention(Integer fireId) {
		this.fireId = fireId;
	}
	
	public boolean isInIntervention() {
		return this.fireId == 0;
	}
	
	public Integer getFacilityRefId() {
		return facilityRefId;
	}

	public void setFacilityRefId(Integer facilityRefId) {
		this.facilityRefId = facilityRefId;
	}

	public Integer getCaserneId() {
		return facilityRefId;
	}

	public Integer getFireId() {
		return fireId;
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

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	public LiquidType getLiquidType() {
		return liquidType;
	}

	public void setLiquidType(LiquidType liquidType) {
		this.liquidType = liquidType;
	}

	public float getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(float liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}

	public float getLiquidConsumption() {
		return liquidConsumption;
	}

	public void setLiquidConsumption(float liquidConsumption) {
		this.liquidConsumption = liquidConsumption;
	}

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}

	public float getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public int getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(int crewMember) {
		this.crewMember = crewMember;
	}

	public int getCrewMemberCapacity() {
		return crewMemberCapacity;
	}

	public void setCrewMemberCapacity(int crewMemberCapacity) {
		this.crewMemberCapacity = crewMemberCapacity;
	}

	public Integer getFacilityRefID() {
		return facilityRefId;
	}

	public void setFacilityRefID(Integer facilityRefID) {
		this.facilityRefId = facilityRefID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
