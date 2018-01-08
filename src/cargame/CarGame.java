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
    Spawner spawner;
    Car car;
    int score;
    Font customFont;
    Menu menu = new Menu();
    sound explosion;
    double alpha = 1.0f;  
    int timer;
    Image highScore;

    
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
                            gameState = GameState.Ingame;
                            car = new Car(menu.getCarNum());
                        }
                    } else if (gameState == GameState.Ingame) {

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

        } else if (gameState == GameState.Ingame) {
            
            if(gameOver == true){
                System.out.println(alpha);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) alpha));
            }
            
            Road.Draw(g, this);

            if (gameOver != true) {
                car.draw(g, this); 
                
            } else {

                if (timer == 1) {
                    g.drawImage(menu.getExplosion(), Window.getX(car.getX() - 50), Window.getY(car.getY() - 50), 100, 100, this);
                } else if (timer == 2) {
                    g.drawImage(menu.getExplosion(), Window.getX(car.getX() - 45), Window.getY(car.getY() - 45), 100, 100, this);
                } else if (timer == 3) {
                    g.drawImage(menu.getExplosion(), Window.getX(car.getX() - 55), Window.getY(car.getY() - 55), 100, 100, this);
                } else if (timer == 4) {
                    g.drawImage(menu.getExplosion(), Window.getX(car.getX() - 53), Window.getY(car.getY() - 43), 100, 100, this);
                }
            }
            Obstacles.Draw(g, this);

            g.setFont(customFont);

            g.setColor(Color.white);
            if (score < 100) {
                g.drawString("Score: 000" + score, Window.getX(1600), Window.getY(50));
            } else if (score < 1000) {
                g.drawString("Score: 00" + score, Window.getX(1600), Window.getY(50));
            } else if (score < 10000) {
                g.drawString("Score: 0" + score, Window.getX(1600), Window.getY(50));
            } else if (score < 100000) {
                g.drawString("Score " + score, Window.getX(1600), Window.getY(50));
            }

            g.drawString("Lives ", Window.getX(50), Window.getY(50));
            int count = 0;
            for (int i = 190; count < car.getLife(); i += 40) {
                g.drawImage(car.getImage(), Window.getX(i), Window.getY(25), 22, 28, this);
                count++;
            }
            
            if(gameOver == true){
                g.setComposite(AlphaComposite.SrcOver.derive(1f - (float) alpha));

                HighScore.Draw(g,this);
            }
            
        } else if (gameState == GameState.Over) {
            
             
            g.setFont(customFont);
            g.setColor(Color.white);
            
            HighScore.Draw(g,this);
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

        timer = 0;

        menu = new Menu();
        spawner = new Spawner();

        car = new Car(0);
        score = 0;
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

            Fonts.addFont(new Fonts("8BitFont.TTF"));
            customFont = new Font("Perfect DOS VGA 437", Font.PLAIN, 40);
            
            Road.Create();
            reset();

        }
        if (Obstacles.HitBox(car.getX(), car.getY()) == 1) {
            car.substractLife();
            explosion = new sound("assets/Explosion3.wav");
        }
        if (car.getLife() == 0) {
            gameOver = true;
        }
        
        
        if (gameOver) {
            

            if (timeCount % 25 == 1) {
                timer++;      
                if(gameState != GameState.Over)
                explosion = new sound("assets/Explosion3.wav");
                if(alpha-0.1 > 0)
                    alpha -= 0.1;
                else{
                    gameState = GameState.Over;
                    
                }
                if (timer == 5) {
                    timer = 0;
                }
            }
            timeCount++;
//          gameState = GameState.Over;
            return;
        }
        if (Obstacles.HitBox(car.getX(), car.getY()) == 2) {
            score += 100;

        }

        if (gameState == GameState.Menu) {

        } else if (gameState == GameState.CarSelect) {

        } else if (gameState == GameState.Ingame) {
            Road.Tick();

            car.tick(mouseX, mouseY);

            Obstacles.Tick();

            spawner.tick();

            if (timeCount % 5 == 1) {
                score++;
        }
        else if(gameState == GameState.Over){

                  
        }

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
