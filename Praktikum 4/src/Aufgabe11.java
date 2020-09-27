//test
public class Aufgabe11 {
	public static void main(String []args)
	{
		int [] schublade = new int[4];
		int userInput;
		
		System.out.println("In welcher Schublade möchten Sie etwas hinzufügen?");
		userInput = io.read_int();
		
		if(userInput == 1)
		{
			System.out.println("Geben Sie eine hinzufügende Anzahl ein");
			schublade[0] = io.read_int();
		}
		
		else if(userInput == 2)
		{
			System.out.println("Geben Sie eine hinzufügende Anzahl ein");
			schublade[1] = io.read_int();
		}
		else if(userInput == 3)
		{
			System.out.println("Geben Sie eine hinzufügende Anzahl ein");
			schublade[2] = io.read_int();
		}
		else if(userInput == 4)
		{
			System.out.println("Geben Sie eine hinzufügende Anzahl ein");
			schublade[3] = io.read_int();
		}
		
		int anzahl = 0;
		for(int i = 0; i < schublade.length; i++)
		{
			
			anzahl += schublade[i];
			
		}
		for(int i = 0; i < schublade.length;i++)
		{
			int schubladenAusgabe = 1 + i;
			System.out.println("In Schublade : "+schubladenAusgabe +" befinden sich " +schublade[i]+" T-Shirts.");
			
		}
		System.out.println("Insgesammt befinden sich im Schrank: "+anzahl+" T-Shirts.");
	}

}
