package tests;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import nonServlet.Car;
import parkhaus.Parkhaus;

public class ParkhausTest {

	@Test
	public void reinRausTest() {
		Parkhaus parkhaus = new Parkhaus();
		
		Date date0 = new Date();
		String string0 = "enter,0,"+new Timestamp(date0.getTime())+",-,-,4:3,4,Bus0";
		Car auto0 = new Car(4, date0);
		assertEquals(parkhaus.Enter(auto0), string0);
		
		Date date1 = new Date();
		String string1 = "enter: error,-,"+new Timestamp(date1.getTime())+",-,-,-,-,-";
		Car auto1 = new Car(6, date1);
		assertEquals(parkhaus.Enter(auto1), string1);
		
		//Zeitpunkt, Parkdauer und Preis hängen von dem in der Methode erstellten 
		//Zeitpunkt ab und kann so nicht getestet werden
		String string2 = "leave,0,";
		String string3 = ",4:3,4,Bus0";
		String string4 = parkhaus.Leave(4, 3);
		assertEquals(string4.startsWith(string2) && string4.endsWith(string3), true);
	}
}
