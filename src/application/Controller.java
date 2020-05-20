package application;


import javafx.scene.shape.Rectangle;

public class Controller {
	// Variables
	public static final int MOVE = Tetris.MOVE;
	public static final int SIZE = Tetris.SIZE;
	public static int X_MAX = Tetris.X_MAX;
	public static int Y_MAX = Tetris.Y_MAX;
	public static int[][] MESH_GRID = Tetris.MESH_GRID;

	// Moves the Form to the right by 1.
	public static void MoveRight(Form form) {
		if (((form.a.getX() + MOVE) <= (X_MAX - SIZE)) && 
			((form.b.getX() + MOVE) <= (X_MAX - SIZE)) && 
			((form.c.getX() + MOVE) <= (X_MAX - SIZE)) && 
			((form.d.getX() + MOVE) <= (X_MAX - SIZE))) {
			
			int moveA = MESH_GRID[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
			int moveB = MESH_GRID[((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
			int moveC = MESH_GRID[((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
			int moveD = MESH_GRID[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
			
			if ((moveA == 0) && 
				(moveA == moveB) && 
				(moveB == moveC) && 
				(moveC == moveD)) {
				
				form.a.setX(form.a.getX() + MOVE);
				form.b.setX(form.b.getX() + MOVE);
				form.c.setX(form.c.getX() + MOVE);
				form.d.setX(form.d.getX() + MOVE);
			}
		}
	}

	// Moves the Form to the left by 1.
	public static void MoveLeft(Form form) {
		if (((form.a.getX() - MOVE) >= 0) &&
			((form.b.getX() - MOVE) >= 0) && 
			((form.c.getX() - MOVE) >= 0) && 
			((form.d.getX() - MOVE >= 0))) {
			
			int moveA = MESH_GRID[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
			int moveB = MESH_GRID[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
			int moveC = MESH_GRID[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
			int moveD = MESH_GRID[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
			
			if ((moveA == 0) && 
				(moveA == moveB) && 
				(moveB == moveC) && 
				(moveC == moveD)) {
				
				form.a.setX(form.a.getX() - MOVE);
				form.b.setX(form.b.getX() - MOVE);
				form.c.setX(form.c.getX() - MOVE);
				form.d.setX(form.d.getX() - MOVE);
			}
		}
	}

	// Returns a new Form (as 4 rectangles) with name & color.
	public static Form makeRect() {
		int block = (int) (Math.random() * 100);
		String name;
		Rectangle a = new Rectangle(SIZE - 1, SIZE - 1);
		Rectangle b = new Rectangle(SIZE - 1, SIZE - 1);
		Rectangle c = new Rectangle(SIZE - 1, SIZE - 1);
		Rectangle d = new Rectangle(SIZE - 1, SIZE - 1);
		
		if (block < 15) { 
			a.setX((X_MAX / 2) - SIZE);
			b.setX((X_MAX / 2) - SIZE);
			b.setY(SIZE);
			c.setX(X_MAX / 2);
			c.setY(SIZE);
			d.setX((X_MAX / 2) + SIZE);
			d.setY(SIZE);
			name = "j";
		} else if (block < 30) { 
			a.setX((X_MAX / 2) + SIZE);
			b.setX((X_MAX / 2) - SIZE);
			b.setY(SIZE);
			c.setX(X_MAX / 2);
			c.setY(SIZE);
			d.setX((X_MAX / 2) + SIZE);
			d.setY(SIZE);
			name = "l";
		} else if (block < 45) { 
			a.setX((X_MAX / 2) - SIZE);
			b.setX(X_MAX / 2);
			c.setX((X_MAX / 2) - SIZE);
			c.setY(SIZE);
			d.setX(X_MAX / 2);
			d.setY(SIZE);
			name = "o";
		} else if (block < 60) { 
			a.setX((X_MAX / 2) + SIZE);
			b.setX(X_MAX / 2);
			c.setX(X_MAX / 2);
			c.setY(SIZE);
			d.setX((X_MAX / 2) - SIZE);
			d.setY(SIZE);
			name = "s";
		} else if (block < 75) { 
			a.setX((X_MAX / 2) - SIZE);
			b.setX(X_MAX / 2);
			c.setX(X_MAX / 2);
			c.setY(SIZE);
			d.setX((X_MAX / 2) + SIZE);
			name = "t";
		} else if (block < 90) { 
			a.setX((X_MAX / 2) + SIZE);
			b.setX(X_MAX / 2);
			c.setX((X_MAX / 2) + SIZE);
			c.setY(SIZE);
			d.setX((X_MAX / 2) + (SIZE + SIZE));
			d.setY(SIZE);
			name = "z";
		} else { 
			a.setX((X_MAX / 2) - 2 * SIZE);
			b.setX((X_MAX / 2) - SIZE);
			c.setX(X_MAX / 2);
			d.setX((X_MAX / 2) + SIZE);
			name = "i";
		}
		
		return new Form(a, b, c, d, name);
	}
}