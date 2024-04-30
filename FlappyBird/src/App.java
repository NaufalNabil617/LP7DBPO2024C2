/* Saya Naufal Nabil Anugrah dengan NIM 2201090 mengerjakan soal Latihan 7
    dalam Praktikum mata kuliah Desain dan Pemrograman Berbasis Objek, untuk keberkahan-Nya
    maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamin.
*/

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class App {
    public static void main(String[] args) {
        // Membuat frame untuk GUI Form
        JFrame startFrame = new JFrame("Main Menu");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(280, 280);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);

        // Menambahkan JPanel dengan gambar latar belakang
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Memuat gambar latar belakang
                ImageIcon backgroundImage = new ImageIcon(App.class.getResource("assets/background.png"));
                // Menggambar gambar latar belakang
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new BorderLayout());

        // Menambahkan label "Game Flappy Bird" di bagian atas tengah
        JLabel titleLabel = new JLabel("Welcome FlappyBird", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(25, 0, 0, 0)); // Set batas
        titleLabel.setForeground(Color.WHITE); // Set warna teks
        panel.add(titleLabel, BorderLayout.NORTH);

        // Menambahkan gambar burung di bawah label "Game Flappy Bird"
        ImageIcon birdIcon = new ImageIcon(App.class.getResource("assets/bird.png"));
        Image birdImage = birdIcon.getImage().getScaledInstance(34, 24, Image.SCALE_SMOOTH);
        ImageIcon resizedBirdIcon = new ImageIcon(birdImage);
        JLabel birdLabel = new JLabel(resizedBirdIcon);
        panel.add(birdLabel, BorderLayout.CENTER);

        // Membuat objek JButton untuk tombol
        ImageIcon startButtonIcon = new ImageIcon(App.class.getResource("assets/start.jpeg"));
        Image img = startButtonIcon.getImage();
        Image newImg = img.getScaledInstance(65, 40, Image.SCALE_SMOOTH); // Menyesuaikan ukuran gambar
        startButtonIcon = new ImageIcon(newImg);
        JButton startButton = new JButton(startButtonIcon);
        startButton.setBorderPainted(false); // Set border tidak tergambar
        startButton.setContentAreaFilled(false); // Set area konten tidak terisi
        startButton.setFocusPainted(false); // Set fokus tidak tergambar
        startButton.setOpaque(false); // Set tidak tembus pandang
        startButton.addActionListener(e -> {
            // Tutup GUI Form
            startFrame.dispose();

            // Buka JFrame untuk game FlappyBird
            JFrame gameFrame = new JFrame("Flappy Bird");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(360, 640);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setResizable(false);

            // Membuat objek JPanel
            FlappyBird flappyBird = new FlappyBird();
            gameFrame.add(flappyBird);
            gameFrame.pack();
            flappyBird.requestFocus();
            gameFrame.setVisible(true);
        });

        // Menambahkan tombol ke JPanel di bagian bawah tengah
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Set JPanel transparan
        startButton.setMargin(new Insets(5, 10, 5, 10)); // Set margin untuk tombol
        buttonPanel.setBorder(new EmptyBorder(0, 0, 50, 0)); // Set batas
        buttonPanel.add(startButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Menambahkan JPanel ke JFrame
        startFrame.add(panel);
        startFrame.setVisible(true);
    }
}
