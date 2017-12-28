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
import javax.swing.*;

public class CarGame extends JFrame implements Runnable {

    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    GameState gameState = GameState.Menu;
    Image menuImage;
    Image play;
    Image quit;
    Image title;
    Image road;

    Car car;
    
    boolean pressPlay;
    boolean pressQuit;
    
    int road1x;
    int road1y;
    int road2x;
    int road2y;
    
    Color color;
    Color color2;
    
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

                System.out.println("Xpos:" + xpos + " Ypos:" + ypos);

               
                if (e.BUTTON1 == e.getButton()) {
                    if(gameState == GameState.Menu){
                        if(pressPlay)
                            gameState = GameState.Ingame;
                        else if(pressQuit)
                            System.exit(0);
                    }
                    else if(gameState == GameState.CarSelect){
                        
                    }
                    else if(gameState == GameState.Ingame){

                    }
                 
                }

                if (e.BUTTON3 == e.getButton()) {
                    reset();
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
                if(gameState == GameState.Menu){
                    if (xpos >= 564 && xpos <= 865 && ypos >= 333 && ypos <= 435) {
                        color = Color.yellow;
                        pressPlay = true;
                    } else {
                        pressPlay = false;
                        color = Color.black;
                    }
                    if (xpos >= 1064 && xpos <= 1365 && ypos >= 334 && ypos <= 434) {
                        color2 = Color.yellow;
                        pressQuit = true;

                    } else {
                        pressQuit = false;
                        color2 = Color.black;
                    }   
                }
                else if(gameState == GameState.CarSelect){
                    
                }
                else if(gameState == GameState.Ingame){
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
            g.setColor(Color.white);
            g.fillPolygon(x, y, 4);



        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
         
        
        if(gameState == GameState.Menu){
           g.drawImage(menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),this);
           
           
            
            g.setColor(color);
            g.fillRoundRect(565, 335, 300, 100, 10, 10);
            g.setColor(color2);
            g.fillRoundRect(1065, 335, 300, 100, 10, 10);
            g.setColor(Color.white);
            g.drawImage(play,Window.getX(600),Window.getY(320),196,81,this);
            g.drawImage(quit,Window.getX(1100),Window.getY(310),196,81,this);
            g.drawImage(title,Window.getX(725),Window.getY(120),476,81,this);
            
            
            
        }
        else if(gameState == GameState.CarSelect){
            g.drawImage(menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),this);
        }
        else if(gameState == GameState.Ingame){            
            g.drawImage(road,Window.getX(road1x),Window.getY(road1y),Window.getWidth2(),Window.getHeight2()+5,this);
            g.drawImage(road,Window.getX(road2x),Window.getY(road2y+25),Window.getWidth2(),Window.getHeight2()+5,this);
             
           car.draw(g, this);
           Obstacles.Draw(g, this);
        }
        
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 1/framerate;    //time that 1 frame takes.
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
        road1x = 0;
        road1y = 0;
        road2x = 0;
        road2y = - Window.getHeight2();

        car = new Car(1);
    }
/////////////////////////////////////////////////////////////////////////

    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            menuImage = Toolkit.getDefaultToolkit().getImage("assets/menuImage2.png");
            play = Toolkit.getDefaultToolkit().getImage("assets/Play.png");
            quit = Toolkit.getDefaultToolkit().getImage("assets/Quit.png");
            title = Toolkit.getDefaultToolkit().getImage("assets/title.png");
            road = Toolkit.getDefaultToolkit().getImage("assets/road.png");
            Obstacles.initSprites();            
            reset();

        }
        gameOver = Obstacles.HitBox(car.getX(), car.getY());
        if(gameOver){
            //gameState = GameState.Over;
            return;
        }
       
        
       
        if(gameState == GameState.Menu){
            
        }
        else if(gameState == GameState.CarSelect){
            
        }
        else if(gameState == GameState.Ingame){
            
            road1y += 10;
            if(road1y >= Window.getHeight2())
                road1y = -Window.getHeight2()+25;
            
            road2y += 10;
            if(road2y >= Window.getHeight2())
                road2y = -Window.getHeight2() +25 ;
            
            
            car.tick(mouseX,mouseY);
            
            Obstacles.Tick();
           
            if(timeCount % 50 == 1)
                Obstacles.Create(1, Obstacles.Type.Car);
            if(timeCount % 150 == 1)
                Obstacles.Create(0, Obstacles.Type.TrashCan);
            timeCount++;
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

}
