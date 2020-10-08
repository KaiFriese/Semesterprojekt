package nonServlet;

import java.util.List;

public class Monatseinnahmen extends Einnahmen {
	int Jahr, Monat;
	
	public Monatseinnahmen(int Jahr, int Monat, List<String> protokoll) {
		super(protokoll);
		this.Jahr = Jahr;
		this.Monat = Monat;
	}

	@Override
	public String getDatum() {
		String datum = "";
		datum += Jahr + "-";
		if(Monat < 10) {
			datum += "0" + Monat;
		}
		else datum += "" + Monat;
		return datum;
	}
}
