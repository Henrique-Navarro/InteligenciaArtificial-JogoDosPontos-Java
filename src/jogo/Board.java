package jogo;

import arvore.Arvore;
import inteligência_artificial.IA;
import util.Operacoes;

public class Board extends Operacoes {
	private int size, level, profundidade;
	private IA ia;
	private char[][] matriz;
	private Player p1, p2;
	private int[] sequencia_jogadas;
	private int jogada_atual, jogada_jogador;
	private Arvore arvore;
	private boolean vez_ia;
	private int index;
	private boolean otimo;

	public Board(int size, int level, int profundidade, boolean otimo) {
		size += 2;
		this.vez_ia = true;
		this.index = 0;
		this.size = size;
		this.otimo = otimo;
		this.profundidade = profundidade;
		this.ia = new IA(profundidade);
		this.matriz = new char[size][size];
		this.sequencia_jogadas = new int[12];
		inicializar();
	}

	public void start() {
		fill_board();
		start_game();
	}

	public void start_game() {
		create_players();
		print_board();
		fazer_jogada(0);
		fazer_jogada(1);
		print_board();

		construir_arvore();

		while (!game_over()) {
			escolher_jogada();
			fazer_jogada(jogada_atual);
			print_board();
		}
		System.out.println("Vencedor: " + get_vencedor());
		System.exit(0);
	}

	// FAZER PROFUNDIDADE
	public void escolher_jogada() {
		do {
			if (vez_ia) {
				if (profundidade > 0) {
					System.out.println("Vez da IA");
					jogada_atual = ia.escolher_melhor_jogada();

				} else {
					jogada_atual = ia.escolher_jogada_aleatoria();
				}

				wait_sec(0.5);

			} else {
				System.out.println("Vez do P1");
				jogada_atual = read_int();
				ia.escolher_node_atual(jogada_atual);
			}
		} while (!jogada_is_valid(jogada_atual));
		profundidade--;
	}

	public boolean jogada_is_valid(int jogada_atual) {
		for (int i = 0; i < 12; i++)
			if (jogada_atual == sequencia_jogadas[i])
				return false;
		return jogada_atual >= 0 ? true : false;
	}

	public void fazer_jogada(int jogada) {
		System.out.println(jogada);
		int[] coords = get_jogada_to_coords(jogada);
		int x = coords[0];
		int y = coords[1];

		if (jogada == 0 || jogada == 1 || jogada == 5 || jogada == 6 || jogada == 10 || jogada == 11)
			matriz[x][y] = '-';
		else
			matriz[x][y] = '|';

		sequencia_jogadas[index++] = jogada;

		if (!verificar_ponto(x, y))
			switch_turn();
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

	public boolean possui_quadrado(int x, int y) {
		return (matriz[x - 1][y] == '-' && matriz[x][y - 1] == '|' && matriz[x][y + 1] == '|'
				&& matriz[x + 1][y] == '-') ? true : false;
	}

	public void construir_arvore() {
		this.arvore = new Arvore(0, 1, otimo);
		this.ia.set_arvore(arvore, sequencia_jogadas);
	}

	public Player get_player_ativo() {
		return vez_ia ? p2 : p1;
	}

	public void inicializar() {
		for (int i = 0; i < 12; i++)
			sequencia_jogadas[i] = -1;
	}

	public void switch_turn() {
		if (vez_ia)
			vez_ia = false;
		else
			vez_ia = true;
	}

	public void create_players() {
		// 0: HUMANO
		// 1: IA
		p1 = new Player(0);
		p1.set_turn(true);
		p1.set_name("P1");

		if (level == 1) {
			p2 = new Player(0);
			p2.set_turn(false);
			p2.set_name("p2");

		} else {
			p2 = new Player(1);
			p2.set_turn(false);
			p2.set_name("IA");
		}
	}

	public void wait_sec(double t) {
		try {
			Thread.sleep((long) (t * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String get_vencedor() {
		if (p1.get_points() == p2.get_points())
			return "Empate";
		return p1.get_points() > p2.get_points() ? p1.get_name() : p2.get_name();
	}

	public boolean game_over() {
		return index == sequencia_jogadas.length ? true : false;
		// return p1.get_points() + p2.get_points() == 4 ? true : false;
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

	public void print_board() {
		for (char[] linha : matriz) {
			for (char dot : linha) {
				System.out.print(dot + "  ");
			}
			System.out.println();
		}
		System.out.println("\n\n\n\n\n");
	}
}
