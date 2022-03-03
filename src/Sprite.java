import java.awt.*;

public class Sprite extends Rect {
	
	final int UP    = 0;
	final int DOWN  = 1;
	final int LEFT  = 2;
	final int RIGHT = 3;
	
	private int SPEED = 3;
	
	Animation[] animation;
	
	boolean moving = false;
	
	boolean jumping = false;
	
	boolean alive = true;
	
	int movement = 0;
	
	public Sprite(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, Color.BLACK);

		animation = new Animation[pose.length];
		
		for(int i = 0; i < pose.length; i++) {
			animation[i] = new Animation(folderPath, pose[i], count, fileType);
		}
	}
	
	public Sprite(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType) {
		super(x, y, w, h, Color.BLACK);
		
		animation = new Animation[count.length];
		
		for(int i = 0; i < pose.length; i++) {
			animation[i] = new Animation(folderPath, pose[i], count[i], fileType);
		}
	}

	@Override
	public void draw(Graphics pen) {
		if(alive) {
			if(moving) {
				pen.drawImage(animation[movement].getCurrentImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);
			}
			else {
				pen.drawImage(animation[movement].getStillImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);
			}
			
//			USE THIS IF YOU WANT THE SPRITE TO MOVE WITH THE SCROLL
//			if(moving) {
//				pen.drawImage(animation[movement].getCurrentImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);
//			}
//			else {
//				pen.drawImage(animation[movement].getStillImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);
//			}
			
			moving = false;
			
			if(!jumping) vx = 0;
			
//			super.draw(pen);	// This helps us determine the size of the sprite's rectangle.
			
		}
	}
		
	public void jump(int dy) {
		vy -= dy;
		
		moving = true;
		jumping = true;
	}
	
	public void dies() {
		alive = false;
		py = -10000;
	}
	
	@Override
	public void moveBy(int dx, int dy) {
		px += dx;
		py +=dy;
		moving = true;
	}

	@Override
	public void moveUp(int dy) {
		vy -= dy;
		moving = true;
		movement = UP;
	}
	
	@Override
	public void moveDown(int dy) {
		vy += dy;
		moving = true;
		movement = DOWN;
	}
	
	@Override
	public void moveLeft(int dx) {
//		if(!jumping) // use this only if you do not want character to change directions while jumping 
//			AND change velocity values to position (vx -> px, vy -> py)
		vx -= dx;
		moving = true;
		movement = LEFT;
	}
	
	@Override
	public void moveRight(int dx) {
		//if(!jumping) // use this only if you do not want character to change directions while jumping
		vx += dx;
		moving = true;
		movement = RIGHT;
	}
	
	public void setSpeed(int s) {
		SPEED = s;
	}
	
	public int getSpeed() {
		return SPEED;
	}
	
	
	
}




