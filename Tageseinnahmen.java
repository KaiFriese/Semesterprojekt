package nonServlet;

import java.util.List;

public class Tageseinnahmen extends Einnahmen {
	int Jahr, Monat, Tag;

	public Tageseinnahmen(int Jahr, int Monat, int Tag, List<String> protokoll) {
		super(protokoll);
		this.Jahr = Jahr;
		this.Monat = Monat;
		this.Tag = Tag;
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
			datum += "0" + Tag;
		}
		else datum += "" + Tag;
		return datum;
	}
}
