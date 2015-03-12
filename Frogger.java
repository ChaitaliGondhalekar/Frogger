/** 
 * acts as Controller, mediates between View and Model(Frog and Car)
 * 
 */
package frogger;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frogger.Frog.Orientation;
/**
 * @authors Chaitali Gondhalekar, Jingru Zhu 
 *
 */
public class Frogger extends JFrame {
	
	private static final int DIVIDER =10;	//yellow line
	private static final int SIDE =45;		//white portion,safe
	private static final int LANE =150;		//lane width
	private static final int ROADLENGTH =800;	//road length
	static int flag=0, livesRemaining=3,gameScore=0,safeFrogs=0;

	JPanel panel = new JPanel();	//single panel, View
	JPanel buttonPanel =new JPanel();	//panel for buttons and score
	JPanel panel2 =new JPanel();	//panel for only buttons
	JPanel labelPanel = new JPanel();	//panel for labels
	
	JButton pause = new JButton("Pause");	//pause
	JButton start = new JButton("Start");	//start
	JButton restart = new JButton("Restart");	//restart

	JLabel labelScore =new JLabel("Score: ");	//score
	JLabel score =new JLabel("0");	//score
	JLabel labelLives =new JLabel("Lives Remaining: ");	//score
	JLabel lives =new JLabel(livesRemaining+"");	//score
	
	Frog frog = new Frog();//model
	Car car = new Car();//model
	View view = new View();//view
	Cast cast=new Cast();	//cast
	
	Timer timer,timer1;
	Key key = new Key();

	/**
	 * deals with the panels
	 * called in main method
	 */
	public void adjustPanels(){
		
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER,view);	//view panel added to frame
		view.requestFocus();
		
		buttonPanel.setPreferredSize(new Dimension(ROADLENGTH,50));//set panel size
		buttonPanel.setLayout(new BorderLayout());
		pause.setPreferredSize(new Dimension(80,buttonPanel.getWidth()));
		
		start.setPreferredSize(new Dimension(80,buttonPanel.getWidth()));
//		panel2.setLayout(new GridLayout(1,2));
		panel2.setLayout(new GridLayout(1,3));
		panel2.add(pause);
		panel2.add(start);
		//try
		panel2.add(restart);
		buttonPanel.add(BorderLayout.WEST,panel2);
		
		labelPanel.setLayout(new GridLayout(1,4));
		labelPanel.add(labelScore);
		labelPanel.add(score);
		labelPanel.add(labelLives);
		labelPanel.add(lives);
		buttonPanel.add(BorderLayout.EAST,labelPanel);
		
		add(BorderLayout.SOUTH,buttonPanel);
		pack();
		
		//action listener for Start button, calls a method that sets up everything
		start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	//start
            	restart.setEnabled(false);
            	start.setEnabled(false);//enabled/disabled
            	pause.setEnabled(true);
            	gameScore=0;
            	frog.alive=true;
            	doYourThing();	//called once	
            }
    });
		//action Listener for pause button, stops both the timers controlling 
		//periodic painting and cars' movement
		pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	//pause
            	timer.cancel();timer1.cancel();
            	pause.setEnabled(false);
            	start.setEnabled(true);
            }
    });
		
		restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	//pause
            	cast.list.clear();
            	Car.velocity=5;
            	gameScore=0;
            	livesRemaining=3;
            	frog.alive=true;
            	pause.setEnabled(true);
            	start.setEnabled(false);
            	restart.setEnabled(false);
            	addKeyListener(key);
            	view.addKeyListener(view.key1);
            	doYourThing();	//called once	
            }
    });
		
		//keyListener attached to the Frame, method to change Frog parameters is called  
		addKeyListener(key);
}
	
	/**
	 * @param args
	 * called in main method
	 */
	public void init() {
		// TODO Auto-generated method stub
		frog.addObserver(view);
		cast.addObserver(view);
		view.controller =this;
		view.cast =cast;
		view.frog=frog;
	}
	
	/**
	 * returns nothing
	 * starts the timers that control periodic painting of animation
	 * and cars' movement
	 * called when start button is pressed
	 */
	public void doYourThing(){
		
		frog.x=Frog.getFrogInitX();
		frog.y=Frog.getFrogInitY();
		timer1 = new Timer(true);
		timer1.schedule(new Strobe1(), 0, 1400);
		timer = new Timer(true);
		timer.schedule(new Strobe(), 0, 50);
	}

	/**
	 * getters
	 * provide access to various road dimensions
	 */
	public static int getDIVIDER(){
		return DIVIDER;
	}
	
	public static int getLANE(){
		return LANE;
	}

	public static int getSIDE(){
		return SIDE;
	}

	public static int getROADLENGTH(){
		return ROADLENGTH;
	}
	
	public static int getROADWIDTH(){
		return (DIVIDER + 2*LANE + 2*SIDE);
	}
	/**
	 *updates car parameters(position) after given period
	 *removes car which move out of frame from the list of Sprites
	 *calls methods that 
	 *detect collision
	 *update the score/lives
	 *keeps the frog from jumping off the view
	 *calls update method of cast
	 */
	private class Strobe extends TimerTask{
		
		public void run(){//gets called every 100ms
			
//			for(Car car: cast.list){
//				car.update();	//called every 100ms, moving car every 100ms		
//			}	
			for(int i=0;i<cast.list.size();i++){
				cast.list.get(i).update();
			}
			cast.removeCar(car);	//remove if reached at the other end
			detectCollision();		//try
			updateLabels();
			isGameOver();
			frogCrossedSafely();
			frogOutOfBounds();
			cast.updateCar();	//painting car every 100ms
		}
	}
	
	/**
	 *has run method to be executed periodically(2s)
	 *calls methods that- generate cars
	 */
	private class Strobe1 extends TimerTask{
		public void run(){//gets called every 1500ms
			cast.generateCars();	//cars generated every 1500ms
		}
	}
	
	/**
	 * detects the collision between car and frog
	 * called in Strobe class, every 100ms
	 */
	public void detectCollision(){
		
			for(int k=0;k<cast.list.size();k++){
				for(int i=cast.list.get(k).y;i<cast.list.get(k).y+Car.getCARWIDTH();i++){
					if(i>=frog.y && i<=frog.y+Frog.frogWIDTH){
						for(int j=cast.list.get(k).x;j<cast.list.get(k).x+Car.getCARLENGTH();j++){
							if(j>=frog.x && j<=frog.x+Frog.frogLENGTH){
								frog.alive= false;
								 if(livesRemaining>0){
								 livesRemaining--;
								 frog.orientation=Orientation.UP;
								 }
								 break;
							}
						}
					}
					if(!frog.alive) break;
				}
				if(!frog.alive) break;
			}	
	}
	
	/**
	 * deals with invalid frog positions
	 * called in Strobe class, every 100ms
	 */
	public void frogOutOfBounds(){
		if(frog.y< getSIDE() || frog.x<=0 || frog.y>getROADWIDTH() || frog.x>=800){
			frog.x=Frog.getFrogInitX();
			frog.y =Frog.getFrogInitY();
			frog.orientation=Orientation.UP;
		}		
	}
	
	/**
	 * counts the number of frogs that cross the road safely
	 * increases velocity of cars
	 * called in Strobe class, every 100ms 
	 */
	public void frogCrossedSafely(){
		if(frog.y<getSIDE()){
			//increase velocity
			Car.velocity =Car.velocity+2;
			safeFrogs++;
			gameScore+=10;
			
			if(safeFrogs==5){
				//display level up
				safeFrogs=0;
			}
		}
	}
	/**
	 * updates the displayed score and remaining lives
	 * called in Strobe class, every 100ms
	 */
	public void updateLabels(){
		lives.setText(livesRemaining+"");
		score.setText(gameScore+"");
	}
	
	/**
	 * checks if game is over
	 *  called in Strobe class, every 100ms
	 */
	public void isGameOver(){
		//display something when game over!
		if(livesRemaining==0){
			//display that the game is over
			timer.cancel();timer1.cancel();
			livesRemaining=0;
			frog.alive=false;
			//try
			removeKeyListener(key);
			view.removeKeyListener(view.key1);
			restart.setEnabled(true);
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static Image loadImage(String fileName) {
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException exc) {
            System.out.println("Can't load image.s");
        }
        return img;
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
	/**
	 * calls methods to make the frame ready to be painted
	 * makes the frame visible, non-resizable, close-able
	 * @param args
	 */
	public static void main(String[] args){
		
		Frogger controller = new Frogger();
		controller.adjustPanels();	//prepare the background for drawBackgnd
		//initializes the controller
		controller.init();
		controller.setVisible(true);
		controller.setResizable(false);
		controller.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
