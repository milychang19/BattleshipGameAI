/* BATTLESHIP CULMINATING PROJECT ICS4U1-01
 * Emily Chang and Amelia Voulgaris 
 * 
 * Classes within project include: Grid.java, ComputerGrid.java, Ship.java, BattleshipMain.java, Display.java
 *
 * This is the BattleshipMain class
 */

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter; 

public class BattleshipMain {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args)throws Exception {

		//Construct ships from ship class - instantiate their attributes (name, size, char, modifiable size) 
		Ship[]ships=new Ship[5];

		//Sorted in order of size (largest to smallest)
		ships[0]=new Ship ("Carrier", 5, 'C');//name, size, char, size(modifiable) 
		ships[1]=new Ship("Battleship", 4, 'B');
		ships[2]=new Ship("Cruiser", 3, 'c');
		ships[3]=new Ship("Submarine", 3, 'S');
		ships[4]=new Ship("Destroyer", 2, 'D');

		//Construct opponent grid and computer grid 
		Grid opponentGrid=new Grid();
		ComputerGrid computerGrid=new ComputerGrid();

		//Local variables
		String opponentTeam="null";
		String respond="null";
		boolean turn=false;

		System.out.println("Welcome to Battleship!");

		//Check to see if the user has played before to determine how to move forward
		if(new File("Opponent Team Grid.txt").isFile()) { //If the file exists, then the user has a previous game

			System.out.println("It looks like you have played before.");
			System.out.println("Would you like to use old game data, or start a new game?");

			//Allow the user to resume an old game, or begin a new one 
			System.out.println("To start a new game, type 'new'. To resume your old game, type 'old'");
			respond=scan.nextLine();

			if(respond.equals("old")) { //User has chosen to resume an old game

				System.out.println("");
				System.out.println("Okay, your old game will resume.");
				System.out.println("");

				//Methods from grid class, reading the data from existing files in order to resume an old game
				opponentGrid.readGrid("Opponent Team Grid");
				computerGrid.readGrid("Computer Team Grid");				

				//Read old opponent team name & information for who's turn it is next  
				File read = new java.io.File("Extra Info.txt"); 
				Scanner input = new Scanner(read);	
				opponentTeam = input.nextLine();
				turn = input.nextBoolean();
				opponentGrid.setTarget(input.nextInt());
				input.close();
				
				//Reads the ship data 
				ships[0].readShips("Ship Info Carrier");
				ships[1].readShips("Ship Info Battleship");
				ships[2].readShips("Ship Info Cruiser");
				ships[3].readShips("Ship Info Submarine");
				ships[4].readShips("Ship Info Destroyer");

			}
			else { //User has chosen to start a new game. 
				System.out.println("");
				System.out.println("Okay, a new game will begin.");
				System.out.println("");
			}

		}
		else;

		//When a new game is begun, the user will be asked to enter a new team name
		if(!respond.equals("old")) {
			System.out.println("Insert your team name:");
			opponentTeam=scan.nextLine();
		}
		
		//Construct display classes for the computer and opponent 
		//This will show the game status information at every step of the game
		Display computerDisplay=new Display ("Amelia & Emily");
		Display opponentDisplay=new Display (opponentTeam); 

		//If the user is resuming an old game, this will call the read display methods from the display class 
		if(respond.equals("old")) {
			opponentDisplay.readDisplay("Opponent Team Display");
			computerDisplay.readDisplay("Computer Team Display");
		}
		else;

		//Displaying the score board 
		opponentDisplay.display();
		System.out.println("");
		computerDisplay.display();

	
		//Displaying the opponent's grid before the game begins 
		System.out.println("");
		System.out.println("This is the opponent's grid.");
		opponentGrid.displayGrid();

		//-------------------------------------------------------------------------------------------------------------
		//Game officially starts here
		System.out.println("");

		//Must decide who begins the game, if this is a new game
		if(!respond.equals("old")) {
			turn=coinToss(); 

			//When turn is true, the opponent/user starts the game 
			//When turn is false, the computer starts the game
			if(turn==true){
				System.out.println(opponentDisplay.getName()+" starts the game");
			}else{
				System.out.println(computerDisplay.getName()+" starts the game");
			}

			//Confirm result of the coin toss in case the other program has a conflicting result
			System.out.println("Do you agree with the coin toss result?");
			String start=scan.next();

			if(start.equals("no")) {
				if(turn==true){
					turn=false;
				}else if(turn==false){
					turn=true;
				}
			}

			//Place's computer ships (ONLY when starting a new game - not necessary for old games) 
			placeShip(ships, computerGrid);
		}
		else;

		//FOR REFERENCE (program does not use this method)
		computerGrid.writeGridView("Computer Grid View");
		
		//More local variables for run time 
		boolean gameDuration=true;
		boolean breakGame=false;

		while(gameDuration==true) {	

			if(turn==false) { 	//It is the computer's turn - starts by firing 
				System.out.println("It's "+computerDisplay.getName()+"'s turn.");
				System.out.println("Type 'go' to keep playing and 'stop' if you wish to stop.");
				String response = scan.next();

				//Allows the option to stop the game at any point
				if(response.equals("stop")) {
					breakGame=true;
					gameDuration=false; //gameDuration will be set to false, and the loop will end
				}

				else {
					//Calls firing AI method - the computer will fire at the opponent's grid
					fire(opponentGrid, opponentDisplay, computerDisplay, ships);

					//Displays game board 
					opponentDisplay.display();
					System.out.println("");
					computerDisplay.display();
					System.out.println("");

					//Displays grid 
					opponentGrid.displayGrid();
					System.out.println("");
					

					turn=true;

					//Check to see if any ships remain, if not then the game is over
					if(opponentDisplay.getShipRemain()==0||computerDisplay.getShipRemain()==0) {
						gameDuration=false;
						break;
					}
				}
			}
			else { //It is the opponent's turn
				System.out.println("It's "+opponentDisplay.getName()+"'s turn.");
				System.out.println("Type 'go' to keep playing and 'stop' if you wish to stop.");
				String response = scan.next();

				//Allows the option to stop the game at any point
				if(response.equals("stop")) {
					breakGame=true;
					gameDuration=false;
				}
				else {
					//Calls defend method, which determines the value of the coordinate the opponent hits
					defend(computerGrid, opponentDisplay, computerDisplay, ships);

					//Displays game board 
					opponentDisplay.display();
					System.out.println("");
					computerDisplay.display();
					System.out.println("");

					turn=false;

					//Check to see if any ships remain, if not then the game is over
					if(opponentDisplay.getShipRemain()==0||computerDisplay.getShipRemain()==0) {
						gameDuration=false;
					}
				}
			}
			

			//Writes the game data to a file
			opponentGrid.writeGrid("Opponent Team Grid");
			computerGrid.writeGrid("Computer Team Grid");
			opponentDisplay.writeDisplay("Opponent Team Display");
			computerDisplay.writeDisplay("Computer Team Display");

			//Writes the ship data to a file 
			ships[0].writeShips("Ship Info Carrier");
			ships[1].writeShips("Ship Info Battleship");
			ships[2].writeShips("Ship Info Cruiser");
			ships[3].writeShips("Ship Info Submarine");
			ships[4].writeShips("Ship Info Destroyer");

			//Also writes the opponent's name to a file & who's turn it is next
			PrintWriter write=new PrintWriter("Extra Info.txt");
			write.println(opponentTeam);
			write.println(turn);
			write.println(opponentGrid.getTarget());
			write.close();
		}

		if(breakGame==true) { //The user has chosen to stop playing		
			//Closing message 
			System.out.println("Game is paused. Data from current game is saved.");
		}
		else;

		System.out.println("----------------------------------------------------------");

		//Only if the game is complete 
		if(opponentDisplay.getShipRemain()==0) {
			System.out.println("GAME OVER");
			System.out.println("Amelia & Emily won!!!");

		}else if(computerDisplay.getShipRemain()==0){
			System.out.print("Congratulations, ");
			System.out.println(opponentTeam+" Won!!!");
			System.out.println("----------------------------------------------------------");
		}
	}


	//DETERMINE WHO GOES FIRST
	public static boolean coinToss() {
		int rand=(int)(Math.random()*2)+1;
		if(rand==1)
			return true;
		else
			return false;		
	}	


	//PLACING THE SHIPS 
	public static void placeShip(Ship ships[], ComputerGrid computerGrid) { 
		for (int i=0; i<ships.length; i++) {
			int row, column;
			int shipSize=ships[i].getSize();
			char shipSymb=ships[i].getShipSymbol();
			boolean shipOrientation=ships[i].getOrientation();

			//If the ship is horizontal 
			if(shipOrientation==false) {

				boolean checkCoord=false;
				do{
					checkCoord=true;
					//ROW 
					row=(int)(Math.random()*10);

					//COLUMN 
					column=(int)(Math.random()*shipSize);

					//Check if space is occupied 
					for(int check=0;check<shipSize;check++) {
						if(computerGrid.gridGetter(row, (column+check))!='o'){
							checkCoord=false;
						}
					}
				}while(checkCoord==false);				

				//Place the ships 
				for(int j=0;j<shipSize;j++) {
					computerGrid.gridSetter(row, (column+j), shipSymb);
				}


				for(int m=0;m<shipSize;m++) {
					if(row!=0) {
						computerGrid.gridSetter((row-1), (column+m), '-');
					}
					if(row!=9) {
						computerGrid.gridSetter((row+1), (column+m), '-');
					}
					if(column!=0) {
						computerGrid.gridSetter(row, (column-1), '-');
					}
					if((column+shipSize)!=9) {
						computerGrid.gridSetter(row, (column+shipSize), '-');
					}
				}
			}

			//If the ship is vertical 
			else{

				boolean checkCoord=false;
				do{
					checkCoord=true;
					//ROW
					row=(int)(Math.random()*shipSize); 

					//COLUMN 
					column=(int)(Math.random()*10);

					//Check if space is occupied 
					for(int check=0;check<shipSize;check++) {
						if(computerGrid.gridGetter((row+check), column)!='o')
							checkCoord=false;
					}
				}while(checkCoord==false);				

				//Place the ships 
				for(int j=0;j<shipSize;j++) {
					computerGrid.gridSetter((row+j), column, shipSymb);
				}

				for(int m=0;m<shipSize;m++) {
					if(column!=0) {
						computerGrid.gridSetter((row+m), (column-1), '-');
					}
					if(column!=9) {
						computerGrid.gridSetter((row+m), (column+1), '-');
					}
					if(row!=0) {
						computerGrid.gridSetter((row-1), column, '-');
					}
					if((row+shipSize)!=9) {
						computerGrid.gridSetter((row+shipSize), column, '-');
					}
				}
			}	
		}
		computerGrid.clearSpace();
	}


	//OPPONENT ATTACK OUR SHIPS
	public static void defend(ComputerGrid computerGrid, Display opponentDisplay, Display computerDisplay, Ship[] ships){
		System.out.println("Where do you want to fire at?");		
		String line=scan.next();
		char character=line.charAt(0);
		int ascii = (int) character;
		int r=ascii-65;
		int c=Integer.parseInt(""+line.charAt(1))-1;
		if (c==-1) {
			c=9;
		}

		computerDisplay.addShotsTaken();
		boolean check=false;

		do {
			check=true;
			if(computerGrid.gridGetter(r,c)=='x'||computerGrid.gridGetter(r,c)=='z') {
				check=false;
				System.out.println("You've already fired at this coordinate, please choose another one");
				line=scan.next();
				character=line.charAt(0);
				ascii = (int) character;
				r=ascii-65;
				c=Integer.parseInt(""+line.charAt(1))-1;
				if (c==-1) {
					c=9;
				}
			}
		}while(check==false);


		if (computerGrid.gridGetter(r, c)=='o'){
			System.out.println("MISS");	
			opponentDisplay.addMissesMade();
			computerGrid.gridSetter(r, c, 'x');
		}else{	
			opponentDisplay.addHitsMade();
			char shipHit=computerGrid.gridGetter(r, c);
			String shipName="null";
			for(int i=0;i<ships.length;i++) {
				if(ships[i].getShipSymbol()==shipHit) {
					shipName=ships[i].getName();
				}
			}
			computerGrid.gridSetter(r, c, 'z');
			if(computerGrid.checkRemain(shipHit)==0) {
				//if that ship is sunk
				System.out.println("HIT, SUNK, "+shipName);
				computerDisplay.setShipsRemain();
			}else {
				//if there are ships remain
				System.out.println("HIT, "+shipName);
			}
		}
	}


	//ATTACKING THEIR SHIPS
	public static void fire(Grid opponentGrid, Display opponentDisplay, Display computerDisplay, Ship[] ships) {
		boolean check=false;
		int row=0, column=0;
		boolean establishedFireCoord=false;
		int target=opponentGrid.getTarget();
		if(target==-1) {
			for(int i=0;i<5;i++) {
				if(ships[i].getSizeModified()<ships[i].getSize()&&ships[i].getSizeModified()!=0) {
					target=i;
					break;
				}
			}
		}

		if(target!=-1) {//we have a target
			int x=ships[target].getFirstRow();
			int y=ships[target].getFirstCol();
			int orientation=ships[target].getOrientationMod();
			establishedFireCoord=false;
			
			if(orientation>0){//hit at least twice already
				//The ship is vertical
				if(orientation==1) {
					for(int i=x+1;i<10;i++) {
						if(establishedFireCoord==false) {
							if(opponentGrid.gridGetter(i, y)=='o') {
								row=i;
								column=y;
								establishedFireCoord=true;
							}else if(opponentGrid.gridGetter(i, y)=='x'||opponentGrid.gridGetter(i, y)=='-'){
								break;
							}
						}
					}
					if(establishedFireCoord==false){//we can only go up now
						for(int i=x-1;i>=0;i--) {
							if(establishedFireCoord==false) {
								if(opponentGrid.gridGetter(i, y)=='o'||opponentGrid.gridGetter(i, y)=='-') {
									row=i;
									column=y;
									establishedFireCoord=true;
								}else if(opponentGrid.gridGetter(i, y)=='x'||opponentGrid.gridGetter(i, y)!=ships[target].getShipSymbol()){
									break;
								}
							}
						}
					}
					if(establishedFireCoord==false) {
						for(int i=x+1;i<10;i++) {
							if(opponentGrid.gridGetter(i, y)=='-') {
								row=i;
								column=y;
								establishedFireCoord=true;
								break;
							}
						}
					}
				}
				if(orientation==2) {//The ship is horizontal
					for(int l=y+1;l<10;l++) {
						if(establishedFireCoord==false) {
							if(opponentGrid.gridGetter(x, l)=='o') {
								row=x;
								column=l;
								establishedFireCoord=true;
							}else if(opponentGrid.gridGetter(x, l)=='x'||opponentGrid.gridGetter(x, l)=='-'){
								break;
							}
						}
					}
					if(establishedFireCoord==false){//we can only go left now
						for(int l=y-1;l>=0;l--) {
							if(establishedFireCoord==false) {
								if(opponentGrid.gridGetter(x, l)=='o'||opponentGrid.gridGetter(x, l)=='-') {
									row=x;
									column=l;
									establishedFireCoord=true;
								}else if(opponentGrid.gridGetter(x, l)=='x'||opponentGrid.gridGetter(x, l)!=ships[target].getShipSymbol()){
									break;
								}
							}
						}
					}
					if(establishedFireCoord==false) {
						for(int l=y+1;l<10;l++) {
							if(opponentGrid.gridGetter(x, l)=='-') {
								row=x;
								column=l;
								establishedFireCoord=true;
								break;
							}
						}
					}
				}	
			}
			//if we aren't sure the orientation yet (only hit once)
			if(orientation==0) {
				//assume that vertical and horizontal space are both enough
				boolean vertical=true;
				boolean horizontal=true;
				int spaceA=0;
				int spaceB=0;//in order to check from both direction

				//check vertical space first
				int verticalSpace;
				for(int i=0;i<10;i++) {//checking from the top
					if(opponentGrid.gridGetter(i, y)=='o'||opponentGrid.gridGetter(i, y)=='-') {
						spaceA++;
					}else if(opponentGrid.gridGetter(i,y)==ships[target].getShipSymbol()) {
						break;
					}else {
						spaceA=0;
					}
				}

				for(int i=9;i>=0;i--) {//checking from the bottom
					if(opponentGrid.gridGetter(i, y)=='o'||opponentGrid.gridGetter(i, y)=='-') {
						spaceB++;
					}else if(opponentGrid.gridGetter(i,y)==ships[target].getShipSymbol()) {
						break;
					}else {
						spaceB=0;
					}
				}
				verticalSpace=spaceA+spaceB+1;
				if(verticalSpace<ships[target].getSize()) {
					//if the space isn't enough, we can't go vertical
					vertical=false;
					ships[target].setOrientationMod(2);
				}

				//reset the counter
				spaceA=0;
				spaceB=0;

				//check horizontal space
				int horizontalSpace;
				for(int l=0;l<10;l++) {//checking from the left
					if(opponentGrid.gridGetter(x, l)=='o'||opponentGrid.gridGetter(x, l)=='-') {
						spaceA++;
					}else if(opponentGrid.gridGetter(x,l)==ships[target].getShipSymbol()) {
						break;
					}else {
						spaceA=0;
					}
				}
				for(int l=9;l>=0;l--) {//checking from the right
					if(opponentGrid.gridGetter(x,l)=='o'||opponentGrid.gridGetter(x,l)=='-') {
						spaceB++;
					}else if(opponentGrid.gridGetter(x, l)==ships[target].getShipSymbol()) {
						break;
					}else {
						spaceB=0;
					}
				}
				horizontalSpace=spaceA+spaceB+1;

				if(horizontalSpace<ships[target].getSize()) {
					//if the space isn't enough, we can't go horizontal
					horizontal=false;
					ships[target].setOrientationMod(1);
				}
				
				
				//start shooting
				if(vertical==true&&verticalSpace>=horizontalSpace) {
					if(x==0) {
						row=x+1;
						establishedFireCoord=true;
					}else if(x==9) {
						row=x-1;
						establishedFireCoord=true;
					}else {
						if(opponentGrid.gridGetter(x+1, y)=='o') {
							row=x+1;
							establishedFireCoord=true;
						}else if(opponentGrid.gridGetter(x-1,y)=='o') {
							row=x-1;
							establishedFireCoord=true;
						}
					}
					column=y;
				}

				if(horizontal==true&&establishedFireCoord==false) {
					//shooting horizontally
					if(y==0) {
						column=y+1;
						establishedFireCoord=true;
					}else if(y==9) {
						column=y-1;
						establishedFireCoord=true;
					}else {
						if(opponentGrid.gridGetter(x, y+1)=='o') {
							column=y+1;
							establishedFireCoord=true;
						}else if(opponentGrid.gridGetter(x, y-1)=='o') {
							column=y-1;
							establishedFireCoord=true;
						}
					}
					row=x;
				}
				
				//the opponent ship place next to each other
				if(establishedFireCoord==false&&vertical==true) {
					//vertically
					if(opponentGrid.gridGetter(x+1, y)=='-') {
						row=x+1;
					}else if(opponentGrid.gridGetter(x-1,y)=='-') {
						row=x-1;
					}
					column=y;
					establishedFireCoord=true;
				}

				if(establishedFireCoord==false&horizontal==true) {
					//horizontally
					if(opponentGrid.gridGetter(x, y+1)=='-') {
						column=y+1;
					}else if(opponentGrid.gridGetter(x, y-1)=='-') {
						column=y-1;
					}
					row=x;
					establishedFireCoord=true;
				}
			}
			
		}else {	//random firing when there's no target
			int countRun=0;
			do {
				countRun++;
				row=(int)(Math.random()*10);
				column=(int)(Math.random()*10);
				check=false;

				//make sure that it is an open coordinate 
				if(opponentGrid.gridGetter(row,column)=='o') {
					check=true;
				}
				
				if(countRun>=20&&opponentGrid.gridGetter(row,column)=='-') {
					check=true;
				}

				//find the minimum space we need
				int minSpace=0;
				if(check==true) {
					for(int i=4;i>=0;i--) {
						if(ships[i].getSizeModified()>0) {
							minSpace=ships[i].getSize();
							break;
						}
					}
				}
				//make sure that there are space for the minimum space
				boolean verticalSpace=true;

				//check vertical space first
				int countSpace=1;//include the space in current coordinate
				if(check==true) {
					for(int i=row-1;i>=0;i--) {
						if(opponentGrid.gridGetter(i, column)=='o'||opponentGrid.gridGetter(i, column)=='-') {
							countSpace++;
						}else {
							break;
						}
					}
					for(int i=row+1;i<10;i++) {
						if(opponentGrid.gridGetter(i, column)=='o'||opponentGrid.gridGetter(i, column)=='-') {
							countSpace++;
						}else {
							break;
						}
					}
					if (countSpace<minSpace) {
						verticalSpace=false;
					}
				}

				//if vertical isn't possible, then we need to check horizontal
				countSpace=1;//reset space counter
				if(verticalSpace==false) {
					for(int l=column-1;l>=0;l--) {
						if(opponentGrid.gridGetter(row, l)=='o'||opponentGrid.gridGetter(row, l)=='-') {
							countSpace++;
						}else {
							break;
						}
					}
					for(int l=column+1;l<10;l++) {
						if(opponentGrid.gridGetter(row, l)=='o'||opponentGrid.gridGetter(row, l)=='-') {
							countSpace++;
						}else {
							break;
						}
					}
					if (countSpace<minSpace) {
						check=false;
					}
				}
			}while(check==false);
		}
		char letterRow;
		int asci;
		asci=row+65;
		letterRow=(char)asci;
		System.out.println("Fire at "+letterRow+(column+1));
		opponentDisplay.addShotsTaken();

		System.out.println("Did we hit the ship?");
		String respond=scan.next();

		if(respond.equals("no")) {
			computerDisplay.addMissesMade();
			opponentGrid.gridSetter(row, column,'x'); //Set the coordinate on the grid equal to x 
		}
		else{
			computerDisplay.addHitsMade();
			System.out.println("What type of boat did we hit? Type with the first letter capitalized for all cases BUT cruiser");
			char shipSymb;
			String name="null";
			int sizeFixed=0; 
			int sizeMod=0;
			boolean correctInput=false;

			do {
				shipSymb=scan.next().charAt(0);
				for(int i=0;i<5;i++) {
					if(shipSymb==ships[i].getShipSymbol()) {
						target=i;
						correctInput=true;
						break;
					}
				}
				if(correctInput==false) {
					System.out.println("Error. Please enter again following the correct input instrutions.");
				}

			}while(correctInput==false);

			opponentGrid.gridSetter(row, column, shipSymb);
			ships[target].setSizeMod(); //decreases the size by 1
			sizeFixed=ships[target].getSize(); //ex. 2
			sizeMod=ships[target].getSizeModified();
			name=ships[target].getName(); //ex. Battleship
			//We've hit the ship once
			if(sizeFixed-sizeMod==1) {
				opponentGrid.setTarget(target);
				ships[target].setFirstRow(row);
				ships[target].setFirstCol(column);
			}

			//We've hit the ship twice
			if(sizeFixed-sizeMod==2) {
				opponentGrid.setTarget(target);
				int test = opponentGrid.checkRowCol(shipSymb);
				//0=NULL, 1=VERTICAL, 2=HORIZONTAL || TRUE=VERTICAL, FALSE=HORIZONTAL****
				if(test==1) {
					ships[target].setOrientationMod(1); //orientation for other ship now has value, vertical
				}else if(test==2) {
					ships[target].setOrientationMod(2);//horizontal 
				}
			}

			//if we've sunk the ship
			if(sizeMod==0) {
				System.out.println("");
				System.out.println("The computer has sunk your "+name);
				System.out.println("");
				int orientation=ships[target].getOrientationMod();
				int firstRow=ships[target].getFirstRow();
				int firstCol=ships[target].getFirstCol();

				opponentGrid.setSpace(orientation, firstRow, firstCol, shipSymb);
				opponentDisplay.setShipsRemain();
				//reset the note we made
				opponentGrid.setTarget(-1);
				//reset target

			}
		}
	}
}
