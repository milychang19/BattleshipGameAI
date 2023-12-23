/*BATTLESHIP CULMINATING PROJECT ICS4U1-01
 * Emily Chang and Amelia Voulgaris 
 * 
 * Classes within project include: Grid.java, ComputerGrid.java, Ship.java, BattleshipMain.java, Display.java
 * 
 * This is the ComputerGrid child class
 */

public class ComputerGrid extends Grid{
	public ComputerGrid() {
		super(); //Constructing everything from Grid 

	}

	//clear the symbol '-' in the grid
	public void clearSpace() {
		for(int r=0;r<grid.length;r++){
			for(int c=0;c<grid[r].length; c++){
				if(grid[r][c]=='-') {
					grid[r][c]='o';
				}
			}
		}
	}
}