# BattleshipGameAI

**Project Overview:**

The `BattleshipGameAI` project is a culmination of the ICS4U1 course, created by Emily Chang and Amelia Voulgaris. The project was developed between December 2020 and January 2021 and is written in Java, utilizing object-oriented programming principles.

**Description:**

`BattleshipGameAI` is a computer-based adaptation of the popular board game Battleship. In this game, players have the opportunity to compete against an AI opponent. The AI strategically places battleships before the start of the game and anticipates the player's ship placement during gameplay. The system records hits and misses, calculates remaining ships, and prints the results of the game upon completion.

**Classes:**

1. **Grid.java:** Represents the main grid used for the game.
2. **ComputerGrid.java:** Manages the grid for the computer's ships. It inheritants the attributes of Grid class.
3. **Ship.java:** Defines the `Ship` class, storing information about ship types (Carrier, Battleship, Cruiser, Submarine, Destroyer).
4. **BattleshipMain.java:** Main class for initiating and running the Battleship game.
5. **Display.java:** Handles the display and user interface aspects of the game.

**Gameplay:**

- The AI strategically places battleships before the game starts.
- The AI anticipates the player's ship placement and records hits and misses.
- The game calculates remaining ships and displays results upon completion.

**Files:**

- The project stores information about the player's and computer's grids in separate files.
- Ships information.

**How to Run:**

To run the `BattleshipGameAI`, execute the `BattleshipMain.java` file.

**Note:**

The player must record their own grid, the file stored of the opponent's grid only shows the hits and misses AI did.

**Acknowledgments:**

This project was developed as part of the ICS4U1 course and serves as a demonstration of object-oriented programming concepts in Java. We had a battleship tournament by the end of the semester and got a decent second place.
