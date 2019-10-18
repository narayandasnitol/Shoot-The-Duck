import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;



public class MouseManager implements MouseListener, MouseMotionListener, KeyListener{
	
	private  int mx;
	private  int my;
	private int gunammo=0, tried=0, reload = 0, gm=0, gIn=0;
	private int mouseX, mouseY, gameMenu=0, exit =0, mX1, mX2, mY1, mY2, fire=0;
	
	private Game game;
	
	private boolean skip;
	
	
	private Audio gunSound;
	
	
	
	public MouseManager(){
		
		game = new Game();
	}
	
	public int getReload(){
		return reload;
	}
	public void setReload(int reload){
		this.reload = reload;
		
	}
	
	public boolean getSkip(){
		return skip;
	}

	public int getMX(){
		return mx;
	}
	
	public void setMX(int mx){
		this.mx = mx;
	}
	
	public int getMY(){
		return my;
	}
	public void setMY(int my){
		this.my = my;
	}
	
	public int getAmmo(){
		return gunammo;
	}
	
	public int getTried(){
		return tried;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	public void setMouseX(int mouseX){
		this.mouseX = mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	public void setMouseY(int mouseY){
		this.mouseY = mouseY;
	}
	
	public void setGameMenu(int gameMenu){
		this.gameMenu = gameMenu;
	}
	
	public int getGameMenu(){
		return gameMenu;
	}
	
	public int getExit(){
		return exit;
	}
	
	public int getMX1(){
		return mX1;
	}
	public void setMX1(int mX1){
		this.mX1 = mX1;
	}
	public int getMX2(){
		return mX2;
	}
	public void setMX2(int mX2){
		this.mX2 = mX2;
	}
	public int getMY1(){
		return mY1;
	}
	public int getMY2(){
		return mY2;
	}
	
	public int getFire(){
		return fire;
	}
	public void setFire(int fire){
		this.fire = fire;
	}
	
	public int getIn(){
		return gIn;
	}
	
	
	public void mouseDragged(MouseEvent e) {
		
		
	}

	
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
		
		
		
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			
			fire = 1;
			
			if(gunammo<6){
				gunSound = new Audio("/gun sound.wav");
				gunSound.play();
			
				tried++;
				
				//System.out.println(game.getD());
				
			mouseX = e.getX();
			mouseY = e.getY();
			
			mX1 = mouseX + 70;
			mX2 = mouseX - 70;
			
			mY1 = mouseY + 70;
			mY2 = mouseY - 70;
			
			reload = 0;
			
			}
			
			
			
			if(gunammo > 6){
				gunSound = new Audio("/empty gun.wav");
				gunSound.play();
				
				
			}
			
			gunammo++;
			
		}
		
		if(e.getButton() == MouseEvent.BUTTON3){
			gunSound = new Audio("/gun reload.wav");
			gunSound.play();
			
			reload = 1;
			
			gunammo = 0;
		}
		
		if(e.getButton() == MouseEvent.BUTTON2){
			//game.setCount(0);
			
			
		}
	}

	
	public void mouseEntered(MouseEvent e) {
		
		
	}

	
	public void mouseExited(MouseEvent e) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}

	
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()== KeyEvent.VK_ENTER){
			
			gameMenu = 1;
			gm++;
			
			if(gm==2){
				gunSound = new Audio("/gun reload.wav");
				gunSound.play();
			}
			
		}
		
		
		if(e.getKeyCode() ==KeyEvent.VK_ESCAPE){
			exit = 1;
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			
			if(gm==0){
				gunSound = new Audio("/gun reload.wav");
				gunSound.play();
			}
			
			if(gIn == 1)
				skip = true;
			
			gm++;
			gIn = 1;
			
			if(gm==3){
				gunSound = new Audio("/gun reload.wav");
				gunSound.play();
			}
		}
	}
	
	

	
	public void keyReleased(KeyEvent e) {
		
		
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

}
