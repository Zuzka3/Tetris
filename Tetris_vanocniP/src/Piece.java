import java.awt.*;

public class Piece {

    private  int x = 4, y = 0;
    private int normal = 600;
    private int fast = 40;
    private int delayTimeForMovement = normal;
    private long beginTime;
    private int deltaX = 0;
    private boolean kolize = false;

    private int[][] coords;
    private Tetris tetris;
    private Color color;

    public Piece(int [][] coords, Tetris tetris, Color color){
        this.coords = coords;
        this.tetris = tetris;
        this.color = color;
    }

    public void reset(){
        this.x = 4;
        this.y = 0;
        kolize= false;
    }


    public void update () {
        if(kolize){
            //FILL THE COLOR FOR BOARD
            for(int i = 0; i < coords.length; i++){
                for(int j = 0; j < coords[0].length; j++){
                    if(coords[i][j] != 0){
                        tetris.getHraciPole()[y + i][x + j] = color;
                    }
                }
            }
            checkLine();
            //set the current shape
            tetris.setTedTvar();
            return;
        }
        //check moving horizontal
        boolean moveX = true;
        if (!(x + deltaX + coords[0].length >10) && !(x + deltaX < 0)){ //radek
            for(int i = 0; i < coords.length; i++){ //sloupec
                for(int j = 0; j < coords[i].length;j++){
                    if (coords[i][j] != 0){
                        if(tetris.getHraciPole()[y+i][x + deltaX + j] != null){
                            moveX = false;
                        }
                    }
                }
            }
            if(moveX){
                x += deltaX;
            }

        }
        deltaX  = 0;

        if(System.currentTimeMillis() - beginTime > delayTimeForMovement) {
            //vertical movement
            if(!(y + coords.length >= Tetris.BOARD_HEIGHT)){
                for(int i = 0; i < coords.length;i++){//radek
                    for(int j =0; j < coords[i].length;j++){ //sloupec
                        if(coords[i][j] != 0){
                            if(tetris.getHraciPole()[y + 1 + i][x + deltaX + j] != null){
                                kolize = true;
                            }
                        }
                    }
                }
                if(!kolize){
                    y++;
                }
            }else {
                kolize = true;
            }
            beginTime = System.currentTimeMillis();
        }
    }

    private void checkLine(){
        int  i = tetris.getHraciPole().length - 1; //spodni radek
        for(int j = tetris.getHraciPole().length - 1; j > 0; j--){ //horni radek
            int count = 0;
            for(int k = 0; k < tetris.getHraciPole()[0].length; k ++){
                if(tetris.getHraciPole()[j][k] != null){
                    count++;
                }
                tetris.getHraciPole()[i][k] = tetris.getHraciPole()[j][k];
            }
            if(count < tetris.getHraciPole()[0].length){
                i--;
            }

        }
    }
    public void rotaceDilku(){
        int[][] rotatedShape = transposeMatrix(coords);
        reverseRows(rotatedShape);
        if((x + rotatedShape[0].length > Tetris.BOARD_WIDTH) || (y + rotatedShape.length > 20)){
            return;
        }
        //check for collision with other shapes before rotation
        for(int i = 0; i < rotatedShape.length; i ++){
            for(int j = 0; j < rotatedShape[i].length; j ++){
                if(rotatedShape[i][j] != 0){
                    if(tetris.getHraciPole()[y + i][x + j] != null){
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    private int [][] transposeMatrix(int[][] matrix){
        int [][] temp = new int [matrix[0].length][matrix.length];
        for(int i = 0; i < matrix.length;i++){ //row
            for (int j = 0; j< matrix[0]. length; j++){ //collum
                temp[j][i] = matrix[i][j];
            }}
        return temp;
    }
    private void reverseRows(int [][] matrix){
        int middle = matrix.length / 2;
        for (int i = 0; i < middle; i++){//row
            int[] temp = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }
    }
    public void render(Graphics g ){
        //NAKRESLENI DILKU
        for(int i = 0; i < coords.length; i++){ //i = radek
            for(int j =0; j < coords[0].length; j++){ //j = sloupec
                if(coords[i][j] != 0) {
                    g.setColor(color);
                    g.fillRect(j * Tetris.TILE_SIZE + x * Tetris.TILE_SIZE, i * Tetris.TILE_SIZE + y * Tetris.TILE_SIZE, Tetris.TILE_SIZE, Tetris.TILE_SIZE);
                }}}}

    public void faster(){
        delayTimeForMovement = fast;
    }
    public void slow(){
        delayTimeForMovement = normal;
    }
    public void right(){
        deltaX = 1;
    }
    public void left(){
        deltaX = -1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int[][] getCoords() {
        return coords;
    }
    public void setCoords(int[][] coords) {
        this.coords = coords;
    }
}
