package nonServlet;

import java.sql.Timestamp;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

import parkhaus.Parkhaus;

public class UndoRedo {
	private Parkhaus parkhaus;
	private ParkhausManager parkhausManager;
	private Statistik statistik;
	private String befehl;
	private Car auto;
	private Stack<String> fertig, unfertig;
	private Stack<Car> fertigAutos, unfertigAutos;
	
	public UndoRedo(Parkhaus parkhaus) {
		this.parkhaus = parkhaus;
		this.parkhausManager = parkhaus.getPm();
		this.statistik = parkhaus.getStats();
		befehl = null;
		auto = null;
		fertig = new Stack<String>();
		unfertig = new Stack<String>();
		fertigAutos = new Stack<Car>();
		unfertigAutos = new Stack<Car>();
	}
	
	public void neuerBefehl(String s, Car c) {
		befehl = s;
		this.auto = c;
	}
	
	public String aktivieren() {
		befehl = statistik.neuerEintrag(befehl, auto);
		fertig.add(befehl);
		fertigAutos.add(auto);
		return befehl;
	}
	
	public void undo() {
		try {
			unfertig.add(fertig.pop()); //Von fertig nach unfertig verschieben
			unfertigAutos.add(fertigAutos.pop());
			if(unfertig.size() > 0) { //Befehl und Auto neu zuweisen
				befehl = unfertig.lastElement();
				auto = unfertigAutos.lastElement();
			}
			else {
				befehl = null;
				auto = null;
			}
			statistik.undo(unfertig.lastElement()); //undo von Statistiken(rein, raus und Verteilung)
			String[] parameter = befehl.split(",");

			String ausgabe = ""; //undo Protokoll
			ausgabe += "undo,"; //Ereignis
			ausgabe += auto.getNr() + ","; //Ticketnr
			ausgabe += new Timestamp(new Date().getTime()) + ","; //Zeitpunkt
			ausgabe += "-,"; //Parkdauer
			ausgabe += "-,"; //Preis
			ausgabe += "-,"; //Parkplatz
			ausgabe += "-,"; //Parkplatzart
			ausgabe += auto; //Auto
			ausgabe = statistik.neuerEintrag(ausgabe, auto); //in Statistik aufnehmen + Konsolenausgabe
			
			if(parameter[0].equals("enter")) { //rein rückgängig machen
				String[] reinParkplatz = parameter[5].split(":");
				parkhausManager.Leave(Integer.parseInt(reinParkplatz[0]), Integer.parseInt(reinParkplatz[1]));
			}
			if(parameter[0].equals("leave")) { //raus rückgänig machen
				String freierParkplatz = parkhausManager.getFree(auto.getArt());
				String[] reinParkplatz = freierParkplatz.split(":");
				parkhausManager.Enter(auto, Integer.parseInt(reinParkplatz[0]), Integer.parseInt(reinParkplatz[1]));
			}
		}
		catch(NullPointerException e) {
			System.out.println("Error: Befehl nicht im Protokoll vorhanden!");
		}
		catch(EmptyStackException e) {
			System.out.println("Error: Befehl nicht im Stapel vorhanden!");
		}
	}
	
	public void redo() {
		try {
			befehl = unfertig.pop(); //Befehl und Auto neu zuweisen
			auto = unfertigAutos.pop();
			statistik.redo(befehl); //undo von Statistiken(rein, raus und Verteilung)
			
			String ausgabe = ""; //redo Protokoll
			ausgabe += "redo,"; //Ereignis
			ausgabe += auto.getNr() + ","; //Ticketnr
			ausgabe += new Timestamp(new Date().getTime()) + ","; //Zeitpunkt
			ausgabe += "-,"; //Parkdauer
			ausgabe += "-,"; //Preis
			ausgabe += "-,"; //Parkplatz
			ausgabe += "-,"; //Parkplatzart
			ausgabe += auto; //Auto
			ausgabe = statistik.neuerEintrag(ausgabe, auto); //in Statistik aufnehmen + Konsolenausgabe
			
			if(befehl.startsWith("enter")) { //redo von rein
				String freierParkplatz = parkhausManager.getFree(auto.getArt());
				String[] reinParkplatz = freierParkplatz.split(":");
				parkhausManager.Enter(auto, Integer.parseInt(reinParkplatz[0]), Integer.parseInt(reinParkplatz[1]));
			}
			if(befehl.startsWith("leave")) { //redo von raus
				String[] parameter = befehl.split(",");
				String[] reinParkplatz = parameter[5].split(":");
				parkhausManager.Leave(Integer.parseInt(reinParkplatz[0]), Integer.parseInt(reinParkplatz[1]));
			}
		}
		catch(NullPointerException e) {
			System.out.println("Error: Befehl nicht im Protokoll vorhanden!");
		}
		catch(EmptyStackException e) {
			System.out.println("Error: Befehl nicht im Stapel vorhanden!");
		}
	}
}
