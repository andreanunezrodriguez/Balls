package threads;

import javafx.application.Platform;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import model.Ball;
import ui.BallController;

public class BallThread extends Thread{

	private Ball Ball;

	private BallController ballControl;

	private Circle relleno;

	private Arc ballss;
	

	private boolean active;
		

	public BallThread(BallController ballControl, Circle relleno, Arc ballss, Ball ps) {
		this.ballControl = ballControl;
		this.Ball = ps;
		this.relleno = relleno;
		this.ballss = ballss;
		active = !ps.isStopped();
	}
	
	@Override
	public void run() {
		while(active) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ballControl.moveBall(relleno, ballss, Ball);
				}
			});
			
			try {
				sleep(Ball.getWaitT());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!ballControl.getGameBackGround().getChildren().contains(ballss)) {
				Ball.setStopped(true);
				deactivate();
			}
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(ballControl.getGame().allStopped())
						ballControl.theGame();
				}
			});
			
		}
	}
	

	public void deactivate() {
		active = false;
	}

	public Arc getballss() {
		return ballss;
	}

	public boolean isActive() {
		return active;
	}

	public Ball getTheBola() {
		return Ball;
	}
	
	public Circle getrelleno() {
		return relleno;
	}
}
