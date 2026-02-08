import java.util.Scanner;

public class Run {
	public static void main(String[] args) {
		// Welcome
		System.out.println("Welcome to Minecraft");
		System.out.println();

		// Scanner creation
		Scanner scanner = new Scanner(System.in);

		// Set Player's name
		System.out.println("What is your name?");
		System.out.print("Name: ");
		String playerName = scanner.next();
		System.out.println();

		// Set Player starting position
		System.out.println("Where do you want to start?(they say that position 10 gives you luck)");
		System.out.print("Position(1-10): ");
		int playerPosition = scanner.nextInt();
		System.out.println();

		// Game creation
		Game game = new Game(playerName, playerPosition);
		

		scanner.close();
	}
}
