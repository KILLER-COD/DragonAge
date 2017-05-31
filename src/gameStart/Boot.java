package gameStart;

import static gameStart.Options.LIVE_START;
import static helpers.Methods.BeginSession;

import data.Player;
import data.Wave;
import helpers.Leveler;
import org.lwjgl.opengl.Display;

import helpers.Clock;


public class Boot {
    public static boolean CONTINUE = true;

    public Boot() {
        BeginSession();

        while (!Display.isCloseRequested()) {
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            Display.sync(20);

            Clock.update();

            StateManager.update();


            if (CONTINUE == false) {
                break;
            } else {
                Display.update();
            }
        }

    }

    public static void main(String[] args) {
        new Boot();
    }

    public static void restart() {
        //Clock.update();
        StateManager.game = null;
        StateManager.mainMenu = null;
        StateManager.map = Leveler.LoadMap("newMap1");
        StateManager.gameState = StateManager.GameState.MAINMENU;
        CONTINUE = true;
        Player.getTowerList().clear();
        Wave.getEnemiesList().clear();
        Player.Lives = LIVE_START;
    }

    public static void youWin(){
        StateManager.game = null;
        StateManager.mainMenu = null;
        StateManager.gameState = StateManager.GameState.WIN;
        CONTINUE = true;
        Player.getTowerList().clear();
        Wave.getEnemiesList().clear();
        Player.Lives = LIVE_START;
    }


}
