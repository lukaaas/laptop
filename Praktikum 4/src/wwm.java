public class wwm 
{//
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
	// Deklaration der Variablen "richtige_antwort" als char
	char richtige_antwort;
	// Deklaration der Variablen "antwort_spieler" als char
	char antwort_spieler;
	// Deklaration der Variablen "richtig" als boolean
	boolean richtig;
	// Deklaration und Initialisierung der Variablen "anzahl_richtige_antworten" als int
	int anzahl_richtige_antworten = 0;	
	
	System.out.println("Herzlich Willkommen zum WHS - WER WIRD MILLION?R!");
	System.out.println("=================================================");
	// Eine Leerzeile ausgeben
	System.out.println();
		
	/*
	###########
	# Frage 1 #
	###########
	 */	
	
	//Korrekte Antwort speichern
	richtige_antwort='d';
	//Frage stellen
	System.out.println("Hier kommt Frage 1:");
	System.out.println("Wo finden Sie die Vorlesungs-, Praktikums- und Tutoriumsunterlagen?");
	System.out.println("a - doodle\nb - google\nc - poodle\nd - moodle");
	//Antwort einlesen
	System.out.println("Bitte a,b,c oder eingeben:");	
	antwort_spieler = io.read_char_abcd();
	//Antwort auswerten	
	richtig = (antwort_spieler == richtige_antwort) ? true : false;
	//Ergebnis ausgeben
	if (richtig)
	{
		System.out.println("Gl?ckwunsch, die Antwort ist richtig!");
		anzahl_richtige_antworten++;
		System.out.println("Sie haben bis jetzt " + anzahl_richtige_antworten + " richtige Antwort(en) gegeben!\n");
	}
	else
	{
		System.out.println("Schade, die Antwort ist leider falsch!");
		System.out.println("Sie haben bis jetzt " + anzahl_richtige_antworten + " richtige Antwort(en) gegeben!\n");
	}
	
	/*
	###########
	# Frage 2 #
	###########
	 */	
	
	//Korrekte Antwort speichern
	richtige_antwort='c';
	//Frage stellen
	System.out.println("Hier kommt Frage 2:");
	System.out.println("Welches der folgenden W?rter d?rfen Sie nicht als Variablenbezeichner in java verwenden?");
	System.out.println("a - inter\nb - mint\nc - int\nd - ginter");
	//Antwort einlesen
	System.out.println("Bitte a,b,c oder eingeben:");	
	antwort_spieler = io.read_char_abcd();
	//Antwort auswerten	
	richtig = (antwort_spieler == richtige_antwort) ? true : false;
	//Ergebnis ausgeben
	if (richtig)
	{
		System.out.println("Gl?ckwunsch, die Antwort ist richtig!");
		anzahl_richtige_antworten++;
		System.out.println("Sie haben bis jetzt " + anzahl_richtige_antworten + " richtige Antwort(en) gegeben!\n");
	}
	else
	{
		System.out.println("Schade, die Antwort ist leider falsch!");
		System.out.println("Sie haben bis jetzt " + anzahl_richtige_antworten + " richtige Antwort(en) gegeben!\n");
	}	

	}

}