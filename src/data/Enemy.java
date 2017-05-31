package data;

import static data.Player.modifyLives;
import static helpers.Methods.*;
import static helpers.Clock.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

import gameStart.Game;
import gameStart.MainMenu;
import org.newdawn.slick.opengl.Texture;

import tiles.Tile;
import tiles.TileGrid;

public class Enemy extends Entity {
    private int currentCheckpoint;
    private float health, startHealth, hiddenHealth;
    private Texture texture, healthBackground, healthForegrounf, healthBorder;
    private Tile startTile;
    private boolean first = true, alive = true;
    private TileGrid grid;

    private ArrayList<Checkpoint> checkpoints;
    private int[] directions;

    public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health) {
        this.texture = texture;
        this.healthBackground = QuickLoad("healthBackground");
        this.healthForegrounf = QuickLoad("healthForegrounf");
        this.healthBorder = QuickLoad("healthBorder");
        this.startTile = startTile;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.health = health;
        this.startHealth = health;
        this.hiddenHealth = health;
        this.grid = grid;
        this.checkpoints = new ArrayList<>();
        this.directions = new int[2];
        //X direction
        this.directions[0] = 0;
        //Y direction
        this.directions[1] = 0;
        directions = findNextD(startTile);
        this.currentCheckpoint = 0;
        populateCheckpointList();
    }

    public void update() {
        if (first) {
            first = false;
        } else {
            if (CheckpointReached()) {
                if (currentCheckpoint + 1 == checkpoints.size())
                    endOfMazeReached();
                else
                    currentCheckpoint++;
            } else {
                x += Delta() * checkpoints.get(currentCheckpoint).getXDirection() * speed;
                y += Delta() * checkpoints.get(currentCheckpoint).getYDirection() * speed;
            }
        }

    }

    private void endOfMazeReached() {
        modifyLives(-1);
        die();

    }

    private boolean CheckpointReached() {
        boolean reached = false;

        Tile t = checkpoints.get(currentCheckpoint).getTile();
        //Check if position reached tile width variance of 3 (arbittrary)
        if (x > t.getX() - 10 &&
                x < t.getX() + 10 &&
                y > t.getY() - 10 &&
                y < t.getY() + 10) {

            reached = true;
            x = t.getX();
            y = t.getY();

        }

        return reached;
    }

    public void populateCheckpointList() {
        checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));

        int counter = 0;
        boolean cont = true;
        while (cont) {
            int[] currentD = findNextD(checkpoints.get(counter).getTile());
            //Check if a next direction/checkpoint exists, end after 20 checkpoint (arbitrary)
            if (currentD[0] == 2 || counter == 20) {
                cont = false;
            } else {
                checkpoints.add(findNextC(checkpoints.get(counter).getTile(),
                        directions = findNextD(checkpoints.get(counter).getTile())));
            }
            counter++;
        }
    }

    private Checkpoint findNextC(Tile s, int[] dir) {
        Tile next = null;
        Checkpoint c = null;

        //Boolean to decide if next checkpoint is found
        boolean found = false;
        int counter = 1;


        //Integer to increment each loop
        while (!found) {
            if (s.getXPlace() + dir[0] * counter == grid.getTileWide() ||
                    s.getYPlace() + dir[1] * counter == grid.getTileHigh() ||
                    s.getType() != grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {

                found = true;
                // Move counter back 1 to find tile before tileType
                counter -= 1;
                next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
            }

            counter++;
        }
        c = new Checkpoint(next, dir[0], dir[1]);
        return c;
    }

    private int[] findNextD(Tile s) {


        int[] randDir = new int[4];
        int[] dir = new int[2];

        int count = 0;

        int[][] opt = {
                {0,-1,0},
                {1,0,1},
                {0,1,2},
                {-1,0,3}
        };

        String[] optName = {"UP","Right","Down", "Left"};

        //directions[0] left right
        //directions[1] up down

        Tile up = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
        Tile right = grid.getTile(s.getXPlace() + 1, s.getYPlace());
        Tile down = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
        Tile left = grid.getTile(s.getXPlace() - 1, s.getYPlace());

/* ka ardyoq mekic aveli chanaparh*/
        if (s.getType() == up.getType() && directions[1] != 1) {

            randDir[0] = 1;
        }

        if (s.getType() == right.getType() && directions[0] != -1) {

            randDir[1] = 1;
        }

        if (s.getType() == down.getType() && directions[1] != -1) {

            randDir[2] = 1;
        }

        if (s.getType() == left.getType() && directions[0] != 1) {

            randDir[3] = 1;

        }
/*ka ardyoq  mekic aveli chanaparh*/


        for (int i = 0; i <= randDir.length-1 ;i++ ) {
            if( randDir[i] == 1)
                count++;
        }


        if( count >  1 ) {

            int Low = 0;
            int High = 3;
            int h = 0;

            for (int i = 0; i <= randDir.length -1;i++ ) {
                if (randDir[i] == 1 && h == 0) {
                    Low = i;
                    h = 1;
                } else if (randDir[i] == 1 && h == 1) {
                    High = i;
                }
            }
            int[] Dir= new int[2];
            Dir[0] = Low;
            Dir[1] = High;
            Low = 0;


            int randomDirection = new Random().nextInt(2);


            switch(randomDirection){
                case 0:
                    dir[0] = opt[Dir[0]][0];
                    dir[1] = opt[Dir[0]][1];
                    break;

                case 1:
                    dir[0] = opt[Dir[1]][0];
                    dir[1] = opt[Dir[1]][1];
                    break;

            }

        } else {


/*ka miayn mek uxutyun*/

            if (s.getType() == up.getType() && directions[1] != 1) {
                dir[0] = 0;
                dir[1] = -1;

            } else if (s.getType() == right.getType() && directions[0] != -1) {
                dir[0] = 1;
                dir[1] = 0;

            } else if (s.getType() == down.getType() && directions[1] != -1) {
                dir[0] = 0;
                dir[1] = 1;

            } else if (s.getType() == left.getType() && directions[0] != 1) {
                dir[0] = -1;
                dir[1] = 0;

            } else {
                dir[0] = 2;
                dir[1] = 2;
            }
/*ka miayn mek uxutyun*/

        }

        return dir;
    }

    public void damage(int amount) {
        health -= amount;
        if (health <= 0) {
            die();
            Player.modifyCash(5);

        }
    }

    private void die() {
        alive = false;
    }


    public void draw() {
        float healthPercentage = health / startHealth;
        DrawQuadTex(texture, x, y, width, height);
        DrawQuadTex(healthBackground, x, y - 16, width, 8);
        DrawQuadTex(healthForegrounf, x, y - 16, TILE_SIZE * healthPercentage, 8);
        DrawQuadTex(healthBorder, x, y - 16, width, 8);
    }

    public void reduceHiddenHealth(float amount) {
        hiddenHealth -= amount;
    }

    public float getHiddenHealth() {
        return hiddenHealth;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public TileGrid getTileGrid() {
        return grid;
    }

    public boolean isAlive() {
        return alive;
    }
}