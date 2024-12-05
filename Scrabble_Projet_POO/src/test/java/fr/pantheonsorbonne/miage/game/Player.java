package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;




public class Player {
    private String name;
    private int score;
    private Language activeLanguage;
    private List<Tile> pile;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.activeLanguage = Language.FRENCH; 
        this.pile = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        if (pile.size() < 7) {
            pile.add(tile);
        }
    }
}
