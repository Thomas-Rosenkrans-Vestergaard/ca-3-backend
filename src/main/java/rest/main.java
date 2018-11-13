package rest;

public class main {
    public static void main(String[] args) {
        PokemonResource pokeres = new PokemonResource();
        System.out.println(pokeres.getPokemonById("150"));
        System.out.println(pokeres.getPokemonById("1").getEntity());
    }
}
