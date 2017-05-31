package data;

import static helpers.Methods.DrawQuadTex;
import static helpers.Methods.HEIGHT;
import static helpers.Methods.QuickLoad;
import static helpers.Methods.TILE_SIZE;
import static helpers.Leveler.LoadMap;
import static helpers.Leveler.SaveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import UI.UI;
import UI.UI.Menu;
import tiles.TileGrid;
import tiles.TileType;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUi;
	private Menu tilePickerMenu;


	public Editor(){
		this.grid = LoadMap("gameStart/newMap5");
		this.index = 0; 
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		DrawQuadTex(QuickLoad("menuRightTile"), 1285, 0, 192, 1024);
		setupUI();
	}

	public void setupUI(){
		editorUi = new UI();
		editorUi.creatMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
		tilePickerMenu = editorUi.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "green64");
		tilePickerMenu.quickAdd("Dirt", "dirt64");
		tilePickerMenu.quickAdd("Water", "blue64");


	}

	public void update(){
		draw();
		
		//Handle Mouse Input
		if(Mouse.next()){
			boolean mouswClicked = Mouse.isButtonDown(0);

			if(mouswClicked){
				if(tilePickerMenu.isButtonClicked("Grass")){
					index = 0;
				} else if(tilePickerMenu.isButtonClicked("Dirt")) { 
					index = 1;
				} else if(tilePickerMenu.isButtonClicked("Water")) { 
					index = 2;
				} else {
					setTile();
				}
			}
		}

		//Handle Keybord Input
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && 
					Keyboard.getEventKeyState()){
				moveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S && 
					Keyboard.getEventKeyState()){
				SaveMap("gameStart/newMap5",grid);
			}
		}
	}
	
	private void draw(){
		grid.Draw();
		editorUi.draw();
	}
	
	private void setTile(){
		grid.setTile((int)Math.floor(Mouse.getX() / TILE_SIZE ),
				(int) Math.floor((HEIGHT - Mouse.getY() -1) / TILE_SIZE) , 
				types[index]);
	}

	private void moveIndex(){
		index++;
		if (index > types.length -1){
			index = 0;
		}
	}

}
