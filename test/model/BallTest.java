package model;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class BallTest {

	private void setupScenery1() {
	}

	@Test
	void pacManSpriteTest() {
		setupScenery1();
		double radius = 30.0;
		double posX = 90.0;
		double posY = 180.0;
		int waitT = 20;
		String direction = Ball.LEFT;
		int bounces = 3;
		boolean stopped = false;
		Ball ballT = new Ball(radius, posX, posY, waitT, direction, bounces, stopped);

		assertTrue("The radius is not assigned correctly", radius == ballT.getRadius());
		assertTrue("The posX is not assigned correctly", posX == ballT.getPosX());
		assertTrue("The posY is not assigned correctly", posY == ballT.getPosY());
		assertTrue("The wait time is not assigned correctly", waitT == ballT.getWaitT());
		assertTrue("The direction is not assigned correctly", direction.equals(ballT.getDirection()));
		assertTrue("The bounces are not assigned correctly", bounces == ballT.getBounces());
		assertTrue("The state is not assigned correctly", stopped == ballT.isStopped());
	}

}
