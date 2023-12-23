/*BATTLESHIP CULMINATING PROJECT ICS4U1-01
 * Emily Chang and Amelia Voulgaris 
 * 
 * Classes within project include: Grid.java, ComputerGrid.java, Ship.java, BattleshipMain.java, Display.java
 * 
 * This is the Display class
 */

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Display {

	//display score attributes
	protected String teamName;
	protected int shotsTaken;
	protected int hitsMade;
	protected int missesMade;
	protected int shipsRemain;
	
	//constructor 
	public Display (String nameGiven)
	{
		teamName=nameGiven;
		shotsTaken=0;
		hitsMade=0;
		missesMade=0;
		shipsRemain=5;
	}
	
	public String getName(){
		return teamName;
	}
	
	//behaviours & setter
	public void addShotsTaken(){
		shotsTaken++;
	}
	
	public void addHitsMade(){
		hitsMade++;
	}
	
	public void addMissesMade(){
		missesMade++;
	}
	
	public void setShipsRemain(){
		shipsRemain--;
	}
	public int getShipRemain() {
		return shipsRemain;
	}
	
	public void display(){
		System.out.println("["+teamName+"]");
		System.out.println("# of shots taken:"+shotsTaken);
		System.out.println("# of hits made:"+hitsMade);
		System.out.println("# of misses made:"+missesMade);
		System.out.println("# of ships remaining: "+shipsRemain);
	}
	
	//Write display to a file 
	public void writeDisplay(String fileName) throws Exception{
		PrintWriter write=new PrintWriter(fileName+".txt");
		
		write.println(teamName);
		write.println(shotsTaken);
		write.println(hitsMade);
		write.println(missesMade);
		write.println(shipsRemain);

		write.close();
	}
	
	//Read display from a file
	public void readDisplay(String fileName)throws Exception {
		File read = new java.io.File(fileName+".txt"); 
		Scanner input = new Scanner(read);	
	
		teamName = input.nextLine();
		shotsTaken = input.nextInt();
		hitsMade = input.nextInt();
		missesMade = input.nextInt();
		shipsRemain = input.nextInt();
		
		input.close(); 
	}

}