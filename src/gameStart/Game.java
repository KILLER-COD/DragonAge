package gameStart;

import static gameStart.Options.*;
import static helpers.Methods.*;

import data.Wave;
import helpers.Leveler;
import org.lwjgl.input.Mouse;

import UI.UI;
import UI.UI.Menu;
import cannonsTower.TowerCannonBlue;
import cannonsTower.TowerCannonIce;
import cannonsTower.TowerType;
import data.Enemy;
import data.Player;
import data.WaveManager;
import org.lwjgl.opengl.Display;
import tiles.TileGrid;
import javax.swing.*;


public class Game {

    private static TileGrid grid;
    private static Player player;
    private static WaveManager waveManager;
    public static UI gameUi;
    private static UI.UI_String gameUIString;
    private static Menu towerPickerMenu;
    public static int maxTower = 0;

    public Game(TileGrid grid) {
        this.grid = grid;
        waveManager = new WaveManager(new Enemy(QuickLoad("UFO64"), grid.getTile(1, 0), grid, TILE_SIZE, TILE_SIZE, ENEMY_START_SPEED, ENEMY_START_LIVE),
                2, ENEMY_START_COUNT);

        player = new Player(grid, waveManager);
        player.setup();
        DrawQuadTex(QuickLoad("menuRightV2"), 1280, 0, 192, 960);
        setupUI();

    }

    public static void setupUI() {
        gameUi = new UI();

        gameUi.creatMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);
        towerPickerMenu = gameUi.getMenu("TowerPicker");
        towerPickerMenu.quickAdd("BlueCannon", "cannonBaseBlue");
        towerPickerMenu.quickAdd("IceCannon", "cannonBaseIce");

        gameUIString = new UI.UI_String();

        gameUIString.drawString(1290, 200, "Max Towers - 10");
        gameUIString.drawString(1290, 300, "Tower Cash Info ");
        gameUIString.drawString(1300, 350, "Blue Tower - 15");
        gameUIString.drawString(1300, 400, "Ice  Tower - 20");

        gameUi.draw();

        updateUI();



    }
    public static void updateUI1() {
        if (Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);

            if (mouseClicked && maxTower < 11) {
                if (towerPickerMenu.isButtonClicked("BlueCannon")) {
                    player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemiesList()));
                    //maxTower++;

                }
                if (towerPickerMenu.isButtonClicked("IceCannon")) {
                    player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemiesList()));
                    //maxTower++;
                }

            }
        }
    }

    public static void updateUI() {

        gameUIString.drawString(1310, 600, "Lives " + Player.Lives);
        gameUIString.drawString(1310, 700, "Cash " + Player.Cash);
        gameUIString.drawString(1310, 500, "Wave " + waveManager.getWaveNumber());


    }

    public void update() {

        grid.Draw();
        waveManager.update();
        player.update();
        updateUI1();

        if (Player.Lives == 0){
            Boot.restart();
        }

        if(waveManager.getWaveNumber() > 10){

            if (Win.nextLvL ==  6 ){
                Display.destroy();;
            }

            maxTower = 0;
            StateManager.game = null;
            StateManager.mainMenu = null;
            StateManager.gameState = StateManager.GameState.WIN;        }
    }
}
