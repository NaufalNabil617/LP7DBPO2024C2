/* Saya Naufal Nabil Anugrah dengan NIM 2201090 mengerjakan soal Latihan 7
    dalam Praktikum mata kuliah Desain dan Pemrograman Berbasis Objek, untuk keberkahan-Nya
    maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamin.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int frameWidth = 360; // Lebar frame
    int frameHeight = 640; // Tinggi frame

    Image backgroundImage; // Gambar latar belakang
    Image birdImage; // Gambar burung
    Image lowerPipeImage; // Gambar pipa bawah
    Image upperPipeImage; // Gambar pipa atas

    // Posisi awal pemain
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY =  frameHeight / 2;
    int playerWidth = 34; // Lebar pemain
    int playerHeight = 24; // Tinggi pemain

    Player player; // Objek pemain

    // Posisi awal pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64; // Lebar pipa
    int pipeHeight = 512; // Tinggi pipa

    ArrayList<Pipe> pipes; // ArrayList untuk menyimpan pipa
    Timer gameLoop; // Timer untuk loop permainan
    Timer pipesCooldown; // Timer untuk penempatan pipa
    int gravity = 1; // Gravitasi

    boolean gameOver = false; // Status permainan
    double score = 0; // Skor

    JLabel scoreLabel; // Label skor
    Font gameOverFont; // Font untuk tulisan "Game Over"

    // Konstruktor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight)); // Ukuran panel
        setFocusable(true); // Fokus dapat diatur
        addKeyListener(this); // Menambahkan listener keyboard

        // Memuat gambar
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // Inisialisasi pemain dan pipa
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<>();

        // Label skor
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setBounds(10, 10, 150, 30);
        add(scoreLabel);

        // Font untuk tulisan "Game Over"
        gameOverFont = new Font("Arial", Font.BOLD, 32);

        // Timer untuk penempatan pipa
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.start();

        // Timer untuk loop permainan
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    // Menggambar komponen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Menggambar objek permainan
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0 , 0 , frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(),pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        if(gameOver) {
            String gameOverMessageLine1 = "Game Over!";
            String gameOverMessageLine2 = "Press R to Restart";

            g.setColor(Color.white);
            g.setFont(gameOverFont);
            int stringWidthLine1 = g.getFontMetrics().stringWidth(gameOverMessageLine1);
            int stringWidthLine2 = g.getFontMetrics().stringWidth(gameOverMessageLine2);

            int x1 = (frameWidth - stringWidthLine1) / 2;
            int x2 = (frameWidth - stringWidthLine2) / 2;

            int y1 = frameHeight / 2 - g.getFontMetrics().getHeight();
            int y2 = frameHeight / 2;

            g.drawString(gameOverMessageLine1, x1, y1);
            g.drawString(gameOverMessageLine2, x2, y2);
        }
    }

    // Logika pergerakan permainan
    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.passed = true;
                score += 0.5;
                scoreLabel.setText("Score: " + String.valueOf((int) score));
            }

            if (collision(player,pipe)){
                gameOver = true;
            }
        }

        if(player.getPosY() > frameHeight){
            gameOver = true;
        }
    }

    // Menempatkan pipa
    public void placePipes() {
        int randomPipePosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPipePosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPipePosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // Deteksi tabrakan antara pemain dan pipa
    public boolean collision(Player player, Pipe pipe) {
        return player.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                player.getPosX() + player.getWidth() > pipe.getPosX() &&
                player.getPosY() < pipe.getPosY() + pipe.getHeight() &&
                player.getPosY() + player.getHeight() > pipe.getPosY();
    }

    // Implementasi ActionListener untuk timer
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            pipesCooldown.stop();
            gameLoop.stop();
        }
    }

    // Implementasi KeyListener untuk input keyboard
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                player.setPosY(playerHeight);
                player.setVelocityY(0);
                pipes.clear();
                score=0;
                gameOver=false;
                gameLoop.start();
                pipesCooldown.start();
                scoreLabel.setText("Score: " + String.valueOf((int) score));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
