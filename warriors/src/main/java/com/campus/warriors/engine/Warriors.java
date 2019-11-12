package com.campus.warriors.engine;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Option;
import com.campus.warriors.contracts.GameId;
import com.campus.warriors.contracts.GameState;
import com.campus.warriors.contracts.Hero;
import com.campus.warriors.contracts.Life;
import com.campus.warriors.contracts.Map;
import com.campus.warriors.contracts.WarriorsAPI;
import com.campus.warriors.model.EmptyCase;
import com.campus.warriors.model.Ennemy;
import com.campus.warriors.model.EnnemyCase;
import com.campus.warriors.model.LifeCase;
import com.campus.warriors.model.Magician;
import com.campus.warriors.model.MapModel;
import com.campus.warriors.model.SpellCase;
import com.campus.warriors.model.Warrior;
import com.campus.warriors.model.WeaponCase;

public class Warriors implements WarriorsAPI {

	private io.vavr.collection.Map<GameId, Game> games;

	public Warriors() {
		this.games = HashMap.empty();
	}

	private Map defaultMap() {
		return new MapModel("Default Map",
			List.of(
				new EmptyCase(),
				new SpellCase("Eclair", 2),
				new WeaponCase("Arc", 1),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new SpellCase("Eclair", 2),
				new WeaponCase("Massue", 3),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new LifeCase("Potion de vie mineure", 1),
				new SpellCase("Eclair", 2),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new WeaponCase("Arc", 1),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new LifeCase("Potion de vie mineure", 1),
				new WeaponCase("Arc", 1),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new EmptyCase(),
				new SpellCase("Eclair", 2),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new WeaponCase("Arc", 1),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new WeaponCase("Massue", 3),
				new SpellCase("Eclair", 2),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new WeaponCase("Arc", 1),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new LifeCase("Potion de vie mineure", 1),
				new LifeCase("Potion de vie mineure", 1),
				new EnnemyCase(new Ennemy("Gobelin", new Life(6, 6), new com.campus.warriors.contracts.Attack(1, 1))),
				new LifeCase("Potion de vie standard", 2),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new LifeCase("Potion de vie mineure", 1),
				new EmptyCase(),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new WeaponCase("Massue", 3),
				new LifeCase("Potion de vie standard", 2),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new LifeCase("Grande potion de vie", 5),
				new WeaponCase("Ep?e", 5),
				new LifeCase("Potion de vie standard", 2),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new EnnemyCase(new Ennemy("Dragon", new Life(15, 15), new com.campus.warriors.contracts.Attack(4, 4))),
				new EmptyCase(),
				new EnnemyCase(new Ennemy("Sorcier", new Life(9, 9), new com.campus.warriors.contracts.Attack(2, 2))),
				new SpellCase("Boule de feu", 7),
				new SpellCase("Boule de feu", 7),
				new EmptyCase(),
				new EmptyCase(),
				new EnnemyCase(new Ennemy("Dragon", new Life(15, 15), new com.campus.warriors.contracts.Attack(4, 4))),
				new WeaponCase("Ep?e", 5),
				new EmptyCase(),
				new EmptyCase(),
				new EnnemyCase(new Ennemy("Dragon", new Life(15, 15), new com.campus.warriors.contracts.Attack(4, 4))),
				new EmptyCase(),
				new EmptyCase(),
				new EmptyCase(),
				new EmptyCase(),
				new EmptyCase(),
				new EnnemyCase(new Ennemy("Dragon", new Life(15, 15), new com.campus.warriors.contracts.Attack(4, 4))),
				new EmptyCase()));
	}

	@Override
	public Iterable<Hero> availableHeroes() {
		return List.of(new Warrior(), new Magician());
	}

	@Override
	public Iterable<Map> availableMaps() {
		return List.of(this.defaultMap());
	}

	@Override
	public GameState createGame(String playerName, Hero hero, Map map) {
		Game newGame = new Game(playerName, hero, map);
		this.games = this.games.put(newGame.getGameId(), newGame);
		return newGame;
	}

	@Override
	public Option<GameState> nextTurn(GameId gameId) {
		return this.games.get(gameId)
			.map(Game::nextTurn);
	}
}
