package com.campus.warriors;

import com.campus.warriors.contracts.*;
import com.campus.warriors.engine.Game;
import com.campus.warriors.model.Magician;
import com.campus.warriors.model.Warrior;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "heroes")
@Path("/heroes")
public class HeroResource implements WarriorsAPI
{
    private static Map<Integer, Hero> DB = new HashMap<>();

    @GET
    @Produces("application/json")
    public Heroes getAllHeroes() {
        Heroes heroes = new Heroes();
        getHeroes();
        heroes.setHeroes(new ArrayList<>(DB.values()));
        return heroes;
    }

    public void getHeroes() {
        Iterable heroes = availableHeroes();
        int i = 0;
        for (Object hero : heroes) {
            DB.put(i, (Hero) hero);
            i++;
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id) throws URISyntaxException
    {
        Hero hero = DB.get(id);
        if(hero == null) {
            return Response.status(404).build();
        }
        return Response
                .status(200)
                .entity(hero)
                .contentLocation(new URI("/user-management/"+id)).build();
    }

    @Override
    public Iterable<com.campus.warriors.contracts.Hero> availableHeroes() {
        return List.of(new Warrior(), new Magician());
    }

    @Override
    public Iterable<com.campus.warriors.contracts.Map> availableMaps() {
        return null;
    }

    @Override
    public GameState createGame(String playerName, Hero hero, com.campus.warriors.contracts.Map map) {
        return null;
    }

    @Override
    public Option<GameState> nextTurn(GameId gameId) {
        return null;
    }

}
