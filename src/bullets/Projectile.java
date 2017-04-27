package bullets;

import static helpers.Methods.*;
import static helpers.Clock.*;


import org.newdawn.slick.opengl.Texture;

import data.Enemy;
import data.Entity;

public abstract class Projectile extends Entity{
	private Texture texture;
	private float xVelocity,yVelocity;
	private int damage;
	private Enemy target;
	private boolean alive;

	public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height){
		this.texture =type.textures;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height =height;
		this.speed = type.speed;
		this.damage = type.damage; 
		this.target = target;
		this.alive = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}

	private void calculateDirection(){
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(target.getX() - x - TILE_SIZE / 4 + TILE_SIZE / 2);		
		float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
		float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
		xVelocity = xPercentOfMovement;
		yVelocity = totalAllowedMovement - xPercentOfMovement;

		if (target.getX() < x )
			xVelocity *= -1;
		if(target.getY() < y)
			yVelocity *= -1;

	}
	
	public void damage(){
		target.damage(damage);
		alive = false;
	}
	
	
	
	public void update(){
		if(alive){
			calculateDirection();
			x += xVelocity * speed * Delta();
			y += yVelocity * speed * Delta();
			if (CheckCollision(x, y, width, height, target.getX(), target.getY(),target.getWidth(), target.getHeight())){
				damage();
			}
			draw();
		}
	}

	public void draw(){
		DrawQuadTex(texture, x, y, 32, 32 );
	}

	
	
	public Enemy getTarget(){
		return target;
	}
	
	public void setAlive(boolean status){
		alive = status;
	}

	@Override
	protected void finalize() throws Throwable {
		texture.release();
	}
}
