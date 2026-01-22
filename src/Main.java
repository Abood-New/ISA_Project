import structure.GameState;
import structure.PlayGame;
import structure.PlayerType;

// public class Main {
//     public static void main(String[] args) {
//         GameState state = new GameState();
//         Senet senetGame = new Senet();
//         senetGame.getInitState(state);
//         PlayGame game = new PlayGame(PlayerType.AI, PlayerType.AI);
//         game.start(state);
//         System.out.println("=== GAME OVER ===");
//     }

// }
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== SENET GAME ===");
            System.out.println("Select game mode:");
            System.out.println("1) AI vs AI");
            System.out.println("2) User vs AI (You play WHITE)");
            System.out.println("3) AI vs User (You play BLACK)");
            System.out.println("4) User vs User");

            int choice = 0;
            while (choice < 1 || choice > 4) {
                System.out.print("Enter choice (1-4): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    scanner.next(); // discard invalid input
                }
            }

            PlayerType whitePlayer;
            PlayerType blackPlayer;

            switch (choice) {
                case 1 -> {
                    whitePlayer = PlayerType.AI;
                    blackPlayer = PlayerType.AI;
                }
                case 2 -> {
                    whitePlayer = PlayerType.HUMAN;
                    blackPlayer = PlayerType.AI;
                }
                case 3 -> {
                    whitePlayer = PlayerType.AI;
                    blackPlayer = PlayerType.HUMAN;
                }
                case 4 -> {
                    whitePlayer = PlayerType.HUMAN;
                    blackPlayer = PlayerType.HUMAN;
                }
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }

            GameState state = new GameState();
            Senet senetGame = new Senet();
            senetGame.getInitState(state);

            PlayGame game = new PlayGame(whitePlayer, blackPlayer);
            game.start(state);
        }

        System.out.println("=== GAME OVER ===");
    }
}
