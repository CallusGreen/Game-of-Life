package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	
	// ------------ Class Variables ------------ //

	private Grid theGameGrid;
	private Scanner scanner;
	private int numberOfIterations;
	private int maxIterations;

	// ------------- Constructors -------------- //

	public Game(Scanner scanner) {
		this.theGameGrid = new Grid();
		this.scanner = scanner;
		this.numberOfIterations = 0;
		this.maxIterations = 0;
	}

	// ---------------- Methods ---------------- //

	public void start() {

		// Asks user to start program
		if(askUserToBegin().equals("s")) {
			printLine();

			// Asks user if they would like to manually input cells or have them randomly assigned
			if(askUserForRandomOrManualCells().equals("m")){
				printLine();

				// Gets manual cells from the user
				List<Cell> userInputCells = getManualCells();
				getTheGameGrid().addCellsManually(userInputCells);

			} else {
				// Gets the number of cells to be places randomly
				printLine();
				getTheGameGrid().addCellsRandomly(askUserForNumberOfStartingCells());
			}

			// Asks user for the max number of iterations that should be run
			printLine();
			askUserForNumberOfIterations();

			// Prints lines showing the start of the game
			printStartSimulation();

			// While loop continues looping until all Cells are dead or the max number of iterations
			// has been reached
			while(true) {

				processCells();

				// Prints the living cells as coordinates
				getTheGameGrid().printLiveCells();

				if(getTheGameGrid().getLiveCells().size() == 0 || getNumberOfIterations() >= getMaxIterations()) {
					break;
				}

				setNumberOfIterations(getNumberOfIterations() + 1);
			}

			System.out.println();
			printNumberOfGenerationsSurvived();

		} else {
			printLine();
			System.out.println("Simulation finished.");
		}
	}

	// Asks user to start program
	public String askUserToBegin() {

		System.out.println("The Game of Life \n");
		System.out.println("Enter \"s\" to begin the simulation or anything else to quit.");

		String userInput = getScanner().nextLine();
		return processInput(userInput);

	}

	// Gives user choice of manual or random placement for starting living cells.
	public String askUserForRandomOrManualCells() {

		System.out.println("Enter \"m\" to place starting cells manually, or anything else for random placement.");

		String userInput = getScanner().nextLine();
		return processInput(userInput);

	}

	// Asks user for the number of random cells to place
	public int askUserForNumberOfStartingCells() {

		int numStartingCells = 0;
		int minRange = 0;
		int maxRange = 2147483647;

		while(true) {

			System.out.println("How many living cells would you like to begin with?");

			// catches exceptions thrown by wrong input type
			try {

				numStartingCells = Integer.parseInt(getScanner().nextLine());
				boolean numberIsWithinRange = (numStartingCells >= minRange) && (numStartingCells <= maxRange);

				if(numberIsWithinRange) {
					break;
				}

			} catch(Exception e) {}

			System.out.println("Not a valid input. \n");
		}

		return numStartingCells;
	}

	// Asks user for the max number of iterations for simulation
	public void askUserForNumberOfIterations() {

		while(true) {

			System.out.println("How many iterations would you like the simulation to run for?");

			try {
				int maxIterations = Integer.parseInt(getScanner().nextLine());
				if(maxIterations > 0) {
					setMaxIterations(maxIterations);
					break;
				}
			} catch(Exception e) {
				System.out.println("Please enter a number.");
			}
		}
	}

	// Method for user to input coordinate 
	public String askUserToEnterCoordinate(int counter) {

		if(counter == 0) {
			System.out.println("Please enter x-coordinate or enter \"e\" to finish entering coordinates.");
		} else {
			System.out.println("Please enter y-coordinate or enter \"e\" to finish entering coordinates.");
		}

		String userInput = getScanner().nextLine();
		String processedInput = processInput(userInput);

		if(processedInput.equals("e")) {
			return "exit";
		} else {
			//catches invalid inputs
			try {
				return "" + Integer.parseInt(processedInput);

			} catch (Exception e) {
				return "invalid";
			}
		}
	}

	public List<Cell> getManualCells(){
		
		ArrayList<Cell> cells = new ArrayList<Cell>();

		// Loops until user quits entering process
		while(true) {

			int xCoord = 0;
			int yCoord = 0;
			int counter = 0;
			String coordinateInput = "";

			// Ensures a pair of coordinates is always entered
			while(counter < 2) {

				// Gets an input from the user
				coordinateInput = askUserToEnterCoordinate(counter);

				// Breaks loop
				if(coordinateInput.equals("exit")){
					break;
					
					// In the case of an invalid input, the process is repeated
				} else if (coordinateInput.equals("invalid") || coordinateInput.isEmpty()){
					printLine();
					System.out.println("Not a valid input.");
					
					// x and y values are assigned
				} else {
					if(counter == 0) {
						xCoord = Integer.parseInt(coordinateInput);
						counter++;
					} else {
						yCoord = Integer.parseInt(coordinateInput);
						counter++;
					}
				}
			}

			// Breaks loop
			if(coordinateInput.equals("exit")) {
				break;
			}

			// New Cell is created and added
			Cell tempCell = new Cell(xCoord, yCoord, true);
			cells.add(tempCell);
			System.out.println("Added Cell: " + tempCell.cellCoordinates() + "\n");
		}
		
		return cells;
	}

	// A method called after the final iteration displaying the number of generations survived
	public void printNumberOfGenerationsSurvived() {

		String finalOutput = "The population survived for ";
		int numGenerations = getNumberOfIterations();

		if(numGenerations == 1) {
			finalOutput += numGenerations + " generation.";

		} else if(numGenerations >= getMaxIterations()){
			finalOutput += "more than " + numGenerations + " generations, with " + getTheGameGrid().getLiveCells().size() + " currently living cells.";

		} else {
			finalOutput += numGenerations + " generations.";
		}

		System.out.println(finalOutput);
	}

	// Method that updates the cells currently living on the grid.
	public void processCells() {

		for(Cell tempCell : getTheGameGrid().getLiveCells().values()) {

			int numLiveNeighbours = getTheGameGrid().howManyLiveNeighbours(tempCell);
			boolean cellInDestroyList = getTheGameGrid().cellExistsInMap(tempCell, getTheGameGrid().getCellsToBeDestroyed());

			// Kills any over-populated/under-populated cells
			if(numLiveNeighbours < 2 || numLiveNeighbours > 3 && !cellInDestroyList) {
				getTheGameGrid().killCell(tempCell);
			}
		}

		// Checks if any new cells can be created
		getTheGameGrid().cellCreation();

		getTheGameGrid().endOfIteration();
	}

	// Method for processing user input for checks
	public String processInput(String input) {

		String processedString = input;
		processedString = processedString.toLowerCase().trim();

		return processedString;
	}

	// prints a new line to space out the output
	public void printLine() {
		System.out.println();
	}

	// prints a clear set of line breaks
	public void printStartSimulation() {
		System.out.println("\n");
		System.out.println("---------------------------------");
		System.out.println("Simulation started..");
		System.out.println("---------------------------------\n");
	}

	// ----------- Getters & Setters ----------- //

	public Grid getTheGameGrid() {
		return theGameGrid;
	}

	public void setTheGameGrid(Grid theGameGrid) {
		this.theGameGrid = theGameGrid;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

}
