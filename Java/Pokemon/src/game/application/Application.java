package game.application;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.NurseJoy;
import game.actors.Player;
import game.grounds.*;
import game.items.Door;
import game.items.Fire;
import game.items.FirePokefruit;
import game.pokemon.Bulbasaur;
import game.pokemon.Charizard;
import game.pokemon.Charmander;
import game.pokemon.Squirtle;

/**
 * The main class to start the game.
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Kerk Han Chin
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(),
                new Floor(), new Tree(),
                new Lava(), new Puddle(), new Crater(), new Hay(), new Waterfall());


        List<String> map = Arrays.asList(
                ".............................................^^^^^^^^^^^^^^",
                "..........^,T,................................,T,..^^^^O^^^",
                ".....................................................^^^^^^",
                "........................................................^^^",
                "............................................,,...........^^",
                "............................###.............,T............^",
                "..,,,...............,T......#_#............................",
                "..,T,......~...............................................",
                "...~~~~~~~~................................................",
                "....~~~~~..........,.......................................",
                "~~W~~~~.,........................^...,,,...................",
                "~~~~~~.,T,...........................,T,...................",
                "~~~~~~~~~............................,.....................");
        GameMap gameMap = new GameMap(groundFactory, map);
        world.addGameMap(gameMap);

        // Add Pokemon Centre map
        List<String> pokemonCentreMap = Arrays.asList(
                "##################",
                "#________________#",
                "#______....._____#",
                "#________________#",
                "#________________#",
                "########___#######");
        GameMap pokemonCentreGameMap = new GameMap(groundFactory, pokemonCentreMap);
        world.addGameMap(pokemonCentreGameMap);

        //Add Nurse Joy
        NurseJoy nurseJoy = new NurseJoy();
        pokemonCentreGameMap.at(9,2).addActor(nurseJoy);

        //Add Doors
        Door doorToPokemonCentre = new Door("Pokemon Center", pokemonCentreGameMap.at(9,5));
        Door doorToPalletTown = new Door("Pallet Town", gameMap.at(29,6));

        gameMap.at(29,6).addItem(doorToPokemonCentre);
        pokemonCentreGameMap.at(9,5).addItem(doorToPalletTown);

        //Add player - Ash
        Player ash = new Player("Ash", '@', 100000000);
        world.addPlayer(ash, gameMap.at(32, 10));


        //Add first pokemon - Charmander
        Actor charmander = new Charmander();
        gameMap.at(33, 10).addActor(charmander);

        Actor bulbasaur = new Bulbasaur();
        gameMap.at(34,10).addActor(bulbasaur);

        Actor squirtle = new Squirtle();
        gameMap.at(34,11).addActor(squirtle);

        Item pokefruit = new FirePokefruit();
        gameMap.at(31,10).addItem(pokefruit);

        Item fire = new Fire();
        gameMap.at(25,10).addItem(fire);

        Actor charizard = new Charizard();
        gameMap.at(10,1).addActor(charizard);

        Actor squirtleTwo = new Squirtle();
        gameMap.at(10,2).addActor(squirtleTwo);

        world.run();

    }
}
