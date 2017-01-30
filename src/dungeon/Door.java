package dungeon;

class Door {

	boolean existence;
	String material;
	boolean locked;

	public Door() {
		this.existence = false;
	}

	public Door(String material, boolean locked) {
		this.existence = true;
		this.material = material;
		this.locked = locked;
	}
}