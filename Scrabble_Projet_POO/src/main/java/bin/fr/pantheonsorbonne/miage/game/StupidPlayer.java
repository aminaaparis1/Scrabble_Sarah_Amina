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
   

}
