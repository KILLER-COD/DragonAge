package data;

import tiles.Tile;

public class Checkpoint {
	private Tile tile;
	private int xDirection,yDirection;
	
	public Checkpoint(Tile tile,int xDirection, int yDirection){
		this.tile= tile;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}

	public Tile getTile() {
		return tile;
	}

	public int getXDirection() {
		return xDirection;
	}

	public int getYDirection() {
		return yDirection;
	}

	@Override
	public String toString() {
		return "Checkpoint{" +
				"tile=" + tile +
				", xDirection=" + xDirection +
				", yDirection=" + yDirection +
				'}';
	}
}
