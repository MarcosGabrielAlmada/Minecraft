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
	private String msgEndGame;

	private Entity playerEntity;
	private Entity zombieEntity;

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
		println("Welcome to Minecraft");
		println();
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
		this.playerName = "Marcos"; // XXX - para testear
		this.gameState = new GameState(playerName, 1); // XXX - para testear
		this.playerEntity = this.gameState.getEntities()[0];
		this.zombieEntity = this.gameState.getEntities()[1];

		this.endGame = false;

		try {
			do {
				cleanTerminal();
				writeWorld();
				writeEntityStatus();
				flush();

				if (this.gameState.getTurn() == this.playerEntity) {
					playerTurn();

				} else {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}

					zombieTurn();
					this.gameState.toggleTurn();
				}

				updateTargets();

			} while (!this.endGame);

		} finally {
			cleanTerminal();
			println(this.msgEndGame);
			flush();
			try {
				terminal.close();
			} catch (Exception e) {
				println(e.getMessage());
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
		println();
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
				println("What is your name?");
				print("Name: ");
				flush();
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
				println("Where do you want to start?(they say that position 10 gives you luck)");
				print("Position(1-10): ");
				flush();
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

	// Printers

	private void print(String msg) {
		terminal.writer().print(msg);
	}

	private void println() {
		terminal.writer().println();
	}

	private void println(String msg) {
		terminal.writer().println(msg);
	}

	private void flush() {
		this.terminal.writer().flush();
	}

	private void writeWorld() {
		int worldSize = World.WORLD_SIZE;
		print("╔");
		writeLineWorld(worldSize - 1, "═══╦");
		println("═══╗");

		print("║");
		writeLineWorld(worldSize, "   ║", this.playerEntity, this.zombieEntity);
		println();

		print("╚");
		writeLineWorld(worldSize - 1, "═══╩");
		println("═══╝");
	}

	private void writeLineWorld(int worldSize, String c) {
		for (int i = 0; i < worldSize; i++) {
			print(c);
		}
	}

	private void writeLineWorld(int worldSize, String c, Entity p, Entity z) {

		String first = c.substring(0, 1);
		String last = c.substring(2);
		for (int i = 1; i <= worldSize; i++) {
			if (p.getPosition() == i) {
				print(first + "o" + last);
			} else if (z.getPosition() == i) {
				print(first + "x" + last);
			} else {
				print(c);
			}
		}
	}

	private void writeEntityStatus() {
		Player player = (Player) this.playerEntity;
		println();
		println("\u001B[1;4;36m" + this.playerName + "(you):\u001B[0m");
		print("\u001B[32mLife: ");
		println(this.playerEntity.getLife() + "\u001B[0m");
		print("\u001B[31mDamage: ");
		println(this.playerEntity.getDamage() + "\u001B[0m");
		print("\u001B[37mArmor: ");
		println(this.playerEntity.getArmor() + "\u001B[0m");
		print("\u001B[35mInventory: ");
		for (int i = 0; i < player.getInventory().getLength(); i++) {
			Item item = player.getInventory().getItemInPosition(i);
			if (player.getInventory().getSelected() == item) {
				print("\u001B[1m[" + item.getName() + " " + item.getNivel() + "]\u001B[0;35m");
			} else {
				print("[" + item.getName() + " " + item.getNivel() + "]");
			}
		}
		println();
		print("\u001B[33mTarget: ");
		println(this.playerEntity.getTarget() + "\u001B[0m");

		println();
		println();
		println("\u001B[1;4;31mZombie:\u001B[0m");
		print("\u001B[32mLife: ");
		println(this.zombieEntity.getLife() + "\u001B[0m");
		print("\u001B[31mDamage: ");
		println(this.zombieEntity.getDamage() + "\u001B[0m");
		print("\u001B[33mTarget: ");
		println(this.zombieEntity.getTarget() + "\u001B[0m");
	}

	// Entity turns

	private void finishGame() {
		this.endGame = true;
		this.msgEndGame = "\u001B[36mGoodbye!\u001B[0m";
	}

	private void finishGame(String msg) {
		this.endGame = true;
		this.msgEndGame = msg;
	}

	private void playerTurn() {
		String action;
		boolean validMainInput;
		Player player = (Player) this.playerEntity;
		do {
			validMainInput = true;
			action = this.keyReader.readBinding(this.keyMap);

			if (action == "LEFT" || action == "RIGHT") {
				int tmpPosition = player.calculateMovement(action);

				// in case player doesn't actually move (invalid turn)
				if (tmpPosition != player.getPosition()) {
					this.gameState.toggleTurn();
				}

				// in case the player collides with the zombie
				if (tmpPosition == this.zombieEntity.getPosition()) {
					if (action == "LEFT") {
						player.moveToPosition(tmpPosition + 1);
					} else {
						player.moveToPosition(tmpPosition - 1);
					}
				} else {
					player.moveToPosition(tmpPosition);
				}

			} else if (action == "1" || action == "2" || action == "3") {
				player.getInventory().setSelected(Integer.parseInt(action) - 1);

			} else if (action == "USE") {

				player.useItem();
				if (zombieEntity.getLife() == 0) {
					finishGame("\u001B[32mYou win!\u001B[0m");
				}
				this.gameState.toggleTurn();

			} else if (action == "EXIT") {
				boolean validExitInput = false;

				do {
					println();
					println("Are you sure you want exit?[Y/n]: ");
					flush();
					action = this.lineReader.readLine();

					if (action.toUpperCase().equals("Y")) {
						finishGame();
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
	}

	private void zombieTurn() {
		Zombie zombie = (Zombie) this.zombieEntity;
		int tmpPosition = zombie.calculateMovement(this.playerEntity);
		if (tmpPosition == this.playerEntity.getPosition()) {
			zombie.attack(this.playerEntity);
			if (this.playerEntity.getLife() <= 0) {
				finishGame("You died!");
			}
		} else {
			zombie.moveToPosition(tmpPosition);
		}
	}

	private void updateTargets() {
		int playerPosition = this.playerEntity.getPosition();
		int zombiePosition = this.zombieEntity.getPosition();
		
		if (playerPosition + 1 == this.zombieEntity.getPosition() ||
				playerPosition - 1 == this.zombieEntity.getPosition()) {
			this.playerEntity.setTarget(this.zombieEntity);
		} else {
			this.playerEntity.setTarget(null);
		}
		
		if (zombiePosition + 1 == this.playerEntity.getPosition() ||
				zombiePosition - 1 == this.playerEntity.getPosition()) {
			this.zombieEntity.setTarget(this.playerEntity);
		} else {
			this.zombieEntity.setTarget(null);
		}
	}


}
