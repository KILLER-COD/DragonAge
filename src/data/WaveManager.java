package data;

import static gameStart.Options.*;

public class WaveManager {

    private float timeBetweenEnemies;
    private int waveNumber, enemiesPerWave;
    private Enemy enemy;
    private static Wave currentWave;


    public WaveManager(Enemy enemy, float timeBetweenEnemies, int enemiesPerWave) {
        this.enemy = enemy;
        this.timeBetweenEnemies = timeBetweenEnemies;
        this.enemiesPerWave = enemiesPerWave;
        this.waveNumber = 0;

        this.currentWave = null;

        newWave();
    }

    public void update() {
        if (!currentWave.isComlated())
            currentWave.update();
        else
            newWave();
    }

    private void newWave() {
        currentWave = new Wave(enemy, timeBetweenEnemies, enemiesPerWave);
        waveNumber++;
        if (waveNumber % UPDATES_PER_LEVEL_COUNT == 0) {
            enemiesPerWave += ENEMY_NEW_PER_WAVE;
            enemy.setHealth(enemy.getHiddenHealth() + ENEMY_INCREASE_LIVE_SIZE);
            enemy.setSpeed(enemy.getSpeed() + ENEMY_UPDATE_SPEED);
        }
    }

    public static Wave getCurrentWave() {
        return currentWave;
    }

    public int getWaveNumber() {
        return waveNumber;
    }
}
