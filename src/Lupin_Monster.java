import java.awt.*;
import java.util.*;

// DIMENSIONS:
//  Attack: 56 x 65
//	Stand:	62 x 66

/*
 * Notes:
 * 
 */

public class Lupin_Monster extends Monster {

	private int rotate;		// cooldown in between attacks
	int rotateTime; 		// countdown til next attack
	
	final int atkFrame = 200;	// how long 
	int atkFrameCD = 200; 		// used to determine if we are in the middle of an attack, if so finish the attack animation		

	final int banana_landed = 90;
	int banana_landedCD = 90;
	
	boolean attack = false;
	boolean attack_landed = false;
	boolean banana_attack = false;
	boolean backwards;
	
	Rect_Projectile banana;
	String[] banana_pose = {"banana_move", "banana_hit"};
	int[] banana_count = {1, 5};
	
	Random rand = new Random();
	
	public Lupin_Monster(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		rotate = rand.nextInt(200) + 300;
		rotateTime = rotate;
		banana = new Rect_Projectile(x-10, y, 50, 50, "../Images/Monsters/Banana/", banana_pose, banana_count, "png");
	}
	
	public Lupin_Monster(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType, boolean face_right) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		rotate = rand.nextInt(200) + 300;
		rotateTime = rotate;
		banana = new Rect_Projectile(x-10, y, 50, 50, "../Images/Monsters/Banana/", banana_pose, banana_count, "png");
	}
	
	public void draw(Graphics pen) {
		if(attack) {
			pen.drawImage(animation[movement].getCurrentImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);	
			atkFrameCD--;
			
			if(atkFrameCD == 0) {
				atkFrameCD = atkFrame;
				movement = STAND;
				changeDimensions(62, 66);
				attack = false;
			}
			
			if(atkFrameCD == 100) {
				banana.setVelocity(-3, 0);
				banana_attack = true;
				banana.alive = true;
				banana.movement = banana.MOVE;
			}
		}
		else {
			movement = STAND;
			pen.drawImage(animation[movement].getCurrentImage(), (int)px - Camera.x, (int)py - Camera.y, w, h, null);
			rotateTime--;
			
			if(rotateTime == 0) {
				rotateTime = rotate;
				attack = true;
				movement = ATTACK;
				changeDimensions(56, 65);
				banana_attack = false;

				banana.px = this.px;
				banana.py = this.py;
			}
		}
		
		if(attack_landed) {
			banana_landedCD--;
			if(banana_landedCD == 0) {
				banana_landedCD = banana_landed;
				attack_landed = false;
				banana.px = -1000;
				banana.py = -1000;
			}
			else {
				banana.setVelocity(0, 0);
				banana.draw(pen);
			}
		}
		else if(banana_attack) {
			banana.moveBy(-4, 0);
			banana.draw(pen);
		}
	}
	
	public void changeDimensions(int w, int h) {
		this.w = w;
		this.h = h;
	}

}
