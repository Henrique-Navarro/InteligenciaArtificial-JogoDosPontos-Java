package inteligência_artificial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import arvore.Arvore;
import arvore.Node;
import jogo.BoardGhost;
import jogo.Player;
import util.Operacoes;

public class IA extends Operacoes {
	private Arvore arvore;
	private Node root;
	private int melhor_qtd_pontos, qtd_pontos;
	private int nivel;
	private int aux = 0;
	private BoardGhost board_ghost;
	private int profundidade;
	private Node node_atual;
	private int[] sequencia_jogadas;

	public IA(int profundidade) {
		this.profundidade = profundidade;
	}

	public void set_arvore(Arvore arvore, int[] sequencia_jogadas) {
		this.nivel = 0;
		this.root = arvore.get_root();
		this.node_atual = arvore.get_root_son();
		this.arvore = arvore;
		this.sequencia_jogadas = sequencia_jogadas;
	}

	public int escolher_jogada_aleatoria() {
		int aleatoria = get_random_int_between(2, 10);
		System.out.println("\nJogada Aleatoria: " + aleatoria);
		return aleatoria;
	}

	public void escolher_node_atual(int no) {
		Node[] children = node_atual.get_children_array();
		if (children != null) {
			for (Node n : children) {
				if (n != null) {
					if (n.get_value() == no)
						node_atual = n;
				}
			}
		}
		// System.out.println("atual: " + node_atual.get_value());
	}

	public int escolher_melhor_jogada() {
		// System.out.println("ATUAL: " + node_atual.get_value());
		ArrayList<Node> perfeitos = new ArrayList<Node>();
		ArrayList<Node> otimos = new ArrayList<Node>();
		ArrayList<Node> positivos = new ArrayList<Node>();
		ArrayList<Node> negativos = new ArrayList<Node>();
		ArrayList<Node> neutros = new ArrayList<Node>();
		ArrayList<Node> pessimos = new ArrayList<Node>();

		int melhor_jogada = 0;

		Node[] children = node_atual.get_children_array();
		if (children != null) {
			for (Node n : children) {
				if (n != null) {
					if (n.get_recompensa() == 4)
						perfeitos.add(n);
					else if (n.get_recompensa() == 3 || n.get_recompensa() == 2)
						otimos.add(n);
					else if (n.get_recompensa() == 1 || n.get_recompensa() == 2)
						positivos.add(n);
					else if (n.get_recompensa() == 0)
						neutros.add(n);
					else if (n.get_recompensa() == -1 || n.get_recompensa() == -2)
						negativos.add(n);
					else if (n.get_recompensa() == -4 || n.get_recompensa() == -3)
						pessimos.add(n);
				}
			}
		}
		if (!pessimos.isEmpty())
			melhor_jogada = pessimos.get(get_random_int_between(0, pessimos.size())).get_value();

		if (!negativos.isEmpty())
			melhor_jogada = negativos.get(get_random_int_between(0, negativos.size())).get_value();

		if (!neutros.isEmpty())
			melhor_jogada = neutros.get(get_random_int_between(0, neutros.size())).get_value();

		if (!positivos.isEmpty())
			melhor_jogada = positivos.get(get_random_int_between(0, positivos.size())).get_value();

		if (!otimos.isEmpty())
			melhor_jogada = otimos.get(get_random_int_between(0, otimos.size())).get_value();

		System.out.println("\nMelhor jogada: " + melhor_jogada);
		// print_array(sequencia_jogadas);
		escolher_node_atual(melhor_jogada);

		return melhor_jogada;
	}
	/*
	 * public int escolher_melhor_jogada() { ArrayList<Node> nodes = new
	 * ArrayList<Node>(); Node[] children = node_atual.get_children_array(); if
	 * (children != null) { for (Node n : children) { if (n != null) { nodes.add(n);
	 * } } } nodes.sort((n1, n2) -> n2.get_recompensa() - n1.get_recompensa()); int
	 * melhor_jogada = nodes.get(0).get_value();
	 * System.out.println("\nMelhor jogada: " + melhor_jogada);
	 * escolher_node_atual(melhor_jogada); return melhor_jogada; }
	 */

}