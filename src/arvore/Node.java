package arvore;

import jogo.Player;

public class Node implements Comparable<Node> {
	private int value;
	private Node pai;
	private Node[] children;
	private int count;
	private int max_filhos;
	private boolean node_folha;
	private int recompensa;
	private Player jogador;

	public Node(int value, Node pai, int max_filhos) {
		this.value = value;
		this.pai = pai;
		this.max_filhos = max_filhos;
		this.children = new Node[max_filhos];
		this.node_folha = true;
		this.count = 0;
		this.set_recompensa(-99);
	}

	public boolean is_IA() {
		return this.jogador.get_name().equals("IA") ? true : false;
	}

	public Integer get_value() {
		return value;
	}

	public void set_folha(boolean folha) {
		this.node_folha = folha;
	}

	public boolean is_folha() {
		return this.node_folha;
	}

	public void print_filhos() {
		for (Node n : children)
			System.out.print(n.get_value() + " ");
	}

	public Node[] get_children_array() {
		return children;
	}

	public void add_child(Node child) {
		node_folha = false;
		this.children[count++] = child;
	}

	public Node getPai() {
		return this.pai;
	}

	public int qtd_filhos() {
		return this.max_filhos;
	}

	public int get_recompensa() {
		return recompensa;
	}

	public void set_recompensa(int recompensa) {
		this.recompensa = recompensa;
	}

	public Player get_jogador() {
		return jogador;
	}

	public void set_jogador(Player jogador) {
		this.jogador = jogador;
	}

	@Override
	public int compareTo(Node n) {
		if (this.get_recompensa() > n.get_recompensa())
			return -1;
		if (this.get_recompensa() < n.get_recompensa())
			return 1;
		return 0;
	}

}