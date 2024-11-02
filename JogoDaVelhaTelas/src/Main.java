import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JLabel lbl00;
	private JLabel lbl02;
	private JLabel lbl01;
	private JLabel lbl10;
	private JLabel lbl11;
	private JLabel lbl12;
	private JLabel lbl20;
	private JLabel lbl21;
	private JLabel lbl22;
	private JLabel lblStatus;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private Tabuleiro t1 = new Tabuleiro();
	private Jogador usu = new Jogador("Usu�rio", "X");
	private Jogador pc = new Jogador("PC", "O");
	private List<JLabel> l1 = new LinkedList<>();
	private JButton btnDesfazer;
	private int cont = 0;
	private JButton btnReiniciar;
	private JLabel lblEdtStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		initComponents();
		this.setLocationRelativeTo(null);
		addLabel();
	}

	private boolean tentaJogada(int x, int y, String s) {
		if (!t1.venceu(usu) && !t1.venceu(pc)) {
			if (s.equals(usu.getSimbolo())) {
				if (t1.verificaJogada(x, y)) {
					t1.setJogada(x, y, s);
					mudaIcone();
					this.cont += 1;
					if (!t1.venceu(usu) && this.cont < 9) {
						jogadaPc();
						this.cont += 1;
					} else {
						if(!t1.venceu(usu) && this.cont == 9) {
							this.lblEdtStatus.setText("O jogo deu empate!");
						}else if(t1.venceu(usu)){
							System.out.println("O vencedor foi o Usuário!");
							this.lblEdtStatus.setText("O vencedor foi o Usuário!");							
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				if (t1.verificaJogada(x, y)) {
					t1.setJogada(x, y, s);
					mudaIcone();
					if (t1.venceu(pc)) {
						System.out.println("O vencedor foi o PC!");
						this.lblEdtStatus.setText("O vencedor foi o PC!");
					}
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}

	}

	private void mudaIcone() {
		for (JLabel l : l1) {
			l.setIcon(null);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!t1.getTabuleiro()[i][j].equals(" ")) {
					for (JLabel l : l1) {
						if (l.getName().equals(i + "" + j) && t1.getTabuleiro()[i][j].equals("X")) {
							l.setIcon(new ImageIcon(getClass().getResource("jogoVelhaX.jpg")));
						}
						if (l.getName().equals(i + "" + j) && t1.getTabuleiro()[i][j].equals("O")) {
							l.setIcon(new ImageIcon(getClass().getResource("jogoVelhaO.jpg")));
						}
					}
				}

			}
		}
	}

	private void jogadaPc() {
		int x = (int) (Math.random() * 3);
		int y = (int) (Math.random() * 3);
		while (!tentaJogada(x, y, pc.getSimbolo())) {
			x = (int) (Math.random() * 3);
			y = (int) (Math.random() * 3);
		}
	}

	private void desfazerJogada() {
		lblEdtStatus.setText("");
		if (this.cont % 2 == 0 && this.cont > 1 && !t1.getPilTab().empty()) {
			t1.volta(2);
			this.cont -= 2;
		} else if (this.cont > 0 && !t1.getPilTab().empty()) {
			t1.volta(1);
			this.cont -= 1;
		}
		t1.imprimeTabuleiro();
		mudaIcone();
	}

	private void reiniciarJogo() {
		lblEdtStatus.setText("");
		t1.novoTabuleiro();
		this.cont = 0;
		mudaIcone();
	}

	private void addLabel() {
		l1.add(lbl00);
		l1.add(lbl01);
		l1.add(lbl02);
		l1.add(lbl10);
		l1.add(lbl11);
		l1.add(lbl12);
		l1.add(lbl20);
		l1.add(lbl21);
		l1.add(lbl22);
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new MigLayout("", "[grow][120.00][][120.00][][120.00][grow]",
				"[34.00,grow][120.00][][120.00][][120.00][grow]"));

		this.lblStatus = new JLabel("Status: ");
		this.lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.contentPane.add(this.lblStatus, "cell 1 0 2 1,alignx right");

		this.lbl00 = new JLabel(" ");
		this.lbl00.setName("00");
		this.lbl00.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(0, 0, usu.getSimbolo());
			}
		});

		this.lblEdtStatus = new JLabel("");
		this.lblEdtStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.contentPane.add(this.lblEdtStatus, "cell 3 0 3 1,alignx center");
		this.lbl00.setOpaque(true);
		this.lbl00.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl00, "cell 1 1,grow");

		this.separator = new JSeparator();
		this.separator.setMinimumSize(new Dimension(10, 400));
		this.separator.setOpaque(true);
		this.separator.setBackground(Color.BLACK);
		this.contentPane.add(this.separator, "cell 2 1 1 5");

		this.lbl01 = new JLabel(" ");
		this.lbl01.setName("01");
		this.lbl01.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(0, 1, usu.getSimbolo());
			}
		});
		this.lbl01.setOpaque(true);
		this.lbl01.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl01, "cell 3 1,grow");

		this.separator_1 = new JSeparator();
		this.separator_1.setOpaque(true);
		this.separator_1.setMinimumSize(new Dimension(10, 400));
		this.separator_1.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_1, "cell 4 1 1 5");

		this.lbl02 = new JLabel(" ");
		this.lbl02.setName("02");
		this.lbl02.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(0, 2, usu.getSimbolo());
			}
		});
		this.lbl02.setOpaque(true);
		this.lbl02.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl02, "cell 5 1,grow");

		this.separator_2 = new JSeparator();
		this.separator_2.setBackground(Color.BLACK);
		this.separator_2.setOpaque(true);
		this.separator_2.setMinimumSize(new Dimension(120, 10));
		this.contentPane.add(this.separator_2, "cell 1 2");

		this.separator_6 = new JSeparator();
		this.separator_6.setOpaque(true);
		this.separator_6.setMinimumSize(new Dimension(120, 10));
		this.separator_6.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_6, "cell 3 2");

		this.separator_7 = new JSeparator();
		this.separator_7.setOpaque(true);
		this.separator_7.setMinimumSize(new Dimension(120, 10));
		this.separator_7.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_7, "cell 5 2");

		this.lbl10 = new JLabel(" ");
		this.lbl10.setName("10");
		this.lbl10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(1, 0, usu.getSimbolo());
			}
		});
		this.lbl10.setOpaque(true);
		this.lbl10.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl10, "cell 1 3,grow");

		this.lbl11 = new JLabel(" ");
		this.lbl11.setName("11");
		this.lbl11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(1, 1, usu.getSimbolo());
			}
		});
		this.lbl11.setOpaque(true);
		this.lbl11.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl11, "cell 3 3,grow");

		this.lbl12 = new JLabel(" ");
		this.lbl12.setName("12");
		this.lbl12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(1, 2, usu.getSimbolo());
			}
		});
		this.lbl12.setOpaque(true);
		this.lbl12.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl12, "cell 5 3,grow");

		this.separator_3 = new JSeparator();
		this.separator_3.setOpaque(true);
		this.separator_3.setMinimumSize(new Dimension(120, 10));
		this.separator_3.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_3, "cell 1 4");

		this.separator_4 = new JSeparator();
		this.separator_4.setOpaque(true);
		this.separator_4.setMinimumSize(new Dimension(120, 10));
		this.separator_4.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_4, "cell 3 4");

		this.separator_5 = new JSeparator();
		this.separator_5.setOpaque(true);
		this.separator_5.setMinimumSize(new Dimension(120, 10));
		this.separator_5.setBackground(Color.BLACK);
		this.contentPane.add(this.separator_5, "cell 5 4");

		this.lbl20 = new JLabel(" ");
		this.lbl20.setName("20");
		this.lbl20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(2, 0, usu.getSimbolo());
			}
		});
		this.lbl20.setOpaque(true);
		this.lbl20.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl20, "cell 1 5,grow");

		this.lbl21 = new JLabel(" ");
		this.lbl21.setName("21");
		this.lbl21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(2, 1, usu.getSimbolo());
			}
		});
		this.lbl21.setOpaque(true);
		this.lbl21.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl21, "cell 3 5,grow");

		this.lbl22 = new JLabel(" ");
		this.lbl22.setName("22");
		this.lbl22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tentaJogada(2, 2, usu.getSimbolo());
			}
		});
		this.lbl22.setOpaque(true);
		this.lbl22.setBackground(Color.WHITE);
		this.contentPane.add(this.lbl22, "cell 5 5,grow");

		this.btnDesfazer = new JButton("DESFAZER");
		this.btnDesfazer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desfazerJogada();
			}
		});

		this.btnReiniciar = new JButton("REINICIAR");
		this.btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarJogo();
			}
		});
		this.btnReiniciar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.contentPane.add(this.btnReiniciar, "cell 1 6");
		this.btnDesfazer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.contentPane.add(this.btnDesfazer, "cell 5 6,growx");
	}

}
