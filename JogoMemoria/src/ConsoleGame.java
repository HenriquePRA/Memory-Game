/**
 * Aplicacao em console para o Memory Game
 * que esta sendo implementado pela classe JogoMemoria.java
 * 
 * @author  Henrique Poggi
 * @version 1.0
 * @since   29-03-2020
 * 
 */

import java.awt.Color;
import java.util.Scanner;

public class ConsoleGame {
	public static void main(String[] args) {
		Color[] paleta = {Color.RED, Color.BLUE,Color.YELLOW,Color.CYAN,Color.BLACK,Color.GRAY,Color.GREEN,Color.PINK};
		MemoryGame jogo = new MemoryGame(paleta);
		System.out.println(jogo);	//chama toString()

		Scanner teclado = new Scanner(System.in);
		int linha1, coluna1, linha2, coluna2;
	
		do{
			System.out.println("\nPrimeira Posicao\n");
			System.out.print("Digite a Linha  (1): ");	linha1 = teclado.nextInt();
			System.out.print("Digite a Coluna (1): ");	coluna1 = teclado.nextInt();			
			System.out.println("\nSegunda Posicao\n");
			System.out.print("Digite a Linha  (2): ");	linha2 = teclado.nextInt();
			System.out.print("Digite a Coluna (2): ");	coluna2 = teclado.nextInt();
			try {
				jogo.adivinhar(linha1,coluna1,linha2,coluna2);
			}
			catch(Exception e) {
				System.out.println("Aviso:"+e.getMessage());
			}
			System.out.println("Total de Acertos:     "+jogo.getAcertos());
			System.out.println("Total de Erros:       " + (30 - (jogo.getTentativas()) - jogo.getAcertos()));
			System.out.println("Tentativas Restantes: "+jogo.getTentativas());
			System.out.println(jogo);

		}while(!jogo.terminou());
		teclado.close();
		
		System.out.println("\n\nGAME OVER !!\n\n");
		System.out.println(jogo.getResultadoFinal());
	}
}
