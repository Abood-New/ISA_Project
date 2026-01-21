import structure.GameState;
import structure.PlayGame;

public class Main {
    public static void main(String[] args) {
        GameState state = new GameState();
        Senet senetGame = new Senet();
        senetGame.getInitState(state);
        PlayGame game = new PlayGame();
        game.start(state);
        System.out.println("=== GAME OVER ===");
    }
}
