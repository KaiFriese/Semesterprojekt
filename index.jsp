<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="tabs.css">
</head>
<body><div class="tabContainer">
		<div class="buttonContainer">
			<button onclick="showPanel(0)">Parkhaus</button>
			<button onclick="showPanel(1)">Statistik</button>
			<button onclick="showPanel(2)">Log</button>
		</div>
		<div class="tabPanel">
			Parkhaus: 
	        <br>
			<form action = "Parkhaus" method = "GET">
	         	Etagen: <input type = "number" min="1" name = "etagen" value = "1">
	         	Plätze: <input type = "number" min="1" name = "plaetze" value = "1"/>
	         	<input type = "submit" name = "Erstellen" value = "Erstellen"/>
	         	<br>
	         	Hinzufügen:
	         	<br>
	         	<SELECT name = "parkplatz" id = "parkplatzArt">
		         	<OPTION Value="zufall">Zufällig</OPTION>
					<OPTION Value="behindert">Behinderten Parkplatz</OPTION>
					<OPTION Value="frau">Frauen Parkplatz</OPTION>
					<OPTION Value="roller">Roller Parkplatz</OPTION>
					<OPTION Value="pkw">PkW Parkplatz</OPTION>
					<OPTION Value="bus">Bus Parkplatz</OPTION>
					<OPTION Value="firmen">Firmen Parkplatz</OPTION>
					<input type="submit" name= "Rein" value="Enter"/>
				</SELECT>
				<br>
				Verlassen:
				<br>
				Etage: <input type = "number" min="0" name = "rausEtage" />
	         	Platz: <input type = "number" min="0" name = "rausPlatz" />
	         	<input type = "submit" name = "Raus" value = "Leave"/>
	         	<br>
	         	<input type = "submit" name = "Undo" value = "Undo"/>
	         	<input type = "submit" name = "Redo" value = "Redo"/>
	         	<br>
	     	</form>
	        <br/>
			
			
			
			${requestScope["tabelle"]}
		</div>
		<div class="tabPanel">Statistik:
			<br>
			<div id='barChart'>Einnahmen: </div>
			
			
			<div id='pieChartG'>Gesamte Verteilung: </div>
			<div id='pieChartA'>Aktuelle Verteilung: </div>
		
		</div>
		<div class="tabPanel">
		
		<br>
		${requestScope["statistikTabelle"]}</div>
	</div>
	
	<script src='https://cdn.plot.ly/plotly-latest.min.js'></script> <!-- Diagramskript von plotly -->
	<script>
	function showPanel(panelIndex){
		var tabButtons=document.querySelectorAll(".tabContainer .buttonContainer button");
		var tabPanels=document.querySelectorAll(".tabContainer .tabPanel");
	
		tabButtons.forEach(function(node) {
			node.style.color="";
			node.style.backgroundColor="#ababab";
		});
		tabButtons[panelIndex].style.backgroundColor="red";
		tabPanels.forEach(function(node) {
			node.style.display="none";
		});
		tabPanels[panelIndex].style.display="block";
	}
	
	showPanel(0)
	
	var vG = '<%= request.getAttribute("verteilungGes") %>'.split(",");
	
	var dataG = [{
		values: [vG[0],vG[1],vG[2],vG[3],vG[4],vG[5]] ,
		labels: ['Behinderte', 'Frauen', 'Roller', 'PKW', 'Bus', 'Firmen'],
		type: 'pie',
		marker: {
		    colors: ['rgb(0,0,85)','rgb(190,0,85)','rgb(0,115,140)','rgb(0,200,50)','rgb(255,135,0)','rgb(114,0,135)']
		  },
	}];
	
	var vA = '<%= request.getAttribute("verteilungAkt") %>'.split(",");
	
	var dataA = [{
		values: [vA[0],vA[1],vA[2],vA[3],vA[4],vA[5],vA[6]] ,
		labels: ['Behinderte', 'Frauen', 'Roller', 'PKW', 'Bus', 'Firmen', 'Frei'],
		type: 'pie',
		marker: {
		    colors: ['rgb(0,0,85)','rgb(190,0,85)','rgb(0,115,140)','rgb(0,200,50)','rgb(255,135,0)','rgb(114,0,135)','rgb(0,180,190)']
		  },
	}];

	var layout = {
		height: 400,
		width: 500
	};
	
	Plotly.newPlot('pieChartG', dataG, layout);
	
	Plotly.newPlot('pieChartA', dataA, layout);
	
	var e = '<%= request.getAttribute("einnahmen") %>'.split(",");
	
	var dataE = [
		  {
		    x: ['Jahreseinnahmen', 'Monatseinnahmen', 'Tageseinnahmen', 'Stundeneinnahmen', 'Minuteneinnahmen'],
		    y: [e[0], e[1], e[2], e[3], e[4]],
		    type: 'bar',
			marker: {
				color: 'rgb(85,0,50)'
				}
		  }
		];

		Plotly.newPlot('barChart', dataE);
	
	function foo(){
		var x = '<%= request.getAttribute("Verteilung") %>';
		document.getElementById("demo").innerHTML = x[1];
	}
	</script>
	
	
</body>
</html>