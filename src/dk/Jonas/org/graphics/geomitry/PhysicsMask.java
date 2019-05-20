package dk.Jonas.org.graphics.geomitry;

public enum PhysicsMask {
	REFRACT(0), REFLECT(1);

	private int mask;
	private int index;
	
	PhysicsMask(int index) {
		this.index = index;
		mask = 1 << index;
	}
	
	public int getMask() {
		return mask;
	}
	
	public boolean testMask(int mask) {
		return ((mask >> index) & 0b1) == 1;
	}
}
