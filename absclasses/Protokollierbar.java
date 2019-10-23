package absclasses;
import java.util.Stack;

public interface Protokollierbar {
    public Stack<Spielzug> szstack =  new Stack<Spielzug>();

    public void addSpielzug(Spielzug spielzug);
    public void removeSpielzug(Spielzug spielzug);
    public Stack getSzstack();
}