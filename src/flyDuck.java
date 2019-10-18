
public class flyDuck {
    
    private int x, x1, x2, y, y1, y2;
    private int speed, speedX1, speedX11;
    
    flyDuck(){
        
    }
    
    flyDuck(int x, int y, int speed){
        
        this.x = x;
        this.y = y;
        this.speed = speed;
        
    }

    public int get1X() {
        return x;
    }
    public int get2X() {
        return x1;
    }
    public int get3X() {
        return x2;
    }

    public void set1X(int x) {
        this.x = x;
    }
    public void set2X(int x1) {
        this.x1 = x1;
    }
    public void set3X(int x2) {
        this.x2 = x2;
    }
    

    public int get1Y() {
        return y;
    }
    public int get2Y() {
        return y1;
    }
    public int get3Y() {
        return y2;
    }

    public void set1Y(int y) {
        this.y = y;
    }
    public void set2Y(int y1) {
        this.y1 = y1;
    }
    public void set3Y(int y2) {
        this.y2 = y2;
    }
    
    public int getSpeed() {
        return speed;
    }
    public int getSpeed1X() {
        return speedX1;
    }
    public int getSpeed2X() {
        return speedX11;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setSpeed1X(int speedX1) {
        this.speedX1 = speedX1;
    }
    public void setSpeed2X(int speedX11) {
        this.speedX11 = speedX11;
    }

    
    
}
