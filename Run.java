import java.util.Scanner;

public class Run {
	public static void main(String[] args) {
		// Scanner creation
		Scanner scanner = new Scanner(System.in);

		// Welcome
		System.out.println("Welcome to Minecraft");
		System.out.println();

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
		Entity player = game.getEntities()[0];
		Entity zombie = game.getEntities()[1];
		

		cleanTerminal();
		writeWorld(game, player, zombie);
		player.move(scanner.nextInt());
		writeWorld(game, player, zombie);


		scanner.close();
	}

	private static void cleanTerminal() {
    	System.out.print("\033[H\033[2J");
    	System.out.flush();
	}

	private static void writeWorld(Game game, Entity player, Entity zombie) {
		int worldSize = game.getWorld().getGrid().length;
		System.out.print("╔");
		writeLineWorld(worldSize-1, "═══╦");
		System.out.println("═══╗");

		System.out.print("║");
		writeLineWorld(worldSize, "   ║", player, zombie);
		System.out.println();

		System.out.print("╚");
		writeLineWorld(worldSize-1, "═══╩");
		System.out.println("═══╝");
	}

	private static void writeLineWorld(int worldSize, String c) {
		for (int i = 0; i < worldSize; i++) {
			System.out.print(c);
		}
	}

	private static void writeLineWorld(int worldSize, String c, Entity p, Entity z) {
		
		String first = c.substring(0, 1);
		String last = c.substring(2);
		for (int i = 1; i <= worldSize; i++) {
			if (p.getPosition() == i) {
				System.out.print(first + "o" + last);
			} else if (z.getPosition() == i) {
				System.out.print(first + "x" + last);
			} else {
				System.out.print(c);
			}
		}
	}
}
