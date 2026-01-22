package structure;

public class MoveLogic {
    public static void moveStone(GameState state, Stone stone, int steps) {
        if (stone.isOut)
            return;

        int oldPos = stone.position;
        int newPos = oldPos + steps;

        if (oldPos < 26 && newPos > 26) {
            System.out.println("Illegal move: must stop at square 26 first.");
            return;
        }

        if (newPos > 30) {
            removeStone(state, stone);
            return;
        }

        Stone target = state.board.stones[newPos];
        if (target.color == stone.color) {
            System.out.println("Illegal move: same color stone.");
            return;
        }
        if (target.color != ColorType.NONE &&
                target.color != stone.color) {
            state.board.stones[newPos] = stone;
            stone.position = newPos;

            state.board.stones[oldPos] = target;
            target.position = oldPos;

            return;
        }
        state.board.stones[oldPos] = new Stone(ColorType.NONE, oldPos);

        stone.position = newPos;
        state.board.stones[newPos] = stone;

        if (stone.position < SpecialSquares.happiness &&
                newPos > SpecialSquares.happiness) {
            System.out.println("Illegal move: must stop at square 26 first.");
            return;
        }
        if (stone.position == SpecialSquares.water) {
            moveStoneToReBirth(state, stone); // silent for AI
            return;
        }
    }

    public static boolean isValidMove(GameState state, Stone stone, int steps) {
        if (stone.isOut)
            return false;

        int start = stone.position;
        int newPos = start + steps;

        if (newPos > 30)
            return true;

        if (start < 26 && newPos > 26)
            return false;

        if (state.punishedThisTurn.contains(stone)) {
            return false;
        }
        // // Check for protected pairs
        // ColorType opponent = (state.currentPlayer == ColorType.WHITE)
        // ? ColorType.BLACK
        // : ColorType.WHITE;

        // for (int i = start + 1; i < newPos; i++) {
        // Stone s1 = state.board.stones[i];
        // Stone s2 = state.board.stones[i + 1];

        // if (s1 == null || s2 == null)
        // continue;

        // if (s1.color == opponent && s2.color == opponent) {
        // return false;
        // }
        // }
        // // 2. Landing on protected pair
        // Stone dest = state.board.stones[newPos];
        // if (dest != null && dest.color == opponent) {
        // if ((newPos < 30 && state.board.stones[newPos + 1] != null &&
        // state.board.stones[newPos + 1].color == opponent) ||
        // (newPos > 1 && state.board.stones[newPos - 1] != null &&
        // state.board.stones[newPos - 1].color == opponent)) {

        // return false;
        // }
        // }

        Stone target = state.board.stones[newPos];
        if (target.color == stone.color)
            return false;

        return true;
    }

    private static void removeStone(GameState state, Stone stone) {

        state.board.stones[stone.position] = new Stone(ColorType.NONE, stone.position);

        stone.isOut = true;

        if (stone.color == ColorType.WHITE)
            state.whiteStonesOut++;
        else
            state.blackStonesOut++;
    }

    public static void moveStoneToReBirth(GameState state, Stone stone) {

        int targetPos = findRebirthPosition(state);

        if (targetPos == -1) {
            System.out.println("No valid rebirth position!");
            return;
        }

        state.board.stones[stone.position] = new Stone(ColorType.NONE, stone.position);

        stone.position = targetPos;
        state.board.stones[targetPos] = stone;
    }

    private static int findRebirthPosition(GameState state) {
        if (state.board.stones[SpecialSquares.reBirth].color == ColorType.NONE)
            return SpecialSquares.reBirth;
        for (int i = SpecialSquares.reBirth - 1; i >= 1; i--) {
            if (state.board.stones[i].color == ColorType.NONE)
                return i;
        }
        return -1;
    }
}