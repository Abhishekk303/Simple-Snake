
public class SnakeController {
    private SnakeModel model;
    private SnakeView view;
    private boolean gameRunning = true;
    private int score;
    private boolean hasEaten;
    private boolean isPaused = false;
    private int highscore = 0;
    private boolean isNewHighScore = false;
    private int gameSpeed;

    public SnakeController(SnakeModel model, SnakeView view){
        this.model = model;
        this.view = view;
        this.score = 0;
        this.hasEaten = false;
        this.gameRunning = true;
        this.isPaused = false;
        this.highscore = 0;
        this.isNewHighScore = false;
        this.gameSpeed = 200;


        model.retning = 3; // Snaken starter med at bevæge sig mod venstre fordi opgaven beder om det.
    }

    public void startGame(){
        System.out.println("Simple Snake Game");
        System.out.println("Move: W = Op,S = Ned, A = Venstre, D = Højre");    
        System.out.println("For at pause/forsætte spillet tryk P");

        view.draw(model,isPaused,score);
    
        while(gameRunning){
            checkPauseInput();

            if (checkVictory()){
                gameWon();
                break;
            }

            if (!isPaused){
                handleInput();
                checkFood();
                moveSnake();
            
                if (checkCollision()){
                    gameRunning = false;
                    showGameOverScreen();
                    break;
                }
            }

            view.draw(model,isPaused, score);

            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException e) {
            }
        }
    }

    private void checkPauseInput(){
        if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_P)){
            togglePause();

            while (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_P)){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void togglePause(){
        isPaused =! isPaused;
        if (isPaused){
            System.out.println("Spillet er pauset.");
            System.out.println("Tryk P igen for at forsætte spillet.");
        }
    }
    
    private void handleInput(){ // WASD-tasterne for at bevæge slangen i adskillige retninger.
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

            if (gameSpeed > 50) {
                gameSpeed = gameSpeed - 5;
                System.out.println("Speed increased! Delay is now: " + gameSpeed + "ms");
            }

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

        if (newX <0) newX = model.width - 1;
        if (newX >= model.width) newX = 0;
        if (newY <0) newY = model.height - 1;
        if (newY >= model.height) newY = 0;

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

    private void showGameOverScreen(){
        boolean wasNewHighScore = false;
        if (score>highscore){
            highscore = score;
            wasNewHighScore = true;
            isNewHighScore = true;

            System.out.println("Ny highscore:" + highscore + "!");
        } else{
            isNewHighScore = false;
        }
        
            try {
                Thread.sleep(800);
            }catch (InterruptedException e){}

            double centerX = model.width/2.0;
            double centerY = model.height/2.0;

            StdDraw.clear(StdDraw.BLACK);

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD,33));
            StdDraw.text(model.width / 2.0, model.height / 2.0 + 1, "GAME OVER!");

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN,28));
            StdDraw.text(model.width /2.0, model.height / 2.0 - 0.5, "Score:" + score);
            
            if (wasNewHighScore){
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
                StdDraw.text(centerX, centerY - 2, "Dit bedste:" + highscore);
            }
            
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            StdDraw.text(centerX,centerY - 3.5, "Du har nu følgende muligheder:");
            StdDraw.text(centerX,centerY - 5, "Tryk på R for at genstarte.");
            StdDraw.text(centerX,centerY - 6, "Tryk på Q for at forlade spillet.");
            
            StdDraw.show();       
             
            waitForGameOverInput();
        }
        
        private void waitForGameOverInput(){
            while (true) { 
                if (StdDraw.hasNextKeyTyped()){
                    char key = StdDraw.nextKeyTyped();
                    
                    if (key=='R'||key=='r'){
                        resetGame();
                        break;
                    } else if (key == 'Q' || key=='q'){
                        System.exit(0);
                    }
                }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
            }
        }
    
    private void resetGame(){
        score = 0;
        hasEaten = false;
        gameRunning = true;
        isNewHighScore = false;

        gameSpeed = 200;
        model.slange.clear();
        int startX = model.width/2;
        int startY = model.height/2;
        model.slange.add(new int[]{startX,startY});
        model.slange.add(new int [] {startX, startY + 1});
        model.retning = 3;
        model.food();

        startGame();
    }
    
    private boolean checkVictory(){
        int maxCells = model.width * model.height;
        return model.slange.size() >= maxCells;
    }

    private void gameWon(){
        model.victory = true;
        view.draw(model, isPaused,score);
        view.displayVictory(score);
        gameRunning = false;
        System.out.println("Tillykke med sejren! Du gennemførte vores spil!");
    }
}
