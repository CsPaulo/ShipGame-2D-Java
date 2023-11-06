import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class JogoDeNave extends JPanel implements ActionListener, KeyListener {
    private int larguraTela = 400;
    private int alturaTela = 600;
    private BufferedImage naveSprite, combustivelSprite;
    private int naveX = larguraTela / 2;
    private int naveY = alturaTela - 50;
    private int pontuacao = 0;
    private Timer timer;
    private ArrayList<Combustivel> combustivels = new ArrayList<>();
    private Random rand = new Random();
    private boolean esquerdaPressionada = false;
    private boolean direitaPressionada = false;
    private int velocidadeNave = 5;
    private int taxaCriacaoCombustivels = 1500;
    private long ultimoCombustivelCriado = 0;

    public JogoDeNave() {
        try {
            naveSprite = ImageIO.read(new File("C:\\Users\\Pedro\\OneDrive\\Documentos\\GameJava\\sprites\\nave.png"));
            combustivelSprite = ImageIO.read(new File("C:\\Users\\Pedro\\OneDrive\\Documentos\\GameJava\\sprites\\combustivel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.black);
        setPreferredSize(new Dimension(larguraTela, alturaTela));
        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(10, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenhar nave
        g.drawImage(naveSprite, naveX, naveY, null);

        // Desenhar combustíveis
        for (Combustivel combustivel : combustivels) {
            g.drawImage(combustivelSprite, combustivel.getX(), combustivel.getY(), null);
        }

        // Exibir pontuação
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Pontuação: " + pontuacao, 20, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moverNave();
        criarCombustivel();
        moverCombustiveis();
        detectarColisoes();
        repaint();
    }

    private void moverNave() {
        if (esquerdaPressionada && naveX > 0) {
            naveX -= velocidadeNave;
        }
        if (direitaPressionada && naveX < larguraTela - naveSprite.getWidth()) {
            naveX += velocidadeNave;
        }
    }

    private void criarCombustivel() {
        long agora = System.currentTimeMillis();
        if (agora - ultimoCombustivelCriado >= taxaCriacaoCombustivels) {
            int x = rand.nextInt(larguraTela);
            int y = -20;
            combustivels.add(new Combustivel(x, y));
            ultimoCombustivelCriado = agora;
        }
    }

    private void moverCombustiveis() {
        Iterator<Combustivel> iter = combustivels.iterator();
        while (iter.hasNext()) {
            Combustivel combustivel = iter.next();
            combustivel.mover();
            if (combustivel.getY() >= alturaTela) {
                iter.remove();
            }
        }
    }

    private void detectarColisoes() {
        Rectangle naveRect = new Rectangle(naveX, naveY, naveSprite.getWidth(), naveSprite.getHeight());
        Iterator<Combustivel> iter = combustivels.iterator();
        while (iter.hasNext()) {
            Combustivel combustivel = iter.next();
            Rectangle combustivelRect = new Rectangle(combustivel.getX(), combustivel.getY(), combustivelSprite.getWidth(), combustivelSprite.getHeight());
            if (naveRect.intersects(combustivelRect)) {
                iter.remove(); 
                pontuacao += 1;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            esquerdaPressionada = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            direitaPressionada = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            esquerdaPressionada = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            direitaPressionada = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo de Nave");
        JogoDeNave game = new JogoDeNave();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Combustivel {
    private int x;
    private int y;

    public Combustivel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void mover() {
        y += 2;
    }
}