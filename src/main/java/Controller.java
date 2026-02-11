import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

public class Controller {
	private Terminal terminal;
	// private Reader keyReader;
	private BindingReader keyReader;
	private KeyMap<String> keyMap;
	private LineReader lineReader;

	private String playerName;
	private int playerStartingPosition;

	private GameState gameState;
	private boolean endGame;

	private Entity playerEntity;
	private Entity zombieEntity;

	// private ;
	// private ;
	// private ;
	// private ;
	// private ;
	// private ;
	// private ;

	public void run() {

		// Create a default(+jansi) terminal
		setTerminal();
		this.terminal.enterRawMode();

		// Create a default Character Reader
		// keyReader = setKeyReader(terminal, keyReader);
		this.keyReader = new BindingReader(this.terminal.reader());
		this.keyMap = new KeyMap<>();
		setKeyMap();

		// Create a default Line Reader
		this.lineReader = setLineReader();

		// Welcome
		cleanTerminal();
		this.terminal.writer().println("Welcome to Minecraft");
		this.terminal.writer().println();
		/*
		 * // Set Player starting position
		 * setPlayerName();
		 * 
		 * cleanTerminal();
		 * 
		 * // Set Player starting position
		 * setPlayerPosition();
		 */
		// Game creation
		// this.gameState = new GameState(this.playerName, this.playerStartingPosition);
		this.gameState = new GameState("Marcos", 1); // XXX - para testear
		this.playerEntity = this.gameState.getEntities()[0];
		this.zombieEntity = this.gameState.getEntities()[1];

		this.endGame = false;

		try {
			do {
				cleanTerminal();
				this.terminal.writer().flush();

				writeWorld();
				writePlayerStatus();

				System.out.println(this.gameState.getTurn());

				if (this.gameState.getTurn() == this.playerEntity) {
					this.endGame = playerTurn();
				} else {
					try {
						Thread.sleep(3);
					} catch (Exception e) {
					}
					zombieTurn();
					this.gameState.toggleTurn();

					// this.endGame = validatePlayerState();
				}

			} while (!this.endGame);

		} finally {
			cleanTerminal();
			terminal.writer().println("GoodBye!");
			terminal.writer().flush();
			try {
				terminal.close();
			} catch (Exception e) {
			}
		}

	}

	// Terminal & Readers

	private void setTerminal() {
		try {
			this.terminal = TerminalBuilder.builder()
					.system(true)
					.jansi(true)
					.build();
		} catch (Exception e) {
		}
	}

	private void cleanTerminal() {
		this.terminal.puts(Capability.clear_screen);
		this.terminal.writer().println();
	}

	/*
	 * private Reader setKeyReader() {
	 * do {
	 * try {
	 * this.keyReader = this.terminal.reader();
	 * } catch (Exception e) {
	 * }
	 * } while (this.keyReader == null);
	 * return this.keyReader;
	 * }
	 */

	private void setKeyMap() {
		this.keyMap.bind("UP", "\033[A");
		this.keyMap.bind("DOWN", "\033[B");
		this.keyMap.bind("RIGHT", "\033[C");
		this.keyMap.bind("LEFT", "\033[D");
		this.keyMap.bind("SPACE", " ");
		this.keyMap.bind("EXIT", "q");
		this.keyMap.bind("USE", "f");
		this.keyMap.bind("1", "1");
		this.keyMap.bind("2", "2");
		this.keyMap.bind("3", "3");
		this.keyMap.bind("Y", "y");
		this.keyMap.bind("Y", "Y");
		this.keyMap.bind("N", "n");
		this.keyMap.bind("N", "Y");
	}

	private LineReader setLineReader() {
		do {
			try {
				this.lineReader = LineReaderBuilder.builder()
						.terminal(this.terminal)
						.build();
			} catch (Exception e) {
			}
		} while (this.lineReader == null);
		return this.lineReader;
	}

	// Player's setters

	private String setPlayerName() {
		do {
			try {
				this.terminal.writer().println("What is your name?");
				this.terminal.writer().print("Name: ");
				this.terminal.writer().flush();
				this.playerName = this.lineReader.readLine();
			} catch (Exception e) {
				cleanTerminal();
			}
		} while (this.playerName == "");
		return this.playerName;
	}

	private int setPlayerPosition() {
		do {
			try {
				this.terminal.writer().println("Where do you want to start?(they say that position 10 gives you luck)");
				this.terminal.writer().print("Position(1-10): ");
				this.terminal.writer().flush();
				this.playerStartingPosition = Integer.parseInt(this.lineReader.readLine());
				if (this.playerStartingPosition <= 0 || this.playerStartingPosition >= 11) {
					throw new IllegalArgumentException("Position must be between 1 and 10 inclusive");
				}
			} catch (Exception e) {
				this.playerStartingPosition = -1;
				cleanTerminal();
			}
		} while (this.playerStartingPosition == -1);
		return this.playerStartingPosition;
	}

	// World's printer

	private void writeWorld() {
		int worldSize = World.WORLD_SIZE;
		System.out.print("╔");
		writeLineWorld(worldSize - 1, "═══╦");
		System.out.println("═══╗");

		System.out.print("║");
		writeLineWorld(worldSize, "   ║", this.playerEntity, this.zombieEntity);
		System.out.println();

		System.out.print("╚");
		writeLineWorld(worldSize - 1, "═══╩");
		System.out.println("═══╝");
	}

	private void writeLineWorld(int worldSize, String c) {
		for (int i = 0; i < worldSize; i++) {
			System.out.print(c);
		}
	}

	private void writeLineWorld(int worldSize, String c, Entity p, Entity z) {

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

	private void writePlayerStatus() {
		// this.terminal.writer();
	}

	// Entity turns

	private boolean playerTurn() {
		String action;
		boolean validMainInput;
		this.endGame = false;

		do {
			validMainInput = true;
			action = this.keyReader.readBinding(this.keyMap);

			if (action == "LEFT" || action == "RIGHT") {
				Player player = (Player) this.playerEntity;
				int tmpPosition = player.calculateMovement(action);

				// in case player doesn't actually move (invalid turn)
				if (tmpPosition != this.playerEntity.getPosition()) { // TEST
					this.gameState.toggleTurn();
				}

				// in case the player collides with the zombie
				if (tmpPosition == this.zombieEntity.getPosition()) {
					if (action == "LEFT") {
						player.move(tmpPosition + 1);
					} else {
						player.move(tmpPosition - 1);
					}
				} else {
					player.move(tmpPosition);
				}

			} else if (action == "1") {

			} else if (action == "2") {

			} else if (action == "3") {

			} else if (action == "USE") {

				this.gameState.toggleTurn();

			} else if (action == "EXIT") {
				boolean validExitInput = false;

				do {
					this.terminal.writer().println("Are you sure you want exit?[Y/n]: ");
					this.terminal.writer().flush();
					action = this.lineReader.readLine();

					if (action.toUpperCase().equals("Y")) {
						this.endGame = true;
						validExitInput = true;
					} else if (action.toUpperCase().equals("N")) {
						validExitInput = true;
						validMainInput = true;
					} else {
						cleanTerminal();
					}

				} while (!validExitInput);

			} else { // if it is not an linked key
				validMainInput = false;
			}

		} while (!validMainInput);

		return this.endGame;
	}

	private void zombieTurn() {
		Zombie zombie = (Zombie) this.zombieEntity;
		int tmpPosition = zombie.calculateMovement(this.playerEntity);
		System.out.println(tmpPosition);
		System.out.println(this.playerEntity.getPosition());
		if (tmpPosition == this.playerEntity.getPosition()) {
			zombie.attack(this.playerEntity);
		} else {
			this.zombieEntity.move(tmpPosition);
		}
	}



}
