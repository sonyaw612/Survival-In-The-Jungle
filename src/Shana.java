//---------------------------------------------------------------------------------
//	SHANA'S VARIABLES (shana_pose, shana_count):
//	String[] shana_pose = {"forward_stand1", "forward_walk1", "forward_prone", "forward_sit",
//			"forward_swingO1", "forward_swingT3", "forward_winged_fly", "forward_jump",
//			
//			"back_stand1", "back_walk1", "back_prone", "back_sit", "back_swingO1",
//			"back_swingT3", "back_winged_fly", "back_jump", 
//			
//			"forward_alert", "forward_ladder"};									
//	int[] shana_count = {4, 4, 2, 2, 4, 4, 2, 2,   4, 4, 2, 2, 4, 4, 2, 2, 4, 3};
//---------------------------------------------------------------------------------

import java.awt.*;

public class Shana extends Sprite{
	
	boolean walk = false;
	boolean backwards = false;
	boolean prone = false;
	boolean alert = false;
	boolean sit = false;
	boolean prone_stab = false;
	boolean climb = false;
	boolean climb_paused = false;
	boolean spike = false;
	
	private int STAND = 0;
	private int WALK = 1;
	private int PRONE = 2;
	private int SIT = 3;
	private int FLY = 4;
	private int JUMP = 5;
	private int PRONE_STAB = 6;
	private int ALERT = 7;
	
	private int jumpCD = 20;
		
	final int alert_cd = 60;
	int alert_CD = 60;
	private int CLIMB = 16;
	
	
	public Shana(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		setSpeed(3);
	}
	
	public Shana(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
	}
	
	@Override
	public void draw(Graphics pen) {
		if(alive) {
			Image draw_img;
			if(backwards) movement +=8;
			
			if(spike) {
				alert_CD--;
				movement = ALERT;
				if (backwards) movement += 8;
				
				draw_img = animation[movement].getCurrentImage();
				
				if(alert_CD == 0) {
					alert_CD = alert_cd;
					spike = false;
				}
			}
			else if(alert_CD < alert_cd) {
				prone = false;
				sit = false;
				climb = false;
				jumping = false;
				walk = false;
				
				draw_img = animation[movement].getCurrentImage();
			}
			else if(prone) {
				draw_img = animation[movement].getCurrentImage();				
			}
//			else if(sit) {
//				movement = SIT;
//				if(backwards) movement += 8;
//				
//				draw_img = animation[movement].getCurrentImage();				
//				backwards = false;
//			}
			else if(climb) {
				if(climb_paused) {
					draw_img = animation[CLIMB].getStillImage();					
				}
				else{
					draw_img = animation[CLIMB].getCurrentImage();					
				}
			}
			else if(jumping) {
				movement = JUMP;
				if(backwards) movement += 8;
				
				if(jumpCD > 0) jumpCD--;
				
				draw_img = animation[movement].getCurrentImage();				
			}
			else if(moving) {
				draw_img = animation[movement].getCurrentImage();				
			}
			else {
				movement = STAND;
				if(backwards) movement += 8;
				
				draw_img = animation[movement].getCurrentImage();				
			}
			
			pen.drawImage(draw_img, (int)px - Camera.x, (int)py - Camera.y, w, h, null);			
			
			changeDimension(65, 77);			
			moving = false;
			prone = false;
			if(!jumping) vx = 0;
			
//			super.draw(pen);	// This helps us determine the size of the sprite's rectangle.
			
		}
	}
	
//---------------------------------------------------------------------------------
/*	REMINDER: AVAILABLE METHODS IN SUPER CLASSES:
 * 		- contains(int mx, int my)
 * 		- overlaps(Rect r)														 */
//---------------------------------------------------------------------------------
	
	private void changeDimension(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void jump(int dy) {
		vy -= dy;
				
		moving = true;
		jumping = true;
		
		prone = false;
//		sit = false;
	}
	
//	public void sit() {
//		sit = true;
//		
//		prone = false;
//		moving = false;
//		jumping = false;		
//	}
	
	public void prone() {
		prone = true;
		
		jumping = false;
//		sit = false;
		climb = false;
		
		movement = PRONE;
		changeDimension(77, 51);
	}
	
	public void spiked() {
		spike = true;
		
		jumping = false;
		sit = false;
		prone = false;
		climb = false;
		
		movement = ALERT;
	}
	
	@Override
	public void moveBy(int dx, int dy) {
		px += dx;
		py +=dy;
		moving = true;
	}

	@Override
	public void moveUp(int dy) {
		py -= dy;
		moving = false;
		sit = false;
		prone = false;
		climb = true;
		movement = CLIMB;
	}

	@Override
	public void moveDown(int dy) {
		py += dy;
		moving = false;
		sit = false;
		prone = false;
		climb = true;
		movement = CLIMB;
	}
	
	@Override
	public void moveLeft(int dx) {
		px -= dx;
		moving = true;
		backwards = true;
		movement = WALK;
		sit = false;
	}
	
	@Override
	public void moveRight(int dx) {
		px += dx;
		moving = true;
		backwards = false;
		movement = WALK;
		sit = false;
	}
	
	
}
