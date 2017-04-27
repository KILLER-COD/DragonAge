package data;

public abstract class Entity {
	
	protected float x,y,speed;
	protected int width,height;
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	
	public void setHeight(int height) {
		this.height = height;			
	}
	
	public abstract void update();
	
	public abstract void draw();
	
}