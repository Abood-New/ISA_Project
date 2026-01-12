package structure;

public class Board{
    public Stone stones[];
    public Board(){
        stones=new Stone[31];
        for(int i=0;i<=30;i++){
            stones[i]= new Stone(ColorType.NONE, i);
        }
    }
    public Board copy(){
        Board newBoard=new Board();
        for(int i=0;i<=30;i++){
             if(this.stones[i] != null) {
                newBoard.stones[i] = new Stone(
                    this.stones[i].color, 
                    this.stones[i].position
                );
                newBoard.stones[i].isOut = this.stones[i].isOut;
            } else {
                newBoard.stones[i] = null;
            }
        }
        return newBoard;
    }
}