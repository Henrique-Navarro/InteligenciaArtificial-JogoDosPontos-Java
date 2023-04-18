package arvore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import jogo.BoardGhost;

public class Arvore {
	private Node root, node_root_son;
	private Node node_atual;
	private boolean[] disponiveis;
	private int move01, move02;
	private int qtd = 0;
	private int nivel = 0;
	private int qtd_folhas = 0;
	private ArrayList<Node> sequencia_nodes;
	private int positivos = 0, negativos = 0, zeros = 0, otimos = 0, pessimos = 0;
	public boolean otimo;
	private boolean vez_ia;
	private boolean[] jogadas;
	// private boolean[] quadrados;

	public Arvore(int move01, int move02, boolean otimo) {
		this.move01 = move01;
		this.move02 = move02;
		this.otimo = otimo;
		this.vez_ia = true;
		this.sequencia_nodes = new ArrayList<Node>();
		this.jogadas = new boolean[12];
		// this.quadrados = new boolean[4];

		for (int i = 0; i < 12; i++)
			jogadas[i] = false;
		// for (int i = 0; i < 4; i++)quadrados[i] = false;

		criar_nodes_disponiveis();

		adicionar(move01);
		adicionar(move02);

		create_tree(node_root_son, disponiveis);
		// System.out.println("Árvore Gerada com Sucesso!");
		// printar();

		atribuir_valores(root, sequencia_nodes, vez_ia);
		System.out.println("Valores Atribuidos com Sucesso!");
		// printar_result();
	}

	public void create_tree(Node node, boolean[] disponiveis) {
		for (int i = 0; i < disponiveis.length; i++) {
			if (disponiveis[i]) {
				Node novo = new Node(i, node, qtd_true_array(disponiveis));
				node.add_child(novo);
				disponiveis[i] = false;
				qtd++;
				create_tree(novo, disponiveis);
				disponiveis[i] = true;
			}
		}
	}

	boolean[] quadrados = new boolean[4];
	private int ultimo_quadrado = 0, penultimo_quadrado = 0;
	ArrayList<Integer> penultimos_quadrados = new ArrayList<Integer>();

	// VERIFICAR 2 PONTOS
	// VERIFICAR 2 ULTIMA JOGADAS
	public boolean foi_ponto() {
		int qtd_pontos = 0;
		boolean ponto = false;

		if (jogadas[0] && jogadas[2] && jogadas[3] && jogadas[5] && !quadrados[0]) {
			quadrados[0] = true;
			ultimo_quadrado = 0;
			qtd_pontos++;
			ponto = true;
			penultimo_quadrado = ultimo_quadrado;
		}
		if (jogadas[1] && jogadas[3] && jogadas[4] && jogadas[6] && !quadrados[1]) {
			if (qtd_pontos > 0)
				penultimo_quadrado = ultimo_quadrado;

			quadrados[1] = true;
			ultimo_quadrado = 1;
			qtd_pontos++;
			ponto = true;

		}
		if (jogadas[5] && jogadas[7] && jogadas[8] && jogadas[10] && !quadrados[2]) {
			if (qtd_pontos > 0)
				penultimo_quadrado = ultimo_quadrado;

			quadrados[2] = true;
			ultimo_quadrado = 2;
			qtd_pontos++;
			ponto = true;

		}
		if (jogadas[6] && jogadas[8] && jogadas[9] && jogadas[11] && !quadrados[3]) {
			if (qtd_pontos > 0)
				penultimo_quadrado = ultimo_quadrado;

			quadrados[3] = true;
			ultimo_quadrado = 3;
			qtd_pontos++;
			ponto = true;

		}
		if (quadrados[0] && quadrados[1] && quadrados[2] && quadrados[3]) {
			if (qtd_pontos > 1) {
				quadrados[ultimo_quadrado] = false;
				quadrados[penultimo_quadrado] = false;
			} else
				quadrados[ultimo_quadrado] = false;
		}

		return ponto;
	}

	public void atribuir_valores(Node node, ArrayList<Node> sequencia_nodes, boolean vez_ia) {
		if (node == null)
			return;

		sequencia_nodes.add(node);
		jogadas[node.get_value()] = true;

		// printar_jogada_atual(node, vez_ia);
		if (node.is_folha()) {
			int soma = simular_jogo();
			node.set_recompensa(soma);

			// verificar_soma(soma);
			// printar_jogada_completa(jogadas, sequencia_nodes);

		} else {
			Node[] child = node.get_children_array();
			if (child != null) {

				// printar_jogada_atual(node, vez_ia);

				if (vez_ia) {
					int max_value = Integer.MIN_VALUE;
					for (Node n : child) {
						if (n != null) {
							if (foi_ponto())
								vez_ia = true;
							else
								vez_ia = false;

							atribuir_valores(n, sequencia_nodes, vez_ia);

							max_value = Math.max(max_value, n.get_recompensa());
							// max_value = escolher_melhor_valor(max_value, n.get_recompensa(), vez_ia);
						}
					}
					node.set_recompensa(max_value);

				} else {
					int min_value = Integer.MAX_VALUE;
					for (Node n : child) {
						if (n != null) {
							if (foi_ponto())
								vez_ia = false;
							else
								vez_ia = true;

							atribuir_valores(n, sequencia_nodes, vez_ia);

							min_value = Math.min(min_value, n.get_recompensa());
							// min_value = escolher_melhor_valor(min_value, n.get_recompensa(), vez_ia);
						}
					}
					node.set_recompensa(min_value);
				}
			}
		}
		jogadas[sequencia_nodes.get(sequencia_nodes.size() - 1).get_value()] = false;
		sequencia_nodes.remove(sequencia_nodes.size() - 1);
	}

	public int simular_jogo() {
		BoardGhost board_ghost = new BoardGhost(3);
		boolean print = false;
		if (otimo)
			return board_ghost.simular_jogo(sequencia_nodes, print);
		else
			return board_ghost.simular_jogo_heuristica(sequencia_nodes, print);
	}

	// OTIMO ESCOLHER MAIOR/MENOR VALOR DOS FILHOS
	// HEURISTICO SOMA TODOS OS VALORES DOS FILHOS
	public int escolher_melhor_valor(int max_value, int recompensa, boolean vez_ia) {
		if (vez_ia)
			return Math.max(max_value, recompensa);
		else
			return Math.min(max_value, recompensa);
	}

	public void printar_jogada_atual(Node node, boolean vez_ia) {
		System.out.print(node.get_value() + " ");
		if (vez_ia)
			System.out.println("IA");
		else
			System.out.println("P1");
		wait_sec();
	}

	public void printar_jogada_completa(boolean[] jogadas_copia, ArrayList<Node> sequencia_nodes) {
		System.out.println("Jogada Completa");
		// print_boolean_array_only_true(jogadas_copia);
		print_array_list(sequencia_nodes);
		wait_sec();
	}

	public boolean[] clonar_array(boolean[] array) {
		boolean[] new_array = new boolean[array.length];
		for (int i = 0; i < array.length; i++)
			new_array[i] = array[i];
		return new_array;
	}

	public void wait_sec() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verificar_soma(int soma) {
		if (soma == 0)
			zeros++;
		else if (soma == 4)
			otimos++;
		else if (soma == -4)
			pessimos++;
		else if (soma >= 1)
			positivos++;
		else if (soma <= -1)
			negativos++;
	}

	public void printar_result() {
		System.out.println("positivos: \t" + positivos);
		System.out.println("negativos: \t" + negativos);
		System.out.println("neutros: \t" + zeros);
		System.out.println("otimos: \t" + otimos);
		System.out.println("pessimos: \t" + pessimos);
	}

	public int qtd_true_array(boolean[] array) {
		int count = 0;
		for (boolean b : array)
			if (b)
				count++;
		return count;
	}

	public void criar_nodes_disponiveis() {
		this.disponiveis = new boolean[12];
		for (int i = 0; i < 12; i++) {
			if (i == move01 || i == move02)
				this.disponiveis[i] = false;
			else
				this.disponiveis[i] = true;
		}
	}

	public void print_array_list(ArrayList<Node> array) {
		// System.out.print("arrayList: ");
		for (Node n : array)
			System.out.print(n.get_value() + " ");
		System.out.println("\n");
	}

	public void print_boolean_array_only_true(boolean[] array) {
		// System.out.print("boolean: ");
		for (int i = 0; i < 12; i++) {
			if (array[i])
				System.out.print(i + " ");
		}
		System.out.println();
	}

	public void print_boolean_array(boolean[] array) {
		for (boolean b : array)
			System.out.print(b + " ");
		System.out.println();
	}

	public Node get_root() {
		return this.root;
	}

	public Node get_root_son() {
		return this.node_root_son;
	}

	public void printar_recompensa() {
		if (root == null)
			return;

		Queue<Node> fila = new LinkedList<>();
		fila.add(root);
		nivel = 0;
		while (!fila.isEmpty()) {
			int tamanho_nivel = fila.size();
			System.out.println("Nível " + nivel + ": \t" + tamanho_nivel);

			for (int i = 0; i < tamanho_nivel; i++) {
				Node node = fila.remove();
				if (node.is_folha())
					qtd_folhas++;
				System.out.print(node.get_recompensa() + " ");

				for (Node filho : node.get_children_array())
					if (filho != null)
						fila.add(filho);
			}
			System.out.println();
			nivel++;
		}
		System.out.println();
	}

	public void printar() {
		if (root == null)
			return;

		Queue<Node> fila = new LinkedList<>();
		fila.add(root);
		nivel = 0;
		while (!fila.isEmpty()) {
			int tamanho_nivel = fila.size();
			System.out.println("Nível " + nivel + ": \t" + tamanho_nivel);

			for (int i = 0; i < tamanho_nivel; i++) {
				Node node = fila.remove();
				if (node.is_folha())
					qtd_folhas++;
				System.out.print(node.get_value() + " ");

				for (Node filho : node.get_children_array())
					if (filho != null)
						fila.add(filho);
			}
			// System.out.println();
			nivel++;
		}
		// info();
	}

	public void info() {
		System.out.println();
		System.out.println("qtd niveis: \t" + nivel);
		System.out.println("qtd nos: \t" + qtd);
		System.out.println("qtd_folhas: \t" + qtd_folhas);
		System.out.println();
	}

	public void adicionar(int jogada) {
		if (qtd == 0) {
			root = new Node(jogada, null, 1);
			root.set_folha(false);
			node_atual = root;
			qtd++;
		} else {
			node_root_son = new Node(jogada, node_atual, qtd_true_array(disponiveis));
			node_atual.add_child(node_root_son);
			node_atual = node_root_son;
			qtd++;
		}
	}
}