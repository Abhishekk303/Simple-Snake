
public class SnakeController {
    private SnakeModel model;
    private SnakeView view;
    private boolean gameRunning = true;
    private int score;
    private boolean hasEaten;

    public SnakeController(SnakeModel model, SnakeView view){
        this.model = model;
        this.view = view;
        this.score = 0;
        this.hasEaten = false;
        this.gameRunning = true;

        model.retning = 3; // Snaken starter med at bevæge sig mod venstre fordi opgaven beder om det.
    }

    public void startGame(){
        System.out.println("Simple Snake Game");
        System.out.println("Move: W = Op,S = Ned, A = Venstre, D = Højre");    

        while(gameRunning){
            handleInput();
            checkFood();
            moveSnake();
            
            if (checkCollision()){
                gameOver();
                break;
            }

            view.draw(model);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }

    private void handleInput(){
        if (StdDraw.hasNextKeyTyped()){
            char key = StdDraw.nextKeyTyped();
            
            if (key=='w'|| key=='W'){
                if (model.retning !=2) model.retning = 0;
            } else if (key=='s'|| key=='S'){
                if (model.retning != 0) model.retning = 2;
            }else if (key=='a'|| key=='A'){
                if (model.retning !=1) model.retning = 3;
            } else if (key=='d'|| key =='D'){
                if (model.retning !=3) model.retning = 1;
            }
        }
    }

    private void checkFood(){
        int[] head =  model.slange.get(0);
        if (head[0] == model.food[0] && head[1] == model.food[1]){
            score++;
            System.out.println("Spist! Score: " + score);
            model.food();
            hasEaten = true;
        } else {
            hasEaten = false;
        }
    }

    private void moveSnake(){
        int[] head = model.slange.get(0);
        int newX = head[0];
        int newY = head[1];

        switch (model.retning){
            case 0: newY = head[1] + 1; break;
            case 1: newX = head[0] + 1; break;
            case 2: newY = head[1] - 1; break;
            case 3: newX = head[0] - 1; break;
        }

        if (newX <0) newX = model.bredde - 1;
        if (newX >= model.bredde) newX = 0;
        if (newY <0) newY = model.hoejde - 1;
        if (newY >= model.hoejde) newY = 0;

        int[] newHead = new int[] {newX, newY};
        model.slange.add(0,newHead);

        if (!hasEaten && model.slange.size()>2){
            model.slange.remove(model.slange.size()-1);
        }
    }

    private boolean checkCollision(){
        if (model.slange.size()<3) return false;
        int[] head = model.slange.get(0);

        for (int i = 1; i<model.slange.size(); i++){
            int[] bodyPart = model.slange.get(i);
            if (head[0] == bodyPart[0]&& head[1] == bodyPart[1]){
                return true;
            }
        }
        return false;
    }

    private void gameOver(){
        gameRunning = false;
        System.out.println("Game Over!");
    }       
            
}
