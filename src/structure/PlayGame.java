package structure;

import java.util.List;
import java.util.Scanner;

import Algorithms.AIConfig;
import Algorithms.ExpectiMinimax;
import Algorithms.SearchStatistics;

public class PlayGame {
    private PlayerType whitePlayer;
    private PlayerType blackPlayer;
    private Scanner scanner = new Scanner(System.in);

    public PlayGame(PlayerType whitePlayer, PlayerType blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public void start(GameState state) {
        while (!isGameOver(state)) {

            System.out.println(state);

            int steps = MoveProbability.throwSticks();
            System.out.println("Sticks result: " + steps);

            handleForcedJudgment(state, steps);

            if (steps == 0) {
                System.out.println("No moves, turn skipped.");
                switchTurn(state);
                continue;
            }

            SearchStatistics stats = new SearchStatistics();

            Stone chosenStone = chooseStone(state, steps, stats);

            if (chosenStone != null) {
                int oldPos = chosenStone.position;
                MoveLogic.moveStone(state, chosenStone, steps);

                if (AIConfig.ANALYSIS_MODE) {
                    String to = chosenStone.isOut
                            ? "exit"
                            : String.valueOf(chosenStone.position);
                    System.out.println(
                            stats.summary("move=" + oldPos + "->" + to, ExpectiMinimax.MAX_DEPTH));
                }
                if (chosenStone.isOut) {
                    System.out.println("Moved stone from " + oldPos + " to exit");
                } else {
                    System.out.println("Moved stone from " + oldPos +
                            " to " + chosenStone.position);
                }
            } else {
                System.out.println("No valid move available.");
            }

            switchTurn(state);
        }

        printWinner(state);
    }

    private Stone chooseStone(GameState state, int steps, SearchStatistics stats) {

        PlayerType controller = (state.currentPlayer == ColorType.WHITE)
                ? whitePlayer
                : blackPlayer;

        if (controller == PlayerType.HUMAN) {
            return chooseStoneForUser(state, steps);
        } else {
            System.out.println("AI is thinking...");
            return ExpectiMinimax.findBestMove(state, steps, stats);
        }
    }

    private Stone chooseStoneForUser(GameState state, int steps) {
        List<Stone> stones = (state.currentPlayer == ColorType.WHITE)
                ? state.whiteStones
                : state.blackStones;
        Stone selected = null;

        // Find valid moves
        List<Stone> validStones = new java.util.ArrayList<>();
        for (Stone s : stones) {
            if (!s.isOut && MoveLogic.isValidMove(state, s, steps)) {
                validStones.add(s);
            }
        }

        if (validStones.isEmpty()) {
            System.out.println("No valid moves available.");
            return null;
        }

        while (selected == null) {
            System.out.println("Your stones with valid moves:");
            for (Stone s : validStones) {
                System.out.print(s.position + " ");
            }
            System.out.println();

            System.out.print("Choose stone position: ");
            int pos = scanner.nextInt();

            for (Stone s : validStones) {
                if (s.position == pos) {
                    selected = s;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("Invalid stone or move, try again.");
            }
        }
        return selected;
    }

    private void switchTurn(GameState state) {
        state.currentPlayer = (state.currentPlayer == ColorType.WHITE)
                ? ColorType.BLACK
                : ColorType.WHITE;
        state.punishedThisTurn.clear();
    }

    private static void handleForcedJudgment(GameState state, int steps) {
        List<Stone> stones = (state.currentPlayer == ColorType.WHITE)
                ? state.whiteStones
                : state.blackStones;

        for (Stone stone : stones) {

            if (stone.isOut)
                continue;

            if (stone.position == SpecialSquares.water) {
                MoveLogic.moveStoneToReBirth(state, stone);
            }
            if (stone.position == SpecialSquares.threeTruths && steps != 3) {
                MoveLogic.moveStoneToReBirth(state, stone);
                state.punishedThisTurn.add(stone);
            }

            if (stone.position == SpecialSquares.reAtoum && steps != 2) {
                MoveLogic.moveStoneToReBirth(state, stone);
                state.punishedThisTurn.add(stone);
            }
        }
    }

    private boolean isGameOver(GameState state) {
        return state.whiteStonesOut == 7 || state.blackStonesOut == 7;
    }

    private void printWinner(GameState state) {
        if (state.whiteStonesOut == 7)
            System.out.println("\nWHITE PLAYER WINS!");
        else
            System.out.println("\nBLACK PLAYER WINS!");
    }
}