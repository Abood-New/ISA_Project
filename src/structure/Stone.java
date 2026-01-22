package structure;

public class Stone {

    public ColorType color;
    public int position;
    public boolean isOut;

    public Stone(ColorType color, int position) {
        this.color = color;
        this.position = position;
        this.isOut = false;
    }

    public boolean isEmpty() {
        return color == ColorType.NONE;
    }

}
