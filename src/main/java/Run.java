import java.io.Reader;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

public class Run {
	public static void main(String[] args) {

		// Create a default(+jansi) terminal
		Terminal terminal = null;
		terminal = setTerminal(terminal);
		terminal.enterRawMode();

		// Create a default Character Reader
		// Reader keyReader = null;
		// keyReader = setKeyReader(terminal, keyReader);
		BindingReader keyReader = new BindingReader(terminal.reader());
		KeyMap<String> keyMap = new KeyMap<>();
		setKeyMap(keyMap);

		// Create a default Line Reader
		LineReader lineReader = null;
		lineReader = setLineReader(terminal, lineReader);

		// Welcome
		cleanTerminal(terminal);
		terminal.writer().println("Welcome to Minecraft");
		terminal.writer().println();

		// Set Player starting position
		// String playerName = ""; XXX
		// playerName = setPlayerName(terminal, lineReader, playerName); XXX

		cleanTerminal(terminal);

		// Set Player starting position
		// int playerPosition = -1; XXX
		// playerPosition = setPlayerPosition(terminal, lineReader, playerPosition); XXX

		// Game creation
		Game game = new Game("", 1);
		// Game game = new Game(playerName, playerPosition); XXX
		Entity playerEntity = game.getEntities()[0];
		Entity zombieEntity = game.getEntities()[1];

		boolean endGame = false;

		// KeyEvent.VK_LEFT 37
		// KeyEvent.VK_UP 38
		// keyEvent.VK_RIGHT 39
		// KeyEvent.VK_DOWN 40

		try {
			do {
				cleanTerminal(terminal);
				terminal.writer().flush();
				writeWorld(game, playerEntity, zombieEntity);

				endGame = playerTurn(keyReader, lineReader, keyMap, playerEntity, zombieEntity, game, terminal);

			} while (!endGame);

		} finally {
			cleanTerminal(terminal);
			terminal.writer().println("GoodBye!");
			terminal.writer().flush();
			try {
				terminal.close();
			} catch (Exception e) {
			}
		}

	}

	// Terminal & Readers

	private static Terminal setTerminal(Terminal terminal) {
		do {
			try {
				terminal = TerminalBuilder.builder()
						.system(true)
						.jansi(true)
						.build();
			} catch (Exception e) {
			}
		} while (terminal == null);
		return terminal;
	}

	private static void cleanTerminal(Terminal terminal) {
		terminal.puts(Capability.clear_screen);
		terminal.writer().println();
	}

	private static Reader setKeyReader(Terminal terminal, Reader cReader) {
		do {
			try {
				cReader = terminal.reader();
			} catch (Exception e) {
			}
		} while (cReader == null);
		return cReader;
	}

	private static void setKeyMap(KeyMap<String> keyMap) {
		keyMap.bind("UP", "\033[A");
		keyMap.bind("DOWN", "\033[B");
		keyMap.bind("RIGHT", "\033[C");
		keyMap.bind("LEFT", "\033[D");
		keyMap.bind("EXIT", "q");
		keyMap.bind("USE", "f");
		keyMap.bind("1", "1");
		keyMap.bind("2", "2");
		keyMap.bind("3", "3");
		keyMap.bind("Y", "y");
		keyMap.bind("Y", "Y");
		keyMap.bind("N", "n");
		keyMap.bind("N", "Y");
	}

	private static LineReader setLineReader(Terminal terminal, LineReader lineReader) {
		do {
			try {
				lineReader = LineReaderBuilder.builder()
						.terminal(terminal)
						.build();

			} catch (Exception e) {
			}
		} while (lineReader == null);
		return lineReader;
	}

	// Player's setters

	private static String setPlayerName(Terminal terminal, LineReader lineReader, String playerName) {
		do {
			try {
				terminal.writer().println("What is your name?");
				terminal.writer().print("Name: ");
				terminal.writer().flush();
				playerName = lineReader.readLine();
			} catch (Exception e) {
				cleanTerminal(terminal);
			}
		} while (playerName == "");
		return playerName;
	}

	private static int setPlayerPosition(Terminal terminal, LineReader lineReader, int playerPosition) {
		do {
			try {
				terminal.writer().println("Where do you want to start?(they say that position 10 gives you luck)");
				terminal.writer().print("Position(1-10): ");
				terminal.writer().flush();
				playerPosition = Integer.parseInt(lineReader.readLine());
			} catch (Exception e) {
				cleanTerminal(terminal);
			}
		} while (playerPosition == -1);
		return playerPosition;
	}

	// World's printer

	private static void writeWorld(Game game, Entity player, Entity zombie) {
		int worldSize = game.getWorld().getGrid().length;
		System.out.print("╔");
		writeLineWorld(worldSize - 1, "═══╦");
		System.out.println("═══╗");

		System.out.print("║");
		writeLineWorld(worldSize, "   ║", player, zombie);
		System.out.println();

		System.out.print("╚");
		writeLineWorld(worldSize - 1, "═══╩");
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

	// Entity turns

	private static boolean playerTurn(BindingReader keyReader, LineReader lineReader, KeyMap<String> keyMap, Entity playerEntity, Entity zombieEntity, Game game, Terminal terminal) {
		String action;
		boolean validMainInput;
		boolean endGame = false;

		do {
			validMainInput = true;
			action = keyReader.readBinding(keyMap);

			if (action == "LEFT" || action == "RIGHT") {
				Player player = (Player) playerEntity;
				int tmpPosition = player.calculateMovement(action);

				if (tmpPosition == zombieEntity.getPosition()) { // in case the player collides with the zombie
					if (action == "LEFT") {
						player.move(tmpPosition + 1);
					} else {
						player.move(tmpPosition - 1);
					}
				} else {
					player.move(tmpPosition);
				}

				game.toggleTurn();

			} else if (action == "1") {

			} else if (action == "2") {

			} else if (action == "3") {

			} else if (action == "USE") {

				game.toggleTurn();

			} else if (action == "EXIT") {
				boolean validExitInput = false;

				do {
					terminal.writer().println("Are you sure you want exit?[Y/n]: ");
					terminal.writer().flush();
					action = lineReader.readLine();

					if (action.toUpperCase().equals("Y")) {
						endGame = true;
						validExitInput = true;
					} else if (action.toUpperCase().equals("N")) {
						validExitInput = true;
						validMainInput = true;
					} else {
						cleanTerminal(terminal);
					}

				} while (!validExitInput);

			} else { // if it is not an linked key
				validMainInput = false;
			}

		} while (!validMainInput);

		return endGame;
	}

}
