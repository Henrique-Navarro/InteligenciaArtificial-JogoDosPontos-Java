package jogo;

public class Player {
	private int points;
	private boolean turn;
	private String name;
	private int level;
	private boolean first_move;

	public Player(String name) {
		this.name = name;
	}

	public Player(int level) {
		this.points = 0;
		this.level = level;
		this.first_move = true;
	}

	public boolean get_first_move() {
		return first_move;
	}

	public void set_first_move(boolean first) {
		this.first_move = first;
	}
	
	public void set_name(String name) {
		this.name = name;
	}

	public String get_name() {
		return name;
	}

	public void set_turn(boolean turn) {
		this.turn = turn;
	}

	public boolean is_able() {
		return turn;
	}

	public int get_points() {
		return points;
	}

	public void increase_points() {
		this.points++;
	}

	public int get_level() {
		return this.level;
	}
}
