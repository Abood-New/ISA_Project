package Algorithms;

import structure.*;
import java.util.List;

public class ExpectiMinimax {

    private static final int MAX_DEPTH = 4;

    public static Stone findBestMove(GameState state, int steps) {
        boolean isMax = (state.currentPlayer == ColorType.WHITE);

        double bestValue = isMax
                ? Double.NEGATIVE_INFINITY
                : Double.POSITIVE_INFINITY;
        Stone bestStone = null;

        List<Stone> stones = getCurrentPlayerStones(state);

        for (Stone stone : stones) {
            if (!MoveLogic.isValidMove(state, stone, steps))
                continue;

            GameState newState = state.copy();
            Stone newStone = findStoneInState(newState, stone);

            MoveLogic.moveStone(newState, newStone, steps);

            switchPlayer(newState);

            double value = expectiminimax(newState, MAX_DEPTH - 1);

            if (isMax && value > bestValue) {
                bestValue = value;
                bestStone = stone;
            }
            if (!isMax && value < bestValue) {
                bestValue = value;
                bestStone = stone;
            }
        }

        return bestStone;
    }

    private static double expectiminimax(GameState state, int depth) {
        if (depth == 0 || isTerminal(state)) {
            return evaluate(state);
        }

        return chanceNode(state, depth);
    }

    private static double chanceNode(GameState state, int depth) {
        boolean isMax = (state.currentPlayer == ColorType.WHITE);
        double expectedValue = 0.0;

        for (int steps = 1; steps <= 5; steps++) {
            double probability = MoveProbability.getProbability(steps);

            double value;
            if (isMax) {
                value = maxNode(state, steps, depth);
            } else {
                value = minNode(state, steps, depth);
            }

            expectedValue += probability * value;
        }

        return expectedValue;
    }

    private static double maxNode(GameState state, int steps, int depth) {
        double bestValue = Double.NEGATIVE_INFINITY;
        boolean hasMove = false;

        for (Stone stone : getCurrentPlayerStones(state)) {
            if (!MoveLogic.isValidMove(state, stone, steps))
                continue;

            GameState newState = state.copy();
            Stone newStone = findStoneInState(newState, stone);

            MoveLogic.moveStone(newState, newStone, steps);

            switchPlayer(newState);

            double value = expectiminimax(newState, depth - 1);
            bestValue = Math.max(bestValue, value);
            hasMove = true;
        }

        if (!hasMove) {
            GameState passedState = state.copy();
            switchPlayer(passedState);
            return expectiminimax(passedState, depth - 1);
        }

        return bestValue;
    }

    private static double minNode(GameState state, int steps, int depth) {
        double bestValue = Double.POSITIVE_INFINITY;
        boolean hasMove = false;

        for (Stone stone : getCurrentPlayerStones(state)) {
            if (!MoveLogic.isValidMove(state, stone, steps))
                continue;

            GameState newState = state.copy();
            Stone newStone = findStoneInState(newState, stone);

            MoveLogic.moveStone(newState, newStone, steps);

            switchPlayer(newState);

            double value = expectiminimax(newState, depth - 1);
            bestValue = Math.min(bestValue, value);
            hasMove = true;
        }

        if (!hasMove) {
            GameState passedState = state.copy();
            switchPlayer(passedState);
            return expectiminimax(passedState, depth - 1);
        }

        return bestValue;
    }

    private static double evaluate(GameState state) {
        if (state.whiteStonesOut == 7)
            return +10000;
        if (state.blackStonesOut == 7)
            return -10000;

        double score = 0.0;

        score += (state.whiteStonesOut - state.blackStonesOut) * 1000;

        for (Stone stone : state.blackStones) {
            if (!stone.isOut) {
                score -= stone.position * 10;
                if (stone.position >= 26)
                    score -= 100;
                if (stone.position == SpecialSquares.water)
                    score += 200;
            }
        }

        for (Stone stone : state.whiteStones) {
            if (!stone.isOut) {
                score += stone.position * 10;
                if (stone.position >= 26)
                    score += 100;
                if (stone.position == SpecialSquares.water)
                    score -= 200;
            }
        }

        return score;
    }

    private static boolean isTerminal(GameState state) {
        return state.blackStonesOut == 7 || state.whiteStonesOut == 7;
    }

    private static void switchPlayer(GameState state) {
        state.currentPlayer = (state.currentPlayer == ColorType.BLACK)
                ? ColorType.WHITE
                : ColorType.BLACK;
    }

    private static List<Stone> getCurrentPlayerStones(GameState state) {
        return (state.currentPlayer == ColorType.BLACK)
                ? state.blackStones
                : state.whiteStones;
    }

    private static Stone findStoneInState(GameState state, Stone original) {
        for (Stone s : getCurrentPlayerStones(state)) {
            if (s.position == original.position && s.isOut == original.isOut) {
                return s;
            }
        }
        return null;
    }
}
