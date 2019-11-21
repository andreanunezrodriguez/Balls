package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Score implements Comparable<Score>, Serializable {

	private String name;
	private int score;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(Score a) {
		return (this.score - a.getScore());
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}
}
