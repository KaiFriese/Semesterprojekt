package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nonServlet.Jahreseinnahmen;
import nonServlet.Minuteneinnahmen;
import nonServlet.Monatseinnahmen;
import nonServlet.Stundeneinnahmen;
import nonServlet.Tageseinnahmen;

public class EinnahmenTest {
	@Test
	void test() {
		List<String> log = new ArrayList();
		String a = "leave,-,2019-10-04 15:30:00,-,50.0,-,-,-";
		String b = "leave,-,2020-10-04 15:30:00,-,1.0,-,-,-";
		String c = "leave,-,2020-10-04 15:30:50,-,1.0,-,-,-";
		String d = "leave,-,2020-10-04 15:31:00,-,3.0,-,-,-";
		String e = "leave,-,2020-10-04 16:30:00,-,4.0,-,-,-";
		String f = "leave,-,2020-10-05 15:00:00,-,5.0,-,-,-";
		String g = "leave,-,2020-10-05 00:30:00,-,6.0,-,-,-";
		String h = "leave,-,2020-11-05 15:00:00,-,7.0,-,-,-";
		String i = "leave,-,2020-11-05 00:30:00,-,8.0,-,-,-";
		log.add(a);
		log.add(b);
		log.add(c);
		log.add(d);
		log.add(e);
		log.add(f);
		log.add(g);
		log.add(h);
		log.add(i);
		Jahreseinnahmen je1 = new Jahreseinnahmen(2019,log);
		Jahreseinnahmen je2 = new Jahreseinnahmen(2020,log);
		Monatseinnahmen me1 = new Monatseinnahmen(2020,10,log);
		Monatseinnahmen me2 = new Monatseinnahmen(2020,11,log);
		Tageseinnahmen te1 = new Tageseinnahmen(2020,10,4,log);
		Tageseinnahmen te2 = new Tageseinnahmen(2020,10,5,log);
		Stundeneinnahmen se1 = new Stundeneinnahmen(2020,10,4,15,log);
		Stundeneinnahmen se2 = new Stundeneinnahmen(2020,10,4,16,log);
		Minuteneinnahmen mie1 = new Minuteneinnahmen(2020,10,4,15,30,log);
		Minuteneinnahmen mie2 = new Minuteneinnahmen(2020,10,4,15,31,log);
		assertEquals(je1.einnahmen(), 50.0);
		assertEquals(je2.einnahmen(), 35.0);
		assertEquals(me1.einnahmen(), 20.0);
		assertEquals(me2.einnahmen(), 15.0);
		assertEquals(te1.einnahmen(), 9.0);
		assertEquals(te2.einnahmen(), 11.0);
		assertEquals(se1.einnahmen(), 5.0);
		assertEquals(se2.einnahmen(), 4.0);
		assertEquals(mie1.einnahmen(), 2.0);
		assertEquals(mie2.einnahmen(), 3.0);
	}
}
