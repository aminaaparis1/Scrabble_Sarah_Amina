package bin.fr.pantheonsorbonne.miage.engine.net;
import java.util.Set;

import bin.fr.pantheonsorbonne.miage.game.ScrabbleGame;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;




 
public class ScrabbleNetworkEngine extends ScrabbleGame {
    private static final int PLAYER_COUNT = 4;

    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game scrabbleGame;

    public ScrabbleNetworkEngine(HostFacade hostFacade, Set<String> players, Game scrabbleGame) {
        this.hostFacade = hostFacade;
        this.players = players;
        this.scrabbleGame = scrabbleGame;
    }

    public static void main(String[] args) {
        
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

       
        hostFacade.createNewPlayer("Host");

        
        Game scrabbleGame = hostFacade.createNewGame("SCRABBLE");

        
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT - 1);

        ScrabbleNetworkEngine host = new ScrabbleNetworkEngine(hostFacade, scrabbleGame.getPlayers(), scrabbleGame);
        host.playGame();
        System.exit(0);
    }

   

    


}
