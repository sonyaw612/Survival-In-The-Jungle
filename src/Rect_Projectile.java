import java.awt.*;

public class Rect_Projectile extends Sprite {
	
	final int MOVE = 0;
	final int ATTACK = 1;
	
	boolean hit = false;
	
	public Rect_Projectile(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		// TODO Auto-generated constructor stub
	}

	public Rect_Projectile(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		// TODO Auto-generated constructor stub
	}
	
	public void fire(double x, double y, int speed) {
		this.px = x;
		this.py = y;
		
		this.vx = speed;
		this.vy = 0;
		
		move();
	}
	
	@Override
	public void draw(Graphics pen) {
		if(alive) pen.drawImage(animation[movement].getCurrentImage(), (int)px - Camera.x, (int)py - Camera.y, null);
	}
	
	public void hit() {
		moving = false;
		movement = ATTACK;
		hit = true;
	}
	
	@Override
	public void moveLeft(int dx) {
		hit = false;
		px -= dx;
		moving = true;
		movement = MOVE;
	}
	
	

}
