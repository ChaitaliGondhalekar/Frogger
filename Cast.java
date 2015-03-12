package frogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;

/**
 *@authors Chaitali Gondhalekar, Jingru Zhu 
 *
 */
public class Cast extends Observable{

		static int noOfCars;
		public ArrayList<Car> list =new ArrayList<Car>();
		
		Timer timer;
		Random randCarGenerate = new Random();
		
		
		/**
		 * generates cars randomly
		 */
		public void generateCars(){
			//randomly choose number of cars to be generated
			noOfCars = randCarGenerate.nextInt(3) + 1;
			for(int i=0;i<noOfCars;i++){
				Car car1=new Car();
//				for(int j=0;j<list.size();i++){
//					if(car1.dy!=list.get(j).dy){
//						if(list.get(j).x<0 || list.get(j).x>800){
//							addToList(car1);
//							break;
//							}
//						}
//				}
				addToList(car1);
			}
			for(Car car:list){
				car.dx=Car.velocity;
			}
		}
		/**
		 * adds new Car to list
		 * @param car
		 */
		public void addToList(Car car){
			list.add(car);
		}
		/**
		 * removes car from the list
		 * @param car
		 */
		public void removeFromList(Car car){
			list.remove(car);
		}
		/**
		 * checks if car has gone off the road
		 * @param car
		 */
		public void removeCar(Car car){
			
				if(car.x<-10 || car.x>810){
					removeFromList(car);
			}
		}
		
		/**
		 * notifies View of the changed properties of cars in the list
		 */
		void updateCar() {
			// TODO Auto-generated method stub
			setChanged();
			notifyObservers();			
		}
}
