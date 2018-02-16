package wordprojekt.server;

import java.io.Serializable;

public class wyslij implements Serializable {
	
	
	private static final long serialVersionUID = -1590808355799495667L;
	private String zapytanie;

	public String getZapytanie() {
		return zapytanie;
	}

	public void setZapytanie(String zapytanie) {
		this.zapytanie = zapytanie;
	}

	public wyslij(String name) {
		super();
		this.zapytanie = name;
	}

	@Override
	public String toString() {
		return "wyslij [" + zapytanie + "]";
	}

}