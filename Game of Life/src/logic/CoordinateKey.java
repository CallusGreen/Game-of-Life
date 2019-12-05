package logic;

public class CoordinateKey {
	
	
	private final int x;
	private final int y;
	
	public CoordinateKey(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null) {
			return false;
		}
		
		if(getClass() != object.getClass()) {
			return false;
		}
		
		CoordinateKey compared = (CoordinateKey) object;
		
		if(this.x != compared.x || this.y != compared.y) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return 17 * this.x + this.y;
	}
}
