package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DatabaseFunctions 
{
	public static Connection connectToServer(String ipAdresse, String benutzerName, String passwort)
	{
		try
		{
			return DriverManager.getConnection(ipAdresse, benutzerName, passwort);			
		}
		catch(SQLInvalidAuthorizationSpecException e1)
		{
			Alert al_wrongPassword = new Alert(Alert.AlertType.WARNING, "Benutzername oder Passwort falsch.", ButtonType.OK);
			al_wrongPassword.setTitle("Fehler!");
			al_wrongPassword.show();
		}
		catch(SQLException e)
		{
			Alert al_connectionFailed = new Alert(Alert.AlertType.ERROR, "Verbindung konnte nicht hergestellt werden.", ButtonType.OK);
			al_connectionFailed.setTitle("Fehler!");
			al_connectionFailed.show();
		}
		return null;
	}
	
	public static void createNewDatabase(Connection conn)
	{
		try
		{
			Statement st = conn.createStatement();
			st.execute("drop database if exists tourdefrance2017");
			st.executeQuery("create database tourdefrance2017");
			Alert al_DBCreationSuccessful = new Alert(AlertType.INFORMATION, "Erstellen der Datenbank 'tourdefrance2017' erfolgreich.", ButtonType.OK);
			al_DBCreationSuccessful.setTitle("Hinweis");
			al_DBCreationSuccessful.showAndWait();
		}
		catch (SQLException e) 
		{
			Alert al_DBCreationFailed = new Alert(AlertType.ERROR, "Erstellen der Datenbank fehlgeschlagen.", ButtonType.OK);
			al_DBCreationFailed.setTitle("Fehler");
			al_DBCreationFailed.show();
		}
	}
	
	public static String createNewTables()
	{
		try 
		{
			
			Statement stAnlegen = Main.databaseConnection.createStatement();
			String disableKeyChecks = "SET FOREIGN_KEY_CHECKS=0;";
			String enableKeyChecks = "SET FOREIGN_KEY_CHECKS=1;";
			Statement dbkc = Main.databaseConnection.prepareStatement(disableKeyChecks);
			dbkc.executeQuery(disableKeyChecks);
			
			//Löschen bereits vorhandener Tabellen
			stAnlegen.execute("drop table if exists user");
			stAnlegen.execute("drop table if exists fahrer");
			stAnlegen.execute("drop table if exists etappen");
			stAnlegen.execute("drop table if exists teams");
			stAnlegen.execute("drop table if exists ranking");
			stAnlegen.execute("drop table if exists tipps");
			stAnlegen.execute("drop table if exists etappenart");
			stAnlegen.execute("drop table if exists ergebnissegelb");
			stAnlegen.execute("drop table if exists ergebnissegruen");
			stAnlegen.execute("drop table if exists ergebnisseberg");
					
				//Anlegen der Tabellen
				stAnlegen.execute("create table user(userID INT(11) NOT NULL AUTO_INCREMENT, userName VARCHAR(100), sessionID VARCHAR(50), vorname VARCHAR(50), nachname VARCHAR(50), passwort VARCHAR(50), angelegt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, Primary KEY(userID));");
				stAnlegen.execute("create table etappenart(artID INT(11) AUTO_INCREMENT, bezeichnung VARCHAR(50) DEFAULT NULL, PRIMARY KEY(artID));");
				stAnlegen.execute("create table fahrer(fahrerID INT(11) NOT NULL AUTO_INCREMENT, startnummer INT(11), fahrerVorname VARCHAR(50), fahrerNachname VARCHAR(50), team INT(11), aktiv TINYINT(1) DEFAULT 1, gesamtzeit TIME DEFAULT NULL, etappensiege INT(11) DEFAULT 0, punkteGruen INT(11) DEFAULT 0, punkteBerg INT(11) DEFAULT 0, PRIMARY KEY(fahrerID));");
				stAnlegen.execute("create table etappen(etappenID INT(11) AUTO_INCREMENT, etappennummer INT(11), datum DATETIME , startort VARCHAR(50), zielort VARCHAR (50), laenge DOUBLE, art INT(11), fahrerPlatz1 VARCHAR(50) DEFAULT NULL, siegerzeit TIME DEFAULT NULL, fahrerPlatz2 VARCHAR(50) DEFAULT NULL, fahrerPlatz3 VARCHAR(50) DEFAULT NULL, teamPlatz1 VARCHAR(50) DEFAULT NULL, teamPlatz2 VARCHAR(50) DEFAULT NULL, teamPlatz3 VARCHAR(50) DEFAULT NULL, fahrerGelb VARCHAR(50) DEFAULT NULL, fahrerGruen VARCHAR(50) DEFAULT NULL, fahrerBerg VARCHAR(50), dopingFahrer VARCHAR(50) DEFAULT NULL, dopingTeam VARCHAR(50) DEFAULT NULL, PRIMARY KEY(etappenID), FOREIGN KEY(art) REFERENCES etappenart(artID));");
				stAnlegen.execute("create table teams(teamID INT(11) AUTO_INCREMENT, teamName VARCHAR(50), teamBuildUrl VARCHAR(100) DEFAULT NULL, PRIMARY KEY(teamID));");
				stAnlegen.execute("create table ranking(rankingID INT(11) AUTO_INCREMENT, datum DATETIME, userID INT(11), punkte INT(11) DEFAULT 0, platz INT(11) DEFAULT 0, PRIMARY KEY(rankingID), FOREIGN KEY (userID) REFERENCES user(userID) );");
				stAnlegen.execute("create table tipps(tippID INT(11) AUTO_INCREMENT, userID INT(11), etappenID INT(11) , fahrerPlatz1 VARCHAR(50) DEFAULT NULL, fahrerPlatz2 VARCHAR(50) DEFAULT NULL,fahrerPlatz3 VARCHAR(50) DEFAULT NULL,teamPlatz1 VARCHAR(50) DEFAULT NULL,teamPlatz2 VARCHAR(50) DEFAULT NULL, teamPlatz3 VARCHAR(50) DEFAULT NULL,fahrerGelb VARCHAR(50) DEFAULT NULL, fahrerGruen VARCHAR(50) DEFAULT NULL,fahrerBerg VARCHAR(50) DEFAULT NULL,fahrerDoping VARCHAR(50) DEFAULT NULL, teamDoping VARCHAR(50) DEFAULT NULL, PRIMARY KEY(tippID), FOREIGN KEY(userID) REFERENCES user(userID), FOREIGN KEY (etappenID) REFERENCES etappen(etappenID) );");
				stAnlegen.execute("CREATE TABLE ergebnisseberg(ergebnisID INT(11) NOT NULL AUTO_INCREMENT, etappe INT(11) NOT NULL, startnummer INT(11) NOT NULL, punkteTemp VARCHAR(50) NOT NULL COLLATE 'utf8_german2_ci', punkte INT(11) NOT NULL, PRIMARY KEY (`ergebnisID`)) COLLATE='utf8_german2_ci' ENGINE=InnoDB AUTO_INCREMENT=300;");
				stAnlegen.execute("CREATE TABLE ergebnissegelb (ergebnisID INT(11) NOT NULL AUTO_INCREMENT, etappe INT(11) NOT NULL, platz INT(11) NOT NULL, startnummer INT(11) NOT NULL, zeit VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci', PRIMARY KEY (`ergebnisID`)) COLLATE='utf8_german2_ci' ENGINE=InnoDB AUTO_INCREMENT=5356;");
				stAnlegen.execute("CREATE TABLE ergebnissegruen (ergebnisID INT(11) NOT NULL AUTO_INCREMENT, etappe INT(11) NOT NULL, startnummer INT(11) NOT NULL, punkteTemp VARCHAR(50) NOT NULL COLLATE 'utf8_german2_ci', punkte INT(11) NOT NULL, PRIMARY KEY (`ergebnisID`)) COLLATE='utf8_german2_ci' ENGINE=InnoDB AUTO_INCREMENT=620;");
				
				
				
				//Die Tabelle "etappenart" mit Initialwerten füllen
				stAnlegen.executeQuery("INSERT INTO `etappenart`(`bezeichnung`) VALUES ('Einzelzeitfahren');");
				stAnlegen.executeQuery("INSERT INTO `etappenart`(`bezeichnung`) VALUES ('Flachetappe');");
				stAnlegen.executeQuery("INSERT INTO `etappenart`(`bezeichnung`) VALUES ('Gebirge');");
				stAnlegen.executeQuery("INSERT INTO `etappenart`(`bezeichnung`) VALUES ('Hügelig');");
				
				dbkc.executeQuery(enableKeyChecks);
			
				
				
				Alert al_DBCreationSuccessful = new Alert(AlertType.INFORMATION, "Erstellen der Tabellen in 'tourdefrance2017' erfolgreich.", ButtonType.OK);
				al_DBCreationSuccessful.setTitle("Hinweis");
				al_DBCreationSuccessful.showAndWait();
				
		} catch (SQLException e) {
			
			Alert al_DBCreationFailed = new Alert(AlertType.ERROR, "Erstellen der Tabellen in 'tourdefrance2017' fehlgeschlagen.", ButtonType.OK);
			al_DBCreationFailed.setTitle("Fehler");
			al_DBCreationFailed.show();
			
		}
		return null;
	}
	
	public static Connection connectToDatabase(Connection conn, String databaseName, String username, String password)
	{
		try 
		{
			//Verbindung über die Datenbank-URL in Kombination mit Benutzername und Passwort
			Connection databaseConnection = Main.databaseConnection = DriverManager.getConnection(Main.serverConnection.getMetaData().getURL()+"/"+
																		databaseName, username, password);
			return databaseConnection;
			
		} 
		catch(SQLInvalidAuthorizationSpecException e1)
		{
			Alert al_wrongPasswordOrUsername = new Alert(AlertType.WARNING, "Benutzername oder Passwort falsch.", ButtonType.OK);
			al_wrongPasswordOrUsername.setTitle("Fehler");
			al_wrongPasswordOrUsername.show();
		}
		catch (SQLException e) 
		{
			Alert al_DBConnectionFailed = new Alert(AlertType.ERROR, "Verbindung zur Datenbank fehlgeschlagen.", ButtonType.OK);
			al_DBConnectionFailed.setTitle("Fehler");
			al_DBConnectionFailed.show();
		}
		return null;
	}
	
	public static List<String> getAvailableDatabases(Connection conn)
	{
		List<String> listOfAvailableDatabases = new LinkedList<String>(); 
		
		
		try {
			//ResultSet mit allen Datenbanken erstellen
			ResultSet rs = Main.serverConnection.getMetaData().getCatalogs();
			
			//Jeden Datenbanknamen in die Liste speichern
			while (rs.next())
			{
			   listOfAvailableDatabases.add(rs.getString("TABLE_CAT"));
			}
		} 
		catch (SQLException e)
		{
			Alert al_DBConnectionFailed = new Alert(AlertType.ERROR, "Datenbankliste konnte nicht erstellt werden.", ButtonType.OK);
			al_DBConnectionFailed.setTitle("Fehler");
			al_DBConnectionFailed.show();
		}
		return listOfAvailableDatabases;
	}
	
	//Funktion, um Daten aus einer CSV-Datei einzulesen
	//Der Parameter "table" bestimmt, in welche Tabelle eingefügt wird, was sich auf das SQL-Statement auswirkt

	public static void importFromCSV(String fileAddress, String table)
	{		
		try 
		{
			// FileReader für die CSV-Datei erstellen, der über die absolute Adresse zugreift
			FileReader fr = new FileReader(fileAddress);
			BufferedReader br = new BufferedReader(fr, 500);
			
			PreparedStatement stm = null;		
			String line;
			String[] lineSplit;
			String sqlString;
			
			
			//Jede Zeile einlesen und die durch Tabulatoren getrennten Einträge in einem Stringarray speichern.
			//Dann die Einträge in die Tabelle der Datenbank eintragen.
			while(true)
			{
				line = br.readLine();
				if(line == null)
					break;
				lineSplit = line.split("	");
				sqlString ="";
				
				//Für jede Zeile werden die einzelnen Einträge in die jeweiligen Tabellenspalten eingetragen
				//Je nachdem welcher String "table" als Parameter übergeben wurde, ändert sich nur das SQL-Statement
				switch(table)
				{
				case "user": sqlString = "INSERT INTO `user`(`userName`, `sessionID`,`vorname`, `nachname`, `passwort`) VALUES ('"+lineSplit[1]+"','"+lineSplit[2]+"','"+lineSplit[3]+"','"+lineSplit[4]+"','"+lineSplit[5]+"');"; 
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.executeQuery();
					break;
					
				case "tipps": 
					sqlString= "INSERT INTO `tipps`(`userID`, `etappenID`, `fahrerPlatz1`, `fahrerPlatz2`, `fahrerPlatz3`, `teamPlatz1`, `teamPlatz2`, `teamPlatz3`, `fahrerGelb`, `fahrerGruen`, `fahrerBerg`, `fahrerDoping`, `teamDoping`) VALUES ('"+Integer.parseInt(lineSplit[1])+"','"+Integer.parseInt(lineSplit[2]) +"', '"+lineSplit[3]+"', '"+lineSplit[4]+"', '"+lineSplit[5]+"', '"+lineSplit[6]+"', '"+lineSplit[7]+"', '"+lineSplit[8]+"', '"+lineSplit[9]+"', '"+lineSplit[10]+"', '"+lineSplit[11]+"', '"+lineSplit[12]+"', '"+lineSplit[13]+"');"; 
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.executeQuery();	
					
					
					break;
					
				case "etappen": 
					sqlString = "INSERT INTO `etappen`(`etappennummer`, `datum`, `startort`, `zielort`, `laenge`, `art`) VALUES (?,?,?,?,?,?);";
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.setInt(1, Integer.parseInt(lineSplit[1]));
					stm.setTimestamp(2, java.sql.Timestamp.valueOf(lineSplit[2]));
					stm.setString(3, lineSplit[3]);
					stm.setString(4, lineSplit[4]);
					stm.setDouble(5, Double.parseDouble(lineSplit[5]));
					stm.setInt(6, Integer.parseInt(lineSplit[6]));
					stm.executeQuery();
					break;
					
				case "fahrer": 
					sqlString = "INSERT INTO `fahrer`(`startnummer`, `fahrerVorname`, `fahrerNachname`, `team`, `aktiv`) VALUES ('"+Integer.parseInt(lineSplit[1])+"', '"+lineSplit[2]+"', '"+lineSplit[3]+"', '"+Integer.parseInt(lineSplit[4])+"', '"+Integer.parseInt(lineSplit[5])+"')";
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.executeQuery();
					break;
					
				case "teams":
					sqlString = "INSERT INTO `teams`(`teamName`, `teamBuildUrl`) VALUES ('"+lineSplit[1]+"', '"+lineSplit[2]+"' );";
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.executeQuery();
					break;
				}		
			
			}
			br.close();
		
			
			
		} 
		catch(FileNotFoundException e2)
		{
			Alert al_FilesNotFound = new Alert(Alert.AlertType.WARNING, "Angegebene Datei(en) konnten nicht gefunden werden.", ButtonType.OK);
			al_FilesNotFound.setTitle("Warnung");
			al_FilesNotFound.showAndWait();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
	
	}
	
	
	
	public static String openFileChooser()
	{
		Stage stage = new Stage();
		String directory;
		FileChooser fileChooser = new FileChooser();
		
        File selectedDirectory = fileChooser.showOpenDialog(stage);
         
        if(selectedDirectory == null)
        {
            directory = "Keine Datei ausgewählt";
        }
        else
        {
           directory = selectedDirectory.getAbsolutePath();
        }
        return directory;
	}

	public static void importResultsFromCSV(String filename, String table)
	{
		try 
		{
			// FileReader für die CSV-Datei erstellen, der über die absolute Adresse zugreift
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr, 200);
			
			PreparedStatement stm = null;		
			String line;
			String[] lineSplit;
			String sqlString;
			
			
			//Jede Zeile einlesen und die durch Tabulatoren getrennten Einträge in einem Stringarray speichern.
			//Dann die Einträge in die Tabelle der Datenbank eintragen.
			while(true)
			{
				line = br.readLine();
				if(line == null)
					break;
				lineSplit = line.split("	");
				sqlString ="";
				
				//Für jede Zeile werden die einzelnen Einträge in die jeweiligen Tabellenspalten eingetragen
				//Je nachdem welcher String "table" als Parameter übergeben wurde, ändert sich nur das SQL-Statement
				switch(table)
				{
				case "gesamtwertung": sqlString = "INSERT INTO `ergebnissegelb`(`etappe`, `platz`, `startnummer`, `zeit`) VALUES (?,?,?,?);"; 
					stm = Main.databaseConnection.prepareStatement(sqlString);
					stm.setInt(1, Integer.parseInt(lineSplit[1]));
					stm.setInt(2, Integer.parseInt(lineSplit[2]));
					stm.setInt(3, Integer.parseInt(lineSplit[3]));
					stm.setString(4, lineSplit[4]);
					stm.executeQuery();
					break;
					
				case "bergwertung": sqlString = "INSERT INTO `ergebnisseberg`(`etappe`, `startnummer`, `punkteTemp`, `punkte`) VALUES (?,?,?,?);"; 
				stm = Main.databaseConnection.prepareStatement(sqlString);
				stm.setInt(1, Integer.parseInt(lineSplit[1]));
				stm.setInt(2, Integer.parseInt(lineSplit[2]));
				stm.setString(3, lineSplit[3]);
				stm.setInt(4, Integer.parseInt(lineSplit[4]));
				stm.executeQuery();
				break;
				
				case "punktwertung": sqlString = "INSERT INTO `ergebnissegruen`(`etappe`, `startnummer`, `punkteTemp`, `punkte`) VALUES (?,?,?,?);"; 
				stm = Main.databaseConnection.prepareStatement(sqlString);
				stm.setInt(1, Integer.parseInt(lineSplit[1]));
				stm.setInt(2, Integer.parseInt(lineSplit[2]));
				stm.setString(3, lineSplit[3]);
				stm.setInt(4, Integer.parseInt(lineSplit[4]));
				stm.executeQuery();
				break;
					
				
				}		
			}
			br.close();
			
			
			
						
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	/*Funktion, um die Tabellen nach dem Ergebnisimport zu aktualisieren. Der Parameter "etappennummer" gibt an, 
	 * auf Basis welcher Ergebnisdaten die Tabellen aktualisiert werden. Dabei werden folgende Schritte durchgeführt:
	 * 1. Die Siegerzeit in der Tabelle "etappen" wird aktualisiert
	 * 2. Die FahrerGelb, FahrerGruen und FahrerBerg werden in der Tabelle "etappen" aktualisiert.
	 * 3. Die Etappensiege in der Tabelle "fahrer" werden aktualisiert
	 * 4. Die PunkteGruen in der Tabelle "fahrer" werden aktualisiert
	 * 5. Die PunkteBerg in der Tabelle "fahrer" werden aktualisiert
	 * 6. Die Spalten fahrerPlatz 1-3 und teamPlatz 1-3 in der Tabelle "etappen" werden aktualisiert.
	 * 7. Für die NOCH AKTIVEN Fahrer werden die Gesamtzeiten ermittelt.
	 * 8. Fahrer, für die kein Ergebnis in der Tabelle ergebnissegelb vorliegt, werden auf inaktiv gesetzt.
	 */
	public static void updateTables(int etappennummer)
	{		
		try 
		{			
			Statement stm = Main.databaseConnection.createStatement();
						
			//SIEGERZEIT AKTUALISIEREN
			ResultSet rs_siegerzeit = stm.executeQuery("SELECT platz, zeit FROM ergebnissegelb WHERE etappe="+etappennummer+" AND platz=1;");
			rs_siegerzeit.next();
			String siegerzeit = rs_siegerzeit.getString(2);
			int siegerzeitSekunden = convertResultStringToTime(siegerzeit);
			String siegerzeitTime = convertTimeToString(siegerzeitSekunden);
			
			String sqlstringSiegerzeit = "UPDATE etappen SET siegerzeit=? WHERE etappennummer="+etappennummer+";";
			PreparedStatement pstmSiegerzeit = Main.databaseConnection.prepareStatement(sqlstringSiegerzeit);
			pstmSiegerzeit.setTime(1, Time.valueOf(siegerzeitTime));
			pstmSiegerzeit.executeQuery();
		
			
			
			//FahrerGELB/GRUEN/BERG AKTUALISIEREN (Außer bei der 1. Etappe)
			
					//Gelbes Trikot aktualisieren
					ResultSet rs_gelbesTrikot = stm.executeQuery("SELECT fahrerVorname, fahrerNachname, aktiv, gesamtzeit FROM fahrer WHERE aktiv=1 ORDER BY gesamtzeit ASC;");
					rs_gelbesTrikot.first();
					String gelbesTrikotName = rs_gelbesTrikot.getString(1)+" "+rs_gelbesTrikot.getString(2);
					
					String sqlStringGelb = "UPDATE etappen SET fahrerGelb=? WHERE etappennummer="+etappennummer+";";
					PreparedStatement pstmGelb = Main.databaseConnection.prepareStatement(sqlStringGelb);
					pstmGelb.setString(1, gelbesTrikotName);
					pstmGelb.executeQuery();
					
					//Grünes Trikot aktualisieren
					ResultSet rs_gruenesTrikot = stm.executeQuery("SELECT fahrerVorname, fahrerNachname, aktiv, punkteGruen FROM fahrer WHERE aktiv=1 ORDER BY punkteGruen ASC ;");
					rs_gruenesTrikot.first();
					String gruenesTrikotName = rs_gruenesTrikot.getString(1)+" "+rs_gruenesTrikot.getString(2);
					
					String sqlStringGruen = "UPDATE etappen SET fahrerGruen=? WHERE etappennummer="+etappennummer+";";
					PreparedStatement pstmGruen = Main.databaseConnection.prepareStatement(sqlStringGruen);
					pstmGruen.setString(1, gruenesTrikotName);
					pstmGruen.executeQuery();
					
					//Bergtrikot aktualisieren
					ResultSet rs_bergTrikot = stm.executeQuery("SELECT fahrerVorname, fahrerNachname, aktiv, punkteBerg FROM fahrer WHERE aktiv=1 ORDER BY punkteBerg ASC ;");
					rs_bergTrikot.first();
					String bergTrikotName = rs_bergTrikot.getString(1)+" "+rs_bergTrikot.getString(2);
					
					String sqlStringBerg = "UPDATE etappen SET fahrerBerg=? WHERE etappennummer="+etappennummer+";";
					PreparedStatement pstmBerg = Main.databaseConnection.prepareStatement(sqlStringBerg);
					pstmBerg.setString(1, bergTrikotName);
					pstmBerg.executeQuery();
			
				
			//ETAPPENSIEGE AKTUALISIEREN
			ResultSet rsGes = stm.executeQuery("SELECT startnummer FROM ergebnissegelb WHERE platz = 1 AND etappe="+etappennummer+";");
			rsGes.next();
			int startnummer = rsGes.getInt(1);
			rsGes = stm.executeQuery("SELECT etappensiege FROM fahrer WHERE startnummer = "+startnummer+";");
			rsGes.next();
			int etappensiege = rsGes.getInt(1);
			//Etappensiege des entpsrechenden Fahrers um eins hochzählen
			etappensiege+=1;
			stm.executeQuery("UPDATE fahrer SET etappensiege="+etappensiege+" WHERE startnummer = "+startnummer+";");
		
			
			//PUNKTEGRUEN AKTUALISIEREN
			ResultSet rsGruen = stm.executeQuery("SELECT startnummer, punkte FROM ergebnissegruen WHERE etappe="+etappennummer+";");
			
			while(rsGruen.next() != false)
			{
				startnummer = rsGruen.getInt(1);
				int punkteErgebnisGruen = rsGruen.getInt(2);
				
				ResultSet punkteGruenBisher = stm.executeQuery("SELECT punkteGruen FROM fahrer WHERE startnummer="+startnummer+";");
				punkteGruenBisher.next();
				
				int punkteBisherGruen = punkteGruenBisher.getInt(1);
				int neuePunkteGruen = punkteBisherGruen+punkteErgebnisGruen;
				
				stm.executeQuery("UPDATE fahrer SET punkteGruen="+neuePunkteGruen+" WHERE startnummer="+startnummer+";");
			}
			
			
			//PUNKTEBERG AKTUALISIEREN
			ResultSet rsBerg = stm.executeQuery("SELECT startnummer, punkte FROM ergebnisseberg WHERE etappe="+etappennummer+";");
			while(rsBerg.next() != false)
			{
				startnummer = rsBerg.getInt(1);
				int punkteErgebnisBerg = rsBerg.getInt(2);
				
				ResultSet punkteBergBisher = stm.executeQuery("SELECT punkteBerg FROM fahrer WHERE startnummer="+startnummer+";");
				punkteBergBisher.next();
				
				int punkteBisherBerg = punkteBergBisher.getInt(1);
				int neuePunkteBerg = punkteBisherBerg+punkteErgebnisBerg;
				
				stm.executeQuery("UPDATE fahrer SET punkteBerg="+neuePunkteBerg+" WHERE startnummer="+startnummer+";");
			}
			
			
			//FahrerPlatz UND TeamPlatz 1/2/3 AKTUALISIEREN
			ResultSet rsFahrerPlatz = stm.executeQuery("SELECT etappe, platz, startnummer FROM ergebnissegelb WHERE platz=1 OR platz=2 OR platz=3 AND etappe="+etappennummer+";");
			
			while(rsFahrerPlatz.next() != false)
			{
				int platz = rsFahrerPlatz.getInt(2);
				startnummer = rsFahrerPlatz.getInt(3);
				ResultSet rsFahrer = stm.executeQuery("SELECT fahrerVorname, fahrerNachname, team FROM fahrer WHERE startnummer="+startnummer+";");
				rsFahrer.next();
				String fahrername = rsFahrer.getString(1)+" "+rsFahrer.getString(2);
				String teamnameID = rsFahrer.getString(3);
				ResultSet rsTeam = stm.executeQuery("SELECT teamName FROM teams WHERE teamID="+teamnameID+";");
				rsTeam.next();
				String teamname = rsTeam.getString(1);
				
				String sqlStringFahrer = null;
				String sqlStringTeam = null;
				if(platz == 1)
				{
					sqlStringFahrer = "UPDATE etappen SET fahrerPlatz1=? WHERE etappennummer="+etappennummer+";";
					sqlStringTeam = "UPDATE etappen SET teamPlatz1=? WHERE etappennummer="+etappennummer+";";
				}
					
					
				if(platz == 2)
				{
					sqlStringFahrer = "UPDATE etappen SET fahrerPlatz2=? WHERE etappennummer="+etappennummer+";";
					sqlStringTeam = "UPDATE etappen SET teamPlatz2=? WHERE etappennummer="+etappennummer+";";
				}
					
				if(platz == 3)
				{
					sqlStringFahrer = "UPDATE etappen SET fahrerPlatz3=? WHERE etappennummer="+etappennummer+";";
					sqlStringTeam = "UPDATE etappen SET teamPlatz3=? WHERE etappennummer="+etappennummer+";";
				}
				
				PreparedStatement pstm = Main.databaseConnection.prepareStatement(sqlStringFahrer);
				pstm.setString(1, fahrername);
				pstm.executeQuery();
				
				pstm = Main.databaseConnection.prepareStatement(sqlStringTeam);
				pstm.setString(1, teamname);
				pstm.executeQuery();
				
				
			}
			
			
			//GESAMTZEITEN AKTUALISIEREN
			ResultSet rsErg = stm.executeQuery("SELECT startnummer, zeit FROM ergebnissegelb WHERE etappe="+etappennummer+";");
				
			while(rsErg.next() != false)
			{
				startnummer = rsErg.getInt(1);
				String zeit = rsErg.getString(2);
				int ergZeitInSek = convertResultStringToTime(zeit);
				
				//Gesamtzeit des Fahrers in der Tabelle fahrer ermitteln
				ResultSet rsFahrer = stm.executeQuery("SELECT gesamtzeit FROM fahrer WHERE startnummer="+startnummer+";");
				rsFahrer.next();
				
				String zeitBisher = rsFahrer.getString(1);
				int zeitBisherSek;
				if(zeitBisher == null)
					zeitBisherSek = convertResultStringToTime("0h 0' 0\"");
				else
				{
					if(zeitBisher.contains(":"))
					{
						zeitBisherSek = convertSQLTimeFormatToTime(zeitBisher);
					}
					else
					{
						zeitBisherSek = convertResultStringToTime(zeitBisher);
					}
				}
					
							
				//neue Gesamtzeit berechnen und in einen String konvertieren
				int neueGesamtzeit = ergZeitInSek+zeitBisherSek;
				String neueGesamtZeitString = convertTimeToString(neueGesamtzeit);

				//Neuen Zeitstring in fahrer.gesamtzeit festhalten
				String sqlString = "UPDATE fahrer SET gesamtzeit = ? WHERE startnummer="+startnummer+";";
				PreparedStatement pstm = Main.databaseConnection.prepareStatement(sqlString);
				pstm.setTime(1, Time.valueOf(neueGesamtZeitString));
		
				pstm.executeQuery();
			
				
				
			}
			
			//AUSGESCHIEDENE FAHRER "INAKTIVIEREN"
			ResultSet rs_aktiveFahrer = stm.executeQuery("SELECT startnummer FROM ergebnissegelb WHERE etappe="+etappennummer+";");
			
			//Zunächst alle Fahrer als inaktiv kennzeichnen
			stm.executeQuery("UPDATE fahrer SET aktiv=0");
			//Dann in der Schleife alle Startnummern, die ein Ergebnis haben, wieder aktivieren
			while(rs_aktiveFahrer.next() != false)
			{
				startnummer = rs_aktiveFahrer.getInt(1);
				stm.executeQuery("UPDATE fahrer SET aktiv=1 WHERE startnummer="+startnummer+";");
			}
		
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			e.getSQLState();
			e.getErrorCode();
		}
		
	}
	
	public static LinkedList<String> printEtappenResults(int nummer)
	{
		LinkedList<String> liste = new LinkedList<>();
		try 
		{
			Statement stm = Main.databaseConnection.createStatement();
			ResultSet rs_ergebnisseAusgeben = stm.executeQuery("SELECT etappennummer, datum, fahrerPlatz1, siegerzeit, teamPlatz1 FROM etappen WHERE etappennummer="+nummer+";");
			rs_ergebnisseAusgeben.next();
			String etappennummer = String.valueOf(rs_ergebnisseAusgeben.getInt(1));
			String datum = DateFormat.getDateTimeInstance().format(rs_ergebnisseAusgeben.getDate(2)).toString();
			String etappensieger = rs_ergebnisseAusgeben.getString(3);
			String siegerzeit = rs_ergebnisseAusgeben.getTime(4).toString();
			String siegerteam = rs_ergebnisseAusgeben.getString(5);
			
			liste.add(etappennummer);
			liste.add(datum);
			liste.add(etappensieger);
			liste.add(siegerzeit);
			liste.add(siegerteam);	
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return liste;
	}
	
	public static int convertResultStringToTime(String zeit)
	{
		//Zeitstring zerlegen und in Sekunden umrechnen
		
		zeit = zeit.replaceAll("h", "");
		zeit = zeit.replaceAll("'", "");
		zeit = zeit.replaceAll("\"", "");
		
		String[] zeitSplit = zeit.split(" ");
		
		int stunden = Integer.parseInt(zeitSplit[0]);
		int minuten = Integer.parseInt(zeitSplit[1]);
		int sekunden = Integer.parseInt(zeitSplit[2]);
		int gesamtzeitInSek = stunden*3600+minuten*60+sekunden;
		
		return gesamtzeitInSek;
	}
	
	public static int convertSQLTimeFormatToTime(String zeit)
	{
		//Zeitstring zerlegen und in Sekunden umrechnen
		
		zeit = zeit.replaceAll(":", " ");
		
		String[] zeitSplit = zeit.split(" ");
		
		int stunden = Integer.parseInt(zeitSplit[0]);
		int minuten = Integer.parseInt(zeitSplit[1]);
		int sekunden = Integer.parseInt(zeitSplit[2]);
		int gesamtzeitInSek = stunden*3600+minuten*60+sekunden;
		
		return gesamtzeitInSek;
	}
	
	public static String convertTimeToString(int zahl)
	{
		int stunden = zahl/3600;
		zahl = zahl-stunden*3600;
		int minuten = zahl/60;
		zahl = zahl-minuten*60;
		int sekunden = zahl;
	
		String stundenString;
		String minutenString;
		String sekundenString;
		
		if(stunden < 10)
			stundenString = "0"+String.valueOf(stunden)+":";
		else
			stundenString = String.valueOf(stunden)+":";
		
		if(minuten < 10)
			minutenString = "0"+String.valueOf(minuten)+":";
		else
			minutenString = String.valueOf(minuten)+":";
		
		if(sekunden < 10)
			sekundenString = "0"+String.valueOf(sekunden);
		else
			sekundenString = String.valueOf(sekunden);
		
		String zeitString = stundenString + minutenString + sekundenString;
		return zeitString;		
	}

	public static void createRanking(int etappenzahl)
	{
		//Timestamp erstellen
		Timestamp datum = new Timestamp(System.currentTimeMillis());
	
		try 
		{
			Statement stm = Main.databaseConnection.createStatement();
			ResultSet rs_Tipps = stm.executeQuery("SELECT userID, fahrerPlatz1, fahrerPlatz2, fahrerPlatz3, teamPlatz1, teamPlatz2, teamPlatz3, fahrerGelb, fahrerGruen, fahrerBerg, fahrerDoping, teamDoping FROM tipps WHERE etappenID="+etappenzahl+";");
			
			ResultSet rs_Etappen = stm.executeQuery("SELECT fahrerPlatz1, fahrerPlatz2, fahrerPlatz3, teamPlatz1, teamPlatz2, teamPlatz3, fahrerGelb, fahrerGruen, fahrerBerg, dopingFahrer, dopingTeam FROM etappen WHERE etappennummer="+etappenzahl+";");
			rs_Etappen.next();
			ResultSet rs_TippsRest = stm.executeQuery("SELECT userID, etappenID FROM tipps WHERE etappenID="+etappenzahl+";");
			rs_TippsRest.next();
			stm.close();
			
			int userID = 0;
			
			
			//Strings erzeugen, die das tatsächliche Ergebnis aus der Tabelle "etappen" enthalten.
			String fahrerPlatz1_Etappen = rs_Etappen.getString(1);
			String fahrerPlatz2_Etappen = rs_Etappen.getString(2);
			String fahrerPlatz3_Etappen = rs_Etappen.getString(3);
			String teamPlatz1_Etappen = rs_Etappen.getString(4);
			String teamPlatz2_Etappen = rs_Etappen.getString(5);
			String teamPlatz3_Etappen = rs_Etappen.getString(6);
			String fahrerGelb_Etappen = rs_Etappen.getString(7);
			String fahrerGruen_Etappen = rs_Etappen.getString(8);
			String fahrerBerg_Etappen = rs_Etappen.getString(9);
			String fahrerDoping_Etappen = rs_Etappen.getString(10);
			if(fahrerDoping_Etappen== null)
				fahrerDoping_Etappen ="leer";
			String teamDoping_Etappen = rs_Etappen.getString(11);
			if(teamDoping_Etappen == null)
				teamDoping_Etappen = "leer";
		
			int userCounter=0;
			
			//Jeden Tipp einzeln auswerten
			while(rs_Tipps.next() != false)
			{
				userID = rs_Tipps.getInt(1);
				int gesamtPunkteTipp = 0;
				//Vergleich der Strings, bei Gleichheit wird die entsprechende Punktzahl auf das Konto des Spielers addiert
				if(fahrerPlatz1_Etappen.compareTo(rs_Tipps.getString(2)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp + 15;
			
				if(fahrerPlatz2_Etappen.compareTo(rs_Tipps.getString(3)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp + 12;
				
				if(fahrerPlatz3_Etappen.compareTo(rs_Tipps.getString(4)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+10;
			
				if(teamPlatz1_Etappen.compareTo(rs_Tipps.getString(5)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+15;
				
				if(teamPlatz2_Etappen.compareTo(rs_Tipps.getString(6)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+12;
				
				if(teamPlatz3_Etappen.compareTo(rs_Tipps.getString(7)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+10;
			
				if(fahrerGelb_Etappen.compareTo(rs_Tipps.getString(8)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+10;
				
				if(fahrerGruen_Etappen.compareTo(rs_Tipps.getString(9)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+8;
				
				if(fahrerBerg_Etappen.compareTo(rs_Tipps.getString(10)) == 0)
					gesamtPunkteTipp = gesamtPunkteTipp+8;
				
				
				if((teamDoping_Etappen.compareTo(rs_Tipps.getString(11)) == 0) && (rs_Tipps.getString(11) == "leer"))
				{
					gesamtPunkteTipp = gesamtPunkteTipp+20;
				}				
				else
				{
					if((teamDoping_Etappen.compareTo(rs_Tipps.getString(11)) == 0) && (rs_Tipps.getString(11) != "leer"))
					{}
					else
					{
						gesamtPunkteTipp = gesamtPunkteTipp-3;
					}
				}
				
				if((teamDoping_Etappen.compareTo(rs_Tipps.getString(12)) == 0) && (rs_Tipps.getString(12) == "leer"))
				{
					gesamtPunkteTipp = gesamtPunkteTipp+20;
				}				
				else
				{
					if((teamDoping_Etappen.compareTo(rs_Tipps.getString(12)) == 0) && (rs_Tipps.getString(12) != "leer"))
					{}
					else
					{
						gesamtPunkteTipp = gesamtPunkteTipp-3;
					}
				}			
				
				String sqlStringRanking = "INSERT INTO ranking(datum, userID, punkte) VALUES (?,?, "+gesamtPunkteTipp+");";
				PreparedStatement pstmRanking = Main.databaseConnection.prepareStatement(sqlStringRanking);
				pstmRanking.setTimestamp(1, datum);
				pstmRanking.setInt(2, userID);
				pstmRanking.executeQuery();
				//Punkte auf die userID mappen
				//map.put(rs_TippsRest.getInt(1), gesamtPunkteTipp);
				pstmRanking.close();
				userCounter+=1;
				
				
			}
			//Platzierungen hinzufügen
			//Einträge absteigend nach Punkten sortieren
			String sqlStringPlaetze = "SELECT userID, MAX(punkte) FROM ranking GROUP BY userID ORDER BY MAX(punkte);";
			PreparedStatement pstm = Main.databaseConnection.prepareStatement(sqlStringPlaetze);
			ResultSet rs_rankingSorted = pstm.executeQuery();
			
			while(rs_rankingSorted.next() != false)
			{
				int userIDPlatz = rs_rankingSorted.getInt(1);
				Statement stmUpdatePlatz = Main.databaseConnection.createStatement();
				stmUpdatePlatz.executeQuery("UPDATE ranking SET platz="+userCounter+" WHERE userID="+userIDPlatz+";");
				userCounter-=1;
			}
			
								
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static String getStageTableString() {
		try {
			if(Main.databaseConnection == null || Main.databaseConnection.isClosed()) {
                return ERROR_UNABLE_TO_LOAD_DATA;
            }
			Statement statement = Main.databaseConnection.createStatement();
			List<String[]> stages = new ArrayList<>();
			int[] longestElements = new int[7];
			stages.add(new String[]{"Etappe", "Datum", "Uhrzeit", "Startort", "Zielort", "Länge", "Etappenart"});
			for(int i = 0; i < longestElements.length; i++) {
				longestElements[i] = stages.get(0)[i].length();
			}

			ResultSet resultSet = statement.executeQuery(
					"SELECT etappenID, datum, startort, zielort, laenge, bezeichnung" +
							" FROM etappen, etappenart" +
							" WHERE artID = art" +
							" ORDER BY etappenID"
			);

			while (resultSet.next()) {
				String[] line = new String[7];
				line[0] = String.valueOf(resultSet.getInt("etappenID"));
				java.sql.Date date = resultSet.getDate("datum");
				LocalDateTime localDateTime = LocalDateTime.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
				line[1] = localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
				line[2] = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				line[3] = resultSet.getString("startort");
				line[4] = resultSet.getString("zielort");
				line[5] = resultSet.getString("laenge");
				line[6] = resultSet.getString("bezeichnung");
				for(int i = 0; i < longestElements.length; i++) {
                    longestElements[i] = Math.max(line[i].length(), longestElements[i]);
                }
				stages.add(line);
			}

			String[] separator = new String[7];
			for(int i = 0; i < longestElements.length; i++) {
			    String string = "";
			    for(int s = 0; s < longestElements[i] + 3; s++) {
			        string += "=";
                }
			    separator[i] = string;
			    longestElements[i] += 3;
            }

            stages.add(0, separator);
			stages.add(2, separator);

			StringBuilder builder = new StringBuilder();
			for(String[] line : stages) {
			    for(int i = 0; i < line.length; i++) {
			        builder.append(line[i]);
			        if(i < line.length - 1) {
			            for(int s = 0; s < longestElements[i] - line[i].length(); s++) {
			                builder.append(" ");
                        }
                    }
                }
				builder.append("\n");
			}

            return builder.toString();

		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return ERROR_UNABLE_TO_LOAD_DATA;
	}
	
	private static final String ERROR_UNABLE_TO_LOAD_DATA = "Daten konnten nicht geladen werden";
	
}

