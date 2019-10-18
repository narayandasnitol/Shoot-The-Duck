import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import sun.java2d.pipe.DrawImage;

import java.awt.*;



public class Game extends JFrame implements Runnable{
	
	
	private static final long serialVersionUID = 1L;


	private Display display;

	
	
	private MouseManager mouseManager;
	
	public int width, height;
	public String title;
	
	public Thread thread;
	public JPanel p;
	
	public boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	private Audio bMusic, sMusic, mMusic, gOver, duckQuack, gunsound;
	
	private BufferedImage backgroundImg, duckImg, cloud, cloud2, dog, gameOver, bird1, bird2, specialDead, stopwatch;
	private BufferedImage bullet6, reload, fire, sight, pistol, pisReload, menu, duckDead, birdDead, blackBack;
	
	private int cloudX = -500, cloudX1 = -1000, cloudX2 = -1400, cloudSpeed = 3;
	private int cloudSpeedMenu, cloudMenuX = -500, cloudMenuX1 = -1000, cloudMenuX2 = -1400;
	private int fallingY, fallingX, falling2Y, falling2X, falling3Y, falling3X;
	private int fallingSpeed, fallingFlag1=0, fallingFlag2=0, fallingFlag3=0,introTime=0;
	private int tim=0, tim2=0, gTried, gStop, gOv=0, gO=0, anc=0, gameTimer=0, remainingTime, playingTime=0;
	
	int mainMusic = 0, menuMusic = 0;
	
	java.awt.Image bird, duckFly, enter, story, loading, smallDuck, intro; // for gif file
	
	
	ArrayList<Duck> ducks ;
	ArrayList<flyDuck> flyDucks ;
	
	
	private int duckCount=0, duckKilled=0, birdCount=0, birdKilled=0, totalScore, dogY = 1100, dogYY = 600;
	
	private int highScore, gt, fB1=0, fB2=0, fB3=0;
	
	private boolean hScoreFlag = true,hScoreFlag1 = true, mMenu = true, mStory = true, mGame = true, gunCount = true;
	private boolean fB11=true, fB22= true, fB33 = true;

	private boolean mF, specialD = false, sDD = true, sDD2= true;
	private String answer="", answer2="";
	private int specialX=2100, specialY=750, sDCount=0;
	
	

	public Game(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		mouseManager = new MouseManager();
		
		
	}
	
	public Game(){
		
		
	}
	
	
	public void init(){
		
		display = new Display(title, width, height);
		
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addKeyListener(mouseManager);
		display.getCanvas().addKeyListener(mouseManager);
		
		display.getFrame().setCursor(display.getFrame().getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(12, 13),
	            "null"));  // for removing cursor
		
		
		//load all image
		loadImage();
		
		//load all GIF
		loadGIF();
		
		// copy of duck
		duckArrayList();
	
		//copy flying duck
		birdArrayList();
		
		//Background Music
		//musicGame();
		
			
		File file = new File("highscore.txt");
		File file2 = new File("highscorename.txt");
		
		Scanner sc = null;
		Scanner sc2 = null;
		
		
		
		try {
			sc = new Scanner(file);
			highScore = sc.nextInt();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		
		try {
			sc2 = new Scanner(file2);
			answer = sc2.nextLine();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		
		
		mF = true;
		
		
		
	}
	
	
	
	private void tick(){
		
		
		
	}
	
	
	
	private void render(){
		
		bs = display.getCanvas().getBufferStrategy();
		
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		
		//Clear Screen
		
		g.clearRect(0, 0, width, height);
				
	
		//Draw Start
		
		
		// for menu
		if(mouseManager.getGameMenu() == 0){
			
			introTime++;
			
			if(mouseManager.getIn() == 1){
				introTime = 5000;
			}
			
			//System.out.println(introTime);
			
			g.drawImage(intro, 0, 0, null);
			
			
			if(introTime >=4000){
			
				if(mMenu == true){
					musicMenu();
					mMenu = false;
				}
				
				
				menu();
			
			}
			
		}
		
		
		// for starting game
		if(mouseManager.getGameMenu() == 1){
			
			if(mStory == true){
				musicStory();
				mStory = false;
			}
			if(mMenu == false)
				mMusic.stop();
			
			
			
			g.drawImage(blackBack,0,0,null);
			g.drawImage(story, 0, 0, null);
			g.drawImage(loading, 0, 990, null);
			
			tim++;
			
			
			//System.out.println(tim);
			
			
			if(mouseManager.getSkip() == true){
				tim = 3500;
			}
			
			if(tim >=3000){
				
				if(mGame == true){
					
					if(playingTime>0){
						musicGame();
					
					if(mStory == false)
						sMusic.stop();
					
					mGame = false;
					
					}
				} //
				
				
				if(hScoreFlag1 == true){
					
		 			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
		 			          "Arial", Font.BOLD, 40)));  
		 			answer2 = JOptionPane.showInputDialog("How long time(seconds) you want to play ?");
		 			
		 			playingTime = Integer.parseInt(answer2);
		 			
		 			hScoreFlag1 = false;
		 			
		 		}
				
				if(playingTime>0)
					gamePlay();
				
				gameTimer++;
				
			}
			
			
			
		} 
		
	            
		g.dispose();
		bs.show();
		
		
	}

	
	public void run(){
					
		init();
		
		
		while(running){
			
			tick();
			render();
			
		}
		
		stop();
		
		
	}
	
	public synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void loadImage(){
		
		backgroundImg = ImageLoader.loadImage("/background.jpeg");
		duckImg = ImageLoader.loadImage("/duck.png");
		cloud = ImageLoader.loadImage("/cloud.png");
		cloud2 = ImageLoader.loadImage("/cloud2.png");
		sight = ImageLoader.loadImage("/sight.png");
		pistol = ImageLoader.loadImage("/pistol.png");
		bullet6 = ImageLoader.loadImage("/bullet6.png");
		reload = ImageLoader.loadImage("/reload.png");
		pisReload = ImageLoader.loadImage("/pisReload.png");
		fire = ImageLoader.loadImage("/fire.png");
		menu= ImageLoader.loadImage("/menu.jpeg");
		duckDead = ImageLoader.loadImage("/duck dead.png");
		birdDead = ImageLoader.loadImage("/dead bird.png");
		dog = ImageLoader.loadImage("/dog.png");
		blackBack = ImageLoader.loadImage("/blackBack.jpeg");
		gameOver = ImageLoader.loadImage("/gameOver.jpeg");
		bird1 = ImageLoader.loadImage("/bird1.png");
		bird2 = ImageLoader.loadImage("/bird2.png");
		specialDead = ImageLoader.loadImage("/specialDead.png");
		stopwatch = ImageLoader.loadImage("/stopwatch.png");
		
	}
	
	public void loadGIF(){
		ImageIcon imageIcon = new ImageIcon("res/fly.gif");
		bird = imageIcon.getImage();
		
		ImageIcon imageIcon2 = new ImageIcon("res/duckFly.gif");
		duckFly = imageIcon2.getImage();
		
		ImageIcon imageIcon3 = new ImageIcon("res/enter.gif");
		enter = imageIcon3.getImage();
		
		ImageIcon imageIcon4 = new ImageIcon("res/story.gif");
		story = imageIcon4.getImage();
		
		ImageIcon imageIcon5 = new ImageIcon("res/loading.gif");
		loading = imageIcon5.getImage();
		
		ImageIcon imageIcon6 = new ImageIcon("res/smallDuck.gif");
		smallDuck = imageIcon6.getImage();
		
		ImageIcon imageIcon7 = new ImageIcon("res/introAni.gif");
		intro = imageIcon7.getImage();
		
	}
	
	
	public void duckArrayList(){
		
		ducks = new ArrayList<>();
        int x =1500;
		
		for(int i=0;i<150;i++) {
            
            Duck duck = new Duck();
            
            // random ducks
            duck.setX0((int)(Math.random() * (x - 10)*75 + 2000));
            duck.setX1((int)(Math.random() * (x - 10)*60 + 2000));
            duck.setX11((int)(Math.random() * (x - 10)*50 + 2000));
            duck.setX12((int)(Math.random() * (x - 10)*40 + 2000));
            
           
            
            
            
            // random duck speed
            duck.setSpeed((int)(Math.random() * (18 - 2) + 2));
            duck.setSpeedX1((int)(Math.random() * (12 - 2) + 2));
            duck.setSpeedX11((int)(Math.random() * (14 - 2) + 2));
            duck.setSpeedX12((int)(Math.random() * (16 - 2) + 2));
            
            // set ducks Y position
            duck.setY0(880);
            duck.setY1(780);
            duck.setY11(680);
            duck.setY12(580);
            
            
            ducks.add(duck);
            
      
            
        }
	}
	
	public void birdArrayList(){
		
		flyDucks = new ArrayList<>();
        int d =-2000;
		
		for(int i=0;i<30;i++) {
            
            flyDuck flyduck = new flyDuck();
            
            // random birds
            flyduck.set1X((int)(Math.random() * (d + 10)*10 ));
            flyduck.set2X((int)(Math.random() * (d + 10)*15 ));
            flyduck.set3X((int)(Math.random() * (d + 10)*20 ));
            
            // random birds speed
            flyduck.setSpeed((int)(Math.random() * (11-1)));
            flyduck.setSpeed1X((int)(Math.random() * (10-1)));
            flyduck.setSpeed2X((int)(Math.random() * (12-1)));
            
            // set birds Y position
            flyduck.set1Y(80);
            flyduck.set2Y(210);
            flyduck.set3Y(310);
            
            
            flyDucks.add(flyduck);
            
            
        }
	}
	
	
	
	public void musicGame(){
		
		bMusic = new Audio("/backgroundMusic.wav");
		bMusic.play();
		
		duckQuack = new Audio("/duckQuack.wav");
		duckQuack.play();
	}
	public void musicStory(){
		
		sMusic = new Audio("/storySound.wav");
		sMusic.play();
	}
	public void musicMenu(){
	
	mMusic = new Audio("/menuSound.wav");
	mMusic.play();
	}
	public void musicGameOver(){
		gOver = new Audio("/gameOver.wav");
		gOver.play();
	}
	
	
	public void menu(){
		g.drawImage(menu.getScaledInstance(1920, 1080, Image.SCALE_DEFAULT),0,0,null);
		g.drawImage(smallDuck, 1450, 30, null);
		
		g.drawImage(enter, 1500, 700, null);
		g.drawImage(bird, 800, 20, null);
		//g.drawImage(dog.getScaledInstance(420, 600, Image.SCALE_DEFAULT), 50, 470, null);
		
		// for menu , moving cloud
		menuCloud();
		
		if(mouseManager.getExit() == 1){
        	display.getFrame().dispose();
        	running = false;
        	//bMusic.stop();
        	
        }
		
		
		
		
		
	}
	
	public void menuCloud(){
		g.drawImage(cloud, cloudMenuX, 0, null);
		g.drawImage(cloud2, cloudMenuX1, 150, null);
		g.drawImage(cloud, cloudMenuX2, 0, null);
		
		cloudSpeedMenu = 5;
		
		
		cloudMenuX += cloudSpeedMenu;
		if(cloudMenuX == 1970){
			cloudMenuX = -500;
		}
		cloudMenuX1 += cloudSpeedMenu;
		if(cloudMenuX1 == 1970){
			cloudMenuX1 = -1000;
		}
		cloudMenuX2 += cloudSpeedMenu;
		if(cloudMenuX2 == 1970){
			cloudMenuX2 = -1400;
		}
	}
	
	
	
	
	public void gamePlay(){
		g.drawImage(backgroundImg, 0, 0, null);
		
		
		

		gameCloud(); // cloud movement
	
		movingDuck();  // duck movement
		
		birdFlying(); // bird flying
	
		
		// sight of gun
		if(mF == true)
			g.drawImage(sight.getScaledInstance(40, 40, Image.SCALE_DEFAULT), mouseManager.getMX(), mouseManager.getMY(), null);
			
		
		// bullet
		if(mF == true){		
			bullet();
		}
		
		
		// gun reload
		if(mF == true){
			if(mouseManager.getReload() == 1){
				g.drawImage(pisReload.getScaledInstance(300, 300, Image.SCALE_DEFAULT),mouseManager.getMX()-100, 790, null);
				
			}
			
			// gun movement
			if(mouseManager.getReload() == 0){
				g.drawImage(pistol.getScaledInstance(250, 180, Image.SCALE_DEFAULT), mouseManager.getMX()-100, 900, null);
				
			}
			
			//gun fire
			if(mouseManager.getFire()==1 && mouseManager.getAmmo()<=6){
				g.drawImage(fire.getScaledInstance(140, 140, Image.SCALE_DEFAULT), mouseManager.getMX()-80, 850, null);
				mouseManager.setFire(0);
			}
		
		}
		
		// running score
		runningScore();
		
            
            // press "escape" key for exit
            if(mouseManager.getExit() == 1){
            	display.getFrame().dispose();
            	running = false;
            	bMusic.stop();
            }
            
            
            
            // dead bird fall
            fallingDeadBird();
            
            if(mF == true){
            	gTried = mouseManager.getTried();
            	
            }
            
            
            
            
            if(gameTimer%25 == 0){
				//tim2++;
				remainingTime = playingTime-tim2++;
				//System.out.println("Remaining Time: "+remainingTime);
			}
            
        	
	}
	
	
	
	
	
	
	public void gameCloud(){
		g.drawImage(cloud, cloudX, 0, null);
		g.drawImage(cloud2, cloudX1, 150, null);
		g.drawImage(cloud, cloudX2, 0, null);
		
		
		cloudX += cloudSpeed;
		if(cloudX == 1970){
			cloudX = -500;
		}
		cloudX1 += cloudSpeed;
		if(cloudX1 == 1970){
			cloudX1 = -1000;
		}
		cloudX2 += cloudSpeed;
		if(cloudX2 == 1970){
			cloudX2 = -1400;
		}
	}
	
	
	public void movingDuck(){
		for(int i=0; i<ducks.size(); i++){
			
			// set duck co-ordinate ( X, Y )
			g.drawImage(duckImg, ducks.get(i).getX0(), ducks.get(i).getY0(), null);
	        g.drawImage(duckImg, ducks.get(i).getX1(), ducks.get(i).getY1(), null);
	        g.drawImage(duckImg, ducks.get(i).getX11(), ducks.get(i).getY11(), null);            
	        g.drawImage(duckImg, ducks.get(i).getX12(), ducks.get(i).getY12(), null);
	        
	        
	        //set duck Speed
	        ducks.get(i).setX0(ducks.get(i).getX0() - ducks.get(i).getSpeed());
	        ducks.get(i).setX1(ducks.get(i).getX1() - ducks.get(i).getSpeedX1());
	        ducks.get(i).setX11(ducks.get(i).getX11() - ducks.get(i).getSpeedX11());
	        ducks.get(i).setX12(ducks.get(i).getX12() - ducks.get(i).getSpeedX12());
	        
	        
	        // duck counter
	        if((ducks.get(i).getX12() <=0 && ducks.get(i).getX12() >= -10)||(ducks.get(i).getX11() <=0 && ducks.get(i).getX11() >= -10)
	        		|| (ducks.get(i).getX1() <=0 && ducks.get(i).getX1() >= -10) || (ducks.get(i).getX0() <=0 && ducks.get(i).getX0() >= -10)){
	            
	        	duckCount++;
	            
	        }
	        
	        
	        // duck 1 hit
	        try{
		        if(((ducks.get(i).getX0()>=mouseManager.getMX2()) && (ducks.get(i).getX0()<=mouseManager.getMX1())) && 
		        		(((ducks.get(i).getY0()>=mouseManager.getMY2()) && (ducks.get(i).getY0()<=mouseManager.getMY1())))){
		        	
		        	g.drawImage(duckDead, ducks.get(i).getX0(), ducks.get(i).getY0(), null);
		        	
		        		duckKilled++;
		        		
		        		
		        		ducks.remove(i);
		        		
		        		
		        		mouseManager.setMX1(0);
		        		mouseManager.setMX2(0);
		        		
		        		
		        }
	        } catch(Exception e){
	        	
	        }
	        
	        
	        // duck 2 hit
	        try{
		        if(((ducks.get(i).getX1()>=mouseManager.getMX2()) && (ducks.get(i).getX1()<=mouseManager.getMX1())) && 
		        		(((ducks.get(i).getY1()>=mouseManager.getMY2()) && (ducks.get(i).getY1()<=mouseManager.getMY1())))){
		        		
		        	
		        	g.drawImage(duckDead, ducks.get(i).getX1(), ducks.get(i).getY1(), null);
		        		
		        		duckKilled++;
		        	
		        		
		        		ducks.remove(i);
		        		
		        		mouseManager.setMX1(0);
		        		mouseManager.setMX2(0);
		        		
		       
		        }
	        } catch(Exception e){
	        	
	        }
	        
	        
	        // duck 3 hit
	        try{
		        if(((ducks.get(i).getX11()>=mouseManager.getMX2()) && (ducks.get(i).getX11()<=mouseManager.getMX1())) && 
		        		(((ducks.get(i).getY11()>=mouseManager.getMY2()) && (ducks.get(i).getY11()<=mouseManager.getMY1())))){
		        	
		        		
		        	g.drawImage(duckDead, ducks.get(i).getX11(), ducks.get(i).getY11(), null);
		        	
		        		duckKilled++;
		        		
		        		ducks.remove(i);
		        		
		        		mouseManager.setMX1(0);
		        		mouseManager.setMX2(0);
		        		
		        		
		        }
	        
	        } catch(Exception e){
	        	
	        }
		
	        
	        
	        // duck 4 hit
	        try{
			        if(((ducks.get(i).getX12()>=mouseManager.getMX2()) && (ducks.get(i).getX12()<=mouseManager.getMX1())) && 
			        		(((ducks.get(i).getY12()>=mouseManager.getMY2()) && (ducks.get(i).getY12()<=mouseManager.getMY1())))){
			        	
			        	g.drawImage(duckDead, ducks.get(i).getX12(), ducks.get(i).getY12(), null);
			        	
			        	
			    		duckKilled++;
			    		
			    		
			    		ducks.remove(i);
			    		
			    		mouseManager.setMX1(0);
			    		mouseManager.setMX2(0);
			    		
			    		
			    }
	        } catch(Exception e){
	        	
	        }
	        
	        
	        // final score of game
	        if( remainingTime < 0){
	        	
	        	bMusic.stop();
	        	duckQuack.stop();
	        	
	        	 mF = false;
	        	 gO = 1;
	        	 sDD2 = false;
	        	 
	        	 
	        	// show final score
	        	 finalScore();
	        	 
	        	
	        	break;
	        }
	        
	    }
		
		
		if(duckCount != 0){
			if(duckCount % 10 == 0){
				
				specialD = true;	
				
			}
		
		}
		
		
	if(sDD2 == true){	
		if(specialD == true){
			specialX -= 20;
			
			if(sDD == true)
				g.drawImage(bird2.getScaledInstance(200, 140, Image.SCALE_DEFAULT), specialX, specialY, null);
			
			if(((specialX>=mouseManager.getMX2()) && (specialX<=mouseManager.getMX1())) && 
	        		(((specialY>=mouseManager.getMY2()) && (specialY<=mouseManager.getMY1())))){
				
				g.drawImage(specialDead.getScaledInstance(200, 140, Image.SCALE_DEFAULT), specialX, specialY, null);
				
				mouseManager.setMX1(0);
	    		mouseManager.setMX2(0);
				
				sDD = false;
				
				sDCount++;
			}
			
			
			if(sDD == false || specialX <=-80){
				specialD = false;
				specialX = 2100;
				specialY = 750;
				sDD = true;
			}
		}
		
	}
		
		
	}
	
	
	
	
	public void birdFlying(){
		
		for(int j=0; j<flyDucks.size(); j++){
			
			
			// flying bird co-ordinate ( X, Y)
			g.drawImage(duckFly, flyDucks.get(j).get1X(), flyDucks.get(j).get1Y(), null);
			g.drawImage(duckFly,flyDucks.get(j).get2X(),  flyDucks.get(j).get2Y(), null);
			g.drawImage(duckFly, flyDucks.get(j).get3X(), flyDucks.get(j).get3Y(), null);
			
			
			
			// flying bird speed
			flyDucks.get(j).set1X(flyDucks.get(j).get1X() + flyDucks.get(j).getSpeed());
			flyDucks.get(j).set2X(flyDucks.get(j).get2X() + flyDucks.get(j).getSpeed1X());
			flyDucks.get(j).set3X(flyDucks.get(j).get3X() + flyDucks.get(j).getSpeed2X());
            
            
            // flying bird counter
            if((flyDucks.get(j).get1X() >=1920 && flyDucks.get(j).get1X() <= 1925)  || (flyDucks.get(j).get2X() >=1920 && flyDucks.get(j).get2X() <= 1925)
            		|| (flyDucks.get(j).get3X() >=1920 && flyDucks.get(j).get3X() <= 1925)){
            	
            	birdCount++;
            	
            }
            
            
            // flying bird 1 hit
            try{
	            if((flyDucks.get(j).get1X()>mouseManager.getMX2()) && (flyDucks.get(j).get1X()<mouseManager.getMX1()) && 
	            		((flyDucks.get(j).get1Y()>mouseManager.getMY2()) && (flyDucks.get(j).get1Y()<mouseManager.getMY1()))){
	            	
	            	fallingX = flyDucks.get(j).get1X();
	            	fallingY = flyDucks.get(j).get1Y();
	            	fallingFlag1 = 1;
	            	
	            	g.drawImage(birdDead, flyDucks.get(j).get1X(), flyDucks.get(j).get1Y(), null);
	            	
	            	birdKilled++;
	         
	        
	            	flyDucks.remove(j);
	            	
	     
	        		mouseManager.setMX1(0);
	        		mouseManager.setMX2(0);
	            		
	       	
	            }
            
            } catch(Exception e){
            	
            }
            
            
         // flying bird 2 hit
            try{
	            if((flyDucks.get(j).get2X()>mouseManager.getMX2()) && (flyDucks.get(j).get2X()<mouseManager.getMX1()) && 
	            		((flyDucks.get(j).get2Y()>mouseManager.getMY2()) && (flyDucks.get(j).get2Y()<mouseManager.getMY1()))){
	            	
	            	falling2X = flyDucks.get(j).get2X();
	            	falling2Y = flyDucks.get(j).get2Y();
	            	fallingFlag2 = 1;
	            	
	            	g.drawImage(birdDead, flyDucks.get(j).get2X(), flyDucks.get(j).get2Y(), null);	
	            	
	            	
	            	birdKilled++;
	            	
	            	flyDucks.remove(j);
	        		
	        		mouseManager.setMX1(0);
	        		mouseManager.setMX2(0);
	            		
	            		
	            }
            } catch(Exception e){
            	
            }
            
            
         // flying bird 3 hit
            try{
	            if((flyDucks.get(j).get3X()>mouseManager.getMX2()) && (flyDucks.get(j).get3X()<mouseManager.getMX1()) && 
	            		((flyDucks.get(j).get3Y()>mouseManager.getMY2()) && (flyDucks.get(j).get3Y()<mouseManager.getMY1()))){
	            	
	            	
	            	falling3X = flyDucks.get(j).get3X();
	            	falling3Y = flyDucks.get(j).get3Y();
	            	fallingFlag3 = 1;
	            	
	            	g.drawImage(birdDead, flyDucks.get(j).get3X(), flyDucks.get(j).get3Y(), null);
	            		
	            		birdKilled++;
	            		
	            		flyDucks.remove(j);
	            		
	            		mouseManager.setMX1(0);
	            		mouseManager.setMX2(0);
	            		
	            	
	            }
            } catch(Exception e){
            	
            }
            
            
            if(remainingTime < 0){
            	break;
            }
		}
		
		}
		
	
	
	
	public void runningScore(){
		 Font myFont = new Font ("Courier New", 1, 20);
         g.setFont (myFont); 
         g.setColor(Color.DARK_GRAY);
         
         if(gO==0){
	         g.drawString("Duck Passed: "+duckCount, 10, 20);
	         g.drawString("Bird Passed: "+birdCount, 230, 20);
	         g.drawString("Duck Killed: "+duckKilled, 460, 20);
	         g.drawString("Bird Killed: "+birdKilled, 700, 20);
	         g.drawString("Target attempt: "+gTried, 920, 20);
	         
	         g.drawString("Score: "+(((((duckKilled + birdKilled)*10) + (sDCount * 50))) -  ((duckCount+birdCount)*2)),1170,20);
	         g.drawString("Special Duck : "+sDCount, 1350, 20);
	         g.drawString("High Score: " +answer +" : "+highScore, 1600, 20);
	         
	         Font myFont2 = new Font ("Courier New", 1, 35);
	         g.setFont (myFont2);
	         g.setColor(Color.DARK_GRAY);
	         g.drawString("Remaining Time: "+remainingTime+"s", 750, 70);
	         
	         g.drawImage(stopwatch.getScaledInstance(50, 60, Image.SCALE_DEFAULT), 690, 30, null);
	         
         }
         
	}
	
	public void finalScore(){
		
		g.drawImage(gameOver,0,0,null);
		//g.drawImage(bird, 810, 20, null);
		g.drawImage(dog.getScaledInstance(420, 600, Image.SCALE_DEFAULT), 0, 490, null);
		g.drawImage(smallDuck, 810, 310, null);
		
		File file = new File("highscore.txt");
		File file2 = new File("highscorename.txt");
		
		PrintWriter pt = null;
		PrintWriter pt2 = null;
		
		try {
			pt = new PrintWriter(file);
			pt2 = new PrintWriter(file2);
			
			if(totalScore>highScore){
				 
					pt.println(totalScore);
					
			 		if(hScoreFlag == true){
			 			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
			 			          "Arial", Font.BOLD, 30)));  
			 			answer = JOptionPane.showInputDialog("Congratulations ! New High Score....Enter your name: ");
			 			
			 			hScoreFlag = false;
			 			
			 		}
			 }
			 
			 else{
				 pt.println(highScore);
			 }
			
			if(totalScore>highScore)
				pt2.println(answer);
			else
				pt2.println(answer);
			 
			 pt.close();
			 pt2.close();
			 
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
		/*Font myFont = new Font ("Courier New", 1, 100);
        g.setFont (myFont); 
        g.drawString("Game Over", 700, 300);*/
	 
        Font myFont2 = new Font ("Courier New", 1, 60);
        g.setFont (myFont2); 
	 
		 //g.drawString("Score: ", 830, 410);
        
         g.setColor(Color.WHITE);
         
         g.drawString("Target Attempt: "+gTried, 420, 680);
		 g.drawString("Duck Passed: "+duckCount, 420, 740);
		 g.drawString("Bird Passed: "+birdCount, 420, 800);
		 g.drawString("Duck Killed: "+duckKilled, 420, 860);
		 g.drawString("Bird Killed: "+birdKilled, 420, 920);
		 g.drawString("Special Duck Killed: "+sDCount, 420, 980);
		 
		 
		 totalScore = (((((duckKilled + birdKilled)*10) + (sDCount * 50))) -  ((duckCount+birdCount)*2)) ;
		 
		 
		 
		 
		 Font myFont3 = new Font ("Courier New", 1, 70);
	        g.setFont (myFont3); 
	        
		 g.setColor(Color.WHITE);
		 g.drawString("Your Score: "+totalScore, 1150, 750);
		 
		 
		 
		 if(totalScore>highScore)
			 g.drawString("New High Score: "+totalScore, 1050, 870);
		 
		 
		 if(gOv == 0){
			 musicGameOver();
			 gOv++;
		 }
		 
	}
	
	
	
	public void fallingDeadBird(){
		
		if(fallingFlag1 == 1){
    		
        	if(fallingY<=1080){
        		fallingSpeed = 20;
        		fallingY += fallingSpeed;
        		
        	g.drawImage(birdDead, fallingX, fallingY , null);
        	
        	
        	
    		}
        	
        	
        	if(fallingY >=1080){
        		fB1++;
        		
        		
        		if(fB1<=20){
        			g.drawImage(dog.getScaledInstance(200, 300, Image.SCALE_DEFAULT),fallingX, 795 , null);
        		}
        		
        		if(fB1>=20){
        			if(fallingY<=1080)
        				fB1=0;
        		}
        	}
        	
        	
    	
    	}
    	
    
    	if(fallingFlag2 == 1){
    		
        	if(falling2Y<=1080){
        		fallingSpeed = 20;
        		falling2Y += fallingSpeed;
        		
        	g.drawImage(birdDead, falling2X, falling2Y , null);
    		
    		}	
        	
        	if(falling2Y >=1080){
        		fB2++;
        		
        		
        		if(fB2<=20){
        			g.drawImage(dog.getScaledInstance(200, 300, Image.SCALE_DEFAULT),falling2X, 795 , null);
        		}
        		
        		if(fB2>=20){
        			if(falling2Y<=1080)
        				fB2=0;
        		}
        	}
    
    	}
    	
	
    	if(fallingFlag3 == 1){
    		
        	if(falling3Y<=1080){
        		fallingSpeed = 20;
        		falling3Y += fallingSpeed;
        		
        	g.drawImage(birdDead, falling3X, falling3Y , null);
    		
    		}	
        	
        	
        	if(falling3Y >=1080){
        		fB3++;
        		
        		
        		if(fB3<=20){
        			g.drawImage(dog.getScaledInstance(200, 300, Image.SCALE_DEFAULT),falling3X, 795 , null);
        		}
        		
        		if(fB3>=20){
        			if(falling3Y<=1080)
        				fB3=0;
        		}
        	}
     
    	}
    	
	}
	
	
	
	
	public void bullet(){
		if(mouseManager.getAmmo() == 0){
			g.drawImage(bullet6, 1700, 987, null);
			
		}
		if(mouseManager.getAmmo() == 1){
			g.drawImage(bullet6, 1750, 987, null);
			
		}
		if(mouseManager.getAmmo() == 2){
			g.drawImage(bullet6, 1785, 987, null);
			
		}
		if(mouseManager.getAmmo() == 3){
			g.drawImage(bullet6, 1814, 987, null);
			
		}
		if(mouseManager.getAmmo() == 4){
			g.drawImage(bullet6, 1850, 987, null);
			
		}
		if(mouseManager.getAmmo() == 5){
			g.drawImage(bullet6, 1885, 987, null);
			
		}
		if(mouseManager.getAmmo() >= 7){
			g.drawImage(reload, 1700, 987, null);
			
			
		}
	}
	
	
	public int getGStop(){
		return gStop;
	}
	

}
