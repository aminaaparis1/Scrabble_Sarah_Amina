package bin.fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StupidPlayer extends Player{

    StupidPlayer(String name){
        super(name);
    }
    
    @Override
    public void penalizeOpponent(List<Player> players){
        List<Player> opponents= new ArrayList<> ();

        for(Player player : players){
    
            if(this!=player){
             opponents.add(player);
            }
        }

        if(!opponents.isEmpty()){
            Random random=new Random();
            int randomIndex=random.nextInt(opponents.size());
            Player target=opponents.get(randomIndex);

            target.reduceScore(5);
            System.out.println(this.name +" a retiré 5 points à "+ target.getName());
            
        }
        else{
            System.out.println(this.name+" n'a trouvé personne à pénaliser.");
        }
    
    }
    
    @Override
    public String chooseWord(Board board, TileBag tileBag){
        StringBuilder word = new StringBuilder();

        Random random= new Random();
        int wordLength= Math.min(3,tilePile.size());
        for(int i=0; i < wordLength; i++){
            Tile tile=tilePile.get(random.nextInt(tilePile.size()));
            word.append(tile.getLetter());

        }

        System.out.println(getName()+" a choisit le mot : "+ word);
        return word.toString();

    }
   

}
