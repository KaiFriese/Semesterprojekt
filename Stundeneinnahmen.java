package nonServlet;

import java.util.List;

public class Stundeneinnahmen extends Einnahmen{
	int Jahr, Monat, Tag, Stunde;

	public Stundeneinnahmen(int Jahr, int Monat, int Tag, int Stunde, List<String> protokoll) {
		super(protokoll);
		this.Jahr = Jahr;
		this.Monat = Monat;
		this.Tag = Tag;
		this.Stunde = Stunde;
	}

	@Override
	public String getDatum() {
		String datum = "";
		
		datum += Jahr + "-";
		if(Monat < 10) {
			datum += "0" + Monat + "-";
		}
		else datum += "" + Monat + "-";
		if(Tag < 10) {
			datum += "0" + Tag + " ";
		}
		else datum += "" + Tag + " ";
		if(Stunde < 10) {
			datum += "0" + Stunde;
		}
		else datum += "" + Stunde;
		
		return datum;
	}
}
