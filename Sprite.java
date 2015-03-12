/**
 * 
 */
package frogger;

import java.awt.Graphics;
import java.util.Observable;

/**
 * @author chaitalisanjaygondhalekar
 *
 */
public abstract class Sprite extends Observable{

	int x,y;
	int dx=2,dy=0;
	/**
	 * @param args
	 */
	abstract void update();
	abstract void draw(Graphics g,View view);
}
