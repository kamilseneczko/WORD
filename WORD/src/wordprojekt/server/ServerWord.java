package wordprojekt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ServerWord {

	public static void main(String[] args) {

		try {
			int i = 1;
			ServerSocket s = new ServerSocket(8185);

			// --------------------Polaczenie z baza danych
			// ---------------------------
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.println("Sterowniki za³adowane");

			Connection polaczenie = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "nowy",
					"orcl");

			Statement statm = polaczenie.createStatement();

			System.out.println("Po³¹czenie nawi¹zane");
			// -------------------- Koniec polaczenia z baza danych
			// --------------------

			System.out.println("Serwer wlaczony, czekam na polaczenie.");
			JOptionPane.showMessageDialog(null,"Serwer zosta³ uruchomiony","Serwer", JOptionPane.INFORMATION_MESSAGE);
			while (true) {
				Socket incoming = s.accept();
				System.out.println("Udane polaczenie z klientem: " + i);
				Runnable r = new ThreadedEchoHandler(incoming, statm);
				Thread t = new Thread(r);
				t.start();
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class ThreadedEchoHandler implements Runnable {

	private Socket incoming;
	private Statement instatm;

	public ThreadedEchoHandler(Socket i, Statement s) {
		incoming = i;
		instatm = s;
	}

	@Override
	public void run() {
		try {
			try {

				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				// Scanner in = new Scanner(inStream);
				// PrintWriter out = new PrintWriter(outStream, true);

				wyslij obiektWyslij = null;
				odbierz obiektOdbierz = new odbierz("obiekt odbierz");

				ObjectInputStream ois = new ObjectInputStream(incoming.getInputStream());
				ObjectOutputStream ous = new ObjectOutputStream(incoming.getOutputStream());

				ResultSet res;

				ArrayList<String> aLBaza = new ArrayList();

				while (true) {
					if (inStream.available() != 0) {
						obiektWyslij = (wyslij) ois.readObject();
						System.out.println(obiektWyslij.getZapytanie());

						if (obiektWyslij.getZapytanie().substring(0, 6).equals("SELECT")
								|| obiektWyslij.getZapytanie().substring(0, 6).equals("select")) {

							res = instatm.executeQuery(obiektWyslij.getZapytanie());

							ResultSetMetaData meta = res.getMetaData();

							while (res.next()) {

								for (int i = 1; i <= meta.getColumnCount(); i++) {
									// System.out.println(res.getString(i));
									aLBaza.add(res.getString(i));
								}

								obiektOdbierz.getListaOdebranych().add(new ArrayList(aLBaza));

								aLBaza.clear();
							}
							// System.out.println(obiektOdbierz.listaOdebranych.get(0));
							// System.out.println(obiektOdbierz.listaOdebranych.get(1));
							// System.out.println(obiektOdbierz.listaOdebranych.get(2));

							ous.writeObject(obiektOdbierz);

							obiektOdbierz.listaOdebranych.clear();

						} else {

							int wynik = 0;
							try {
								wynik = instatm.executeUpdate(obiektWyslij.getZapytanie());

								if (wynik != -1) {
									aLBaza.add("sukces");
									obiektOdbierz.getListaOdebranych().add(new ArrayList(aLBaza));
									aLBaza.clear();
									ous.writeObject(obiektOdbierz);
									obiektOdbierz.listaOdebranych.clear();
								}

							} catch (SQLException e) {
								System.out.println(e.getMessage());
							}

						}
					}

					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				incoming.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
