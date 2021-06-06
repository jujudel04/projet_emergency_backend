package Feu.gestion_vehicule.model;

public class VehiculeInIntervention {
	private Integer vehiculeId;
	private Integer caserneId;
	private Integer fireId; // if if not in intervention.

	public Integer getCaserneId() {
		return caserneId;
	}

	public void setCaserneId(Integer caserneId) {
		this.caserneId = caserneId;
	}

	public VehiculeInIntervention() {
		super();
	}

	public Integer getVehiculeId() {
		return vehiculeId;
	}

	public void setVehiculeId(Integer vehiculeId) {
		this.vehiculeId = vehiculeId;
	}

	public Integer getFireId() {
		return fireId;
	}

	public void affectFire(Integer fireId) {
		this.fireId = fireId;
	}

	public void returnToCasern() {
		this.fireId = null;
	}

	public boolean isInIntervention() {
		return this.fireId != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		VehiculeInIntervention vi = (VehiculeInIntervention) o;
		// field comparison
		return this.vehiculeId == vi.vehiculeId;
	}

	@Override
	public int hashCode() {
		return this.vehiculeId.hashCode();
	}
}
