public class SnakeMain {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Skriv dette i terminalen:");
            System.out.println("java SnakeMain <bredde n> <højde m>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        SnakeModel model = new SnakeModel(n, m);
        SnakeView view = new SnakeView(n, m);
        
        view.draw(model);

        // Kører indtil man trykker på en tast
        while (!StdDraw.hasNextKeyTyped()) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }
}