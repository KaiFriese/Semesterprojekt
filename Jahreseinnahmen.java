package nonServlet;

import java.util.List;

public class Jahreseinnahmen extends Einnahmen {
	int Jahr;
	
	public Jahreseinnahmen(int Jahr, List<String> protokoll) {
		super(protokoll);
		this.Jahr = Jahr;
	}

	@Override
	public String getDatum() {
		String datum = "";
		datum += Jahr;
		return datum;
	}

}
