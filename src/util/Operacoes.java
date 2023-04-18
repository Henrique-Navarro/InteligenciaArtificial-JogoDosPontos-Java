package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import arvore.Node;

public class Operacoes {

	public String read_dot_string() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}

	public int read_int() {
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public int[] read_dot_coordinates() {
		int[] vet = new int[2];
		Scanner scan = new Scanner(System.in);
		vet[0] = scan.nextInt();
		vet[1] = scan.nextInt();
		return vet;
	}

	public int get_random_int_between(int min, int max) {
		return (int) (Math.random() * max) + min;
	}

	public boolean dots_are_equal(int dot1, int dot2) {
		return get_dot_coordinates_string(dot1).equals(get_dot_coordinates_string(dot2)) ? true : false;
	}

	public String get_dot_coordinates_string(int dot) {
		int vet[] = get_dot_coordinates_vet(dot);
		return dot + ": " + vet[0] + " " + vet[1];
	}

	public int[] get_jogada_to_coords(int line) {
		int[][] coordinates = { { 0, 1 }, { 0, 3 }, { 1, 0 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 3 }, { 3, 0 },
				{ 3, 2 }, { 3, 4 }, { 4, 1 }, { 4, 3 } };
		return coordinates[line];
	}

	public int[] get_dot_coordinates_vet(int dot) {
		int[][] coordinates = { { 0, 0 }, { 0, 2 }, { 0, 4 }, { 2, 0 }, { 2, 2 }, { 2, 4 }, { 4, 0 }, { 4, 2 },
				{ 4, 4 } };
		return (dot < 0 || dot >= coordinates.length) ? null : coordinates[dot];
	}

	public int get_line_posic_int(int x, int y) {
		int[][] jogadas_possiveis = { { 0, 1 }, { 0, 3 }, { 1, 0 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 3 }, { 3, 0 },
				{ 3, 2 }, { 3, 4 }, { 4, 1 }, { 4, 3 } };

		for (int i = 0; i < jogadas_possiveis.length; i++) {
			int[] jogada_possivel = jogadas_possiveis[i];
			if (jogada_possivel[0] == x && jogada_possivel[1] == y)
				return i;

		}
		return -1;
	}

	public void print_array(int[] array) {
		for (Integer i : array)
			System.out.print(i + " ");
		System.out.println("\n");
	}

	public void printar_array_list(ArrayList<Node> sequencia_nodes) {
		for (Node n : sequencia_nodes)
			System.out.print(n.get_value() + " ");
		System.out.println("\n");
	}

	public void printar_players(ArrayList<Node> sequencia_nodes) {
		int ia = 0, p1 = 0;
		for (Node n : sequencia_nodes) {
			if (n.get_jogador().get_name().equals("P1"))
				ia++;
			else if (n.get_jogador().get_name().equals("IA"))
				p1++;
		}
		System.out.println("ia: " + ia + " p1: " + p1);
	}
}
