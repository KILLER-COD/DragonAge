package data;

import static helpers.Methods.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;


public class Wave {
	private float timeSinceLastSwapn,swapnTime;
	private Enemy enemyType;
	private static CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;	

	public Wave (Enemy enemyType, float swapnTime, int enemiesPerWave){
		this.enemyType = enemyType;
		this.swapnTime = swapnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSwapn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted =false;
		
		swapn();

	}

	public void update(){
		boolean allEnemiesDead = true;
		if(enemiesSpawned < enemiesPerWave){
			timeSinceLastSwapn += Delta();
			if (timeSinceLastSwapn > swapnTime){
				swapn();
				timeSinceLastSwapn = 0;
			}
		}
		for (Enemy e: enemyList){
			if(e.isAlive()){
				allEnemiesDead = false;
				e.update();
				e.draw();
			} else {
				enemyList.remove(e);
			}
		}
		
		if (allEnemiesDead)
			waveCompleted = true;
	}
	
	public void swapn(){
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(),enemyType.getTileGrid(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(), enemyType.getHealth()));
		enemiesSpawned++;
	}
	
	public boolean isComlated(){
		return waveCompleted;
	}


	public static CopyOnWriteArrayList<Enemy> getEnemiesList(){
		return enemyList;
	}

}
