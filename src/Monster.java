import java.awt.*;

public class Monster extends Sprite {
	
//	boolean move is in Sprite class!!
	boolean standing = true;
	boolean attacked = false;
	boolean attacking = false;
	
	final int STAND = 0;
	final int ATTACK = 1;
	final int MOVE = 2;
	final int HIT = 3;
	final int DIE = 4;

	public Monster(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
	}
	
	public Monster(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
	}
	
	public void chase(int x, int y) {
		if(px < x) moveRight(3);
		if(px > x) moveLeft(3);
		if(py < y) moveDown(3);
		if(py > y) moveUp(3);
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(animation[movement].getCurrentImage(), (int)px, (int)py, w, h, null);
	}
	
	public void hit() {
		attacked = true;
		movement = HIT;
	}
	
	@Override
	public void dies() {
		movement = DIE;
		alive = false;
		py += 5000;
	}
	
	public void attack() {
		movement = ATTACK;
	}

}
