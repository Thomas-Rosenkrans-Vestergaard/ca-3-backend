package com.group3.ca3.rest;

public class HearthstoneCard
{

    private String  flavor;
    private Integer attack;
    private Integer health;
    private String  img;
    private Integer cost;
    private String  name;
    private String  text;
    private String  cardSet;

    public HearthstoneCard(String flavor, Integer attack, Integer health, String img, Integer cost, String name, String text,
                           String cardSet)
    {
        this.flavor = flavor;
        this.attack = attack;
        this.health = health;
        this.img = img;
        this.cost = cost;
        this.name = name;
        this.text = text;
        this.cardSet = cardSet;
    }
}
