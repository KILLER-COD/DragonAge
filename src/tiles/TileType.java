package tiles;

public enum TileType {
	Grass("green64", true), Dirt("dirt64", false), Water("blue64",false), NULL("blue64",false);
	
	String textureName;
	boolean buildable;
	
	TileType(String textureName,boolean buildable){
		this.textureName = textureName;
		this.buildable = buildable;
	}

}
