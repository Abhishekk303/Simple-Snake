import java.util.ArrayList;
import java.util.Random;

public class SnakeModel {
    public int bredde, hoejde;
    public ArrayList<int[]> slange; 
    public int[] food;
    public int retning;
    public boolean victory = false;

    private Random rand = new Random();

    public SnakeModel(int n, int m) {
        this.bredde = n;
        this.hoejde = m;
        this.slange = new ArrayList<>();

        // Start position midt på skærmen
        int startX = n / 2;
        int startY = m / 2;

        slange.add(new int[] { startX, startY });     // Hoved
        slange.add(new int[] { startX, startY + 1 }); // Haleled

        food();
    }

    public void food() {
        while (true) {
            int x = rand.nextInt(bredde);
            int y = rand.nextInt(hoejde);
            
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