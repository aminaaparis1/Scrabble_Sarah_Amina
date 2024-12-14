package bin.fr.pantheonsorbonne.miage.game;

import java.util.List;

public class SmartPlayer extends Player {

    SmartPlayer(String name) {
        super(name);
    }

    @Override
    public void penalizeOpponent(List<Player> players) {
        Player target = null;
        int maxScore = -1;

        for (Player player : players) {
            if (player != this && player.getScore() > maxScore) {
                target = player;
                maxScore = player.getScore();
            }
        }

        if (target != null) {
            target.reduceScore(5);
            System.out.println(this.name + "a retiré 5 points à" + target.getName());
        }

        else {
            System.out.println(this.name + "n'a trouvé personne à pénaliser.");
        }
    }  

}
