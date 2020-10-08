package nonServlet;

import java.util.ArrayList;
import java.util.List;

public abstract class Einnahmen {
	protected double einnahmen;
	protected List<String> protokoll;
	
	public Einnahmen(List<String> protokoll) {
		this.protokoll = protokoll;
		einnahmen = 0.0;
	}
	
	public double einnahmen() {
		einnahmen = 0.0;
		double[] einnahmenArray = getEinnahmen(getDatum());
		for(int i = 0; i < einnahmenArray.length; i++) {
			einnahmen += einnahmenArray[i];
		}
		return einnahmen;
	}
	
	public double[] getEinnahmen(String datum) {
		//Ereignis, Ticketnummer, timestamp, Dauer, Preis, Parkplatz, Parkplatzart, Auto
		List<String> einnahmenList = new ArrayList(); //abspeichern der Preise mit passendem Zeitraum
		for(int i = 0; i < protokoll.size(); i++) {
			String[] parameter = protokoll.get(i).split(",");
			if(parameter[0].equals("leave") && parameter[2].startsWith(datum) && !parameter[4].equals("-")) {
				einnahmenList.add(parameter[4]);
			}
		}
		
		double[] einnahmenArray = new double[einnahmenList.size()]; //umrechnen in int
		for(int i = 0; i < einnahmenArray.length; i++) {
			einnahmenArray[i] = Double.parseDouble(einnahmenList.get(i));
		}
		return einnahmenArray;
	}
	
	public abstract String getDatum();
	
	public String toString() {
		return "" + einnahmen();
	}
}
