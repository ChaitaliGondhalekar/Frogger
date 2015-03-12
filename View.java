package frogger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
/**
 *@authors Chaitali Gondhalekar, Jingru Zhu 
 *
 */
public class View extends JPanel implements Observer{
	
	Background backg;
	Frog frog;
	Frogger controller;
	Cast cast;
	Key key1;

	View(){
		setPreferredSize(new Dimension(Frogger.getROADLENGTH(),Frogger.getROADWIDTH()));//set panel size
		backg =new Background();
		setFocusable(true);
		key1=new Key();
		addKeyListener(key1);
	}

	/**
	 * called when any Observable(Frog or Cast) notifies a change in its state 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	/**
	 * paints the background, all the Sprites(cars and frog)
	 */
	public void paint(Graphics g){

		setFocusable(true);
		backg.drawBackground(g);
		frog.draw(g,this);
		for(int j=0;j<cast.list.size();j++){
			cast.list.get(j).draw(g, this);
		}
	}
	
	public class Key implements KeyListener{
		
		/**
		 * 
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			frog.changeOrientation(e);
			frog.update();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub	
		}
	
}
}
