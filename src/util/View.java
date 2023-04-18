package util;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jogo.Board;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;

public class View extends JFrame {
	public static int size;
	public static int level;
	public static int profundidade;

	private static Board board;
	private JPanel contentPane;
	private JSlider slider_profundidade;
	private JLabel label_slider;
	public ArrayList<JLabel> all_labels;

	public static void create_view() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View() {
		// CONFIG LAYOUT
		setBackground(SystemColor.controlDkShadow);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(47, 79, 79));
		setTitle("Jogo dos Dots");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(503, 503);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setLayout(null);

		// SLIDER
		slider_profundidade = new JSlider();
		slider_profundidade.setOrientation(SwingConstants.VERTICAL);
		slider_profundidade.setMinimum(0);
		slider_profundidade.setValue(10);
		slider_profundidade.setMaximum(10);
		slider_profundidade.setBounds(419, 72, 58, 267);
		slider_profundidade.setBackground(new Color(51, 51, 51));
		contentPane.add(slider_profundidade);

		// LABELS SLIDER
		label_slider = new JLabel("10");
		label_slider.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_slider.setBounds(396, 204, 48, 21);
		label_slider.setForeground(Color.WHITE);
		contentPane.add(label_slider);

		// ATUALIZAR OS SLIDERS
		slider_profundidade.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label_slider.setText(String.valueOf(slider_profundidade.getValue()));
			}
		});

		JRadioButton btn01 = new JRadioButton("normal");
		btn01.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btn01.setForeground(Color.WHITE);
		btn01.setBounds(37, 108, 195, 57);
		btn01.setSelected(true);
		btn01.setBackground(new Color(51, 51, 51));
		contentPane.add(btn01);

		JRadioButton btn02 = new JRadioButton("heurística");
		btn02.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btn02.setForeground(Color.WHITE);
		btn02.setBounds(37, 168, 195, 57);
		btn02.setBackground(new Color(51, 51, 51));
		contentPane.add(btn02);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(btn01);
		grupo.add(btn02);

		// BOTÃO START
		JButton btn_start = new JButton("start");
		btn_start.setForeground(Color.WHITE);
		btn_start.setBackground(new Color(51, 51, 51));
		btn_start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// PROFUNDIDADE
				profundidade = slider_profundidade.getValue();

				// NORMAL X HEURISTICO
				boolean otimo = false;
				if (btn01.isSelected())
					otimo = true;
				Board board = new Board(3, 0, profundidade, otimo);
				board.start();
			}
		});
		btn_start.setBounds(0, 386, 487, 78);
		contentPane.add(btn_start);

		JLabel lb_algoritmo = new JLabel("Escolha o Algoritmo:");
		lb_algoritmo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_algoritmo.setForeground(Color.WHITE);
		lb_algoritmo.setBounds(37, 55, 195, 21);
		contentPane.add(lb_algoritmo);

		JLabel lb_profundidade = new JLabel("Profundidade:");
		lb_profundidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lb_profundidade.setForeground(Color.WHITE);
		lb_profundidade.setBounds(314, 208, 110, 14);
		contentPane.add(lb_profundidade);
	}

	public void print_atributos() {
		System.out.println("size: " + size);
		System.out.println("level: " + level);
		System.out.println("profundidade: " + profundidade);
	}
}
