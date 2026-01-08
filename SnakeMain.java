public class SnakeMain {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Skriv dette i terminalen:");
            System.out.println("java SnakeMain <bredde n> <højde m>");
            return;
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        if (width <5 || width >100 || height <5 ||  height > 100){
            System.out.println("Error: Width og height skal være melem 5 og 100");
            return;
        } 

        SnakeModel model = new SnakeModel(width, height);
        SnakeView view = new SnakeView(width, height);
        SnakeController controller = new SnakeController(model, view);

        controller.startGame();
    }
}