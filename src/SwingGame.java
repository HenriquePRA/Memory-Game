/**
 * Aplicacao Swing para o Memory Game
 * que esta sendo implementado pela classe MemoryGame.java
 * 
 * @author  Henrique Poggi
 * @version 1.0
 * @since   29-03-2020
 * 
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.SystemColor;

public class SwingGame  {
	private JFrame frame;
	private JLabel[][] matriz;
	private JLabel infolabel;
	private JLabel tentativaslabel;
	private JLabel acertoslabel;
	private JLabel primeiroselecionado, segundoselecionado;
	private JButton button;
	private final int TAMANHO=60;	//Largura e altura de um label (pixels)
	private MemoryGame jogo;

	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingGame window = new SwingGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public SwingGame() {
		initialize();
	}
	
	private void initialize() {

		frame = new JFrame();
		frame.setTitle("Memory Game");
		frame.setBounds(100, 100, 460, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		infolabel = new JLabel("Inicie o jogo");
		infolabel.setFont(new Font("Gadugi", Font.BOLD, 13));
		infolabel.setHorizontalAlignment(SwingConstants.CENTER);
		infolabel.setBounds(0, 150, 444, 30);
		frame.getContentPane().add(infolabel);

		acertoslabel = new JLabel("");
		acertoslabel.setHorizontalAlignment(SwingConstants.CENTER);
		acertoslabel.setBackground(SystemColor.control);
		acertoslabel.setFont(new Font("Monospace", Font.BOLD, 16));
		acertoslabel.setBounds(305, 100, 100, 25);
		frame.getContentPane().add(acertoslabel);

		tentativaslabel = new JLabel("");
		tentativaslabel.setHorizontalAlignment(SwingConstants.CENTER);
		tentativaslabel.setBackground(SystemColor.control);
		tentativaslabel.setFont(new Font("Monospace", Font.BOLD, 16));
		tentativaslabel.setBounds(305, 155, 100, 25);
		frame.getContentPane().add(tentativaslabel);
		
		//Criacao da matriz de labels com 16 labels
		matriz = new JLabel[4][4];		
		for(int i=0; i < 4; i++){
			for(int j=0; j < 4; j++){
				matriz[i][j] = new JLabel("");				//Criacao um label
				frame.getContentPane().add(matriz[i][j]);	//Adicao do label ao frame
			}
		}

		button = new JButton("Iniciar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color[] paleta = {Color.RED, Color.BLUE,Color.YELLOW,Color.CYAN,Color.ORANGE,Color.GRAY,Color.GREEN,Color.PINK};
				jogo = new MemoryGame(paleta);

				button.setText("Reiniciar");
				button.setBounds(305, 25, 100, 25);
				infolabel.setBounds(0, 280, 444, 20);
				infolabel.setText("Clique o mouse em uma das 16 posicoes: ");
				
				//Acertos txt
				JLabel acertosTxt = new JLabel("Acertos");
				acertosTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
				acertosTxt.setBounds(304, 80, 100, 15);
				acertosTxt.setHorizontalAlignment(SwingConstants.CENTER);
				frame.getContentPane().add(acertosTxt);				
				
				//Acertos Label
				acertoslabel.setText(Integer.toString(jogo.getAcertos()));
				acertoslabel.setBorder(new LineBorder(Color.BLACK, 1, true));
				acertoslabel.setBackground(Color.BLACK);
				acertoslabel.setOpaque(true);
				acertoslabel.setForeground(Color.GREEN);
								
				//Tentativas txt
				JLabel lblNewLabel = new JLabel("Tentativas");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblNewLabel.setBounds(304, 135, 100, 15);
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				frame.getContentPane().add(lblNewLabel);
			
				//Tentativas Label
				tentativaslabel.setText(Integer.toString(jogo.getTentativas()));
				tentativaslabel.setBorder(new LineBorder(Color.BLACK, 1, true));
				tentativaslabel.setBackground(Color.BLACK);
				tentativaslabel.setOpaque(true);
				tentativaslabel.setForeground(Color.GREEN);
				primeiroselecionado = null;		//Guarda o primeiro label que for selecionado
				segundoselecionado = null;		//Guarda o segundo label que for selecionado
				
				//Inicializa a matriz de labels
				for(int l=0; l<4; l++){			//Uma linha da matriz
					for(int c=0; c<4; c++){		//Uma coluna da matriz
						matriz[l][c].setBounds(((int)Math.round((0.5+l)*TAMANHO)), 
											   ((int)Math.round((0.4+c)*TAMANHO)), TAMANHO, TAMANHO); //(x,y,largura,altura)
						matriz[l][c].setOpaque(true);
						matriz[l][c].setBackground(Color.WHITE);	//fundo
						matriz[l][c].setBorder(new LineBorder(Color.BLACK, 1, false)); //arredondado
						//Registrar evento de click do mouse para o label da posicao l,c
						//Existirao 16 mouselisteners no total - todos com a mesma implementacao do click
						matriz[l][c].addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {	
								JLabel labelclicado = (JLabel) e.getSource();	//guarda o label que acabou se ser clicado
								int linha = labelclicado.getY()/TAMANHO;		//calcula a linha dele na matriz
								int coluna = labelclicado.getX()/TAMANHO;		//calcula a coluna dele na matriz
								//teste
								System.out.println("Posicao (linha,coluna) do label clicado =>"+  linha + "," + coluna);
								
								Color cor=null;
								try {
									cor = jogo.getCor(linha,coluna);	//obtem a cor (objeto Color) baseado na posicao do label
								}
								catch(Exception erro) {
									infolabel.setText("Erro: "+ erro.getMessage());
								}

								//---------------------- PROCESSAMENTO DO CLICK UNICO DO MOUSE ----------------------//
								if(e.getClickCount()==1) {
									if(primeiroselecionado == null) 
									{ 	//primeiro label selecionado deve ser pintado
										primeiroselecionado = labelclicado;
										primeiroselecionado.setBackground(cor);  //colorir com a cor obtida acima
										infolabel.setText("Selecione uma nova posicao");
										segundoselecionado = null;
									}
									else 
										
										if(segundoselecionado == null && labelclicado!= primeiroselecionado) 
										{	//segundo label selecionado deve ser pintado
											segundoselecionado = labelclicado;			
											segundoselecionado.setBackground(cor); //colorir com a cor obtida acima
											int coluna1 = primeiroselecionado.getX()/TAMANHO;
											int linha1 = primeiroselecionado.getY()/TAMANHO;
											int coluna2 = segundoselecionado.getX()/TAMANHO;
											int linha2 = segundoselecionado.getY()/TAMANHO;
											
											boolean acertou=false;
											try {
												acertou = jogo.adivinhar(linha1,coluna1,linha2,coluna2);
											}
											catch(Exception erro) {
												infolabel.setText("erro:"+ erro.getMessage());
											}

											if(acertou){
												infolabel.setText("Voce acertou a cor:  " + getColorName(cor));
												acertoslabel.setText(Integer.toString(jogo.getAcertos()));
												//remover o evento mouselistener dos dois labels iguais, impedindo 
												//que esses labels possam ser clicados novamente.
												//considerando que um label possui um array de mouselisteners,
												//remove-se somente o primeiro objeto mouselistener
												primeiroselecionado.removeMouseListener(primeiroselecionado.getMouseListeners()[0]);
												segundoselecionado.removeMouseListener(segundoselecionado.getMouseListeners()[0]);
												primeiroselecionado=null;
												segundoselecionado=null;
											}	
											else{
												//errou
												infolabel.setText("Errou - clique 2 vezes para ocultar");
											}
											tentativaslabel.setText(Integer.toString(jogo.getTentativas()));


										}
								}//fim click unico
								else 
									//---------------------- PROCESSAMENTO DO CLICK DUPLO DO MOUSE ----------------------//
									if(e.getClickCount()==2) {
										if(primeiroselecionado!=null && segundoselecionado!=null 
												&& primeiroselecionado!=segundoselecionado) {
											//ocultar os dois lebels selecionados
											primeiroselecionado.setBackground(Color.WHITE);
											segundoselecionado.setBackground(Color.WHITE);
											primeiroselecionado=null;
											segundoselecionado=null;
											infolabel.setText("Selecione novamente");
										}

									}//fim click duplo

								if (jogo.terminou()) {
									//exibir o resultado do jogo
									infolabel.setText(jogo.getResultadoFinal() + " - Reinicie o jogo");
									//remover o mouselistener de todos os labels(impedindo o clique)
									for(int a=0; a < 4; a++) {
										for(int b=0; b < 4; b++) {
											if(matriz[a][b].getMouseListeners().length>0) {
												matriz[a][b].removeMouseListener(matriz[a][b].getMouseListeners()[0]);
											} // fim do if											
										} // for b
									} // for a
								} // jogo.terminou

							} //fim mouseClicked()
						});  //fim MouseAda1ter()
					} //For c
				} // For l
			} //Action Performed
		}); //Action listener
		button.setBounds(172, 110, 100, 25);
		frame.getContentPane().add(button);
	}

	public String getColorName(Color c) {
        for (Field f : Color.class.getFields()) {
            try {
                if (f.getType() == Color.class && f.get(null).equals(c)) {
                    return f.getName();
                }
            } catch (java.lang.IllegalAccessException e) {
                // it should never get to here
            } 
        }
        return "Unknown";
    }
}
