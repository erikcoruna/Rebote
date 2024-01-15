package domain;

import java.awt.Point;

public class Basket  extends GameScore{
	public Point coordinates;
	public int value;

	public Basket(Player author, int value) {
		super(author);
		this.value = value;
	}
	
	public Basket(Player author, int value, Point coordinates) {
		super(author);
		this.value = value;
		this.coordinates = coordinates;
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
}
