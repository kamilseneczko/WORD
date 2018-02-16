package wordprojekt.panellogowaniaiwyboru;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import wordprojekt.egzaminator.panelGlownyEgzaminator;
import wordprojekt.pracownikbiura.panelGlownyPracownikBiura;
import wordprojekt.server.Bindable;
import wordprojekt.server.ClientTest;
import wordprojekt.server.odbierz;
import wordprojekt.server.wyslij;
import wordprojekt.zdajacy.panelGlownyZdajacy;
import java.awt.Color;

public class panelLogowania extends JFrame implements ActionListener, Bindable {

	private static final long serialVersionUID = 2L;

	private JTextField jtfLogin;
	private JPasswordField jPassword;
	private JLabel jlLogin, jlHaslo, jlUzytkownik, jlBlad;
	private String rodzajUzytkownikaLogowania;
	private JButton bZaloguj;

	private panelGlownyZdajacy zdajacy;
	private panelGlownyEgzaminator egzaminator;
	private panelGlownyPracownikBiura pracownikBiura;


	public panelLogowania() {
		this("Nie podano");
	}

	public panelLogowania(String rul) {
		setSize(563, 227);
		setTitle("Panel lgowania");
		getContentPane().setLayout(null);

		this.rodzajUzytkownikaLogowania = rul;

		jlUzytkownik = new JLabel("Logowanie do: " + rodzajUzytkownikaLogowania);
		jlUzytkownik.setBounds(206, 11, 242, 20);
		getContentPane().add(jlUzytkownik);

		jlLogin = new JLabel("Login: ");
		jlLogin.setBounds(144, 72, 54, 20);
		getContentPane().add(jlLogin);

		jtfLogin = new JTextField("");
		jtfLogin.setBounds(206, 72, 170, 20);
		getContentPane().add(jtfLogin);
		jtfLogin.addActionListener(this);

		jlHaslo = new JLabel("Hasło: ");
		jlHaslo.setBounds(144, 103, 54, 20);
		getContentPane().add(jlHaslo);


		bZaloguj = new JButton("Zaloguj");
		bZaloguj.setBounds(154, 134, 251, 44);
		getContentPane().add(bZaloguj);
		bZaloguj.addActionListener(this);
		
		jPassword = new JPasswordField();
		jPassword.setBounds(206, 103, 170, 20);
		getContentPane().add(jPassword);
		
		
		jlBlad = new JLabel("");					
		jlBlad.setBounds(168, 42, 231, 20);
		getContentPane().add(jlBlad);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();

		if (z == bZaloguj) {
			System.out.println("Zalogowany na: " + rodzajUzytkownikaLogowania);

			if (rodzajUzytkownikaLogowania.equals("Zdajacy")) {

				try {
					Socket socket = new Socket("localhost", 8185);

					odbierz odbierzob = null;



					wyslij wys = new wyslij("select * from ZDAJACY where PESEL_ZDAJACEGO LIKE '" + jtfLogin.getText()
							+ "' AND HASLO_ZDAJACY LIKE '" + String.valueOf(jPassword.getPassword()) + "'");

					odbierzob = this.zbindujWyslij(socket, wys);

					socket.close();


					if (odbierzob.listaOdebranych.size() > 0) {
						zdajacy = new panelGlownyZdajacy(jtfLogin.getText());
						zdajacy.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
						zdajacy.setLocationRelativeTo(null);
						zdajacy.setVisible(true);
						this.setVisible(false);
					} else {
						jlBlad.setForeground(Color.RED);
						jlBlad.setText("Błędna nazwa użytkownika lub hasło");
					

					}

				} catch (Exception e1) {
					System.err.println(e1);
				}
			} else if (rodzajUzytkownikaLogowania.equals("Pracownik biura")) {
				System.out.println("W Pracowni biura");
				try {
					Socket socket = new Socket("localhost", 8185);

					odbierz odbierzob = null;
					
					

					wyslij wys = new wyslij("select * from PRACOWNIK_BIURA where PESEL_PRACOW_BIURA LIKE'" + jtfLogin.getText()
							+ "' AND HASLO_PRACOW_BIURA LIKE '" + String.valueOf(jPassword.getPassword()) + "'");
					odbierzob = this.zbindujWyslij(socket, wys);

					socket.close();
					
					if (odbierzob.listaOdebranych.size() > 0) {
						
						pracownikBiura = new panelGlownyPracownikBiura(jtfLogin.getText());
						pracownikBiura.setDefaultCloseOperation(EXIT_ON_CLOSE);
						pracownikBiura.setLocationRelativeTo(null);
						pracownikBiura.setVisible(true);
						this.setVisible(false);
						
					} else {
						jlBlad.setForeground(Color.RED);
						jlBlad.setText("Błędna nazwa użytkownika lub hasło");;
					}
				} catch (Exception e1) {
					System.err.println(e1);
				}
			} else if (rodzajUzytkownikaLogowania.equals("Egzaminator")) {
				System.out.println("W Egzaminator");
				try {
					Socket socket = new Socket("localhost", 8185);

					odbierz odbierzob = null;

					wyslij wys = new wyslij("select * from EGZAMINATOR where PESEL_EGZAMINATORA LIKE '" + jtfLogin.getText()
							+ "' AND HASLO_EGZAMINATOR LIKE '" + String.valueOf(jPassword.getPassword()) + "'");

					odbierzob = this.zbindujWyslij(socket, wys);

					socket.close();
					
					
					if (odbierzob.listaOdebranych.size() > 0) {
						
						egzaminator = new panelGlownyEgzaminator(jtfLogin.getText());
						egzaminator.setDefaultCloseOperation(EXIT_ON_CLOSE);
						egzaminator.setLocationRelativeTo(null);
						egzaminator.setVisible(true);
						this.setVisible(false);
						
					} else {
						
						jlBlad.setForeground(Color.RED);
						jlBlad.setText("Błędna nazwa użytkownika lub hasło");
					}
				} catch (Exception e1) {
					System.err.println(e1);
				}
			}

			System.out.println("Login = " + jtfLogin.getText());
			System.out.println("Haslo = " + String.valueOf(jPassword.getPassword()));

		}

	}

	public static void main(String[] args) {

		panelLogowania pl = new panelLogowania("WAT");
		pl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pl.setLocationRelativeTo(null);
		pl.setVisible(true);
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