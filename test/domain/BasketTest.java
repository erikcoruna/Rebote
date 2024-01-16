package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Point;

import org.junit.Test;


//Hugo Rey Insausti_2024-01-16_18-49
//IAG Se ha utilizado para ver como ejemplo una implementacion en JUnit
public class BasketTest {
	
	@Test
	public void testConstructorValues() {
		Player player = new Player();
		int value = 1;
		
		Basket basket = new Basket(player, value);
		
		assertNotNull(basket);		
		assertEquals(player, basket.getAuthor());
		assertEquals(value, basket.getValue());
		assertNull(basket.getCoordinates());
	}
	
	@Test
	public void testConstructorValuesAndCoordinates() {
		Player player = new Player();
		int value = 1;
		Point coordinates = new Point();
		
		Basket basket = new Basket(player, value, coordinates);
		
		assertNotNull(basket);
		assertEquals(player, basket.getAuthor());
		assertEquals(value, basket.getValue());
		assertEquals(coordinates,basket.getCoordinates());
	}
	
	@Test
    public void testGetSetCoordinates() {
        Player player = new Player();
        int value = 1;
        Point coordinates = new Point();

        Basket basket = new Basket(player, value);

        assertNotNull(basket);
        basket.setCoordinates(coordinates);
        assertEquals(coordinates, basket.getCoordinates());
    }
	
	@Test
    public void testGetSetValue() {
        Player player = new Player();
        int value = 1;

        Basket basket = new Basket(player, value);

        assertNotNull(basket);
        
        int newValue = 10;
        basket.setValue(newValue);
        assertEquals(newValue, basket.getValue());
    }

}
