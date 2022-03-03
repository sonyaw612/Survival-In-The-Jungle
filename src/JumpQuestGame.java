import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class JumpQuestGame extends GameStart {
		
//	Image controls; // Display to user what the controls are
	
//	-------------------------------------------------------------------------------------------
//		SHANA DECLARATION
//	-------------------------------------------------------------------------------------------
	String[] shana_pose = {"forward_stand1", "forward_walk1", "forward_prone", "forward_sit",
			"forward_winged_fly", "forward_jump", "forward_proneStab", "forward_alert", 
			
			"back_stand1", "back_walk1", "back_prone", "back_sit",
			"back_winged_fly", "back_jump", "back_proneStab", "back_alert",
			
			"forward_ladder"};									
	int[] shana_count = {4, 5, 2, 2, 3, 2, 3, 4,   4, 5, 2, 2, 3, 2, 3, 4,   3};
	Shana shana = new Shana(150, 500, 65, 77, "Shana/", shana_pose, shana_count, "png"); //	(int)(65 * 1.4) , (int)(77 * 1.4)
	// 1728, 500 
	
	int    shana_top = (int) shana.py;
	int shana_bottom = (int) (shana.py + shana.h - 1);
	int   shana_left = (int) shana.px;
	int  shana_right = (int) (shana.px + shana.w - 1);
	
//	-------------------------------------------------------------------------------------------
//		LUPIN DECLARATION
//	-------------------------------------------------------------------------------------------
	String[] lupin_poses = {"stand", "attack"};
	int[] lupin_count = {1, 10};
	Lupin_Monster[] lupins = 
	{	new Lupin_Monster(280, 191, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(280, 447, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(280, 671, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(280, 767, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
	
		new Lupin_Monster(2040, 319, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(2040, 703, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(2040, 799, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false),
		new Lupin_Monster(2040, 1151, 62, 66, "Monsters/Lupin/", lupin_poses, lupin_count, "png", false)	};
	
//	-------------------------------------------------------------------------------------------
//		ARRAY OF ROPES
//	-------------------------------------------------------------------------------------------
	Rect[] ladders = { new Rect(1058, 455, 10, 150, Color.RED),
					   new Rect(1332, 455, 10, 150, Color.RED)	};
	
	Image vine1 = Toolkit.getDefaultToolkit().getImage("../Images/vine-removebg-preview.png");
	Image vine2 = Toolkit.getDefaultToolkit().getImage("../Images/vine-removebg-preview.png");

//	-------------------------------------------------------------------------------------------
//		SPIKE TRAPS
//	-------------------------------------------------------------------------------------------
	String[] spike_empty = {""};
//	Sprite spike_trap = new Sprite(400, 559, 30, 15, "spike_trap", spike_empty, 1, "png");

	Sprite[] spikes = 
	  { new Sprite(750, 1425, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		new Sprite(950, 1425, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		new Sprite(1150, 1425, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		
		new Sprite(417, 944, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		new Sprite(770, 944, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		new Sprite(1123, 944, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
		new Sprite(1475, 944, 30, 15, "../Images/spike_trap", spike_empty, 1, "png"),
	};
//	-------------------------------------------------------------------------------------------
	
	Image backgroundImage;
	int scale = 32;
	Image[][] map; //	BufferedImage[][] map;
	String[] mapString = {""};
	String[] tile_name = null;
	String letter_codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

//---------------------------------------------------------------------------------
//	Testing variables:
//	Image img = Toolkit.getDefaultToolkit().getImage("../Images/Shana/forward_stand1_0.png");
//	pen.drawImage(img, 65, 77, null);
//---------------------------------------------------------------------------------
	
	public void initialize() {		
//		SETTING ACCELERATION FOR CHARACTER AND MONSTERS THAT CAN JUMP
		shana.setAcceleration(0, 0.3);
		
		String background_name = null;
		File file = new File("JumpQuest.map.txt");
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
						
//		------------------------------------------------------------------------
//		LOAD MAP CODE
			int rows = Integer.parseInt(input.readLine());	// How many rows in the map?
			
			mapString = new String[rows];					// Set map's length to number of rows
			
			for(int row = 0; row < rows; row++) {			// Initialize each index of map according to the row number of the map
				mapString[row] = input.readLine();	
//				System.out.println(mapString[row]);
			}
			
			int cols = mapString[0].length();
			map = new Image[rows][cols];

//		------------------------------------------------------------------------	
//		LOAD TILE'S FILE NAMES
			int tiles = Integer.parseInt(input.readLine());		// How many tiles?
			tile_name = new String[tiles];					// Set tile_name's length to the number of available tiles
			for(int i = 0; i < tiles; i++) {				// Initialize names of tiles into the array tile_name
				tile_name[i] = "../Images/" + input.readLine();
//				System.out.println(tile_name[i]);
			}

//		------------------------------------------------------------------------
//		LOAD BACKGROUND FILE NAME
			backgroundImage = Toolkit.getDefaultToolkit().getImage("../Images/" + input.readLine());	// file name of background
			
			input.close();

		}	catch(IOException e) {};
//		------------------------------------------------------------------------
		
		for(int row = 0; row < mapString.length; row++) {
			for(int col = 0; col < mapString[row].length(); col++) {
				char c = mapString[row].charAt(col);
				if(c != '.') {
					int tileIndex = letter_codes.indexOf(c);
//					try {
//						map[row][col] = ImageIO.read(new File(tile_name[tileIndex]));
//					} catch (IOException e) {}
					map[row][col] = Toolkit.getDefaultToolkit().getImage(tile_name[tileIndex]); // (col * 32) - Camera.x, (row * 32) - Camera.y, 32, 32, null);
				}
			}
		}
	}
	
	@Override
	public void paint(Graphics pen) {
		
		Camera.x = (int) shana.px - 500;
		Camera.y = (int) shana.py - 500;
		if(Camera.x < 0) Camera.x = 0;
		if(Camera.y < 0) Camera.y = 0;
		
		pen.drawImage(backgroundImage, 0, 0, 1920, 1080, null);
				
		for(int i = 0; i < lupins.length; i++) {
			lupins[i].draw(pen);
		}
		for(int i = 0; i < spikes.length; i++) {
			spikes[i].draw(pen);
		}
		pen.drawImage(vine1, 980 - Camera.x, 455 - Camera.y, 160, 150, null);
		pen.drawImage(vine2, 1255 - Camera.x, 455 - Camera.y, 160, 150, null);

		shana.draw(pen);		

		for(int row = 0; row < mapString.length; row++) {
			for(int col = 0; col < mapString[row].length(); col++) {
				char c = mapString[row].charAt(col);
				if(c != '.') {
					int tileIndex = letter_codes.indexOf(c);
					pen.drawImage(map[row][col], (col * scale) - Camera.x, (row * scale) - Camera.y, scale, scale, null);
//					pen.drawRect(col * scale - Camera.x, row * scale - Camera.y, scale, scale);
				}
			}
		}
	
	}
	
	public void respond_To_Keyboard_Input() {
		
/*	---- CAMERA CONTROLS -----------------------------
		if(pressing[RT] == true) {			
			Camera.moveRight(10);
		}
		else if(pressing[LT] == true) {			
			Camera.moveLeft(10);
		}
		else if(pressing[DN]) {			
			Camera.moveDown(10);
		}
		else if(pressing[UP]) {				
			Camera.moveUp(10);
		}
//	--------------------------------------------------- */
	
		if(!shana.spike) {
			if(shana.climb) {}
			else if(pressing[RT] == true) {
				shana.moveRight(shana.getSpeed());
			}
			else if(pressing[LT] == true) {
				shana.moveLeft(shana.getSpeed());
			}
			else if(pressing[DN]) {
				if(!shana.jumping) shana.prone();
			}
			
			if(pressing[SPACE] && !shana.jumping) {
				shana.jump(6);
				shana.sit = false;
				shana.prone = false;
				shana.climb = false;
			}
		}
		shana.move();
	}
	
	public void move_Computer_Controlled_Entities() {}
	
	public void resolve_Collisions() {
		
//	------------------------------------------------------------------------
//		IF OVERLAPS BANANAS
		for(int i = 0; i < this.lupins.length; i++) {
			if(shana.overlaps(lupins[i].banana) && (shana.px + shana.w - 20 >= lupins[i].banana.px)) {
				lupins[i].attack_landed = true;
				lupins[i].banana.hit();
				
				if(shana.prone) { shana.prone = false; }
	
				shana.spiked();
				
				if(shana.px < lupins[i].banana.px) {
					shana.moving = false;
					shana.jump(5);
					shana.move();
					shana.setVelocity(-5, -4);
					shana.setAcceleration(0.13, 0.6);
				}
				else { 
					shana.moving = false;
					shana.jump(5);
					shana.move();
					shana.setVelocity(5, -4);
					shana.setAcceleration(0.13, 0.6);
				}
				
			}
		}
		
//	------------------------------------------------------------------------
//		IF OVERLAPS LADDER
		for(int i = 0; i < ladders.length; i++) {
			if(shana.overlaps(ladders[i]) && !shana.spike) {
				if(pressing[UP]) {
					if(shana.py + shana.h - 5 <= ladders[i].py) {
						shana.climb = false;
					}
					else {
						shana.climb = true;
						shana.px = ladders[i].px - 20;
						shana.setAcceleration(0, 0);
						shana.setVelocity(0, 0);
						shana.moveUp(shana.getSpeed());
					}
				}
				else if(pressing[DN]) {
					
					if(!shana.jumping && !shana.moving && !shana.climb) {
						shana.prone();
					}
					else {
						if((shana.py + 5 < ladders[i].py + ladders[i].h) && (shana.py + shana.h + 3 > ladders[i].py)) {
							shana.climb = true;
							shana.px = ladders[i].px - 20;
							shana.setAcceleration(0, 0);
							shana.setVelocity(0, 0);
							shana.moveDown(shana.getSpeed());
						}
						if((shana.py + shana.h) > (ladders[i].py + ladders[i].h)) {
							shana.climb = false;
							shana.setAcceleration(0, 0.6);
							if(!shana.jumping && !shana.moving && !shana.climb) {}
						}
					}
					
				}
				else {
					shana.climb_paused = true;
				}
			}	
		}

//	------------------------------------------------------------------------
//		IF OVERLAPS SPIKES
		for(int i = 0; i < this.spikes.length; i++) {
			
			if(shana.overlaps(spikes[i])) {
				
				if((shana.px + shana.w - 3 > spikes[i].px) && shana.px + shana.w - 25 <= (spikes[i].px + spikes[i].w)) {
					shana.spiked();
					shana.moving = false;
					shana.jump(5);
					shana.move();
					shana.setVelocity(-5, -4);
					shana.setAcceleration(0.13, 0.6);
				}
				else if((shana.px + 5 < spikes[i].px + spikes[i].w)) { 
					shana.spiked();
					shana.moving = false;
					shana.jump(5);
					shana.move();
					shana.setVelocity(5, -4);
					shana.setAcceleration(0.13, 0.6);
				}
			}
		}
		
		int shana_row = (int) ((shana.py + shana.h) / scale);
		int shana_middle = (int) ( ( (shana.px + shana.w) / 2) / scale);
		
		if(mapString[shana_row].charAt((int) (shana.px/scale) + 1) != '.') { 
			if(!shana.climb) {
				shana.setAcceleration(0, 0.3);
				shana.setVelocity(0, 0.1);
				shana.py = (shana_row * 32) - shana.h;
				shana.jumping = false;
			}
		}
		
//	------------------------------------------------------------------------
//		IF OVERLAPS BORDERS
		
	}
//	------------------------------------------------------------------------
//		END OF COLLISION RESOLUTION
//	------------------------------------------------------------------------
	
	
	public void mousePressed(MouseEvent e) {
		mx = e.getX();
    	my = e.getY();
    	
    	System.out.println("my: " + my + ", mx: " + mx);
	}
	
	public void mouseReleased(MouseEvent e) {}

}
