package cannonsTower;

import static helpers.Methods.DrawQuadTex;
import static helpers.Methods.DrawQuadTexRot;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import bullets.Projectile;
import data.Enemy;
import data.Entity;
import tiles.Tile;

public abstract class Tower extends Entity {

	private float timeSinceLastShot,firingSpeed,angle;
	private int damage, range, cost;;
	public Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean targeted;
	public CopyOnWriteArrayList<Projectile> projectiles;
	public TowerType type;

	public Tower(TowerType type, Tile startTile,CopyOnWriteArrayList<Enemy> enemies){
		this.type = type;
		this.textures =type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.cost = type.cost;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height =startTile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
	}

	private Enemy acuireTarget(){
		Enemy closeset = null;
		float closesetDistance = 10000;
		for (Enemy e: enemies){
			if(isInRange(e) && findDistance(e) < closesetDistance && e.getHiddenHealth() > 0){
				closesetDistance =  findDistance(e);
				closeset = e;
			}

		}
		if(closeset != null)
			targeted = true;
		return closeset;
	}

	private boolean isInRange(Enemy e){
		float xDistance = Math.abs(e.getX()- x);
		float yDistance = Math.abs(e.getY()- y);
		if(xDistance < range && yDistance < range)
			return true;
		return false;
	}

	private float findDistance(Enemy e){
		float xDistance = Math.abs(e.getX()- x);
		float yDistance = Math.abs(e.getY()- y);
		return xDistance + yDistance;
	}

	private float calculateAngle(){
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float)Math.toDegrees(angleTemp) - 90;
	}

	public abstract void shoot(Enemy target);

	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList){
		enemies = newList;
	}

	public void update(){
		if(!targeted || target.getHiddenHealth() < 0){
			target = acuireTarget();
		} else {
			angle = calculateAngle();
			if(timeSinceLastShot > firingSpeed){
				shoot(target);	
				timeSinceLastShot = 0;
			}
		}

		if(target == null || target.isAlive() == false){
			targeted = false;
		}

		timeSinceLastShot += Delta();


		for(Projectile p : projectiles ){
			p.update();
		}

		draw();
	}


	public void draw() {
		DrawQuadTex(textures[0], x, y, width, height);
		if(textures.length > 1)
			for (int i = 1; i < textures.length; i++) {
				DrawQuadTexRot(textures[i], x, y, width, height,angle);			
			}
	}

	public Enemy getTatrget(){
		return target;
	}
	
	public int getCost(){
		return cost;
	}
}
