import java.util.Scanner;

import logic.Game;

public class Main {

	public static void main(String[] args) {

		Game theGameOfLife = new Game(new Scanner(System.in));

		theGameOfLife.start();

	}
}
