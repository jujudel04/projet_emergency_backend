package Feu.gestion_vehicule.model;

public class VehiculeInIntervention {
	private Integer vehiculeId;
	private Integer caserneId;
	private Integer fireId; // if if not in intervention.
	private double latFinal;
	private double lonFinal;
	private double latInitial;
	private double lonInitial;
	private int currentStep;
	private int maxStep;
	private boolean inMove;
	private double lonStepDistance;
	private double latStepDistance;

	public Coord nextMove() {
		Coord coord = new Coord();
		currentStep += 1;
		if (currentStep > maxStep) {
			this.inMove = false;
			coord.lon = this.lonFinal;
			coord.lat = this.latFinal;
		} else {

			if (lonFinal > lonInitial) {
				coord.lon = lonInitial + currentStep * lonStepDistance;
			} else {
				coord.lon = lonInitial - currentStep * lonStepDistance;
			}
			if (latFinal > latInitial) {
				coord.lat = latInitial + currentStep * latStepDistance;
			} else {
				coord.lat = latInitial - currentStep * latStepDistance;
			}
		}
		System.out.println("vehicule:" + this.getVehiculeId() + " Coord:" + coord.lon + "/" + coord.lat);
		return coord;

	}

	public void setMove(double lonFinal, double latFinal, double lonInitial, double latInitial) {
		this.lonFinal = lonFinal;
		this.latFinal = latFinal;
		this.lonInitial = lonInitial;
		this.latInitial = latInitial;
		this.inMove = true;
		this.currentStep = 0;
		double distance = Math.sqrt(
				(latFinal - latInitial) * (latFinal - latInitial) + (lonFinal - lonInitial) * (lonFinal - lonInitial));
		this.maxStep = (int) Math.round(distance / 0.005);
		this.lonStepDistance = Math.abs(lonFinal - lonInitial) / maxStep;
		this.latStepDistance = Math.abs(latFinal - latInitial) / maxStep;
	}

	public boolean isInMove() {
		return this.inMove;
	}

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
		return this.fireId != null || this.inMove;
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

	public class Coord {
		public double lon;
		public double lat;

	}
}
