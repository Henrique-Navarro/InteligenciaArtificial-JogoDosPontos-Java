package jogo;

import java.util.ArrayList;

import arvore.Node;
import util.Operacoes;

public class BoardGhost extends Operacoes {
	private int size;
	private char[][] matriz;
	private Player p1, p2;
	private int jogada_atual;
	private boolean vez_ia;
	private int index;
	private ArrayList<Integer> sequencia_jogadas;
	private boolean foi_ponto = false;

	public BoardGhost(int size) {
		size += 2;
		this.index = 0;
		this.size = size;
		this.matriz = new char[size][size];
		this.vez_ia = true;
	}

	public boolean simular_ponto(ArrayList<Node> sequencia_nodes) {
		this.sequencia_jogadas = node_to_int(sequencia_nodes);
		this.start_game();
		return foi_ponto;
	}

	public int simular_jogo_heuristica(ArrayList<Node> sequencia_nodes, boolean print) {
		this.sequencia_jogadas = node_to_int(sequencia_nodes);
		this.start_game();
		if(print) {
			System.out.println("\n\nSimulação: "+(p2.get_points() - p1.get_points()));
			print_board();
		}
		return p2.get_points() - p1.get_points();
	}

	public int simular_jogo(ArrayList<Node> sequencia_nodes, boolean print) {
		this.sequencia_jogadas = node_to_int(sequencia_nodes);
		this.start_game();
		if(print) {
			System.out.println("\n\nSimulação: "+get_pontos_vencedor());
			print_board();
		}
		return get_pontos_vencedor();
	}

	public ArrayList<Integer> node_to_int(ArrayList<Node> sequencia_nodes) {
		sequencia_jogadas = new ArrayList<Integer>();
		for (Node n : sequencia_nodes)
			sequencia_jogadas.add(n.get_value());
		return sequencia_jogadas;
	}

	public void start_game() {
		fill_board();
		create_players();

		for (Integer i : sequencia_jogadas) {
			escolher_jogada();
			fazer_jogada(jogada_atual);
		}
	}

	public void escolher_jogada() {
		jogada_atual = sequencia_jogadas.get(index);
		index++;
	}

	public void fazer_jogada(int jogada) {
		int[] coords = get_jogada_to_coords(jogada);
		int x = coords[0];
		int y = coords[1];

		if (jogada == 0 || jogada == 1 || jogada == 5 || jogada == 6 || jogada == 10 || jogada == 11)
			matriz[x][y] = '-';
		else
			matriz[x][y] = '|';

		if (!verificar_ponto(x, y)) {
			switch_turn();
			foi_ponto = false;

		} else
			foi_ponto = true;
	}

	private boolean verificar_ponto(int x, int y) {
		boolean point = false;
		int[][] pontos_quadrado = { { 1, 1 }, { 1, 3 }, { 3, 1 }, { 3, 3 } };

		for (int[] coords : pontos_quadrado) {
			int row = coords[0];
			int col = coords[1];

			if (matriz[row][col] == ' ' && possui_quadrado(row, col)) {
				if (vez_ia)
					matriz[row][col] = '#';
				else
					matriz[row][col] = 'o';

				point = true;
				get_player_ativo().increase_points();
			}
		}
		return point;
	}

	public void print_board() {
		for (char[] linha : matriz) {
			for (char dot : linha) {
				System.out.print(dot + "  ");
			}
			System.out.println();
		}
	}
	
	public boolean possui_quadrado(int x, int y) {
		return (matriz[x - 1][y] == '-' && matriz[x][y - 1] == '|' && matriz[x][y + 1] == '|'
				&& matriz[x + 1][y] == '-') ? true : false;
	}

	public Player get_player_ativo() {
		return vez_ia ? p2 : p1;
	}

	public void switch_turn() {
		if (vez_ia)
			vez_ia = false;
		else
			vez_ia = true;
	}

	public void create_players() {
		p1 = new Player(0);
		p1.set_name("P1");
		p2 = new Player(1);
		p2.set_name("IA");
	}

	public int get_pontos_vencedor() {
		if (p1.get_points() == p2.get_points())
			return 0;
		return p1.get_points() > p2.get_points() ? -1 : +1;
	}

	public String get_vencedor() {
		if (p1.get_points() == p2.get_points())
			return "Empate";
		return p1.get_points() > p2.get_points() ? p1.get_name() : p2.get_name();
	}

	public void fill_board() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matriz[i][j] = '•';
				if (i % 2 == 0) {
					if (j % 2 != 0)
						matriz[i][j] = ' ';
				} else if (j % 2 == 0) {
					matriz[i][j] = ' ';
				} else if (j == i || i > 0 && i < 4)
					matriz[i][j] = ' ';
			}
		}
	}
}
