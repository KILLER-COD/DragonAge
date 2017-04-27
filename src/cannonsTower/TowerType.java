package cannonsTower;

import static helpers.Methods.QuickLoad;

import org.newdawn.slick.opengl.Texture;

import bullets.ProjectileType;

public enum TowerType {
	
	CannonBlue(new Texture[]{QuickLoad("cannonBaseBlue"),QuickLoad("cannonGunBlue")}, ProjectileType.CannonBall,30, 1000, 3, 15),
	
	CannonIce(new Texture[]{QuickLoad("cannonBaseIce"),QuickLoad("cannonGunIce")}, ProjectileType.IceVall ,30, 1000, 3, 20);

	
	public Texture[] textures ;
	public ProjectileType projectileType;
	public int damage,range, cost;
	public float firingSpeed;
	
	//Comments
	TowerType(Texture[] textures, ProjectileType projectileType, int damage,int range, float firingSpeed, int cost){
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
}
