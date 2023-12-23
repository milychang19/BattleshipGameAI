/*BATTLESHIP CULMINATING PROJECT ICS4U1-01
 * Emily Chang and Amelia Voulgaris 
 * 
 * Classes within project include: Grid.java, ComputerGrid.java, Ship.java, BattleshipMain.java, Display.java
 * 
 * This is the Grid class
 */

import java.io.*;
import java.util.Scanner;

public class Grid {
	protected char grid[][]=new char[10][10];
	protected int target;

	public Grid () {
		//Empty Grid 
		for(int r=0;r<grid.length;r++) {
			for(int c=0;c<grid[r].length;c++) {
				grid[r][c]='o';
			}

		}
		target=-1;
	}

	//WRITE TO FILE.. JUST TO VIEW (for reference) - the game does not use this method other than when the game first begins 
	public void writeGridView(String fileName) throws Exception{
		PrintWriter write=new PrintWriter(fileName+".txt");
		write.print("   ");
		for(int i=0;i<grid.length;i++) {
			write.print((i+1)+"  ");
		}
		write.println("");
		char a='A';
		for(int r=0;r<grid.length;r++, ++a) {
			write.print(a+ "  ");
			for(int c=0;c<grid[r].length;c++) {
				write.print(grid[r][c]+"  ");
			}
			write.println("");
		}
		write.close();
	}

	//WRITE TO FILE
	public void writeGrid(String fileName) throws Exception{
		PrintWriter write=new PrintWriter(fileName+".txt");

		for(int r=0;r<grid.length;r++) {
			for(int c=0;c<grid[r].length;c++) {
				write.print(grid[r][c]);
			}
		}
		write.close();
	}

	//READ TO FILE 
	public void readGrid(String fileName)throws Exception{
		//read it as one string, and then read each character for the grid 
		File read = new java.io.File(fileName+".txt"); 
		Scanner input = new Scanner(read);	
		String getData="null";

		while (input.hasNext()) {  
			getData = input.next();
		}
		input.close(); 	

		//Stores data into the grid coordinates 
		int i=0;
		for(int r=0;r<grid.length;r++) {
			for(int c=0;c<grid[r].length;c++) {
				grid[r][c]=getData.charAt(i);
				i++;
			}

		}
	}

	//DRAW GRID
	public void displayGrid() {
		System.out.print("   ");
		for(int i=0;i<grid.length;i++) {
			System.out.print((i+1)+"  ");
		}
		System.out.println("");
		char a='A';
		for(int r=0;r<grid.length;r++, ++a) {
			System.out.print(a+ "  ");
			for(int c=0;c<grid[r].length;c++) {
				if(grid[r][c]=='-') {
					System.out.print("o  ");
				}else {
					System.out.print(grid[r][c]+"  ");	
				}
			}
			System.out.println("");
		}
	}	

	//Get the value of certain coordinates 
	public char gridGetter(int x, int y) {
		return grid[x][y];
	}

	//SETTING COORDINATES WITH DIFFERENT VALUES 
	public void gridSetter(int x, int y, char shipSymbol) {
		grid[x][y]=shipSymbol;
	}

	//Check that certain ship values exist (used in Defend method)
	public int checkRemain(char coordState){ //count = 0 means that it doesn't exist 
		int count=0;
		for(int r=0;r<grid.length;r++){
			for(int c=0;c<grid[r].length; c++){
				if(grid[r][c]==coordState){
					count++;
				}
			}
		}	
		return count;
	}

	public int checkRowCol(char shipSymb) { 
		int[]rows=new int[5]; //(3, 1) (3, 2) 
		int[]cols=new int[5];
		int i=0;
		int check=0; //check=0 means that its not applicable 

		for(int r=0;r<grid.length;r++){ 
			for(int c=0;c<grid[r].length; c++){
				if(grid[r][c]==shipSymb) { //find the ship symbol 

					//store the rows in one array and the columns in one array
					rows[i]=r; //store all the rows where the ship occurs in one array
					cols[i]=c; //store all the columns where the ship occurs in one array
					i++;
				}
				else;
			}
		}

		if(rows[0]==rows[1]) //rows are all the same 
			check=2; //check = 2 means horizontal = false
		else if(cols[0]==cols[1]) //columns are all the same
			check=1; //check = 1 means vertical = true
		else; //check is 0, ship has only been hit once

		return check;
	}

	public void setTarget(int index) {
		target=index;
	}

	public int getTarget() {
		return target;
	}


	public void setSpace(int orientation, int row, int col, char ship) {
		if(orientation==1) {
			int left=col-1;
			int right=col+1;
			for(int i=0;i<10;i++) {
				if(grid[i][col]==ship) {
					if(left>=0) {//can go left
						if(grid[i][left]=='o') {
							grid[i][left]='-';
						}
					}
					if(right<10) {
						if(grid[i][right]=='o') {
							grid[i][right]='-';
						}
					}
				}
				if(i>0&&i<9) {
					if(grid[i][col]==ship) { 
						if(grid[i-1][col]=='o') {
							grid[i-1][col]='-';
						}
						if(grid[i+1][col]=='o') {
							grid[i+1][col]='-';
						}
					}
				}
			}
		}
		if(orientation==2) {
			int up=row-1;
			int down=row+1;
			for(int l=0;l<10;l++) {
				if(grid[row][l]==ship) {
					if(up>=0) { //can go up
						if(grid[up][l]=='o') {
							grid[up][l]='-';
						}
					}
					if(down<10) {
						if(grid[down][l]=='o'){
							grid[down][l]='-';
						}
					}
				}
				if(l>0&&l<9) {
					if(grid[row][l]==ship) { 
						if(grid[row][l-1]=='o') {
							grid[row][l-1]='-';
						}
						if(grid[row][l+1]=='o') {
							grid[row][l+1]='-';
						}
					}
				}
			}
		}
	}
}

