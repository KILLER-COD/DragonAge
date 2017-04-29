package data;

import static gameStart.Options.*;
import static helpers.Methods.*;

import java.util.concurrent.CopyOnWriteArrayList;

import UI.UI;
import gameStart.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cannonsTower.Tower;
import helpers.Clock;
import org.lwjgl.opengl.GL11;
import tiles.Tile;
import tiles.TileGrid;
import tiles.TileType;

public class Player {

    private TileGrid grid;
    private TileType[] types;
    private int index;
    private WaveManager waveManager;
    private static CopyOnWriteArrayList<Tower> towerList;
    private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower;
    private Tower tempTower;
    public static int Cash, Lives;



    public Player(TileGrid grid, WaveManager waveManager) {
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
        this.index = 0;
        this.waveManager = waveManager;
        this.towerList = new CopyOnWriteArrayList<Tower>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        this.holdingTower = false;
        this.tempTower = null;
        Cash = 0;
        Lives = 10;

    }

    // Check if player can afford tower, if so: charge player tower cost
    public static boolean modifyCash(int amount) {
        if (Cash + amount >= 0) {
            Cash += amount;
            GL11.glClear( GL11.GL_DEPTH_BUFFER_BIT);
            DrawQuadTex(QuickLoad("menuRightV2"), 1280, 0, 192, 960);
            Game.setupUI() ;
            Game.updateUI();


            return true;
        }
        return false;
    }

    public void setup() {
        Cash = CASH_START;
        Lives = LIVE_START;
    }

    public static void modifyLives(int amount) {
        Lives += amount;
    }

    public void update() {

        //Update holding tower
        if (holdingTower) {
            tempTower.setX(getMouseTile().getX());
            tempTower.setY(getMouseTile().getY());
            tempTower.draw();
        }

        for (Tower t : towerList) {
            t.update();
            t.draw();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemiesList());
        }

        //Handle Mouse Input
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
            placeTower();
        }

        if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
            //System.out.println("Right clicked");
        }

        leftMouseButtonDown = Mouse.isButtonDown(0);
        rightMouseButtonDown = Mouse.isButtonDown(1);


        //Handle Keybord Input
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
                Clock.Pause();
            }
        }
    }

    private void placeTower() {
        Tile currentTile = getMouseTile();
        if (holdingTower) {
            if (modifyCash(-tempTower.getCost()) && currentTile.getType() == TileType.Grass && !currentTile.getOcupaied()) {
                Game.maxTower++;
                currentTile.setOcupaied(true);
                towerList.add(tempTower);
                holdingTower = false;
                tempTower = null;
                System.out.println(Game.maxTower);
            }
        }
    }

    public void pickTower(Tower t) {
        tempTower = t;
        holdingTower = true;
    }

    private Tile getMouseTile() {
        return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
    }

    public static CopyOnWriteArrayList<Tower> getTowerList() {
        return towerList;
    }

}
