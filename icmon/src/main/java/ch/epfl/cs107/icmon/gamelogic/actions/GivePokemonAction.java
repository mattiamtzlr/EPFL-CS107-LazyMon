package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class GivePokemonAction implements Action{

    private final ICMon.ICMonGameState state;
    private final String pokemonName;

    /**
     * Gives the player a new Pokémon. The Pokémon must have already been initialised in ICMon.java.
     * @param state The game's state. (ICMon.ICMonGameState)
     * @param pokemonName The <b>name</b> of the Pokémon to be added. (String)
     */
    public GivePokemonAction(ICMon.ICMonGameState state, String pokemonName) {
        this.pokemonName = pokemonName;
        this.state = state;
    }
    @Override
    public void perform() {
        state.givePlayerPokemon(pokemonName);
    }
}
