
public class Gericht {
	
	String name;
	double basispreis;	
	
	public Gericht(String name, double basispreis) {		
		this.name = name;
		this.basispreis = basispreis;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBasispreis() {
		return basispreis;
	}
	public void setBasispreis(double basispreis) {
		this.basispreis = basispreis;
	}
	
	
	

}
