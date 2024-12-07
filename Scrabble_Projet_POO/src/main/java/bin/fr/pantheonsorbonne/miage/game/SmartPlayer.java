package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;

public class SmartPlayer extends Player{

    SmartPlayer (String name){
        super(name);
    }

    @Override
    public void penalizeOpponent(List<Player> players){
        Player target=null;
        int maxScore=-1;

        for(Player player : players){
            if(player!= this && player.getScore()> maxScore){
                target=player;
                maxScore=player.getScore();
            }
        }

        if (target!=null){
            target.reduceScore(5);
            System.out.println(this.name +"a retiré 5 points à"+ target.getName());
        }

        else{
            System.out.println(this.name + "n'a trouvé personne à pénaliser.");
        }
    }

    @Override
    public String chooseWord(Board board, TileBag tileBag) {
        List<String> possibleWords = generatePossibleWords(); // Générer les mots possibles
     
        String bestWord = null;
    
        for (String word : possibleWords) {
            for (int x = 0; x < 15; x++) {
                for (int y = 0; y < 15; y++) {
                    for (boolean isHorizontal : new boolean[]{true, false}) {
                        if (board.isValidMove(word, x, y, isHorizontal, this)) {
                            bestWord = word;
                            break;
                        }
                    }
                    if (bestWord != null) break;
                }
                if (bestWord != null) break;
            }
        }
    
        if (bestWord == null) {
            System.out.println(getName() + " n'a trouvé aucun mot valide.");
        } else {
            System.out.println(getName() + " a choisi le mot : " + bestWord);
        }
    
        return bestWord;
    }
    
    private List<String> generatePossibleWords() {
        List<String> words = new ArrayList<>();
        generateWordsRecursive("", this.tilePile, words);
        return words;
    }
    
    private void generateWordsRecursive(String currentWord, List<Tile> remainingTiles, List<String> words) {
        Dictionary dictionary = new Dictionary();
        if (!currentWord.isEmpty() && dictionary.isWordValid(currentWord, getActiveLanguage().name())) {
            words.add(currentWord);
        }
        for (int i = 0; i < remainingTiles.size(); i++) {
            Tile tile = remainingTiles.get(i);
            List<Tile> nextTiles = new ArrayList<>(remainingTiles);
            nextTiles.remove(i);
            generateWordsRecursive(currentWord + tile.getLetter(), nextTiles, words);
        }
    }
    
}
