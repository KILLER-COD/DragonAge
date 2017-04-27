package gameStart;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import  UI.*;
import gameStart.StateManager.GameState;

import static helpers.Methods.*;

public class MainMenu {
	private Texture background;
	private UI menuUI;

	public MainMenu(){
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play" , "play", WIDTH / 2 -128 , (int)(HEIGHT * 0.45F));
		menuUI.addButton("Editor", "editor", WIDTH / 2 -128 , (int)(HEIGHT * 0.55F));
		menuUI.addButton("Quit", "quit", WIDTH / 2 -128 , (int)(HEIGHT * 0.65F));

	}

	private void updateButtons(){
		if(Mouse.isButtonDown(0)) {
			if(menuUI.isButtonClicked("Play"))
				StateManager.setState(GameState.GAME);
			if(menuUI.isButtonClicked("Editor"))
				StateManager.setState(GameState.EDITOR);		
			if(menuUI.isButtonClicked("Quit"))
				System.exit(0);  
		}
	}
	


	public void update(){
		DrawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();

	}
}
