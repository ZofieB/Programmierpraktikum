package absclasses;
import java.util.Stack;

public interface Protokollierbar {
    public Stack<Spielzug> szstack =  new Stack<Spielzug>();

    public void addSpielzug(Spielzug spielzug);
    public Spielzug removeSpielzug();
    public Stack<Spielzug> getSzstack();
}