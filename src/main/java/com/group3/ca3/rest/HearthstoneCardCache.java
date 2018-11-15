package com.group3.ca3.rest;

import java.util.ArrayList;
import java.util.List;

public class HearthstoneCardCache {

    private List<HearthstoneCard> cards;

    public HearthstoneCardCache(List<HearthstoneCard> cards) {
        this.cards = cards;
    }

    public List<HearthstoneCard> getPaginated(int pageSize, int pageNumber) {
        pageSize = Math.max(pageSize, 1);
        pageNumber = Math.max(pageNumber, 1);

        int start = pageSize * (pageNumber - 1);
        int end = Math.min(pageSize * pageNumber, this.cards.size());

        if (start > this.cards.size())
            return new ArrayList<>();


        return cards.subList(start, end);
    }

    public int size(){
        return cards.size();
    }
}
