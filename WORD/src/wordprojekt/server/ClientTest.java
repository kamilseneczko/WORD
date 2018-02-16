package wordprojekt.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

public class ClientTest implements Bindable {
	public static void main(String args[]) {

		try {

			Socket socket = new Socket("localhost", 8185);

			odbierz odbierzob = null; // obiekt w ktorym beda informacje z bazy
										// zwrocone przez serwer

			wyslij wys = new wyslij("select * from ZDAJACY where HASLO_ZDAJACY LIKE 'KOTY34' AND PESEL_ZDAJACEGO LIKE '94827508294'"); // nasze zapytanie
																// do bazy
																// danych

			ClientTest ct = new ClientTest();

			odbierzob = ct.zbindujWyslij(socket, wys); // wysylamy zapytanie do
														// serwera i zapisujemy
														// w obiekcie odbierz

			// wyniki zapytania mamy w wektorze "ListaOdebranych" obiektu
			// "odbierz"
			System.out.println(odbierzob.getListaOdebranych().size());
			for (int i = 0; i < odbierzob.getListaOdebranych().size(); i++) {
				System.out.println("DLA i = " + i);
				
				System.out.println(odbierzob.getListaOdebranych().get(i));
			}

			socket.close();
			System.out.println("Client zamkniety: ");
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	@Override
	public odbierz zbindujWyslij(Socket socket, wyslij w) {
		odbierz odbierzob = null;

		try {
			InputStream inStream = socket.getInputStream();
			OutputStream outStream = socket.getOutputStream();

			Scanner in = new Scanner(inStream);
			PrintWriter outt = new PrintWriter(outStream, true);

			ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			ous.writeObject(w);

			while (true) {

				if (inStream.available() > 0) {
					odbierzob = (odbierz) ois.readObject();
					break;
				}

			}

			ous.close();

		} catch (Exception e) {
			System.err.println(e);
		}

		return odbierzob;
	}
}
