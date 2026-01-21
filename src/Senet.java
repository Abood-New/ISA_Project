import structure.*;

public class Senet {
    public GameState getInitState(GameState gameState) {
        GameState initState = gameState;
        for (int i = 0; i < 7; i++) {
            Stone whiteStone = new Stone(ColorType.WHITE, 2 * i + 1);
            initState.whiteStones.add(whiteStone);
            initState.board.stones[2 * i + 1] = whiteStone;

            Stone blackStone = new Stone(ColorType.BLACK, 2 * i + 2);
            initState.blackStones.add(blackStone);
            initState.board.stones[2 * i + 2] = blackStone;
        }
        for (int i = 15; i <= 30; i++) {
            Stone specialStone = new Stone(ColorType.NONE, i);
            if (SpecialSquares.isSpecialSquare(i)) {
                initState.board.stones[i] = specialStone;
            }
        }
        initState.currentPlayer = ColorType.WHITE;
        return initState;
    }
}
