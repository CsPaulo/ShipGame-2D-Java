# ShipGame-2D-Java
## Esse código foi realizado para a prova de POO

### Desafio/Explicação
O intuito do jogo é a nave coletar os combustível, e a medida que isso ocorre
a pontuação vai aumentando ao decorrer do jogo. O codígo está muito simples, pois
fizemos ele de acordo com a proposta que o professor passou. Porém, iremos adicionar 
mais elementos ao jogo para deixar ele mais dinâmico e desafiador.

## Sprites
<img src="sprites/naveREADME.png" width = 100, height = 100/>
<img src="sprites/combustivelREADME.png" width = 100, height = 100/>


## Funcionalidades importantes

*Construtor para a criação dos sprites, juntamente com a tela*
~~~~java

public JogoDeNave() {
        try {
            naveSprite = ImageIO.read(new File("sprites/nave.png"));
            combustivelSprite = ImageIO.read(new File("sprites/combustivel.png"));
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
~~~~
<br/>

*Detector de colisões das sprites*
~~~~java
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
~~~~
<br/>

*Criação de um evento sobre o teclado*
~~~~java
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
~~~~

**Os codigos acima estão levando mais enfase, pois era uma parte obrigatória do projeto.**


