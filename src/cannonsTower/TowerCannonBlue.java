package cannonsTower;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import bullets.ProjectileCannonball;
import data.Enemy;
import tiles.Tile;

public class TowerCannonBlue extends Tower {
	
	public TowerCannonBlue(TowerType type ,Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(type,startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target){
		super.projectiles.add(new ProjectileCannonball(super.type.projectileType,super.target,super.getX(),super.getY(), 32, 32));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
	
}
