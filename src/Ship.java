/*BATTLESHIP CULMINATING PROJECT ICS4U1-01
 * Emily Chang and Amelia Voulgaris 
 * 
 * Classes within project include: Grid.java, ComputerGrid.java, Ship.java, BattleshipMain.java, Display.java
 * 
 * This is the Ship class
 */

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class Ship {
	//Defining attributes 
	protected String name; 
	protected int size, sizeMod, orientationMod;//0=NULL, 1=VERTICAL, 2=HORIZONTAL*****
	protected boolean orientation; 
	protected char shipSymbol; 
	protected int firstRow, firstCol;

	//constructor
	public Ship(String nameGiven, int sizeGiven,char symbolGiven){
		name=nameGiven;
		size=sizeGiven;
		sizeMod=sizeGiven;
		orientationMod=0;
		firstRow=-1;
		firstCol=-1;
		
		int random=(int)(Math.random()*2)+1;
		if (random==1){
			orientation= true;
		}else{
			orientation=false;
		}
		shipSymbol=symbolGiven;
	}

	//Defining methods 
	public String getName(){
		return name;
	}
	public int getSize(){
		return size;
	}
	public boolean getOrientation(){
		return orientation;
	}
	public char getShipSymbol(){
		return shipSymbol;
	}
	public int getSizeModified() {
		return sizeMod;
	}
	public int getOrientationMod() {
		return orientationMod;
	}

	public void setSizeMod() {
		sizeMod--;
	}

	public void setOrientationMod(int changeOrientation) {
		orientationMod=changeOrientation;
	}
	//NEW CODES
	public void setFirstRow(int x) {
		firstRow=x;
	}
	
	public void readShips(String fileName) throws Exception {
		File read = new java.io.File(fileName+".txt"); 
		Scanner input = new Scanner(read);	

		name = input.nextLine();
		size = input.nextInt();
		sizeMod = input.nextInt();
		orientation = input.nextBoolean();
		orientationMod = input.nextInt();
		firstRow = input.nextInt();
		firstCol = input.nextInt();
		shipSymbol = input.next().charAt(0);

		input.close(); 
	}
	
	public void writeShips(String fileName) throws Exception {
		PrintWriter write=new PrintWriter(fileName+".txt");

		write.println(name);
		write.println(size);
		write.println(sizeMod);
		write.println(orientation);
		write.println(orientationMod);
		write.println(firstRow);
		write.println(firstCol);
		write.println(shipSymbol);

		write.close();
	}
	
	public void setFirstCol(int y) {
		firstCol=y;
	}
	
	public int getFirstRow() {
		return firstRow;
	}
	
	public int getFirstCol() {
		return firstCol;
	}
	

}
