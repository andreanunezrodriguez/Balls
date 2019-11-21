package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	private static final String PATH_SCORES = "Data/theScores.pac";
	private List<Ball> theBalls;

	private List<Score> theScores;
	private int level;

	public Game() throws IOException, ClassNotFoundException {
		theBalls = new ArrayList<Ball>();
		loadScores();
	}

	private void saveScores() throws IOException {

		File fl = new File(PATH_SCORES);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fl));
		oos.writeObject(theScores);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	private void loadScores() throws IOException, ClassNotFoundException {

		File fl = new File(PATH_SCORES);
		if (fl.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fl));
			theScores = (ArrayList<Score>) ois.readObject();
			ois.close();
		} else {
			theScores = new ArrayList<Score>();
		}

	}

	public void loadGame(String path) throws IOException {
		File fl = new File(path);
		FileReader fr = new FileReader(fl);
		BufferedReader bf = new BufferedReader(fr);
		bf.readLine();
		level = Integer.parseInt(bf.readLine());
		bf.readLine();
		String s = bf.readLine();

		while (s != null) {
			String[] arr = s.split(" ");
			double radius = Double.parseDouble(arr[0]);
			double posX = Double.parseDouble(arr[1]);
			double posY = Double.parseDouble(arr[2]);
			int waitT = Integer.parseInt(arr[3]);
			String direction = arr[4];
			int bounces = Integer.parseInt(arr[5]);
			boolean stopped = Boolean.parseBoolean(arr[6]);
			Ball ps = new Ball(radius, posX, posY, waitT, direction, bounces, stopped);
			theBalls.add(ps);
			s = bf.readLine();
		}
		fr.close();
		bf.close();
	}

	public void saveGame(String path) throws IOException {
		PrintWriter pw = new PrintWriter(path);
		pw.print("#Level\n" + level + "\n#radius posX posY wait direction bounces stopped\n");
		for (int i = 0; i < theBalls.size(); i++) {
			Ball ps = theBalls.get(i);
			pw.println(ps.getRadius() + " " + ps.getPosX() + " " + ps.getPosY() + " " + ps.getWaitT() + " "
					+ ps.getDirection() + " " + ps.getBounces() + " " + ps.isStopped());
		}
		pw.close();
	}

	public void addScore(String name, int score) throws IOException {
		Score sc = new Score(name, score);
		if (theScores.size() < 10) {
			theScores.add(sc);
			Collections.sort(theScores);
		} else {
			theScores.add(sc);
			Collections.sort(theScores);
			theScores.remove(10);
		}
		saveScores();
	}

	public boolean allStopped() {
		boolean exit = true;
		for (int i = 0; i < theBalls.size() && exit; i++) {
			if (!theBalls.get(i).isStopped())
				exit = false;
		}
		return exit;
	}

	public List<Ball> gettheBalls() {
		return theBalls;
	}

	public String[][] gettheScores() {
		String[][] hall = new String[10][2];
		for (int i = 0; i < 10; i++) {
			hall[i][0] = "Empty";
			hall[i][1] = "Empty";
		}
		for (int i = 0; i < theScores.size(); i++) {
			hall[i][0] = theScores.get(i).getName();
			hall[i][1] = theScores.get(i).getScore() + "";
		}

		return hall;
	}

	public int getLevel() {
		return level;
	}
}
