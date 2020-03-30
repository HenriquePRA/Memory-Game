/**
 * Classe MemoryGame que pode ser usado por SwingGame.java
 * e o ConsoleGame para exibicao do jogo.
 * 
 * @author  Henrique Poggi
 * @version 1.0
 * @since   29-03-2020
 * 
 */

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

public class MemoryGame{
	//Geracao da paleta
	private Color[] paleta = null;

	//Geracao das matrizes
	private Color[][] board = new Color[4][4];		 //Matriz de classe Color, usada durante a manipulacao do jogo
	private String[][] gameBoard = new String[4][4]; //Matriz usada durante a exibicao do jogo

	//Variaveis de Controle
	private int maxTentativas = 30;
	private int hits = 0;

	//Controle do uso das cores
	private HashMap<Color, Integer> colorUse = new HashMap<Color, Integer>(); // controle de uso

	public MemoryGame(Color[] paleta) {
		//Construtor do objeto classe
		this.paleta = paleta;
		// Ao iniciar o objeto faz o mapeamento das cores com
		// a quantidade de vezes que cada cor foi usada que
		// inicialmente cada cor foi usada zero vezes.
		for (Color cor : paleta) {
			this.colorUse.put(cor, 0);
		}
		// Povoamento das Matrizes board e gameBoard
		this.povarMatrizes();
	}


	private void povarMatrizes() {
		// Funcao de Povoamento da matriz, de forma aleatoria distribui a paleta de cores
		// pelos dezesseis campos da matriz, a paleta de cores deve ter no minimo oito elementos,
		// uma unica cor da paleta de cores pode ser usada por no maximo duas vezes.
		Random random = new Random();
		for (int colun = 0; colun < 4; colun++) {
			for (int linha = 0; linha < 4; linha++) {
				//Posicionamentio aleatorio de cada cor
				int cor = random.nextInt(8);
				if (colorUse.get(paleta[cor]) < 2) {
					board[colun][linha] = paleta[cor];
					colorUse.put(paleta[cor], colorUse.get(paleta[cor]) + 1);
				} else {
					linha -= 1;
				}
			}
		}
		// invocacao das funcao responsavel por povoar a matriz exibida durante o jogo.
		this.povoarMatrizJogavel();
	}


	private void povoarMatrizJogavel() {
		// Funcao responsavel por povoar a matriz exibida durante o jogo com espacos em branco.		
		for (int colun = 0; colun < 4; colun++) {
			for (int linha = 0; linha < 4; linha++) {
				 this.gameBoard[colun][linha] = "           ";
			}
		}
	}


	public boolean adivinhar(int linha1, int coluna1, int linha2, int coluna2) {
		// Recebe dois pares de numeros cada par representa uma posicao, caso as duas posicoes
		// tenham o mesmo dado altera a matriz gameBoard com um "X" para exibir que essas duas posicoes
		// ja foram testadas e exibe na tela o dado contido nas duas posições retorna true se as
		// duas posicoes forem iguais e false caso contrario.
		try {
			// Verifica se os pares de numeros recebidos sao menor ou igual a 3 e maior ou igual a 0
			if ((linha1 > 3) || (linha1 < 0) || (coluna1 > 3) || (coluna1 < 0) ||
				(linha2 > 3) || (linha2 < 0) || (coluna2 > 3) || (coluna2 < 0)) {
				throw new Exception("Posicao Invalida !\n");
			}
			// Inpede que uma posicao nao seja testata consigo mesma
			if (!((linha1 == linha2) && (coluna1 == coluna2))) {
				//Reducao do numero de tentativas.
				maxTentativas -= 1;
				// Compara a igualdade entre entre dados em duas posições da matriz.
				if (board[linha1][coluna1] == board[linha2][coluna2]) {
					//Acrescimo em acertos e retorno true
					this.gameBoard[linha1][coluna1] = "    X      ";
					this.gameBoard[linha2][coluna2] = "    X      ";
					this.hits += 1;
					System.out.println("\nAcertou, adivinhou a cor "+ getColorName(this.getCor(linha1,coluna1)) +".\n");
					return true;
				} else {
					// retorno false.
					System.out.println("\nErrou, as duas posicoes possuem cores diferentes.\n");
					return false;
				}
			} else {
				throw new Exception("Nao e possível testar uma posição consigo mesma !\n");
			}
		} catch(Exception e) {
			System.out.println("\nAviso: "+e.getMessage());
		}
		return false;
	}


	public boolean terminou() {
		// Funcao utilizada para verificar o estado do jogo, retorna true se o total
		// de acertos for maior ou igual a 8 ou se total de tentativas for menor ou igual a zero.
		if (this.maxTentativas <= 0 || this.hits >= 8) {
			return true;
		}
		return false;
	}


	public Color getCor(int linha, int coluna) {
		// Recebe um par de numeros inteiros, usa esse par de numeros para
		// encontrar e retornar uma string da matriz de cores.
		try {
			// Verifica se o par de numeros recebidos sao menor ou igual a 3 e maior ou igual a 0.
			if ((linha > 3) || (linha < 0) || (coluna > 3) || (coluna < 0)) {
				throw new Exception("Posicao Invalida !\n");
			} 
		} catch(Exception e) {
			System.out.println("Aviso: " + e.getMessage());
		}
		return board[linha][coluna];
	}


	public String getResultadoFinal() {
		//Funcao utilizada para exibir o resultado final apos o fim do jogo
		if (this.hits >= 8) {
			return ("Acertou com : " + (30 - this.maxTentativas) + " Tentativas\n\n");
		}
		return ("Terminou com : " + (30 - this.maxTentativas) + " Tentativas\n\n");
	}


	public int getAcertos() {
		// Retorna o numero de acertos.
		return this.hits;
	}


	public int getTentativas() {
		// Retorna o numero de tentativas

		return this.maxTentativas;
	}

    public static String getColorName(Color c) {
    	// Funcao que retorna o nome de uma cor em ingles a partir de um objeto da classe Color
        for (Field f : Color.class.getFields()) {
            try {
                if (f.getType() == Color.class && f.get(null).equals(c)) {
                    return f.getName();
                }
            } catch (java.lang.IllegalAccessException e) {
            	System.out.println("Aviso:"+e.getMessage());
            } 
        }
        return "Unknown";
    }

	@Override
	public String toString() {
		// Funcao toString que retorna uma string que mostra a situacao atual do jogo.
		String str = ( "\n                             Matriz                            \n"
				+ " ______________________________________________________________\n"
				+ "|     |       0      |      1      |      2      |      3      |\n"
				+ "|-----|--------------|-------------|-------------|-------------|\n");

		for (int colun = 0; colun < 4; colun++) {
			str += ("|  " + colun + "  | ");
			for (int linha = 0; linha < 4; linha++) {
				str += ("  " + this.gameBoard[colun][linha] + "|");
			}
			if (colun != 3) {
				str += ("\n|-----|--------------|-------------|-------------|-------------|\n");
			}
		}
		str += ("\n|_____|______________|_____________|_____________|_____________|\n");

		return(str);
	}
}