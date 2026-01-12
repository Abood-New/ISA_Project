package structure;

public class SpecialSquares{
    public static final int reBirth = 15;
    public static final int happiness = 26;
    public static final int water = 27;
    public static final int threeTruths = 28;
    public static final int reAtoum = 29;
    public static final int horus = 30;

public static boolean isSpecialSquare(int position){
        return position == reBirth || position == happiness || position == water ||
               position == threeTruths || position == reAtoum || position == horus;
    }

public static String getSquareType(int position){
    switch(position){
        case 15:  return "reBirth";
        case 26:  return "happiness";
        case 27:  return "water";
        case 28:  return "threeTruths";
        case 29:  return "reAtoum";
        case 30:  return "horus";
        default:  return "normal";
    }
}
}