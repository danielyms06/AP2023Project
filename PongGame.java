import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

public class PongGame extends Frame implements KeyListener {
    private int paddle1Y;
    private int paddle2Y;
    private int ballX;
    private int ballY;
    private int ballXSpeed;
    private int ballYSpeed;
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 80;
    private static final int PADDLE_SPEED = 20;
    private BufferedImage faceImage;
    private Image backgroundImage;

    public PongGame() {
        setTitle("POOLE PONG");
        setSize(1400, 800);
        setResizable(false);
        setVisible(true);
        paddle1Y = getHeight() / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = getHeight() / 2 - PADDLE_HEIGHT / 2;
        ballX = getWidth() / 2 - BALL_SIZE / 2;
        ballY = getHeight() / 2 - BALL_SIZE / 2;
        ballXSpeed = 2;
        ballYSpeed = 2;
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        try {
            URL imageUrl = new URL("https://cdn.pixabay.com/photo/2014/04/03/10/23/ping-pong-ball-310271_1280.png");
            faceImage = ImageIO.read(imageUrl);
            URL backgroundImageUrl = new URL("https://drive.google.com/uc?export=download&id=1_qivRLVPn_NqjHdr6ofwsfTcfEC3c-9m");
            backgroundImage = ImageIO.read(backgroundImageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGameLoop();
    }

    private void startGameLoop() {
        while (true) {
            moveBall();
            checkCollision();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;
        if (ballY <= 0 || ballY >= getHeight() - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }
        if (ballX <= 0 || ballX >= getWidth() - BALL_SIZE) {
            ballXSpeed = -ballXSpeed;
        }
    }

    private void checkCollision() {
        if (ballX <= PADDLE_WIDTH && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }
        if (ballX >= getWidth() - BALL_SIZE - PADDLE_WIDTH && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setColor(Color.RED);
        g2d.fillRect(PADDLE_WIDTH, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(getWidth() - 2 * PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g2d.drawImage(faceImage, ballX, ballY, BALL_SIZE, BALL_SIZE, null);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }
      

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            paddle1Y -= PADDLE_SPEED;
            if (paddle1Y < 0) {
                paddle1Y = 0;
            }
        } else if (keyCode == KeyEvent.VK_S) {
            paddle1Y += PADDLE_SPEED;
            if (paddle1Y > getHeight() - PADDLE_HEIGHT) {
                paddle1Y = getHeight() - PADDLE_HEIGHT;
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            paddle2Y -= PADDLE_SPEED;
            if (paddle2Y < 0) {
                paddle2Y = 0;
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            paddle2Y += PADDLE_SPEED;
            if (paddle2Y > getHeight() - PADDLE_HEIGHT) {
                paddle2Y = getHeight() - PADDLE_HEIGHT;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();    
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                paddle1Y -= PADDLE_SPEED;
                if (paddle1Y < 0) {
                    paddle1Y = 0;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                paddle1Y += PADDLE_SPEED;
                if (paddle1Y > getHeight() - PADDLE_HEIGHT) {
                    paddle1Y = getHeight() - PADDLE_HEIGHT;
                }
            }
            repaint();
        }
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                paddle2Y -= PADDLE_SPEED;
                if (paddle2Y < 0) {
                    paddle2Y = 0;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                paddle2Y += PADDLE_SPEED;
                if (paddle2Y > getHeight() - PADDLE_HEIGHT) {
                    paddle2Y = getHeight() - PADDLE_HEIGHT;
                }
            }
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new PongGame();
    }
}