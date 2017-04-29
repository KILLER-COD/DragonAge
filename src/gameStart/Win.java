package gameStart;

import UI.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static helpers.Leveler.LoadMap;
import static helpers.Methods.*;

/**
 * Created by user on 4/27/17.
 */
public class Win {
    private Texture background;
    private UI winUI;
    private UI.UI_String gameUIString;
    public static int nextLvL = 1;


    public Win(){
        background = QuickLoad("mainmenu");
        winUI = new UI();
        gameUIString = new UI.UI_String();
        winUI.addButton("Next" , "next", WIDTH / 2 - 80 , (int)(HEIGHT * 0.42F));
        DrawQuadTex(background, 0, 0, 2048, 1024);
        winUI.draw();
        gameUIString.drawString(450, 500, "NEXT", Color.black);
        DrawQuadTex(QuickLoad("you_win"),400,200,800,300);


    }

    private void updateButtons(){
        if(Mouse.isButtonDown(0)) {
            if(winUI.isButtonClicked("Next")){
                StateManager.map = LoadMap("newMap"+nextLvL);
                StateManager.win = null;
                StateManager.game = null;
                StateManager.mainMenu = null;
                StateManager.setState(StateManager.GameState.GAME);


            }
        }
    }

    public void update(){
        updateButtons();
    }
}
