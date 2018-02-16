package wordprojekt.server;

import java.io.Serializable;
import java.util.ArrayList;

public class odbierz implements Serializable {

	private String zapytanie;

	public ArrayList<ArrayList<String>> listaOdebranych = new ArrayList<ArrayList<String>>();

	public ArrayList<ArrayList<String>> getListaOdebranych() {
		return listaOdebranych;
	}

	public void setListaOdebranych(ArrayList<ArrayList<String>> listaOdebranych) {
		this.listaOdebranych = listaOdebranych;
	}

	private static final long serialVersionUID = -1590808355799495667L;

	public String getZapytanie() {
		return zapytanie;
	}

	public void setZapytanie(String zapytanie) {
		this.zapytanie = zapytanie;
	}

	public odbierz(String name) {
		super();
		this.zapytanie = name;
	}

	@Override
	public String toString() {
		return "odbierz [" + zapytanie + "]";
	}

}