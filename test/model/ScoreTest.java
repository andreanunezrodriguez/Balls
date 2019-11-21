package model;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class ScoreTest {

	private Score theScores;

	private void setupScenery1() {}

	private void setupScenery2() {
		theScores = new Score("Andrea", 10);
	}

	@Test
	void scoreTest() {
		setupScenery1();
		String name = "Andrea";
		int score = 40;
		Score testScore = new Score(name, score);
		
		assertTrue("The name is not assigned correctly", name.equals(testScore.getName()));
		assertTrue("The size is not assigned correctly", score == testScore.getScore());
	}

	@Test
	void compareToTest() {
		setupScenery2();
		String name = "Andrea";
		int score = 34;
		Score testScore = new Score(name, score);
		assertTrue("The sorting is not being done correctly", theScores.compareTo(testScore) < 0);
	}

}
