package bullets;

import static gameStart.Options.*;
import static helpers.Methods.QuickLoad;

import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {

	CannonBall(QuickLoad("bullet"), START_DAMAGE_BLUE, START_SPEED_BLUE),
	IceVall(QuickLoad("cannonGunIce"), START_DAMAGE_ICE, START_SPEED_ICE);
	
	Texture textures;
	public int damage;
	float speed;
	
	ProjectileType(Texture textures, int damage, float speed){
		this.textures = textures;
		this.damage = damage;
		this.speed = speed;
	}

}
