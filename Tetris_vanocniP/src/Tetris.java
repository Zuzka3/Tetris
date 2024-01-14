import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Random;

public class Tetris extends JPanel implements KeyListener {
    public static int PLAYING = 0;
    public static int GAME_OVER = 2;
    private int state = PLAYING;
    Random r = new Random();
    private static int FRAME_RATE = 60;
    private static int delay = 1000 / FRAME_RATE;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int TILE_SIZE = 50;
    private Timer gameLoop;
    private Color[][] grid = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Color[] barva = {Color.decode("#00ffff"),
            Color.decode("#800080"),
            Color.decode("#ff7f00"),
            Color.decode("#0000ff"),
            Color.decode("#00ff00"),
            Color.decode("#ff0000"),
            Color.decode("#ffff00") };
    private final Piece[] pieces = new Piece[7];
    private Piece currentPiece; //tvar ninejsi



    public Tetris() {
        initializePiece();
        currentPiece = randomPiece();
        gameLoop = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                update();
            }
        });
        gameLoop.start();
    }

    private void initializePiece(){

        pieces[0] = new Piece(new int[][]{{1,1}, {1,1},}, this, barva[6]);
        pieces[1] = new Piece(new int[][]{{0,1,1}, {1,1,0}}, this, barva[4]);
        pieces[2] = new Piece(new int[][]{{1,1,0}, {0,1,1},}, this, barva[5]);
        pieces[3] = new Piece(new int[][]{{1,1,1}, {1,0,0},}, this, barva[2]);
        pieces[4] = new Piece(new int[][]{{1,1,1}, {0,1,0},}, this, barva[1]);
        pieces[5] = new Piece(new int[][]{{1,1,1}, {0,0,1}}, this, barva[3]);
        pieces[6] = new Piece(new int[][]{{1,1,1,1},}, this, barva[0]);

        /*currentPiece = pieces[r.nextInt(pieces.length)];
        gameLoop = new Timer(delay, new ActionListener() {
            int n = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
               repaint();
               update();

            }
        });
        gameLoop .start();

         */
    }

    private Piece randomPiece(){
        return pieces[r.nextInt(pieces.length)];
    }

    public void setHraciPole(Color[][] hraciPole) {
        this.grid = hraciPole;
    }

    public Color[][] getHraciPole(){
        return grid;
    }
    private void update(){
        if(state == PLAYING){
            currentPiece.update();
        }
    }
    public void setTedTvar(){
        currentPiece = randomPiece(); //hazi se nam random dilky
        currentPiece.reset();
        checkOverGame();

    }
    private void checkOverGame(){
        int[][] coords = currentPiece.getCoords();
        for(int i = 0; i < coords.length;i ++){
            for(int j = 0; j < coords[0].length; j++){
                if(coords[i][j] != 0){
                    if(grid[i + currentPiece.getY()][j + currentPiece.getX()] != null){
                        state = GAME_OVER;

                    }
                }
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        drawBoard(g);
        if(state == GAME_OVER) {
            gameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        currentPiece.render(g);
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    g.setColor(grid[i][j]);
                    g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }


    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        currentPiece.render(g);
        for(int i =0; i < grid.length; i++){
            for (int j = 0; j < grid[i]. length; j++){
                if (grid[i][j] != null){
                    g.setColor(grid[i][j]);
                    g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

     */

        //NAKRESLENI HRACI PLOCHY
        g.setColor(Color.DARK_GRAY);
        for(int i =0; i < BOARD_HEIGHT; i++){
            g.drawLine(0,TILE_SIZE * i, TILE_SIZE * BOARD_WIDTH,TILE_SIZE * i);
        }

        for(int k =0; k < BOARD_WIDTH + 1; k++){
            g.drawLine(k * TILE_SIZE,0, k *TILE_SIZE,TILE_SIZE * BOARD_HEIGHT);
        }
       // if(state == GAME_OVER){
         //   g.setColor(Color.RED);
          //  g.setFont(new Font("Arial",Font.BOLD, 70));
           // g.drawString("GAME OVER", 25,300);
        //}
    }

    private void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.drawString("GAME OVER", 25, 300);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e){
        keyPress(e.getKeyCode());
    }
    private void keyPress(int KeyCode){
        if(state == GAME_OVER){
            keyPress(KeyCode);
        }else{
            keyPress(KeyCode);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { //podrzis sipku dolu - zrychly se padani kosticky
            currentPiece.faster();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //podrzis pravou sipku - kosticka pojede doprava
            currentPiece.right();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) { //podrzis levou sipku - kosticka pojede doleva
            currentPiece.left();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) { //podrzis horni sipku - kosticka se otoci
            currentPiece.rotaceDilku();
        }
    }

    private void clean(int keyCode){
        if (keyCode == KeyEvent.VK_SPACE){
            resetGame();
        }
    }
    private void resetGame(){
        for (int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = null;
            }
        }
        setTedTvar();
        state = PLAYING;
    }
/*
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { //podrzis sipku dolu - zrychly se padani kosticky
            currentPiece.faster();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //podrzis pravou sipku - kosticka pojede doprava
            currentPiece.right();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) { //podrzis levou sipku - kosticka pojede doleva
            currentPiece.left();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) { //podrzis horni sipku - kosticka se otoci
            currentPiece.rotaceDilku();
        }

        //clean the board
        if (state == GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = null;
                    }
                }
                setTedTvar();
                state = PLAYING;
            }
        }
    }

 */
    private void handleKeyRelease(int keyCode){
        if(keyCode == KeyEvent.VK_DOWN){
            currentPiece.slow();
        }
    }




}
