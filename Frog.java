package frogger;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

/**
 *@authors Chaitali Gondhalekar, Jingru Zhu 
 *
 */
public class Frog extends Sprite {
	public boolean alive;
	public enum Orientation{ UP, RIGHT, DOWN, LEFT};
	public Orientation orientation ;
	public int step = Frogger.getLANE()/2;
	private static final int frogInitX=400;
	private static final int frogInitY=370;
	public static final int frogLENGTH=30;
	public static final int frogWIDTH=23;
	public static int xNew=Frogger.getROADLENGTH()-frogLENGTH;
	public static int yNew=0;
	
	Image frogup = loadImage("frog-up.png");
    Image frogdown = loadImage("frog-down.png");
    Image frogleft = loadImage("frog-left.png");
    Image frogright = loadImage("frog-right.png");
    Image frogdead = loadImage("splat.gif");
    /***
     * set the original location of the frog
     */
	Frog(){
		x = frogInitX;
		y = frogInitY;
		dx = 0;
		dy = 0;
		alive = true;
		orientation = Orientation.UP;
	}
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Image loadImage(String fileName) {
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException exc) {
            System.out.println("Can't load image.s");
        }
        return img;
    }
	
	/**
	 * changes the frog's position according to the kep pressed
	 */
	@Override
	void update(){

			if(Frogger.flag==1){
				if(orientation == Orientation.UP)   y -= step;
				else if (orientation == Orientation.DOWN) y += step;
				else if (orientation == Orientation.RIGHT) x += step;
				else if (orientation == Orientation.LEFT) x -= step;
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * paints the frog according to the orientation or alive fag
	 */
	@Override
	public void draw(Graphics g,View view){

		if(!alive){
			g.drawImage(frogdead, x, y, view);
			alive=true;
			x=frogInitX;y=frogInitY;
			for(int i=1000;i>0;i--) i--;
			//put some delay
		}
		else{
	     	if(orientation == Orientation.UP)   g.drawImage(frogup, x, y, view);
			else if (orientation == Orientation.DOWN) g.drawImage(frogdown, x, y, view);
			else if (orientation == Orientation.RIGHT) g.drawImage(frogright, x, y, view);
			else if (orientation == Orientation.LEFT) g.drawImage(frogleft, x, y, view);
     	}
	}
	/**
	 * changes orientation of frog according to the key pressed
	 * @param e
	 */
	public void changeOrientation(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_UP) { 
          Frogger.flag=1;
		}
		
      if(e.getKeyCode() == KeyEvent.VK_DOWN) { 

      	if(orientation == Orientation.UP)   orientation = Orientation.DOWN;
  		else if (orientation == Orientation.DOWN)  orientation = Orientation.UP;
  		else if (orientation == Orientation.RIGHT)  orientation = Orientation.LEFT;
  		else if (orientation == Orientation.LEFT)  orientation = Orientation.RIGHT;
      	Frogger.flag=0;
 
      }
      if(e.getKeyCode() == KeyEvent.VK_LEFT) {   

      	if(orientation == Orientation.UP)   orientation = Orientation.LEFT;
  		else if (orientation == Orientation.DOWN)  orientation = Orientation.RIGHT;
  		else if (orientation == Orientation.RIGHT)  orientation = Orientation.UP;
  		else if (orientation == Orientation.LEFT)  orientation = Orientation.DOWN;
      	Frogger.flag=0;
      }
      if(e.getKeyCode() == KeyEvent.VK_RIGHT) {

      	if(orientation == Orientation.UP)   orientation = Orientation.RIGHT;
  		else if (orientation == Orientation.DOWN)  orientation = Orientation.LEFT;
  		else if (orientation == Orientation.RIGHT)  orientation = Orientation.DOWN;
  		else if (orientation == Orientation.LEFT)  orientation = Orientation.UP;
      	Frogger.flag=0;
      }
	}
	/**
	 * getter
	 * @return X coordinate of frog's initial position 
	 */
	public static int getFrogInitX(){
		return frogInitX;
	}
	/**
	 * getter
	 * @return Y coordinate of frog's initial position 
	 */
	public static int getFrogInitY(){
		return frogInitY;
	}
	
}
