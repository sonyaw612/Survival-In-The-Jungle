
public class Camera {
	
	public static int x;
	public static int y;
	
	public static void initialize(int x, int y) {
		Camera.x = x;
		Camera.y = y;
	}
	
	public static void moveRight(int dx) {
		x += dx;
	}
	
	public static void moveLeft(int dx) {
		x -= dx;
	}
	
	public static void moveUp(int dy) {
		y -= dy;
	}
	
	public static void moveDown(int dy) {
		y += dy;
	}

}


/*
	my: 193, mx: 278
	my: 323, mx: 282
	my: 450, mx: 285
	my: 216, mx: 286
	my: 313, mx: 287
	my: 397, mx: 635
	my: 376, mx: 634
	my: 408, mx: 635
	my: 314, mx: 636
	my: 415, mx: 635
	my: 288, mx: 635
 */


