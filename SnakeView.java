import java.awt.Color;
import java.awt.Font;

public class SnakeView {
    private int n, m;
    private int cellStr = 25;
    private boolean tongueState = false;

    public SnakeView(int n, int m) {
        this.n = n;
        this.m = m;

        StdDraw.setCanvasSize(n * cellStr, (m+2) * cellStr);
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, m+2);

        StdDraw.show(0);
    }

    public void draw(SnakeModel model, boolean isPaused, int score ) {

        drawHeader(score);

        //tegning af baggrund. Et græs mønster
        //Hvis græsset er for laggy, kan vi ændre tilbage til en mere plain farve her
        Color darkGrass = new Color(34, 139, 34);
        Color lightGrass = new Color(50, 205, 50);

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                if ((x + y) % 2 == 0) {
                    StdDraw.setPenColor(darkGrass);
                } else {
                    StdDraw.setPenColor(lightGrass);
                }
                StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
            }
        }

        // Tegning af maden. Et æble med et blad
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledCircle(model.food[0] + 0.5, model.food[1] + 0.5, 0.4);
        
        //Det grønne blad på æblen
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledCircle(model.food[0] + 0.5 + 0.1, model.food[1] + 0.5 + 0.2, 0.15);

        if (!isPaused) {
            tongueState = !tongueState;
        }

        //Tegning af slangen, med øjne og tunge
        //Også en animation af tungen, så slangen trækker den ud og ind
        for (int i = 0; i < model.slange.size(); i++) {
            int[] del = model.slange.get(i);
            double x = del[0] + 0.5;
            double y = del[1] + 0.5;

            // hoved (index 0)
            if (i == 0) {
                // tunge
                if (tongueState) {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.setPenRadius(0.015);
                    switch(model.retning) {
                        case 0: StdDraw.line(x, y, x, y + 0.9); break; // Når retning er Op
                        case 1: StdDraw.line(x, y, x + 0.9, y); break; // Højre
                        case 2: StdDraw.line(x, y, x, y - 0.9); break; // Ned
                        case 3: StdDraw.line(x, y, x - 0.9, y); break; // Venstre
                    }
                    StdDraw.setPenRadius();
                }

                //Hoved farve: lila
                StdDraw.setPenColor(new Color(160, 100, 240)); 
                StdDraw.filledCircle(x, y, 0.5);

                //Øjne
                StdDraw.setPenColor(Color.WHITE);
                double eyeOffset = 0.2;
                double eyeSize = 0.12;
                double pupilSize = 0.05;
                double pupilOffset = 0.03;

                //øjnene følger retningen
                switch(model.retning) {
                    case 0: // op
                        StdDraw.filledCircle(x - eyeOffset, y + 0.1, eyeSize);
                        StdDraw.filledCircle(x + eyeOffset, y + 0.1, eyeSize);

                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledCircle(x-eyeOffset,y+0.1+eyeSize-pupilSize/2,pupilSize);
                        StdDraw.filledCircle(x+eyeOffset,y+0.1+eyeSize-pupilSize/2,pupilSize);
                        break;
                    case 1: // højre
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.filledCircle(x + 0.1, y - eyeOffset, eyeSize);
                        StdDraw.filledCircle(x + 0.1, y + eyeOffset, eyeSize);

                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledCircle(x+0.1+eyeSize-pupilSize/2,y-eyeOffset,pupilSize);
                        StdDraw.filledCircle(x+0.1+eyeSize-pupilSize/2,y+eyeOffset,pupilSize);
                        break;
                    case 2: // Ned
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.filledCircle(x - eyeOffset, y - 0.1, eyeSize);
                        StdDraw.filledCircle(x + eyeOffset, y - 0.1, eyeSize);

                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledCircle(x-eyeOffset,y-0.1-eyeSize+pupilSize/2,pupilSize);
                        StdDraw.filledCircle(x+eyeOffset,y-0.1-eyeSize+pupilSize/2,pupilSize);
                        break;
                    case 3: // Venstre
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.filledCircle(x - 0.1, y - eyeOffset, eyeSize);
                        StdDraw.filledCircle(x - 0.1, y + eyeOffset, eyeSize);

                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledCircle(x-0.1-eyeSize+pupilSize/2,y-eyeOffset,pupilSize);
                        StdDraw.filledCircle(x-0.1-eyeSize+pupilSize/2,y+eyeOffset,pupilSize);
                        break;
                }

            } else {
                //Krop farve: Lyseblå
                StdDraw.setPenColor(new Color(0, 100, 200));
                StdDraw.filledCircle(x, y, 0.45);
            }
        }

        if (isPaused){
            drawPauseScreen();
        }

        StdDraw.show(0);
    }
    
    private void drawHeader(int score){
        double headerY = m + 1.0;
        
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(n/2.0, headerY, n/2.0, 0.8);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setPenRadius(0.01);
        StdDraw.rectangle(n/2.0,headerY,n/2.0-0.1,0.7);
        StdDraw.setPenRadius();

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font ("Arial", Font.BOLD,24));
        StdDraw.text(n/2.0,headerY,"Score:" + score);

        drawAppleHeader(2.5,headerY);
        drawAppleHeader(n-2.5,headerY);
    }
    
    private void drawAppleHeader(double x, double y){
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledCircle(x,y,0.25);
        
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledCircle (x+0.08, y+0.12,0.08);
    }

    private void drawPauseScreen(){
        StdDraw.setPenColor(new Color(0,0,0,150));
        StdDraw.filledRectangle(n/2.0, m/2.0, n/2.0, m/2.0);

        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 36));
        StdDraw.text(n/2.0,m/2.0+1,"PAUSED");

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.PLAIN,20));
        StdDraw.text(n/2.0, m/2.0-1,"Tryk på P for at starte igen");
    }

    private void drawScore(int score){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Arial", Font.BOLD,16));
        StdDraw.textLeft(0.5,m-0.5,"Score: " + score);
    }

    public void displayVictory(int score) {
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.text(n/2.0, m/2.0, "DU VANDT!");
        StdDraw.text(n/2.0, (m/2.0)-1, "Score: " + score);
        StdDraw.show(0);
    }
}