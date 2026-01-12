package structure;

import java.util.ArrayList;
import java.util.List;

public class GameState{
    public Board board;
    public ColorType currentPlayer;
    public List<Stone> blackStones;
    public List<Stone> whiteStones;
    public int blackStonesOut;
    public int whiteStonesOut;

    public GameState(){
        board=new Board();
        whiteStones=new ArrayList<Stone>();
        blackStones=new ArrayList<Stone>();
        currentPlayer=ColorType.WHITE;
        blackStonesOut=0;   
        whiteStonesOut=0;
    }

    public GameState copy(){
        GameState newState= new GameState();
        newState.board=this.board.copy();
        newState.currentPlayer=this.currentPlayer;
        newState.blackStonesOut=this.blackStonesOut;
        newState.whiteStonesOut=this.whiteStonesOut;
        for(Stone s:this.blackStones){
            Stone newstone=new Stone(s.color,s.position);
            newstone.isOut=s.isOut;
            newState.blackStones.add(newstone);
        }
        for(Stone s:this.whiteStones){
            Stone newstone= new Stone(s.color,s.position);
            newstone.isOut=s.isOut;
            newState.whiteStones.add(newstone);
        }
        return newState;
    }
}