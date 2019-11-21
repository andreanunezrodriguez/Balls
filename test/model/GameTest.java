package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class GameTest {

	private Game juegoBolita;

	private void setupScenery1() {
	}

	private void setupScenery2() {
		try {
			juegoBolita = new Game();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void gameTest() {
		setupScenery1();
		Game gameTest = null;
		try {
			gameTest = new Game();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull("The list is null", gameTest.gettheBalls() != null);
		assertNotNull("The list is null", gameTest.gettheScores() != null);
	}

	@Test
	void loadGameTest() {
		setupScenery2();
		String camino = "Data/Level1.txt";
		try {
			juegoBolita.loadGame(camino);
		} catch (IOException e) {
			fail("The game wasn't loaded");
		}
		assertTrue("The list doesn't have a size greater than 0", juegoBolita.gettheBalls().size() > 0);
	}

	@Test
	void saveGameTest() {
		setupScenery2();
		String camino = "Data/savedGame.txt";
		try {
			juegoBolita.loadGame(camino);
		} catch (IOException e) {
			fail("The game wasn't loaded");
		}
		File fl = new File(camino);
		assertTrue("The file exists", fl.exists());
	}

	@Test
	void exceptionTest1() {
		setupScenery2();
		String camino = "Dataa/MiCaminito.txt";
		try {
			juegoBolita.loadGame(camino);
			fail("The exception wasn't thrown");
		} catch (IOException e) {
			assertTrue(true);
		}
	}

	@Test
	void exceptionTest2() {
		setupScenery2();
		String camino = "Data/Level1.txt";
		try {
			juegoBolita.loadGame(camino);
			assertTrue(true);
		} catch (IOException e) {
			fail("The exception was thrown");
		}
	}

	@Test
	void exceptionTest3() {
		setupScenery2();
		String camino = "Dataa/MiCaminito.txt";
		try {
			juegoBolita.saveGame(camino);
			fail("The exception wasn't thrown");
		} catch (IOException e) {
			assertTrue(true);
		}
	}

	@Test
	void exceptionTest4() {
		setupScenery2();
		String camino = "Data/savedGame.txt";
		try {
			juegoBolita.saveGame(camino);
			assertTrue(true);
		} catch (IOException e) {
			fail("The exception was thrown");
		}
	}
	@Test
	void addScoreTest() {
		setupScenery2();
		String name = "kiko";
		int score = 9;
		try {
			juegoBolita.addScore(name, score);
		} catch (IOException e) {
			fail("An exception was thrown");
		}

		assertTrue("The list doesn't have size 1",
				juegoBolita.gettheScores()[0][0].equals(name) && juegoBolita.gettheScores()[0][1].equals(score + ""));
	}

}
