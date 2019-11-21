package model;


public class Ball {

	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String DOWN = "DOWN";
	public static final String UP = "UP";
	private double radius;
	private double posX;
	private double posY;
	private int waitT;
	private String direction;
	private int bounces;
	private boolean stopped;

	public Ball(double radius, double posX, double posY, int waitT, String direction, int bounces, boolean stopped) {
		this.radius = radius;
		this.posX = posX;
		this.posY = posY;
		this.waitT = waitT;
		this.direction = direction;
		this.bounces = bounces;
		this.stopped = stopped;
	}


	public double getRadius() {
		return radius;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public int getWaitT() {
		return waitT;
	}

	public String getDirection() {
		return direction;
	}

	public int getBounces() {
		return bounces;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setBounces(int bounces) {
		this.bounces = bounces;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}	
}