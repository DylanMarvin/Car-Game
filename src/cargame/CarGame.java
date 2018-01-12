package cargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CarGame extends JFrame implements Runnable {

    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    GameState gameState = GameState.Menu;
    Difficulty difficulty;
    Spawner spawner;
    Car car;
    int score;
    Font customFont;
    Menu menu = new Menu();
    sound explosion;
    sound mainSound;
    sound ingame;
    sound trashSound;
    sound over;
    double alpha; 
    int timer;
    Image highScore;
    int scoreKeep;
    
    int timer2;
    
    int soundTime;
    boolean start;
    boolean start2;
    
    boolean finishLine;
    boolean play;

    String name;
    
    
    int mouseX;
    int mouseY;

    double framerate = 60.0;
    int timeCount;
    boolean gameOver;

    public static void main(String[] args) {
        CarGame frame = new CarGame();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Car Game");
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setResizable(true);

    }

    public CarGame() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int xpos = e.getX();
                int ypos = e.getY();

//                System.out.println("Xpos:" + xpos + " Ypos:" + ypos);

                if (e.BUTTON1 == e.getButton()) {
                    if (gameState == GameState.Menu) {
                        if (menu.getpressPlay()) {
                            gameState = GameState.CarSelect;
                        } else if (menu.getpressQuit()) {
                            System.exit(0);
                        }
                    } else if (gameState == GameState.CarSelect) {
                        if (xpos >= 600 && xpos <= 670 && ypos >= 490 && ypos <= 615) {
                            menu.subtractCarNum();
                        } else if (xpos >= 1225 && xpos <= 1295 && ypos >= 490 && ypos <= 615) {
                            menu.addCarNum();
                        } else if (xpos >= 761 && xpos <= 1152 && ypos >= 851 && ypos <= 924) {
                            gameState = GameState.DifficultySel;                           
                            car = new Car(menu.getCarNum());  
                        }
                    }else if(gameState == GameState.DifficultySel){                                               
                            if (menu.getpressDemo()) {
                                gameState = GameState.Ingame;
                                difficulty = Difficulty.Demo;
                            } else if (menu.getpressNormal()) {
                                gameState = GameState.Ingame;
                                difficulty = Difficulty.Normal;                                
                        }                       
                    } else if (gameState == GameState.Ingame) {

                    } else if(gameState == GameState.Over){
                        //if(xpos ){
                            
                        //}
                    }
                    

                }

                if (e.BUTTON3 == e.getButton()) {
                    Reset();
                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseScrolled(MouseEvent e) {

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int xpos = e.getX();
                int ypos = e.getY();
                if (gameState == GameState.Menu) {
                    if (xpos >= 564 && xpos <= 865 && ypos >= 333 && ypos <= 435) {
                        menu.setColor1(Color.yellow);
                        menu.setpressPlay(true);
                    } else {
                        menu.setpressPlay(false);
                        menu.setColor1(Color.black);
                    }
                    if (xpos >= 1064 && xpos <= 1365 && ypos >= 334 && ypos <= 434) {
                        menu.setColor2(Color.yellow);
                        menu.setpressQuit(true);

                    } else {
                        menu.setpressQuit(false);
                        menu.setColor2(Color.black);
                    }
                } else if (gameState == GameState.CarSelect) {

                }else if(gameState == GameState.DifficultySel){
                    if (xpos >= 695 && xpos <= 930 && ypos >= 425 && ypos <= 505) {
                        menu.setColor3(Color.yellow);
                        menu.setpressDemo(true);
                    } else {
                        menu.setpressDemo(false);
                        menu.setColor3(Color.black);
                    }
                    if (xpos >= 995 && xpos <= 1230 && ypos >= 425 && ypos <= 505) {
                        menu.setColor4(Color.yellow);
                        menu.setpressNormal(true);

                    } else {
                        menu.setpressNormal(false);
                        menu.setColor4(Color.black);
                    }
                    
                } else if (gameState == GameState.Ingame) {
                    mouseX = xpos;
                    mouseY = ypos;

                }

                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_C == e.getKeyCode()) {
                } else if (e.VK_R == e.getKeyCode()) {
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////

    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////

    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////

    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.white);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};

//fill border
        g.setColor(Color.black);
        g.fillPolygon(x, y, 4);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        if (gameState == GameState.Menu) {
            menu.draw(g, this, gameState);
        } else if (gameState == GameState.CarSelect) {

            menu.draw(g, this, gameState);

        } else if(gameState == GameState.DifficultySel){
            menu.draw(g, this, gameState);
        } else if (gameState == GameState.Ingame) {
            
            if(gameOver == true){
                
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) alpha));
            }
            
            Road.Draw(g, this);
            
            Obstacles.Draw(g, this);
            
            if (gameOver != true) {
                car.draw(g, this); 
                
            } else {
                   g.drawImage(menu.getExplosion(), Window.getX(car.getX() - 50), Window.getY(car.getY() - 50), 100, 100, this);

            }
            

            g.setFont(customFont);

            g.setColor(Color.white);
            g.drawString(getScore(), Window.getX(1600), Window.getY(50));


            g.drawString("Lives ", Window.getX(50), Window.getY(50));
            int count = 0;
            for (int i = 190; count < car.getLife(); i += 40) {
                g.drawImage(car.getImage(), Window.getX(i), Window.getY(25), 22, 28, this);
                count++;
            }
            
            if(gameOver == true){
                g.setComposite(AlphaComposite.SrcOver.derive(1f - (float) alpha));

                
                HighScore.Draw(g,this);
                g.drawString(getScore(), Window.getX(860), Window.getY(310));
                
            }
            
        } else if (gameState == GameState.Over) {
             
            g.setFont(customFont);

            
            HighScore.Draw(g,this);
            
            g.setColor(Color.white);
            g.drawString(getScore(), Window.getX(860), Window.getY(310));
        }

        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 1 / framerate;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }

/////////////////////////////////////////////////////////////////////////
    public void reset() {
        timeCount = 0;
        start2 = true;
        alpha = 1.0f;
        
        timer = 0;

        menu = new Menu();
        spawner = new Spawner();

        car = new Car(0);
        score = 0;
        soundTime = 0;

        scoreKeep = 0;
        difficulty = Difficulty.Demo;
        
        timer2 = 0;
        play = true;
        finishLine = true;
        

        start = true;
    }
    
    public void Reset(){
        alpha = 1.0f;
        timeCount = 0;
        timer = 0;
        score = 0;
        timer2 = 0;
        menu.Reset();
        spawner.Reset();
        Road.Reset();
        gameState = GameState.Menu;
        gameOver = false;
        Obstacles.Reset();
        scoreKeep = 0;
        play = true;
        start = true;
        
    }
/////////////////////////////////////////////////////////////////////////

    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
                
            }
            Car.initSprites();
            Menu.initImages();
            Obstacles.initSprites();
            Road.initImage();
            mainSound = new sound("assets/main.wav");   
            
            Fonts.addFont(new Fonts("8BitFont.TTF"));
            customFont = new Font("Perfect DOS VGA 437", Font.PLAIN, 40);
            
            Road.Create();
            reset();

        }
        

        
        if (car.getLife() <= 0) {
            gameOver = true;
        }

        if (gameOver) {

        ingame.stopPlaying = true;
        if(start2 == true){
            startPlaying2();
            start2 = false;
            }
            if(over.donePlaying == true){               
                
                    if (soundTime == 0)
                        soundTime = 28;
                    else
                    {
                        soundTime--;
                        if (soundTime == 1)
                        {
                            //over = new sound("assets/gameOver.wav");
                            soundTime = 0;
                        }
                    }
            }

            if (timeCount % 25 == 1) {
                timer++;      
                
                if(gameState != GameState.Over){
                    explosion = new sound("assets/Explosion3.wav");

                if(alpha-0.1 > 0)
                    alpha -= 0.1;
                else{
                    gameState = GameState.Over;
  //                  System.out.println(gameState);
                }
                     
                }
                if (timer == 5) {
                    timer = 0;
                }
            }
            timeCount++;
            
        }
        

        

        if (gameState == GameState.Menu) {
            
            if(mainSound.donePlaying == true){               
                
 //                   System.out.println("sound is done");
                    if (soundTime == 0)
                        soundTime = 28;
                    else
                    {
                        soundTime--;
                        if (soundTime == 1)
                        {
                            mainSound = new sound("assets/main.wav");
                            soundTime = 0;
                        }
                    }
            }
            
        } else if (gameState == GameState.CarSelect) {

        } else if (gameState == GameState.Ingame) {
            if(gameOver == false){
                if(difficulty == Difficulty.Demo){
                    if(scoreKeep == 750){
                        play = false;
                    }
                    if(finishLine == true){
                        Obstacles.Create(0, Obstacles.Type.FinishLine);
                        finishLine = false;
                    }
                    car.tick(mouseX, mouseY, play);
                    Obstacles.Tick(play);
                    if(play == true){
                        Road.Tick();

                        spawner.tick();

                        if (timeCount % 5 == 1) 
                            scoreKeep++;

                        if (Obstacles.HitBox(car.getX(), car.getY(),Obstacles.Type.Car) == 1) {
                            car.substractLife();
                            explosion = new sound("assets/Explosion3.wav");
                        }
                        else if (Obstacles.HitBox(car.getX(), car.getY(),Obstacles.Type.TrashCan) == 2) {

                            trashSound = new sound("assets/trashSound.wav");
                            score += 100;

                        }
                    }
                }
                else if(difficulty == Difficulty.Normal){
                        car.tick(mouseX, mouseY, play);
                        Obstacles.Tick(play);

                        Road.Tick();
                        spawner.tick();


                        if (timeCount % 5 == 1) 
                            scoreKeep++;

                        if (Obstacles.HitBox(car.getX(), car.getY(),Obstacles.Type.Car) == 1) {
                            car.substractLife();
                            explosion = new sound("assets/Explosion3.wav");
                        }
                        else if (Obstacles.HitBox(car.getX(), car.getY(),Obstacles.Type.TrashCan) == 2) {

                            trashSound = new sound("assets/trashSound.wav");
                            score += 100;

                        }
                        if (Obstacles.HitBox(car.getX(), car.getY(),Obstacles.Type.FinishLine) == 4) {
                            gameOver = true;
                        }
                }
            }
            timeCount++;
            
            mainSound.stopPlaying = true;
            if(start == true){
                startPlaying();
                start = false;            
            }
            if(ingame.donePlaying == true){
                
                    if (soundTime == 0)
                        soundTime = 28;
                    else
                    {
                        soundTime--;
                        if (soundTime == 1)
                        {
                            ingame = new sound("assets/ingame.wav");
                            soundTime = 0;
                        }
                    }
            }
        }
        else if(gameState == GameState.Over){


                HighScore.setHighScore(score);
                                   

                  
        }
         

    }
    ////////////////////////////////////////////////////////////////////////
    public void startPlaying(){
        if(start == true){
            ingame = new sound("assets/ingame.wav"); 
        }
    }
     public void startPlaying2(){
        if(start2 == true){
            over = new sound("assets/gameOver.wav");
        }
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////

    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////

    public String getScore(){
        String _score = "Score: ";
            if (score+ scoreKeep < 100) {
                _score+="000" + (score + scoreKeep);
            } else if (score+ scoreKeep < 1000) {
                _score+="00" + (score + scoreKeep);
            } else if (score+ scoreKeep < 10000) {
                _score+="0" + (score + scoreKeep);
            } else if (score+ scoreKeep < 100000) {
                _score+="" + (score + scoreKeep);
            }
        return _score;
    }
}
