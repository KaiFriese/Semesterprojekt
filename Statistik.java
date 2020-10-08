package nonServlet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statistik {
	private List<String> protokoll;
	private List<Car> autos;
	private int rein, raus, drin;
	private int[] verteilungGes, verteilungAkt;
	
	public Statistik(int max) {
		protokoll = new ArrayList<String>();
		autos = new ArrayList<Car>();
		rein = 0;
		raus = 0;
		drin = 0;
		verteilungGes = new int[6];
		verteilungAkt = new int[7];
		verteilungAkt[6] = max;
	}
	
	public String neuerEintrag(String s, Car c) {
		//Ereignis, Ticketnummer, timestamp, Dauer, Preis, Parkplatz, Parkplatzart, Auto
		String[] parameter = s.split(","); //Eingabe aufteilen
		if("enter".equals(parameter[0])) { //Statistik für rein anpassen
			verteilungGes[Integer.parseInt(parameter[6])] += 1;
			verteilungAkt[Integer.parseInt(parameter[6])] += 1;
			verteilungAkt[6] -= 1;
			rein++;
		}
		if("leave".equals(parameter[0])){ //Statistik für raus anpassen
			String rein = "";
			for(int i = 0; i < protokoll.size(); i++) { //passendes Reinprotokoll finden
				String[] tempParameter = protokoll.get(i).split(",");
				if(tempParameter[1].equals(parameter[1]) && tempParameter[0].equals("enter")) { //gleiches Ticket
					rein = protokoll.get(i);
				}
			}
			if(!rein.equals("")) { //wenn ein passendes Protokoll vorhanden ist
				long d = getParkdauer(rein, s); //Parkdauer berechnen
				String format = new SimpleDateFormat("SS:mm:ss").format(d); //Parkdauer umformen
				String[] formatParameter = format.split(":");
				int zeit = Integer.parseInt(formatParameter[2]) + Integer.parseInt(formatParameter[1])*60 
						+ Integer.parseInt(formatParameter[0])*3600; 
				double preis = zeit * 0.005; //Preis berechnen
				parameter[3] = "" + zeit;
				parameter[4] = "" + preis;
				s = parameter[0]; //String mit neuen Parametern erstellen
				for(int i = 1; i < parameter.length; i++) {
					s += "," + parameter[i];
				}
				verteilungAkt[Integer.parseInt(parameter[6])] -= 1;
				verteilungAkt[6] += 1;
				raus++;
			}
		}
		drin = rein - raus;
		protokoll.add(s);
		autos.add(c);
		System.out.println(s); //Konsolenausgabe
		return s;
	}
	
	@SuppressWarnings("deprecation")
	public long getParkdauer(String rein, String raus) {
		rein = rein.split(",")[2]; //Zeitpunkt von rein
		raus = raus.split(",")[2]; //Zeitpunkt von raus
		String[] parameter = rein.split("[- :.]"); //aufteilen
		
		int[] intParamameter = new int[parameter.length]; //enter in int umwandeln
		for(int i = 0; i < parameter.length; i++) {
			intParamameter[i] = Integer.parseInt(parameter[i]);
		}
		//date aus enter erstellen
		Date reinDate = new Date(intParamameter[0]-1900,intParamameter[1]-1,intParamameter[2],intParamameter[3],intParamameter[4],intParamameter[5]);
		
		parameter = raus.split("[- :.]"); //raus in int umwandeln
		intParamameter = new int[parameter.length];
		for(int i = 0; i < parameter.length; i++) {
			intParamameter[i] = Integer.parseInt(parameter[i]);
		}
		//date aus raus erstellen
		Date rausDate = new Date(intParamameter[0]-1900,intParamameter[1],intParamameter[2],intParamameter[3],intParamameter[4],intParamameter[5]);
		
		return rausDate.getTime() - reinDate.getTime(); //differenz berechnen
	}

	public int getDrin() {
		return drin;
	}
	
	public String erzeugeTabelle() {
		//Ereignis, Ticketnummer, timestamp, Dauer, Preis, Parkplatz, Parkplatzart, Auto
		String tabelle = "<table border = 1>" + '\n';
		if(protokoll.size() > 0) {
			tabelle += "<th>Event</th>"  + '\n';
			tabelle += "<th>Ticket</th>"  + '\n';
			tabelle += "<th>Timestamp</th>"  + '\n';
			tabelle += "<th>Duration</th>"  + '\n';
			tabelle += "<th>Price</th>"  + '\n';
			tabelle += "<th>Slot</th>"  + '\n';
			tabelle += "<th>Parking Type</th>"  + '\n';
			tabelle += "<th>Car</th>"  + '\n';
			for(int z = 0; z < protokoll.size(); z++) { //pro Eintrag
				tabelle += "<tr>" + '\n';
				String[] l = protokoll.get(z).split(","); //Eintrag aufteilen
				for(int s = 0; s < l.length; s++) { //pro Spalte
					tabelle += "<td>" + l[s] + "</tb>" + '\n'; //Inhalt
				}
				tabelle += "</tr>" + '\n';
			}
		}
		tabelle += "</table>";
		return tabelle;
	}
	
	public String verteilungGes() {
		//Ausgabestring für Javascript Chart
		String ausgabe = "" + verteilungGes[0] + "," + verteilungGes[1] + "," + verteilungGes[2] + "," + verteilungGes[3] + "," + verteilungGes[4] + "," + verteilungGes[5];
		return ausgabe;
	}
	public String verteilungAkt() {
		//Ausgabestring für Javascript Chart
		String ausgabe = "" + verteilungAkt[0] + "," + verteilungAkt[1] + "," + verteilungAkt[2] + "," + verteilungAkt[3] + "," + verteilungAkt[4] + "," + verteilungAkt[5] + "," + verteilungAkt[6];
		return ausgabe;
	}
	
	public String einnahmen() {
		String[] zeitpunkt = new Timestamp(new Date().getTime()).toString().split("[- :.]"); //Aktuellen Zeitpunkt ermitteln und aufteilen
		int[] zeitpunktInt = new int[zeitpunkt.length]; //Zeit als int speichern
		for(int i = 0; i < zeitpunkt.length; i++) {
			zeitpunktInt[i] = Integer.parseInt(zeitpunkt[i]);
		}
		//Berechnung der Einnahmen
		Jahreseinnahmen jahresEinnahme = new Jahreseinnahmen(zeitpunktInt[0], protokoll);
		Monatseinnahmen monatsEinnahme = new Monatseinnahmen(zeitpunktInt[0], zeitpunktInt[1], protokoll);
		Tageseinnahmen tagesEinnahme = new Tageseinnahmen(zeitpunktInt[0], zeitpunktInt[1], zeitpunktInt[2], protokoll);
		Stundeneinnahmen stundenEinnahme = new Stundeneinnahmen(zeitpunktInt[0], zeitpunktInt[1], zeitpunktInt[2], zeitpunktInt[3], protokoll);
		Minuteneinnahmen minutenEinnahme = new Minuteneinnahmen(zeitpunktInt[0], zeitpunktInt[1], zeitpunktInt[2], zeitpunktInt[3], zeitpunktInt[4], protokoll);
		//Ausgabestring für Javascript Chart
		String ausgabe = "" + jahresEinnahme.toString()  + "," + monatsEinnahme.toString()  + "," + tagesEinnahme.toString()  + "," + stundenEinnahme.toString()  + "," + minutenEinnahme.toString();
		return ausgabe;
	}
	
	public void undo(String s) {
		for(int i = protokoll.size()-1; i >= 0; i--) { //passendes Protokoll finden
			if(protokoll.get(i).equals(s)) {
				String[] parameter = s.split(",");
				if(parameter[0].equals("enter")) { //undo von rein
					verteilungGes[Integer.parseInt(parameter[6])] -= 1;
					verteilungAkt[Integer.parseInt(parameter[6])] -= 1;
					verteilungAkt[6] += 1;
					rein--;
				}
				if(parameter[0].equals("leave")) { //undo von raus
					verteilungAkt[Integer.parseInt(parameter[6])] += 1;
					verteilungAkt[6] -= 1;
					raus--;
				}
			}
		}
	}
	
	public void redo(String s) {
		for(int i = protokoll.size()-1; i >= 0; i--) {
			if(protokoll.get(i).equals(s)) { //passendes Protokoll finden
				String[] parameter = s.split(",");
				if(parameter[0].equals("enter")) { //redo von rein
					verteilungGes[Integer.parseInt(parameter[6])] += 1;
					verteilungAkt[Integer.parseInt(parameter[6])] += 1;
					verteilungAkt[6] -= 1;
					rein++;
				}
				if(parameter[0].equals("leave")) { //redo von raus
					verteilungAkt[Integer.parseInt(parameter[6])] -= 1;
					verteilungAkt[6] += 1;
					raus++;
				}
			}
		}
	}
	
	public String toString() {
		String ausgabe = "";
		for(int i = 0; i < protokoll.size(); i++) {
			ausgabe += protokoll.get(i) + '\n';
		}
		return ausgabe;
	}
}
