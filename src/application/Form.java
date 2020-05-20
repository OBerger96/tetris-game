package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Form {
	// Variables
	Rectangle a; // Rectangle up.						a
	Rectangle b; // Rectangle left.					b	d	c
	Rectangle c; // Rectangle right.
	Rectangle d; // Rectangle down.
	
	Color color; // Form's color.
	
	private String name; // Form's name.
	
	public int form = 1; // Defines the Form's form between 1 to 4.

	// CTOR without Form's name.
	public Form(Rectangle otherA, Rectangle otherB, Rectangle otherC, Rectangle otherD) {
		this.a = otherA;
		this.b = otherB;
		this.c = otherC;
		this.d = otherD;
	}

	// CTOR with Form's name.
	public Form(Rectangle otherA, Rectangle otherB, Rectangle otherC, Rectangle otherD, String otherName) {
		this.a = otherA;
		this.b = otherB;
		this.c = otherC;
		this.d = otherD;
		
		this.name = otherName;
		
		// Sets color of the stones.
		switch (otherName) {
		case "j":
			color = Color.FORESTGREEN; break;
		case "l":
			color = Color.DARKBLUE; break;
		case "o":
			color = Color.ORANGERED; break;
		case "s":
			color = Color.DARKRED; break;
		case "t":
			color = Color.LIME; break;
		case "z":
			color = Color.YELLOW; break;
		case "i":
			color = Color.DEEPPINK; break;
		}
		
		// Fills each Form's rectangle with the specific color to the form.
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}

	// Returns Form's name.
	public String getName() { return name; }

	// Changes form of block (After moving it to the left-side or to the right-side).
	public void changeForm() {
		if (this.form != 4) { this.form++; } 
		else { this.form = 1; }
	}
}