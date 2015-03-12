/**
 * implements Car and its properties
 */
package frogger;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

/**
 *@authors Chaitali Gondhalekar, Jingru Zhu 
 *
 */
public class Car extends Sprite {

	static String[] leftImgList = { "red-car-right.png", "yellow-car-right.png",
			"blue-car-right.png", "white-car-right.png","aqua-car-right.png","green-car-right.png" };
	static String[] rightImgList = {"red-car-left.png", "yellow-car-left.png",
			"blue-car-left.png", "white-car-left.png","aqua-car-left.png","green-car-left.png"};
	static Integer[] yCoordListRight ={60,110};//= {50,90,130};
	static Integer[] yCoordListLeft = {230, 290};//{210,260,300};
	private static final int CARLENGTH = 74;	//image dimension
	private static final int CARWIDTH = 37;
	public static int DELAY = 40,velocity=5;
	static int pickedNumber;
	int isWest = 1;
	
	Image imgCar;
	Graphics g;
	Random rand = new Random();
	
	Car() {
		generateParameters();
	}

	/**
	 * chooses Car's x and y Coordinates, direction, image randomly
	 */
	public void generateParameters() {

		//randomly choose East(0) or West(1)
		pickedNumber = rand.nextInt(2) + 0;
		isWest = pickedNumber;
		if(isWest==1){
			//randomly choose car going West
			pickedNumber = rand.nextInt(rightImgList.length) + 0;
			imgCar=Frogger.loadImage(rightImgList[pickedNumber]);
			x=810;													//starting from right, going West
			pickedNumber = rand.nextInt(yCoordListRight.length) + 0;//deal with y
			y=yCoordListRight[pickedNumber];
		}
		else{
			//randomly choose car going East
			pickedNumber = rand.nextInt(leftImgList.length) + 0;
			imgCar=Frogger.loadImage(leftImgList[pickedNumber]);
			x=-10;													//starting from left, going East
			pickedNumber = rand.nextInt(yCoordListLeft.length) + 0;//deal with y
			y=yCoordListLeft[pickedNumber];
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see frogger.Sprite#update()
	 * updates the X coordinate of the car to make it move
	 */
	@Override
	void update() {
		// TODO Auto-generated method stub
		if (isWest == 1) {
			// from right to left
			x -= dx;
		} else{
			// from left to right
			x += dx;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frogger.Sprite#draw()
	 */
	@Override
	public void draw(Graphics g, View view) {
	
		// TODO Auto-generated method stub
		// draw car
		g.drawImage(imgCar, x, y, view);
	}

	/**
	 * getter
	 * @return length of the car
	 */
	public static int getCARLENGTH(){
		return CARLENGTH;
	}
	
	/**
	 * getter
	 * @return width of the car
	 */
	public static int getCARWIDTH(){
		return CARWIDTH;
	}
}
