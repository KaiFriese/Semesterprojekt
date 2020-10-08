package parkhaus;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nonServlet.Car;
import nonServlet.ParkhausManager;
import nonServlet.Statistik;
import nonServlet.UndoRedo;

/**
 * Servlet implementation class Parkhaus
 */
@WebServlet("/Parkhaus")
public class Parkhaus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Statistik stats;
	private static ParkhausManager pm;
	private static UndoRedo ur;

    /**
     * Default constructor. 
     */
    public Parkhaus() {
    	pm = new ParkhausManager(6,15);
    	stats = new Statistik(pm.getPlaetze().length * pm.getPlaetze()[0].length);
    	ur = new UndoRedo(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("stats") != null) stats = (Statistik) request.getSession().getAttribute("stats");
		
		if(request.getParameter("Erstellen") != null) {
			if(request.getParameter("etagen") == null || request.getParameter("plaetze") == null) {
				pm = new ParkhausManager(1, 1);
		    	stats = new Statistik(pm.getPlaetze().length * pm.getPlaetze()[0].length);
		    	ur = new UndoRedo(this);
		    	Car.snr = 0;
			}
			else {
				pm = new ParkhausManager(Integer.parseInt(request.getParameter("etagen")), 
						Integer.parseInt(request.getParameter("plaetze")));
		    	stats = new Statistik(pm.getPlaetze().length * pm.getPlaetze()[0].length);
		    	ur = new UndoRedo(this);
		    	Car.snr = 0;
			}
		}
		
		if(request.getParameter("parkplatz") != null && request.getParameter("Rein") != null) {
			String param = request.getParameter("parkplatz");
			Date denter = new Date();
			if(param.equals("zufall")) {
				Enter(new Car(denter));
			}
			else if(param.equals("behindert")) {
				Enter(new Car(0,denter));
			}
			else if(param.equals("frau")) {
				Enter(new Car(1,denter));
			}
			else if(param.equals("roller")) {
				Enter(new Car(2,denter));
			}
			else if(param.equals("pkw")) {
				Enter(new Car(3,denter));
			}
			else if(param.equals("bus")) {
				Enter(new Car(4,denter));
			}
			else if(param.equals("firmen")) {
				Enter(new Car(5,denter));
			}
		}
		
		if(request.getParameter("Raus") != null) {
			if(!request.getParameter("rausEtage").equals("") || !request.getParameter("rausPlatz").equals("")) {
				Leave(Integer.parseInt(request.getParameter("rausEtage")), 
						Integer.parseInt(request.getParameter("rausPlatz")));
			}
			else {
				int in = stats.getDrin();
				int ran = (int)(Math.random()*in-1)+1;
				int x = 0;
				int y = 0;
				Iterator it = pm.iterator();
				while(it.hasNext()) {
					String next = (String) it.next();
					String[] plz = next.split(",");
					int e = Integer.parseInt(plz[0]);
					int p = Integer.parseInt(plz[1]);
					if(ran == 0) {
						break;
					}
					if(pm.getPlaetze()[e][p]!=null) {
						ran--;
						if(ran == 0) {
							x = e;
							y = p;
						}
					}
				}
				
				Leave(x,y);
			}
		}
		
		if(request.getParameter("Undo") != null) {
			ur.undo();
		}
		
		if(request.getParameter("Redo") != null) {
			ur.redo();
		}
		
		request.setAttribute("tabelle", pm.makeTable());
		request.setAttribute("statistikTabelle", stats.erzeugeTabelle());
		request.setAttribute("verteilungGes", stats.verteilungGes());
		request.setAttribute("verteilungAkt", stats.verteilungAkt());
		request.setAttribute("einnahmen", stats.einnahmen());
		request.getSession().setAttribute("stats", stats);
		RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String Enter(Car c) { //noch nicht fertig!!!
		//event, ticket, timestamp, duration, price, slot, pArt, Car ...
		String out = "";
		String slot = pm.getFree(c.getArt());
		if(!slot.equals("-")) {
			out += "enter,"; //Event
			out += c.getNr() + ","; //Ticketnr
			out += new Timestamp(c.getEnter().getTime()) + ","; //timestamp
			out += "-,"; //duration
			out += "-,"; //price
			out += slot + ","; //slot
			String[] slota = slot.split(":");
			out += pm.getPArt()[Integer.parseInt(slota[0])][Integer.parseInt(slota[1])] + ","; //pArt
			out += c; //Car
			ur.neuerBefehl(out, c);
			out = ur.aktivieren();
			pm.Enter(c,Integer.parseInt(slota[0]),Integer.parseInt(slota[1]));
		} else {
			out += "enter: error,"; //Event
			out += "-,"; //Ticketnr
			out += new Timestamp(c.getEnter().getTime()) + ","; //timestamp
			out += "-,"; //duration
			out += "-,"; //price
			out += "-,"; //slot
			out += "-,"; //pArt
			out += "-"; //Car
			out = stats.neuerEintrag(out, c); //in Statistik aufnehmen + Console
			System.out.println("Error: Kein Platz frei!"); //Console
		}
		return out;
	}
	
	public String Leave(int e, int p) { //Car schreiben, Auto mit Ticket suchen, an Statistic geben und duration, price auslesen
		String out = "";
		if(e < pm.getPlaetze().length && p < pm.getPlaetze()[0].length) {
			Car c = pm.getPlaetze()[e][p];
			if(c != null) {
				out = "leave,"; //Event
				out += c.getNr() + ","; //Ticket
				Date dleave = new Date();
				out += new Timestamp(dleave.getTime()) + ","; //timestamp
				out += "-,"; //duration
				out += "-,"; //price
				out += e + ":" + p +","; //slot
				out += pm.getPArt()[e][p] + ","; //pArt
				out += c; //Car
				ur.neuerBefehl(out, c);
				out = ur.aktivieren();
				pm.Leave(e, p);
			}
			else {
				Date dleave = new Date();
				out += "leave: error,"; //Event
				out += "-,"; //Ticketnr
				out += new Timestamp(dleave.getTime()) + ","; //timestamp
				out += "-,"; //duration
				out += "-,"; //price
				out += "-,"; //slot
				out += "-,"; //pArt
				out += "-"; //Car
				out = stats.neuerEintrag(out, c); //in Statistik aufnehmen + Console
				System.out.println("Error: Kein Auto an diesem Platz!");
			}
		}
		return out;
	}
	
	public ParkhausManager getPm() {
		return pm;
	}
	
	public Statistik getStats() {
		return stats;
	}
}