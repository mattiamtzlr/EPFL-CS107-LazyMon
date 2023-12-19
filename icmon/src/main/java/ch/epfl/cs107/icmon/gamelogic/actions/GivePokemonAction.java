package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class GivePokemonAction implements Action{

    private ICMon.ICMonGameState state;
    private String pokemonName;
    public GivePokemonAction(ICMon.ICMonGameState state, String pokemonName) {
        this.pokemonName = pokemonName;
        this.state = state;
    }
    @Override
    public void perform() {
        state.givePlayerPokemon(pokemonName);
    }
}
