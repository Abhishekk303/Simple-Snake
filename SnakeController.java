import java.awt.Font;
import javax.net.ssl.TrustManager;

public class SnakeController {
    private SnakeModel model;
    private SnakeView view;
    private boolean gameRunning = true;
    private int score;
    private boolean hasEaten;

    public SnakeController(SnakeModel model, SnakeView view){
        this.model = moden;
        this.view = view;
        this.score = 0;
        this.hasEaten = false;
        this.gameRunning = true;

        model.retning = 3; // Snaken starter med at bevæge sig mod venstre fordi opgaven beder om det.
    }

    public void startGame(){
        System.out.println("Simple Snake Game");
        System.out
    }


}







import java.awt.event.KeyEvent;

public class SnakeController {
    private SnakeModel model;
    private SnakeView view;
    private boolean running = true;

    public SnakeController(SnakeMode model, SnakeView view){
        this.model = model;
        this.view = view;
    }

    public void startGame(){
        while (running){
            if(StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                handleKeyPress(key);
            }

            model.move();

        }
    }

}