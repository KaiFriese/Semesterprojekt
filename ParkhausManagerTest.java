package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import nonServlet.ParkhausManager;


public class ParkhausManagerTest {
	
	@Test
	void konstruktorTest() {
		ParkhausManager parkhausManager1 = new ParkhausManager();
		ParkhausManager parkhausManager2 = new ParkhausManager(25);
		ParkhausManager parkhausManager3 = new ParkhausManager(400);
		ParkhausManager parkhausManager4 = new ParkhausManager(3);
		ParkhausManager parkhausManager5 = new ParkhausManager(9);
		ParkhausManager parkhausManager6 = new ParkhausManager(0, 0);
		ParkhausManager parkhausManager7 = new ParkhausManager(5, 5);
		ParkhausManager parkhausManager8 = new ParkhausManager(0,0,0,0,0,0);
		ParkhausManager parkhausManager9 = new ParkhausManager(2,3,0,10,5,9);
		ParkhausManager parkhausManager10 = new ParkhausManager(15,30,20,215,80,40);
		//parkhausManager1
		assertEquals(parkhausManager1.getPlaetze().length,0);
		assertEquals(parkhausManager1.getPArt().length,0);
		//parkhausManager2
		assertEquals(parkhausManager2.getPlaetze().length,1);
		assertEquals(parkhausManager2.getPlaetze()[0].length,25);
		assertEquals(parkhausManager2.getPArt().length,1);
		assertEquals(parkhausManager2.getPArt()[0].length,25);
		int[][] platzArt = {{
			0,0,1,1,2,2,3,3,3,3,
			3,3,3,3,3,3,3,3,4,4,
			4,4,4,5,5
			}};
		for(int e = 0; e < platzArt.length; e++) {
			for(int p = 0; p < platzArt[e].length; p++) {
				assertEquals(parkhausManager2.getPArt()[e][p],platzArt[e][p]);
			}
		}
		//parkhausManager3
		assertEquals(parkhausManager3.getPlaetze().length,1);
		assertEquals(parkhausManager3.getPlaetze()[0].length,400);
		assertEquals(parkhausManager3.getPArt().length,1);
		assertEquals(parkhausManager3.getPArt()[0].length,400);
		int[][] platzArt2 = {{
			0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,2,2,2,2,2,
			2,2,2,2,2,2,2,2,2,2,
			2,2,2,2,2,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5,
			}};
		for(int e = 0; e < platzArt2.length; e++) {
			for(int p = 0; p < platzArt2[e].length; p++) {
				assertEquals(parkhausManager3.getPArt()[e][p],platzArt2[e][p]);
			}
		}
		//parkhausManager4
		assertEquals(parkhausManager4.getPlaetze().length,1);
		assertEquals(parkhausManager4.getPlaetze()[0].length,3);
		assertEquals(parkhausManager4.getPArt().length,1);
		assertEquals(parkhausManager4.getPArt()[0].length,3);
		int[][] platzArt3 = {{
			3,3,3
			}};
		for(int e = 0; e < platzArt3.length; e++) {
			for(int p = 0; p < platzArt3[e].length; p++) {
				assertEquals(parkhausManager4.getPArt()[e][p],platzArt3[e][p]);
			}
		}
		//pm5
		assertEquals(parkhausManager5.getPlaetze().length,1);
		assertEquals(parkhausManager5.getPlaetze()[0].length,9);
		assertEquals(parkhausManager5.getPArt().length,1);
		assertEquals(parkhausManager5.getPArt()[0].length,9);
		int[][] platzArt4 = {{
			0,1,2,3,3,3,3,3,4
			}};
		for(int e = 0; e < platzArt4.length; e++) {
			for(int p = 0; p < platzArt4[e].length; p++) {
				assertEquals(parkhausManager5.getPArt()[e][p],platzArt4[e][p]);
			}
		}
		//parkhausManager6
		assertEquals(parkhausManager6.getPlaetze().length,0);
		assertEquals(parkhausManager6.getPArt().length,0);
		//parkhausManager7
		assertEquals(parkhausManager7.getPlaetze().length,5);
		assertEquals(parkhausManager7.getPlaetze()[0].length,5);
		assertEquals(parkhausManager7.getPArt().length,5);
		assertEquals(parkhausManager7.getPArt()[0].length,5);				
		int[][] platzArt5 = {
			{0,0,1,1,2},
			{2,3,3,3,3},
			{3,3,3,3,3},
			{3,3,3,4,4},
			{4,4,4,5,5}
			};
		for(int e = 0; e < platzArt5.length; e++) {
			for(int p = 0; p < platzArt5[e].length; p++) {
				assertEquals(parkhausManager7.getPArt()[e][p],platzArt5[e][p]);
			}
		}
		//parkhausManager8
		assertEquals(parkhausManager8.getPlaetze().length,0);
		assertEquals(parkhausManager8.getPArt().length,0);
		//parkhausManager9
		assertEquals(parkhausManager9.getPlaetze().length,1);
		assertEquals(parkhausManager9.getPlaetze()[0].length,29);
		assertEquals(parkhausManager9.getPArt().length,1);
		assertEquals(parkhausManager9.getPArt()[0].length,29);
		int[][] platzArt6 = {{
			0,0,1,1,1,3,3,3,3,3,
			3,3,3,3,3,4,4,4,4,4,
			5,5,5,5,5,5,5,5,5}
			};
		for(int e = 0; e < platzArt6.length; e++) {
			for(int p = 0; p < platzArt6[e].length; p++) {
				assertEquals(parkhausManager9.getPArt()[e][p],platzArt6[e][p]);
			}
		}
		//parkhausManager10
		assertEquals(parkhausManager10.getPlaetze().length,8);
		assertEquals(parkhausManager10.getPlaetze()[0].length,50);
		assertEquals(parkhausManager10.getPArt().length,8);
		assertEquals(parkhausManager10.getPArt()[0].length,50);
		int[][] platzArt7 = {
			{0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,2,2,2,2,2},
			{2,2,2,2,2,2,2,2,2,2,
			2,2,2,2,2,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			3,3,3,3,3,3,3,3,3,3,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4},
			{4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4,
			4,4,4,4,4,4,4,4,4,4},
			{4,4,4,4,4,4,4,4,4,4,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5,
			5,5,5,5,5,5,5,5,5,5}
			};
		for(int e = 0; e < platzArt7.length; e++) {
			for(int p = 0; p < platzArt7[e].length; p++) {
				assertEquals(parkhausManager10.getPArt()[e][p],platzArt7[e][p]);
			}
		}
	}
}
