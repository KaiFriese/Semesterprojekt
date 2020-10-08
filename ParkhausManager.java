package nonServlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParkhausManager implements Iterable{
	private Car[][] plaetze; //[e][p]
	private int[][] pArt; //0 = Behinderten, 1 = Frau&/Kind, 2 = Roller, 3 = PKW, 4 = Bus, 5 = Firmen

	public ParkhausManager() {
		plaetze = new Car[0][0];
		pArt = new int[0][0];
	}
	
	public ParkhausManager(int p) {
		plaetze = new Car[1][p];
		pArt = new int[1][p];
		
		int ges = p;
		//Aufteilung der verfügbaren Plätze auf Parkplatzarten
		int be = 0;
		if(ges >= 5) {
			be = ges/10;
			if(be < 1) {
				be = 1;
			} 
			else if(be > 15) {
				be = 15;
			}
		}
		
		int fr = 0;
		if(ges > 5) {
			fr = ges/10;
			if(fr < 1 && ges > 5) {
				fr = 1;
			}
			else if(fr > 30) {
				fr = 30;
			}
		}

		int ro = 0;
		if(ges > 3) {
			ro = ges/10;
			if(ro < 1) {
				ro = 1;
			} 
			else if(ro > 20) {
				ro = 20;
			}
		}

		int bu = 0;
		if(ges > 8) {
			bu = ges/5;
			if(bu < 1 && ges > 8) {
				bu = 1;
			}
		}
		
		int fi = 0;
		if(ges >= 20) {
			fi = ges/10;
		}  

		//PArt array erstellen
		int pk = ges - be - fr - ro - bu - fi;
		sortPArt(be, fr, ro, pk, bu, fi);
	}
	
	public ParkhausManager(int e, int p) {
		if(e >= 0 && p >= 0) {
			plaetze = new Car[e][p];
			pArt = new int[e][p];
			int ges = e*p;

			//Aufteilung der verfügbaren Plätze auf Parkplatzarten
			int be = 0;
			if(ges >= 5) {
				be = ges/10;
				if(be < 1) {
					be = 1;
				} 
				else if(be > 15) {
					be = 15;
				}
			}
			
			int fr = 0;
			if(ges > 5) {
				fr = ges/10;
				if(fr < 1 && ges > 5) {
					fr = 1;
				}
				else if(fr > 30) {
					fr = 30;
				}
			}
			
			int ro = 0;
			if(ges > 3) {
				ro = ges/10;
				if(ro < 1) {
					ro = 1;
				} 
				else if(ro > 20) {
					ro = 20;
				}
			}

			int bu = 0;
			if(ges > 8) {
				bu = ges/5;
				if(bu < 1 && ges > 8) {
					bu = 1;
				}
			}
			
			int fi = 0;
			if(ges >= 20) {
				fi = ges/10;
			} 
			
			//PArt array erstellen
			int pk = ges - be - fr - ro - bu - fi;
			sortPArt(be, fr, ro, pk, bu, fi);
		}
	}
	
	public ParkhausManager(int be, int fr, int ro, int pk, int bu, int fi) {
		int ges = be + fr+ ro + pk + bu + fi;
		int e = 0;
		int p = 0;

		//Aufteilung der verfügbaren Plätze auf Parkplatzarten
		if(ges > 11 && !isPrime(ges)) {
			int x = 2;
			List<Integer> options = new ArrayList<Integer>();
			int sum = 0;
			while(ges/x >= 5) {
				if(ges%x == 0) {
					options.add(x);
					sum += ges/x;
					x++;
				}
				else x++;
			}
			int avg = sum / options.size();
			int nearest = 0;
			for(int i = 0; i < options.size(); i++) {
				if(Math.abs(avg - ges/options.get(i)) <= Math.abs(avg - ges/options.get(nearest))) {
					nearest = i;
				}
			}
			e = options.get(nearest);
			p = ges / e;
		}
		else {
			if(ges>0) {
				e = 1;
				p = ges;
			}
		}

		plaetze = new Car[e][p];
		pArt = new int[e][p];
		
		//PArt array erstellen
		sortPArt(be, fr, ro, pk, bu, fi);
	}
	
	private void sortPArt(int be, int fr, int ro, int pk, int bu, int fi) {
		int[] pl = {be, fr, ro, pk, bu, fi}; //Anz der verschiedenen Plätze
		int i = 0; //PLatzartnummer 0 = be, 5 = fi
		for(int e = 0; e < pArt.length; e++) {
			for(int p = 0; p < pArt[e].length; p++) {
				if(pl[i] < 1) {
					i++;
					p--;
				}
				else {
					pl[i] -= 1;
					pArt[e][p] = i;
				}
			}
		}
	}
	
	public String getFree(int art){
		Iterator it = iterator();
		while(it.hasNext()) {
			String next = (String) it.next();
			String[] plz = next.split(",");
			if(pArt[Integer.parseInt(plz[0])][Integer.parseInt(plz[1])] == art && plaetze[Integer.parseInt(plz[0])][Integer.parseInt(plz[1])] == null) {
				return plz[0] + ":" + plz[1];
			}
		}
		return "-";
	}

	private boolean isPrime(int p) { //feststellen, ob eine Zahl eine Primzahl ist
		if (p == 2 || p == 3) {
			return true;
			} 
		if (p % 2 == 0) {
			return false;
			} 
		int sqrt = (int) Math.sqrt(p) + 1; 
		for (int i = 3; i < sqrt; i += 2) {
			if (p % i == 0) {
				return false;
				}
			}
		return true;
	}
	
	public Car[][] getPlaetze() {
		return plaetze;
	}
	
	public int[][] getPArt(){
		return pArt;
	}
	
	public String makeTable() { //HTML Tabelle anhand der Parkhausstrucktur erstellen
		String table = "<table border = 1>" + '\n';
		if(plaetze.length > 0 && plaetze[0].length > 0) {
			for(int e = 0; e < plaetze.length; e++) {
				table += "<tr>" + '\n';
				for(int p = 0; p < plaetze[e].length; p++) {
					
					if(p == 0 && e > 0) {
						table += "<th>Etage: " + (e-1) + "</th>"  + '\n';
					}
					else if(e == 0 && p > 0) {
						table += "<th>Platz: " + (p-1) + "</th>"  + '\n';
					}
					else if(e == 0 && p == 0) {
						table += "<th></th>"  + '\n';
					}
					else {
						table += "<td>" + plaetze[e-1][p-1] + "</tb>" + '\n';
					}
				}
				if(e == 0) {
					table += "<th>Platz: " + (plaetze[e].length-1) + "</th>"  + '\n';
				}
				else {
					if(e > 0) {
						table += "<td>" + plaetze[e-1][plaetze[e].length-1] + "</tb>" + '\n';
					}
				}
				table += "</tr>" + '\n';
			}
			table += "<tr>" + '\n';
			table += "<th>Etage: " + (plaetze.length-1) + "</th>"  + '\n';
			for(int p = 0; p < plaetze[0].length; p++) {
				table += "<td>" + plaetze[plaetze.length-1][p] + "</tb>" + '\n';
			}
			table += "</tr>" + '\n';
		}
		table += "</table>";
		return table;
	}
	
	
	public Car Enter(Car c, int e, int p) {
		if(plaetze[e][p] == null) {
			plaetze[e][p] = c;
			return c;
		}
		else {
			System.out.println("Error: Platz belegt!");
			return null;
		}
	}
	
	public Car Leave(int e, int p) {
		Car c = plaetze[e][p];
		plaetze[e][p] = null;
		return c;
	}

	@Override
	public Iterator iterator() {
		return new ParkhausManagerIterator(plaetze);
	}
}