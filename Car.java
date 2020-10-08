package nonServlet;

import java.util.Date;

public class Car {
	public static int snr;
	private int nr, art;
	private Date enter, leave;
	
	public Car(Date enter) {
		this.nr = snr++;
		int ran = (int)(Math.random()*13); //be fr fr ro ro pkw pkw pkw pkw bu bu fi fi
		if(ran == 0) {
			art = 0;
		}
		else if(ran <= 2) {
			art = 1;
		}
		else if(ran <= 4) {
			art = 2;
		}
		else if(ran <= 8) {
			art = 3;
		}
		else if(ran <= 10) {
			art = 4;
		}
		else art = 5;
		this.enter = enter;
		this.leave = null;
	}
	
	public Car(int art, Date enter) {
		this.nr = snr++;
		this.art = art;
		this.enter = enter;
		this.leave = null;
	}
	
	public void setLeave(Date d){
		leave = d;
	}
	public int getNr() {
		return nr;
	}
	public int getArt() {
		return art;
	}
	public Date getEnter() {
		return enter;
	}
	
	public String toString() {
		String out = "";
		if(art == 0) {
			out += "Be" + nr;
		}
		else if(art == 1) {
			out += "Fr" + nr;
		}
		else if(art == 2) {
			out += "Ro" + nr;
		}
		else if(art == 3) {
			out += "PkW" + nr;
		}
		else if(art == 4) {
			out += "Bus" + nr;
		}
		else out += "Fi" + nr;
		return out;
	}
}
