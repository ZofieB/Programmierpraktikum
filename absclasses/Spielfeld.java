package absclasses;

public abstract class Spielfeld{
    protected int horizontal;
    protected int vertical;

    public abstract void printSpielfeld();
    public void setHorizontal(int horizontal){
        this.horizontal = horizontal;
    }
    public int getHorizontal() {
        return this.horizontal;
    }
    public void setVertical(int vertical) {
        this.vertical = vertical;
    }
    public int getVertical() {
        return this.vertical;
    }
}