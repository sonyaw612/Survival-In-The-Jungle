import java.awt.*;

public class ImageLayer {
	
	int x;
	int y;
	int z; // this is the value for the "distance away from screen". The bigger it is, the farther it is.
	
	
	Image image;
	
	public ImageLayer(int x, int y, int z, String filename) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.image = Toolkit.getDefaultToolkit().getImage("../Images/" + filename);
	}
	
	public void draw(Graphics pen) {
		int w = image.getWidth(null);
		for(int i = 0; i < 20; i++) {
			pen.drawImage(image, x + i*w - Camera.x / z, y - Camera.y, null);
			//depending on z, the image will move faster/slower than others.
		}
	}

}
