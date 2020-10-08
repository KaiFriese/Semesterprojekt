package nonServlet;

import java.util.Iterator;

public class ParkhausManagerIterator implements Iterator {
	private Car[][] plaetze;
	private int e,p;
	
	public ParkhausManagerIterator(Car[][] p) {
		plaetze = p;
		e = 0;
		this.p = -1;
	}
	
	@Override
	public boolean hasNext() {
		if(p < plaetze[e].length-1) {
			return true;
		}
		else {
			if(e < plaetze.length-1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String next() {
		if(hasNext()) {
			if(p < plaetze[e].length-1) {
				p++;
			}
			else {
				e++;
				p = 0;
			}
			return e + "," + p;
		}
		else return null;
	}
}
