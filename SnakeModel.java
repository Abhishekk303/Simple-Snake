import java.util.ArrayList;
import java.util.Random;

public class SnakeModel {
    public int width, height;
    public ArrayList<int[]> slange; 
    public int[] food;
    public int retning;
    public boolean victory = false;

    private Random rand = new Random();

    //  Stopur / timer
    private long startTime;
    private long pauseStartTime;
    private long totalPausedTime;
    private boolean paused;

    public SnakeModel(int n, int m) {
        this.width = n;
        this.height = m;
        this.slange = new ArrayList<>();

        // Start position midt på skærmen
        int startX = n / 2;
        int startY = m / 2;

        slange.add(new int[] { startX, startY });     // Hoved
        slange.add(new int[] { startX, startY + 1 }); // Haleled

        food();

        //  start tid
        startTime = System.currentTimeMillis();
        totalPausedTime = 0;
        paused = false;
    }

    // ⏱Forløbet tid i sekunder (pausetid tæller ikke med)
    public int getElapsedTime() {
        long now = paused ? pauseStartTime : System.currentTimeMillis();
        return (int) ((now - startTime - totalPausedTime) / 1000);
    }

    public void pauseGame() {
        if (!paused) {
            paused = true;
            pauseStartTime = System.currentTimeMillis();
        }
    }

    public void resumeGame() {
        if (paused) {
            paused = false;
            totalPausedTime += System.currentTimeMillis() - pauseStartTime;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void food() {
        while (true) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            
            // Sørg for at æblet ikke spawner inde i selve slangen
            boolean optaget = false;
            for (int[] del : slange) {
                if (del[0] == x && del[1] == y) {
                    optaget = true;
                    break;
                }
            }
            
            if (!optaget) {
                food = new int[] { x, y };
                break;
            }
        }
    }
}
