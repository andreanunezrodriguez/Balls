package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.Ball;
//import threads.MouthAnimationThread;
import threads.BallThread;

public class BallController {

	private HostServices hostServices ;

	private Stage stage;

	private GridPane scoreGrid;

	private Game theGame;

	@FXML
	private Label levelLabel;

    @FXML
    private Pane gameBackGround;
    @FXML
    private MenuBar menuOptions;
    @FXML
    private Label bounceCounter;
    private List<BallThread> threads;

    @FXML
    public void initialize() {
    	scoreGrid = null;
    	threads = new ArrayList<BallThread>();
    	try {
			theGame = new Game();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
    	int counter = 1;
    	for(int i = 0; i < menuOptions.getMenus().size(); i++) {
    		for(int j = 0 + counter; j < menuOptions.getMenus().get(i).getItems().size();j++) {
    			String a = menuOptions.getMenus().get(i).getItems().get(j).getText();
    			menuOptions.getMenus().get(i).getItems().get(j).setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						if(a.equals("Exit game")) {
							try {
								onCloseRequest();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							System.exit(0);
						}
						else if(a.equals("Save game")) {
							try {
								theGame.saveGame("Data/savedGame.txt");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else if(a.equals("Scores")) {
							showAllTheScores();
						}
						else if(a.equals("About")) {
							aboutTheGame();
						}
					}
    				
    			});
    		}
    		counter--;	
    	}
    }

    @FXML
    void levelOne(ActionEvent event) throws IOException {
    	bounceCounter.setText("BOUNCES: 0");
    	bounceCounter.setFont(Font.font("Marker Felt",50));
    	gameBackGround.getChildren().clear();
    	theGame.gettheBalls().clear();
    	theGame.loadGame("Data/Level1.txt");
    	levelLabel.setText("Level " + theGame.getLevel());
    	levelLabel.setFont(Font.font("Marker Felt",50));
    	levelLabel.setTextFill(Color.web("#0076a3"));
    	startGame();
    }


    @FXML
    void levelTwo(ActionEvent event) throws IOException {
    	bounceCounter.setText("BOUNCES: 0");
    	gameBackGround.getChildren().clear();
    	theGame.gettheBalls().clear();
    	theGame.loadGame("Data/Level2.txt");
    	levelLabel.setText("Level " + theGame.getLevel());
    	levelLabel.setFont(Font.font("Marker Felt",50));
    	levelLabel.setTextFill(Color.web("#0076a3"));
    	startGame();
    }

    @FXML
    void levelZero(ActionEvent event) throws IOException {
    	bounceCounter.setText("BOUNCES: 0");
    	bounceCounter.setFont(Font.font("Times New Roman"));
    	gameBackGround.getChildren().clear();
    	theGame.gettheBalls().clear();
    	theGame.loadGame("Data/Level0.txt");
    	levelLabel.setText("Level " + theGame.getLevel());
    	levelLabel.setFont(Font.font("Marker Felt",50));
    	levelLabel.setTextFill(Color.web("#0076a3"));
    	startGame();
    }

    @FXML
    void savedGame(ActionEvent event) throws IOException {
    	bounceCounter.setText("BOUNCES: 0");
    	gameBackGround.getChildren().clear();
    	theGame.gettheBalls().clear();
    	theGame.loadGame("Data/savedGame.txt");
    	levelLabel.setText("Level " + theGame.getLevel());
    	startGame();
    }

    public void startGame(){
    	for(int i = 0; i < theGame.gettheBalls().size(); i++) {
    		Ball b = theGame.gettheBalls().get(i);
    		if(!b.isStopped()) {
	    		Circle relleno = new Circle(b.getPosX()+5, b.getPosY() - b.getRadius()/2, 5, Color.SALMON);
	    		Arc theball = new Arc(b.getPosX(), b.getPosY(), b.getRadius(), b.getRadius(), 0, 360);
	    		theball.setType(ArcType.ROUND);
	    		theball.setFill(Color.SALMON);
	    		if(b.getDirection().equals(Ball.DOWN)) {
	    			theball.setRotate(-270);
	    			relleno.setCenterX(b.getPosX() + b.getRadius()/2);
	    			relleno.setCenterY(b.getPosY() + b.getRadius()/2);
	    		}
	    		else if(b.getDirection().equals(Ball.UP)) {
	    			theball.setRotate(-90);
	    			relleno.setCenterX(b.getPosX() + b.getRadius()/2);
	    			relleno.setCenterY(b.getPosY() - b.getRadius()/2);
	    		}
	    		else if(b.getDirection().equals(Ball.LEFT)) {
	    			theball.setRotate(-180);
	    			relleno.setCenterX(b.getPosX() - 5);
	    		}
				gameBackGround.getChildren().add(theball);
				gameBackGround.getChildren().add(relleno);
				theball.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
					@Override
					public void handle(MouseEvent e) {
						gameBackGround.getChildren().remove(relleno);
						gameBackGround.getChildren().remove(theball);
					}
					
				});
				relleno.setOnMouseClicked(theball.getOnMouseClicked());
				BallThread pt = new BallThread(this, relleno, theball, b);
				threads.add(pt);
				pt.start();
				
    		}
    		int times = Integer.parseInt(bounceCounter.getText().split(" ")[1])+b.getBounces();
			bounceCounter.setText("Bounces: " + times);
			bounceCounter.setFont(Font.font("Marker Felt",50));
    	}
    }

    public void theGame() {
    	gameBackGround.getChildren().clear();
    	Label mensaje= new Label();
    	mensaje.setText("FELICIDADES, INGRESA TU NOMBRE");
    	mensaje.setFont(Font.font("Marker Felt",20));
    	mensaje.setTextFill(Color.PLUM);
    	mensaje.setLayoutX(450);
    	mensaje.setLayoutY(450);
    	TextField nameTF = new TextField();
		nameTF.setPromptText("FELICIDADES! Ingresa tu nombre");
		nameTF.setLayoutX(500);
		nameTF.setLayoutY(500);
		Button submit = new Button("Ingresa tu puntaje");
		submit.setFont(Font.font("Marker Felt",20));
		submit.setTextFill(Color.CRIMSON);
		submit.setLayoutX(500);
		submit.setLayoutY(550);
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int bounces = Integer.parseInt(bounceCounter.getText().split(" ")[1]);
				try {
					theGame.addScore(nameTF.getText(), bounces);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				gameBackGround.getChildren().remove(nameTF);
				gameBackGround.getChildren().remove(submit);
				gameBackGround.getChildren().remove(mensaje);
				showAllTheScores();
				Button tocontinuee = new Button("CONTINUAR");
				tocontinuee.setFont(Font.font("Marker Felt",20));
				tocontinuee.setTextFill(Color.PURPLE);
				tocontinuee.setMaxWidth(500);
				tocontinuee.setMaxHeight(500);
				tocontinuee.setLayoutX(900);
				tocontinuee.setLayoutY(450);
				tocontinuee.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(theGame.getLevel() == 0) {
							try {
								levelOne(null);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else if(theGame.getLevel() == 1) {
							try {
								levelTwo(null);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else {
							
						}
					}
					
				});
				gameBackGround.getChildren().add(tocontinuee);
			}
			
		});
		gameBackGround.getChildren().add(nameTF);
		gameBackGround.getChildren().add(submit);
		gameBackGround.getChildren().add(mensaje);
		
    }
    
	public void moveBall(Circle relleno, Arc theball, Ball b) {
		
	    if(b.getDirection().equals(Ball.LEFT)) {
			relleno.setCenterX(relleno.getCenterX()-5);
			theball.setCenterX(theball.getCenterX()-5);
			b.setPosX(theball.getCenterX());
			if(theball.getCenterX() - theball.getRadiusX()<= 0 || ballIsTouchingAnotherBall(theball)) {
				theball.setRotate(theball.getRotate() + 180);
				relleno.setCenterX(theball.getCenterX() + 5);
				relleno.setCenterY(theball.getCenterY() - b.getRadius()/2);
				int times = Integer.parseInt(bounceCounter.getText().split(" ")[1])+1;
				bounceCounter.setText("Bounces: " + times);
				b.setBounces(b.getBounces()+1);
				b.setDirection(Ball.RIGHT);
				theball.setCenterX(theball.getCenterX() + 5);
				relleno.setCenterX(relleno.getCenterX() + 5);
			}
		}
		else if(b.getDirection().equals(Ball.RIGHT)) {
			relleno.setCenterX(relleno.getCenterX()+5);
			theball.setCenterX(theball.getCenterX()+5);
			b.setPosX(theball.getCenterX());
			if(theball.getCenterX() >= (stage.getScene().getWidth() - theball.getRadiusX()) || ballIsTouchingAnotherBall(theball)) {
				theball.setRotate(-180);
				relleno.setCenterX(theball.getCenterX() - 5);
				relleno.setCenterY(theball.getCenterY() - b.getRadius()/2);
				int times = Integer.parseInt(bounceCounter.getText().split(" ")[1])+1;
				bounceCounter.setText("Bounces: " + times);
				b.setBounces(b.getBounces()+1);
				b.setDirection(Ball.LEFT);
				theball.setCenterX(theball.getCenterX() - 5);
				relleno.setCenterX(relleno.getCenterX() - 5);
			}
		}
		else if(b.getDirection().equals(Ball.DOWN)) {
			relleno.setCenterY(relleno.getCenterY()+5);
			theball.setCenterY(theball.getCenterY()+5);
			b.setPosY(theball.getCenterY());
			if(theball.getCenterY() >= (stage.getScene().getHeight() - (theball.getRadiusX()*2)) || ballIsTouchingAnotherBall(theball)) {
				theball.setRotate(theball.getRotate()+180);
				relleno.setCenterX(theball.getCenterX() + b.getRadius()/2);
				relleno.setCenterY(theball.getCenterY() - b.getRadius()/2);
				int times = Integer.parseInt(bounceCounter.getText().split(" ")[1])+1;
				bounceCounter.setText("Bounces: " + times);
				b.setBounces(b.getBounces()+1);
				b.setDirection(Ball.UP);
				theball.setCenterY(theball.getCenterY() - 5);
				relleno.setCenterY(relleno.getCenterY() - 5);
			}
		}
		else {
			relleno.setCenterY(relleno.getCenterY()-5);
			theball.setCenterY(theball.getCenterY()-5);
			b.setPosY(theball.getCenterY());
			if(theball.getCenterY() - theball.getRadiusY()<=0 || ballIsTouchingAnotherBall(theball)) {
				theball.setRotate(theball.getRotate()-180);
				relleno.setCenterX(theball.getCenterX() + b.getRadius()/2);
				relleno.setCenterY(theball.getCenterY() + b.getRadius()/2);
				int times = Integer.parseInt(bounceCounter.getText().split(" ")[1])+1;
				bounceCounter.setText("Bounces: " + times);
				b.setBounces(b.getBounces()+1);
				b.setDirection(Ball.DOWN);
				theball.setCenterY(theball.getCenterY() + 5);
				relleno.setCenterY(relleno.getCenterY() + 5);
			}
		}
    }

	public boolean ballIsTouchingAnotherBall(Arc theball) {
		boolean exit = false;
		for(int i = 0; i < threads.size(); i++) {
			Arc otherBall = threads.get(i).getballss();
			Circle relleno = threads.get(i).getrelleno();
			double cx1 = otherBall.getCenterX();
			double cy1 = otherBall.getCenterY();
			double r1 =  otherBall.getRadiusX();
			double cx2 = theball.getCenterX();
			double cy2 = theball.getCenterY();
			double r2 = theball.getRadiusX();
			double distance = Math.sqrt( (cx1 - cx2)*(cx1 - cx2) + (cy1 - cy2)*(cy1 - cy2) );
			if(distance <= r1+r2 && threads.get(i).isActive() && ! otherBall.equals(theball)) {
				if(threads.get(i).getTheBola().getDirection().equals(Ball.LEFT)) {
					otherBall.setRotate(otherBall.getRotate() + 180);
					relleno.setCenterX(otherBall.getCenterX() + 5);
					relleno.setCenterY(otherBall.getCenterY() -  threads.get(i).getTheBola().getRadius()/2);
					threads.get(i).getTheBola().setDirection(Ball.RIGHT);
					otherBall.setCenterX(otherBall.getCenterX() + 5);
					relleno.setCenterX(relleno.getCenterX() + 5);
				}
				else if(threads.get(i).getTheBola().getDirection().equals(Ball.RIGHT)) {
					otherBall.setRotate(-180);
					relleno.setCenterX(otherBall.getCenterX() - 5);
					relleno.setCenterY(otherBall.getCenterY() -  threads.get(i).getTheBola().getRadius()/2);
					threads.get(i).getTheBola().setDirection(Ball.LEFT);
					otherBall.setCenterX(otherBall.getCenterX() - 5);
					relleno.setCenterX(relleno.getCenterX() - 5);
				}
				else if(threads.get(i).getTheBola().getDirection().equals(Ball.DOWN)) {
					otherBall.setRotate(otherBall.getRotate()+180);
					relleno.setCenterX(otherBall.getCenterX() + threads.get(i).getTheBola().getRadius()/2);
					relleno.setCenterY(otherBall.getCenterY() - threads.get(i).getTheBola().getRadius()/2);
					threads.get(i).getTheBola().setDirection(Ball.UP);
					otherBall.setCenterY(otherBall.getCenterY() - 5);
					relleno.setCenterY(relleno.getCenterY() - 5);
				}
				else if(threads.get(i).getTheBola().getDirection().equals(Ball.UP)) {
					otherBall.setRotate(otherBall.getRotate()-180);
					relleno.setCenterX(otherBall.getCenterX() + threads.get(i).getTheBola().getRadius()/2);
					relleno.setCenterY(otherBall.getCenterY() + threads.get(i).getTheBola().getRadius()/2);
					threads.get(i).getTheBola().setDirection(Ball.DOWN);
					otherBall.setCenterY(otherBall.getCenterY() + 5);
					relleno.setCenterY(relleno.getCenterY() + 5);
				}
				exit = true;
			}
		}
		return exit;
	}
	

    public void showAllTheScores() {
    	GridPane grid = new GridPane();
    	String[][] score = theGame.gettheScores();
    	for(int i = 0; i < score.length; i++) {
    		for(int j = 0; j < score[i].length; j++) {
    			Label lb = new Label(score[i][j]);
    			grid.add(lb, j+1, i);
    			
    			GridPane.setHalignment(lb, HPos.CENTER);
    		}
    	}
    	for(int j = 0; j < 10; j++) {
    		Label lb = new Label(String.valueOf(j+1));
    		lb.setFont(Font.font("Marker Felt", 15));
    		lb.setTextFill(Color.MEDIUMSPRINGGREEN);
    		grid.add(lb, 0, j);
    		GridPane.setHalignment(lb, HPos.CENTER);
    	}
    	ColumnConstraints column = new ColumnConstraints();
    	column.setPercentWidth(30);
    	grid.getColumnConstraints().add(column);

    	column = new ColumnConstraints();
    	column.setPercentWidth(30);
    	grid.getColumnConstraints().add(column);
    	grid.getColumnConstraints().add(column);
    	
    	grid.setMaxHeight(500);
    	grid.setPrefWidth(500);
    	grid.setAlignment(Pos.CENTER);
    	grid.setGridLinesVisible(true);
    	grid.setLayoutX(700);
    	grid.setLayoutY(175);
    	grid.getStyleClass().add("mygridStyle");
    	Button hide = new Button("Hide");
    	hide.setFont(Font.font("Marker Felt"));
    	hide.setTextFill(Color.ORCHID);
    	hide.setMaxSize(300, 300);
    	hide.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				gameBackGround.getChildren().remove(grid);
			}
    		
    	});
    	grid.add(hide, 1, 10);
    	GridPane.setHalignment(hide, HPos.CENTER);
    	if(scoreGrid!=null)
    		gameBackGround.getChildren().remove(scoreGrid);
    	
    	gameBackGround.getChildren().add(grid);
    	scoreGrid = grid;
    }
    
    public void aboutTheGame() {
    	Hyperlink h = new Hyperlink("www.jijfjkd.com");
    	hostServices.showDocument(h.getText());
    }

    @FXML
    public void onCloseRequest() throws InterruptedException {
    	for (int i = 0; i < threads.size(); i++) {
			threads.get(i).deactivate();
			threads.get(i).join();
			
		}
    	System.out.println("El juego ha terminado");
    }

	public Pane getGameBackGround() {
		return gameBackGround;
	}

	public Game getGame() {
		return theGame;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}
}
