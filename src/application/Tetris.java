package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {
	// variables
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	
	public static int X_MAX = SIZE * 18;
	public static int Y_MAX = SIZE * 24;
	public static int score = 0;
	
	public static int[][] MESH_GRID = new int[X_MAX / SIZE][Y_MAX / SIZE];

	private static Pane group = new Pane();
	
	private static Form objForm = null;
	private static Form nextObjForm = Controller.makeRect();
	
	private static Scene scene = new Scene(group, X_MAX + 150, Y_MAX);
	
	private static int top = 0;
	private static int numOfLines = 0;

	private static boolean isGameOn = true;

	// Main.
	public static void main(String[] args) {
		launch(args);
	}

	// Let's start playing Tetris!
	@Override
	public void start(Stage stage) throws Exception {
		for (int[] a : MESH_GRID) {
			Arrays.fill(a, 0);
		}
		
		// Creates a line to separate from the game and the 'score-text' & the 'level-text'
		Line line = new Line(X_MAX, 0, X_MAX, Y_MAX);
		
		// Creates a score-board-text on the middle-right side.
		Text scoreText = new Text("Score: ");
		scoreText.setStyle("-fx-font: 20 arial;");
		scoreText.setY(70);
		scoreText.setX(X_MAX + 5);
		scoreText.setFill(Color.DARKBLUE);
		
		// Creates a level-text on the middle-right side.
		Text levelText = new Text("Lines: ");
		levelText.setStyle("-fx-font: 20 arial;");
		levelText.setY(130);
		levelText.setX(X_MAX + 5);
		levelText.setFill(Color.DARKBLUE);
		
		group.getChildren().addAll(scoreText, line, levelText);

		Form formA = nextObjForm;
		
		group.getChildren().addAll(formA.a, formA.b, formA.c, formA.d);
		
		moveOnKeyPress(formA);
		
		objForm = formA;
		nextObjForm = Controller.makeRect();
		
		scene.setFill(Color.DEEPSKYBLUE);
		
		// Sets the game-stage.
		stage.setScene(scene);
		stage.setTitle("T E T R ! S ");
		stage.show();
		stage.setResizable(false);

		// Sets a timer.
		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if ((objForm.a.getY() == 0) || 
							(objForm.b.getY() == 0) || 
							(objForm.c.getY() == 0) || 
							(objForm.d.getY() == 0)) { top++; }
						else { top = 0; }

						// GAME OVER.
						if (top == 2) {
							group.getChildren().retainAll();
							
							// Writes 'Game Over'.
							Text gameOver = new Text("GAME OVER");
							gameOver.setFill(Color.RED);
							gameOver.setStyle("-fx-font: 80 boulder;");
							gameOver.setY(270);
							gameOver.setX(12);
							
							// Writes the final score. 
							scoreText.setStyle("-fx-font: 50 boulder;");
							scoreText.setY(340);
							scoreText.setX(12);
							scoreText.setFill(Color.RED);
							
							group.getChildren().addAll(gameOver, scoreText);
							isGameOn = false;
						}
						
						// Exit.
						if (top == 15) { System.exit(0); }

						// KEEP PLAYING.
						if (isGameOn == true) {
							MoveDown(objForm);
							scoreText.setText("Score: " + Integer.toString(score));
							levelText.setText("Lines: " + Integer.toString(numOfLines));
						}
					}
				});
			}
		};
		
		fall.schedule(task, 0, 300); 
	}

	// Moves the Form base on the key(UP, DOWN, LEFT, RIGHT) the player pressed.
	private void moveOnKeyPress(Form form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT: Controller.MoveRight(form); break;
				case DOWN: MoveDown(form); score++; break;
				case LEFT: Controller.MoveLeft(form); break;
				case UP: MoveTurn(form); break;
				default: break;
				}
			}
		});
	}

	// Moves (if possible) the form to a new form (moves to the left/right).
	private void MoveTurn(Form form) {
		int formNumber = form.form;
		Rectangle rectA = form.a;
		Rectangle rectB = form.b;
		Rectangle rectC = form.c;
		Rectangle rectD = form.d;
		switch (form.getName()) {
		case "j":
			if ((formNumber == 1) && checkBlock(rectA, 1, -1) && checkBlock(rectC, -1, -1) && checkBlock(rectD, -2, -2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 2) && checkBlock(rectA, -1, -1) && checkBlock(rectC, -1, 1) && checkBlock(rectD, -2, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 3) && checkBlock(rectA, -1, 1) && checkBlock(rectC, 1, 1) && checkBlock(rectD, 2, 2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 4) && checkBlock(rectA, 1, 1) && checkBlock(rectC, 1, -1) && checkBlock(rectD, 2, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "l":
			if ((formNumber == 1) && checkBlock(rectA, 1, -1) && checkBlock(rectC, 1, 1) && checkBlock(rectB, 2, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveRight(form.b);
				form.changeForm();
				break;
			}
			if ((formNumber == 2) && checkBlock(rectA, -1, -1) && checkBlock(rectB, 2, -2) && checkBlock(rectC, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveRight(form.b);
				MoveRight(form.b);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if ((formNumber == 3) && checkBlock(rectA, -1, 1) && checkBlock(rectC, -1, -1) && checkBlock(rectB, -2, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveLeft(form.b);
				form.changeForm();
				break;
			}
			if ((formNumber == 4) && checkBlock(rectA, 1, 1) && checkBlock(rectB, -2, 2) && checkBlock(rectC, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveLeft(form.b);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			break;
		case "o":
			break;
		case "s":
			if ((formNumber == 1) && checkBlock(rectA, -1, -1) && checkBlock(rectC, -1, 1) && checkBlock(rectD, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 2) && checkBlock(rectA, 1, 1) && checkBlock(rectC, 1, -1) && checkBlock(rectD, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 3) && checkBlock(rectA, -1, -1) && checkBlock(rectC, -1, 1) && checkBlock(rectD, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 4) && checkBlock(rectA, 1, 1) && checkBlock(rectC, 1, -1) && checkBlock(rectD, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "t":
			if ((formNumber == 1) && checkBlock(rectA, 1, 1) && checkBlock(rectD, -1, -1) && checkBlock(rectC, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			if ((formNumber == 2) && checkBlock(rectA, 1, -1) && checkBlock(rectD, -1, 1) && checkBlock(rectC, 1, 1)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.c);
				MoveRight(form.c);
				form.changeForm();
				break;
			}
			if ((formNumber == 3) && checkBlock(rectA, -1, -1) && checkBlock(rectD, 1, 1) && checkBlock(rectC, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if ((formNumber == 4) && checkBlock(rectA, -1, 1) && checkBlock(rectD, 1, -1) && checkBlock(rectC, -1, -1)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.c);
				MoveLeft(form.c);
				form.changeForm();
				break;
			}
			break;
		case "z":
			if ((formNumber == 1) && checkBlock(rectB, 1, 1) && checkBlock(rectC, -1, 1) && checkBlock(rectD, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 2) && checkBlock(rectB, -1, -1) && checkBlock(rectC, 1, -1) && checkBlock(rectD, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 3) && checkBlock(rectB, 1, 1) && checkBlock(rectC, -1, 1) && checkBlock(rectD, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if ((formNumber == 4) && checkBlock(rectB, -1, -1) && checkBlock(rectC, 1, -1) && checkBlock(rectD, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		case "i":
			if (formNumber == 1 && checkBlock(rectA, 2, 2) && checkBlock(rectB, 1, 1) && checkBlock(rectD, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (formNumber == 2 && checkBlock(rectA, -2, -2) && checkBlock(rectB, -1, -1) && checkBlock(rectD, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (formNumber == 3 && checkBlock(rectA, 2, 2) && checkBlock(rectB, 1, 1) && checkBlock(rectD, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (formNumber == 4 && checkBlock(rectA, -2, -2) && checkBlock(rectB, -1, -1) && checkBlock(rectD, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		}
	}

	// Removes a row after it gets full.
	private void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		
		int full = 0;
		
		for (int i = 0; i < MESH_GRID[0].length; i++) {
			for (int j = 0; j < MESH_GRID.length; j++) {
				if (MESH_GRID[j][i] == 1) { full++; }
			}
			
			if (full == MESH_GRID.length) { lines.add(i + lines.size()); }
			
			full = 0;
		}
		
		if (lines.size() > 0)
			do {
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle) { rects.add(node); }
				}
				
				score += 50;
				numOfLines++;

				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
				
					if (a.getY() == lines.get(0) * SIZE) {
						MESH_GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						pane.getChildren().remove(node);
					} 
					else { newrects.add(node); }
				}

				for (Node node : newrects) {
					Rectangle a = (Rectangle) node;
					
					if (a.getY() < (lines.get(0) * SIZE)) {
						MESH_GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						a.setY(a.getY() + SIZE);
					}
				}
				
				lines.remove(0);
				rects.clear();
				newrects.clear();
				
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle) { rects.add(node); }
				}
				
				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
				
					try {
						MESH_GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
					} catch (ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
				}
				
				rects.clear();
			} while (lines.size() > 0);
	}

	// Moves the rectangle down.
	private void MoveDown(Rectangle rect) {
		if (rect.getY() + MOVE < Y_MAX)
			rect.setY(rect.getY() + MOVE);

	}

	// Moves the rectangle to the right.
	private void MoveRight(Rectangle rect) {
		if (rect.getX() + MOVE <= X_MAX - SIZE)
			rect.setX(rect.getX() + MOVE);
	}

	// Moves the rectangle to the left.
	private void MoveLeft(Rectangle rect) {
		if (rect.getX() - MOVE >= 0)
			rect.setX(rect.getX() - MOVE);
	}

	// Moves the rectangle up.
	private void MoveUp(Rectangle rect) {
		if (rect.getY() - MOVE > 0)
			rect.setY(rect.getY() - MOVE);
	}

	// Moves the Form down.
	private void MoveDown(Form form) {
		if ((form.a.getY() == (Y_MAX - SIZE)) || moveA(form) || 
			(form.b.getY() == (Y_MAX - SIZE)) || moveB(form) ||
			(form.c.getY() == (Y_MAX - SIZE)) || moveC(form) ||
		    (form.d.getY() == (Y_MAX - SIZE)) || moveD(form)) {
		    	
			MESH_GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			MESH_GRID[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
			MESH_GRID[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
			MESH_GRID[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
			
			RemoveRows(group);

			Form formA = nextObjForm;
			nextObjForm = Controller.makeRect();
			objForm = formA;
			
			group.getChildren().addAll(formA.a, formA.b, formA.c, formA.d);
			moveOnKeyPress(formA);
		}

		if (((form.a.getY() + MOVE) < Y_MAX) && 
			((form.b.getY() + MOVE) < Y_MAX) && 
			((form.c.getY() + MOVE) < Y_MAX) && 
			((form.d.getY() + MOVE) < Y_MAX)) {
			
			int moveRecA = MESH_GRID[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
			int moveRecB = MESH_GRID[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
			int moveRecC = MESH_GRID[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
			int moveRecD = MESH_GRID[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];

			if ((moveRecA == 0) && 
				(moveRecA == moveRecB) && 
				(moveRecB == moveRecC) && 
				(moveRecC == moveRecD)) {
				
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
			}
		}
	}
	
	// Returns true if rectangleA can be moved.
	private boolean moveA(Form form) { return (MESH_GRID[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1); }

	// Returns true if rectangleB can be moved.
	private boolean moveB(Form form) { return (MESH_GRID[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1); }

	// Returns true if rectangleC can be moved.
	private boolean moveC(Form form) { return (MESH_GRID[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1); }

	// Returns true if rectangleD can be moved.
	private boolean moveD(Form form) { return (MESH_GRID[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1); } 

	private boolean checkBlock(Rectangle rect, int x, int y) {
		boolean xBlock = false;
		boolean yBlock = false;
		
		if (x >= 0) { xBlock = (rect.getX() + x * MOVE) <= (X_MAX - SIZE); }
		if (x < 0) { xBlock = (rect.getX() + x * MOVE) >= 0; }
		if (y >= 0) { yBlock = (rect.getY() - y * MOVE) > 0; }
		if (y < 0) { yBlock = (rect.getY() + y * MOVE) < Y_MAX; }
		
		return (xBlock && yBlock && (MESH_GRID[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0));
	}

}