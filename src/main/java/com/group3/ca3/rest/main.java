package com.group3.ca3.rest;

public class main {
    public static void main(String[] args) throws Exception{
        DogImageResource dogs = new DogImageResource();
        GhibliResource ghib = new GhibliResource();
        HearthstoneResource hres = new HearthstoneResource();
        JokeResource jokeres = new JokeResource();
        dogs.getDogById("2");
       // ghib.getAllMovies();
       // hres.getCardByName("Snowflipper Penguin");
        //jokeres.getJoke();


    }
}
