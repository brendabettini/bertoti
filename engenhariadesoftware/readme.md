----- Atividade 1. Comentar com suas palavras o primeiro trecho do livro Software Engineering at Google, Oreilly. 

- O livro explica que "engenharia de software" vai além de programar, envolvendo práticas mais rigorosas para criar sistemas confiáveis. Ele compara com outras engenharias, dizendo que o software precisa de mais atenção, já que pode afetar bastante a vida das pessoas.
 
----- Atividade 2. Comentar com suas palavras o segundo trecho do livro Software Engineering at Google, Oreilly.

- O livro fala que engenharia de software não é só escrever código, mas também manter e evoluir o software ao longo do tempo. Ele destaca três pontos: como o código muda, como a empresa cresce e como tomar decisões importantes.
 
----- Atividade 3. Listar e explicar 3 exemplos de tradeoffs

- Velocidade vs. Qualidade: Lançar rápido pode ter mais bugs, e focar só em qualidade pode atrasar o lançamento.

- Simplicidade vs. Funcionalidade: Fazer algo simples é mais fácil de manter, mas tem menos funcionalidades.

- Centralização vs. Descentralização: Centralizar facilita o controle, mas pode ser mais lento. Descentralizar dá agilidade, mas pode ficar desorganizado.


----- Atividade 4. UML

- Foto na pasta do repositório.

----- Atividade 5. Código JAVA do UML:

class Hamburguer {
    private String tamanho;
    private String ingredientes;
    private int calorias;

   public Hamburguer(String tamanho, String ingredientes, int calorias) {
        this.tamanho = tamanho;
        this.ingredientes = ingredientes;
        this.calorias = calorias;
    }

   public void montar() {
        System.out.println("Hamburguer tamanho " + tamanho + " com " + ingredientes + " montado.");
    }

   public void aquecer() {
        System.out.println("Hamburguer aquecido.");
    }

   public void comer() {
        System.out.println("Você está comendo um hamburguer de " + calorias + " calorias.");
    }
}

class BatataFrita {
    private int quantidade;
    private double temperatura;
    private boolean crocancia;

   public BatataFrita(int quantidade, double temperatura, boolean crocancia) {
        this.quantidade = quantidade;
        this.temperatura = temperatura;
        this.crocancia = crocancia;
    }

   public void fritar() {
        System.out.println("Batatas fritando a " + temperatura + " graus.");
    }

   public void salgar() {
        System.out.println("Batatas salgadas.");
    }

   public void servir() {
        String status = crocancia ? "crocantes" : "murchas";
        System.out.println("Servindo " + quantidade + " batatas " + status + ".");
    }
}

class Refrigerante {
    private String sabor;
    private double volume;
    private boolean gelo;

   public Refrigerante(String sabor, double volume, boolean gelo) {
        this.sabor = sabor;
        this.volume = volume;
        this.gelo = gelo;
    }

   public void gelar() {
        System.out.println("O refrigerante sabor " + sabor + " está gelado.");
    }

   public void abrir() {
        System.out.println("Abrindo lata/garrafa de refrigerante de " + volume + "ml.");
    }

   public void beber() {
        String comGelo = gelo ? "com gelo" : "sem gelo";
        System.out.println("Bebendo refrigerante " + sabor + " " + comGelo + ".");
    }
}

public class Main {
    public static void main(String[] args) {
        
   Hamburguer burguer = new Hamburguer("grande", "carne, queijo, alface e tomate", 750);
   BatataFrita fritas = new BatataFrita(25, 180.0, true);
   Refrigerante refri = new Refrigerante("cola", 350.0, true);

   burguer.montar();
   burguer.aquecer();
   burguer.comer();

   System.out.println("----");
   
   fritas.fritar();
   fritas.salgar();
   fritas.servir();

   System.out.println("----");

   refri.gelar();
   refri.abrir();
    refri.beber();
    }
}


----- Atividade 6. Testes Automatizados: (foi utilizado o google e inteligência artificial como auxílio: https://www.devmedia.com.br/introducao-aos-testes-funcionais-automatizados-com-junit-e-selenium-webdriver/28037 ---https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayOutputStream.html)

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class LanchoneteTest {
    
   private final ByteArrayOutputStream output = new ByteArrayOutputStream();
   private final PrintStream originalOut = System.out;

   @BeforeEach
   void setUpStreams() {
     System.setOut(new PrintStream(output));
    }

   @Test
   void testHamburguer() {
       Hamburguer burguer = new Hamburguer("grande", "carne, queijo, alface e tomate", 750);
        burguer.montar();
        burguer.aquecer();
        burguer.comer();

   String saida = output.toString().trim();
   assertTrue(saida.contains("Hamburguer tamanho grande com carne, queijo, alface e tomate montado."));
   assertTrue(saida.contains("Hamburguer aquecido."));
   assertTrue(saida.contains("Você está comendo um hamburguer de 750 calorias."));
    }

   @Test
   void testBatataFrita() {
       BatataFrita fritas = new BatataFrita(25, 180.0, true);
        fritas.fritar();
        fritas.salgar();
        fritas.servir();

   String saida = output.toString().trim();
   assertTrue(saida.contains("Batatas fritando a 180.0 graus."));
   assertTrue(saida.contains("Batatas salgadas."));
   assertTrue(saida.contains("Servindo 25 batatas crocantes."));
    }

   @Test
   void testRefrigerante() {
       Refrigerante refri = new Refrigerante("cola", 350.0, true);      
        refri.gelar();
        refri.abrir();
        refri.beber();

   String saida = output.toString().trim();
   assertTrue(saida.contains("O refrigerante sabor cola está gelado."));
   assertTrue(saida.contains("Abrindo lata/garrafa de refrigerante de 350.0ml."));
   assertTrue(saida.contains("Bebendo refrigerante cola com gelo."));
    }
}

