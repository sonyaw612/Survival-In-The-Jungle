import java.awt.*;

public class Animation {

	Image[] image;
	
	int current = 0; // which image the animation will start from
	
	final int still = 0;
	
	final int delay = 20;
	int delayCount = 20;
	
	public Animation(String folderPath, String name, int count, String filetype) {
		image = new Image[count];
		
		for(int i = 0; i < count; i++) {
			image[i] = Toolkit.getDefaultToolkit().getImage("../Images/" + folderPath + name + "_" + i + "." + filetype);		
		}
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(getCurrentImage(), 65, 77, null);
	}
	
	public Image getCurrentImage() {
		Image temp = image[current];
		delayCount--;

		if(delayCount == 0) {
			current++;
			
			if(current == image.length) current = 0;
			
			delayCount = delay;
		}
		
		return temp;
	}
	
	// DO NOT USE
	public Image getStillImage() {
		return image[still];
	}
	
}
