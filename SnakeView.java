import java.awt.Color;
import java.awt.Font;

public class SnakeView {
    private int n, m;
    private int cellStr = 25;

    public SnakeView(int n, int m) {
        this.n = n;
        this.m = m;

        StdDraw.setCanvasSize(n * cellStr, m * cellStr);
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, m);
        StdDraw.show(0);
    }

    public void draw(SnakeModel model, boolean isPaused) {
        StdDraw.clear(Color.WHITE);

        // Tegning af slangen
        StdDraw.setPenColor(Color.BLACK);
        for (int[] p : model.slange) {
            StdDraw.filledSquare(p[0] + 0.5, p[1] + 0.5, 0.5);
        }

        // Tegning af maden
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledCircle(model.food[0] + 0.5, model.food[1] + 0.5, 0.3);

        StdDraw.setPenColor(Color.RED);
        StdDraw.filledCircle(model.food[0]+ 0.5, model.food[1]+0.5,0.3);

        if (isPaused){
            drawPauseOverlay();
        }

        StdDraw.show();
    }

    private void drawPauseOverlay(){
        StdDraw.setPenColor(new Color(0,0,0,150));
        StdDraw.filledRectangle(n/2.0, m/2.0, n/2.0, m/2.0);

        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 36));
        StdDraw.text(n/2.0,m/2.0+1,"PAUSE");
    }
}