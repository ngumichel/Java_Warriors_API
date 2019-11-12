package com.campus.warriors.client.console;

import java.util.Scanner;

import io.vavr.collection.Stream;
import com.campus.warriors.contracts.GameState;
import com.campus.warriors.contracts.Hero;
import com.campus.warriors.contracts.Map;
import com.campus.warriors.contracts.WarriorsAPI;
import com.campus.warriors.engine.Warriors;

public class ClientConsole {

	enum MainMenuChoice {
		Start,
		Quit;
	}

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Console console = new Console(sc);
			switch (console.mainMenu()) {
					case Start:
						startGame(new Warriors(), console);
						break;
					case Quit:
						console.endMessage();
				}
		}
	}

	private static void startGame(WarriorsAPI warriors, Console console) {
		String playerName = console.askName();
		Hero hero = console.chooseHero(warriors.availableHeroes());
		Map map = console.chooseMap(warriors.availableMaps());

		Stream
			.iterate(
				warriors.createGame(playerName, hero, map),
				gameState -> warriors.nextTurn(gameState.getGameId()).getOrElseThrow(IllegalStateException::new))
			.peek(console::displayTurn)
			.filter(state -> state.getGameStatus() != GameState.GameStatus.IN_PROGRESS)
			.head();
	}
}
