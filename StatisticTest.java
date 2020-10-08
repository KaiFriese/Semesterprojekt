package tests;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import nonServlet.Car;
import nonServlet.Statistik;

public class StatisticTest {

	@Test
	public void addTest() {
		Statistik stats = new Statistik(10);
		Date d0 = new Date();
		String a = "enter,0,"+new Timestamp(d0.getTime())+",-,-,4:3,4,Bus0";
		Car bu0 = new Car(4, d0);
		Date d1 = new Date();
		String b = "enter,1,"+new Timestamp(d1.getTime())+",-,-,0:3,3,PkW1";
		Car pk0 = new Car(3, d1);
		Date d2 = new Date();
		String c = "enter,2,"+new Timestamp(d2.getTime())+",-,-,0:2,2,Ro2";
		Car ro0 = new Car(2, d2);

		assertEquals(stats.neuerEintrag(a, bu0),a);
		assertEquals(stats.neuerEintrag(b, pk0),b);
		assertEquals(stats.neuerEintrag(c, ro0),c);
		assertEquals(stats.getDrin(), 3);
		assertEquals(stats.verteilungGes(),"0,0,1,1,1,0");
		assertEquals(stats.verteilungAkt(),"0,0,1,1,1,0,7");

		Date d4 = new Date();

		String dv = "leave,0,"+new Timestamp(d4.getTime())+",-,-,4:3,4,Bus0";
		long du = stats.getParkdauer(a, dv);
		String sdf = new SimpleDateFormat("SS:mm:ss").format(du);
		String[] sdfa = sdf.split(":");
		int time = Integer.parseInt(sdfa[2]) + Integer.parseInt(sdfa[1])*60 + Integer.parseInt(sdfa[0])*3600; 
		double price = time * 0.005;
		String dn = "leave,0,"+new Timestamp(d4.getTime())+","+time+","+price+",4:3,4,Bus0";

		assertEquals(stats.neuerEintrag(dv, bu0),dn);
		assertEquals(stats.getDrin(), 2);
		assertEquals(stats.verteilungGes(),"0,0,1,1,1,0");
		assertEquals(stats.verteilungAkt(),"0,0,1,1,0,0,8");
	}
}
