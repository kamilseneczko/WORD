package wordprojekt.zdajacy;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import wordprojekt.panellogowaniaiwyboru.panelLogowania;
import wordprojekt.panellogowaniaiwyboru.panelWyboru;
import wordprojekt.server.Bindable;
import wordprojekt.server.ClientTest;
import wordprojekt.server.odbierz;
import wordprojekt.server.wyslij;

import javax.swing.SwingConstants;

public class panelGlownyZdajacy extends JFrame implements ActionListener, Bindable {

	// da³em jakiœ na odwal aby warningi sie nie jara³y
	private static final long serialVersionUID = 1L;

	private JLabel jlabelIdZalogowanego, jlabelWolne, jlabelWolneKategoria, jlabelWolneMiesiac, jlabelTwoje,
			jlabelWyniki, jlabelZdajacyImie, jlabelZdajacyNazwisko, jlabelZdajacyUlica, jlabelWybierzRok, jlabelIdZ, jlabelNieWybranoOpcji;
	
	JComboBox<String> jcomboboxWolneKategorie, jcomboboxWolneDzien, jcomboboxTwojeKategorie, jcomboboxTwojeDzien, jcomboboxWybierzRok;


	private JButton jbuttonWolneSprawdz, jbuttonWyloguj;
	private ImageIcon imageiconKalendarz, imageiconWyloguj;
	private panelWyboru pWyboru;
	private JPanel  jpanelRamkaInfoGora, jpanelRamkaInfoDol,
			jpanelRamkaInfoLewo, jpanelRamkaInfoPrawo, jpanelRamkaWolneGora, jpanelRamkaWolneDol, jpanelRamkaWolneLewo,
			jpanelRamkaWolnePrawo;
	
	private JTable jtTabelkaTwojeEgzaminy, jtTabelkaWolneTerminy;
	JScrollPane jspPanelTwojeEgzaminy, jspPanelWolneTerminy;

	private Font font_Naglowki = new Font("Tahoma", Font.BOLD, 14);
	

	// tworzenie okienka
	public panelGlownyZdajacy(String nazwaUz) {

		// tworzenie okna
		this.setTitle("Aplikacja dla Zdajacego");
		setSize(800, 600);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		
		//Informacje o twoich egzaminach
		String[] columnsTwojeEgzaminy = new String[] {
	            "Kategoria", "Status", "Data", "Godzina", "Nazwisko egzaminatora", "Imie egzaminatora", "Nr pojazdu"
	        };
		//wyciaganie informacji o egzaminie
		try {
			odbierz odbierzobEgzamin = null;
			odbierz odbierzobEgzaminator = null;
			odbierz odbierzobPojazd = null;
           

			//pobieramy dane o egzaminie
			
			Socket socket = new Socket("localhost", 8185);
			
			wyslij wys = new wyslij("select KATEGORIA, STATUS_EGZAMINU, DATA_EGZAMINU, GODZINA_EGZAMINU from EGZAMIN where ID_ZDAJACEGO = (SELECT ID_ZDAJACEGO FROM ZDAJACY where PESEL_ZDAJACEGO LIKE '" + nazwaUz + "')");
			
			odbierzobEgzamin = this.zbindujWyslij(socket, wys);
			
			socket.close();


			//pobieramy dane o egzminatorze
			Socket socket1 = new Socket("localhost", 8185);
			
			wyslij wys1 = new wyslij("select NAZWISKO_EGZAMINATORA, IMIE_EGZAMINATORA from EGZAMINATOR, EGZAMIN, ZDAJACY where EGZAMINATOR.ID_EGZAMINATORA = EGZAMIN.ID_EGZAMINATORA AND EGZAMIN.ID_ZDAJACEGO = ZDAJACY.ID_ZDAJACEGO AND ZDAJACY.PESEL_ZDAJACEGO LIKE '" + nazwaUz + "'");

			odbierzobEgzaminator = this.zbindujWyslij(socket1, wys1);
			
			socket1.close();

			

			//pobieramy dane o pojezdzie
			Socket socket2 = new Socket("localhost", 8185);
			
			wyslij wys2 = new wyslij("select NR_REJESTRACYJNY from POJAZD, EGZAMIN, ZDAJACY where POJAZD.ID_POJAZDU = EGZAMIN.ID_POJAZDU AND EGZAMIN.ID_ZDAJACEGO = ZDAJACY.ID_ZDAJACEGO AND ZDAJACY.PESEL_ZDAJACEGO LIKE '" + nazwaUz + "'");

			odbierzobPojazd = this.zbindujWyslij(socket2, wys2);
			
			socket2.close();

			
			System.out.println("egzamin = " + odbierzobEgzamin.listaOdebranych.size());
			System.out.println("Egzaminator = " + odbierzobEgzaminator.listaOdebranych.size());
			System.out.println(" Pojazd = " + odbierzobPojazd.listaOdebranych.size());
			

			//Uzupelnienie tabelki
			Object[][] dataTwojeEgzaminy = new Object[odbierzobEgzamin.listaOdebranych.size()][7];
			
			for(int i = 0; i < odbierzobEgzamin.listaOdebranych.size(); i++){
				for(int k = 0; k < 4; k++){
					dataTwojeEgzaminy[i][k] = odbierzobEgzamin.listaOdebranych.get(i).get(k);
				}
				
				dataTwojeEgzaminy[i][4] = odbierzobEgzaminator.listaOdebranych.get(i).get(0);
				dataTwojeEgzaminy[i][5] = odbierzobEgzaminator.listaOdebranych.get(i).get(1);
				dataTwojeEgzaminy[i][6] = odbierzobPojazd.listaOdebranych.get(i).get(0);
				
				
				
			}

			socket.close();
	        
	        jtTabelkaTwojeEgzaminy = new JTable(dataTwojeEgzaminy, columnsTwojeEgzaminy);
	        jtTabelkaTwojeEgzaminy.setBounds(100, 100, 100, 100);
	         
	        jspPanelTwojeEgzaminy = new JScrollPane(jtTabelkaTwojeEgzaminy);
	        jspPanelTwojeEgzaminy.setBounds(307, 89, 477, 91);
	       
	 
	        getContentPane().add(jspPanelTwojeEgzaminy);

		

		} catch (Exception e1) {
			System.err.println(e1);
		}
		
		
	        
		
		
		
		

		// wgrywanie ikonek
		imageiconKalendarz = new ImageIcon("Kalendarz.png");
		imageiconWyloguj = new ImageIcon("wyloguj.png");



		
		


		// ramka informacje o zalogowanym uzytkowniku
		jpanelRamkaInfoLewo = new JPanel();
		jpanelRamkaInfoLewo.setBackground(new Color(0, 0, 150));
		jpanelRamkaInfoLewo.setBounds(10, 75, 3, 480);
		getContentPane().add(jpanelRamkaInfoLewo);
		jpanelRamkaInfoPrawo = new JPanel();
		jpanelRamkaInfoPrawo.setBackground(new Color(0, 0, 150));
		jpanelRamkaInfoPrawo.setBounds(300, 75, 3, 480);
		getContentPane().add(jpanelRamkaInfoPrawo);
		jpanelRamkaInfoGora = new JPanel();
		jpanelRamkaInfoGora.setBackground(new Color(0, 0, 150));
		jpanelRamkaInfoGora.setBounds(10, 75, 290, 3);
		getContentPane().add(jpanelRamkaInfoGora);
		jpanelRamkaInfoDol = new JPanel();
		jpanelRamkaInfoDol.setBackground(new Color(0, 0, 150));
		jpanelRamkaInfoDol.setBounds(10, 555, 293, 3);
		getContentPane().add(jpanelRamkaInfoDol);

		// ramka twoje egzaminy
		jpanelRamkaWolneGora = new JPanel();
		jpanelRamkaWolneGora.setBackground(new Color(230, 10, 10));
		jpanelRamkaWolneGora.setBounds(326, 233, 450, 3);
		getContentPane().add(jpanelRamkaWolneGora);
		jpanelRamkaWolneDol = new JPanel();
		jpanelRamkaWolneDol.setBackground(new Color(230, 10, 10));
		jpanelRamkaWolneDol.setBounds(326, 350, 450, 3);
		getContentPane().add(jpanelRamkaWolneDol);
		jpanelRamkaWolneLewo = new JPanel();
		jpanelRamkaWolneLewo.setBackground(new Color(230, 10, 10));
		jpanelRamkaWolneLewo.setBounds(325, 233, 3, 120);
		getContentPane().add(jpanelRamkaWolneLewo);
		jpanelRamkaWolnePrawo = new JPanel();
		jpanelRamkaWolnePrawo.setBackground(new Color(230, 10, 10));
		jpanelRamkaWolnePrawo.setBounds(773, 233, 3, 120);
		getContentPane().add(jpanelRamkaWolnePrawo);

		// jlabely
		jlabelIdZ = new JLabel(nazwaUz);
		jlabelIdZ.setBounds(221, 21, 408, 25);
		jlabelIdZ.setFont(font_Naglowki);
		getContentPane().add(jlabelIdZ);
		
		
		jlabelIdZalogowanego = new JLabel("Jesteœ zalogowany jako : ");
		jlabelIdZalogowanego.setBounds(32, 21, 408, 25);
		jlabelIdZalogowanego.setFont(font_Naglowki);
		getContentPane().add(jlabelIdZalogowanego);
		
		//jlabely blad jesli nie wybrano roku, miesiaca, kategorii
		jlabelNieWybranoOpcji = new JLabel("");					
		jlabelNieWybranoOpcji.setBounds(431, 361, 231, 20);
		getContentPane().add(jlabelNieWybranoOpcji);
		
		
		// jlabely wybierz rok
		jlabelWybierzRok = new JLabel("Wybierz rok");
		jlabelWybierzRok.setBounds(654, 233, 88, 25);
		getContentPane().add(jlabelWybierzRok);

		// jlabely wolnych egzaminow
		jlabelWolne = new JLabel("Sprawdz wolne terminy");
		jlabelWolne.setBounds(458, 206, 220, 25);
		jlabelWolne.setFont(font_Naglowki);
		getContentPane().add(jlabelWolne);
		jlabelWolneKategoria = new JLabel("Wybierz kategorie:");
		jlabelWolneKategoria.setBounds(358, 233, 110, 25);
		getContentPane().add(jlabelWolneKategoria);
		jlabelWolneMiesiac = new JLabel("Wybierz miesiac");
		jlabelWolneMiesiac.setBounds(504, 233, 88, 25);
		getContentPane().add(jlabelWolneMiesiac);

		// jlabely twoich egzaminow
		jlabelTwoje = new JLabel("Twoje egzaminy");
		jlabelTwoje.setBounds(475, 59, 140, 25);
		jlabelTwoje.setFont(font_Naglowki);
		getContentPane().add(jlabelTwoje);

		// jlabely wynikow wolnych terminow
		jlabelWyniki = new JLabel("Informacje o wolnych terminach:");
		jlabelWyniki.setBounds(385, 381, 350, 25);
		jlabelWyniki.setFont(font_Naglowki);
		getContentPane().add(jlabelWyniki);
		
		// wyciagamy informacje o uzytkownikach
		try {
			Socket socket = new Socket("localhost", 8185);

			odbierz odbierzob = null;

			wyslij wys = new wyslij("select IMIE_ZDAJACEGO, NAZWISKO_ZDAJACEGO, ADRES_ZDAJACEGO from ZDAJACY where PESEL_ZDAJACEGO LIKE '" + nazwaUz + "'");
					
			odbierzob = this.zbindujWyslij(socket, wys);
			
			socket.close();
			
			// jlabely informacje o uzytkowniku
			jlabelZdajacyImie = new JLabel(odbierzob.listaOdebranych.get(0).get(0));					
			jlabelZdajacyImie.setHorizontalAlignment(SwingConstants.CENTER);
			jlabelZdajacyImie.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
			jlabelZdajacyImie.setBounds(23, 89, 267, 20);
			getContentPane().add(jlabelZdajacyImie);
			
			jlabelZdajacyNazwisko = new JLabel(odbierzob.listaOdebranych.get(0).get(1));					
			jlabelZdajacyNazwisko.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
			jlabelZdajacyNazwisko.setHorizontalAlignment(SwingConstants.CENTER);
			jlabelZdajacyNazwisko.setBounds(23, 138, 267, 20);
			getContentPane().add(jlabelZdajacyNazwisko);
			
			jlabelZdajacyUlica = new JLabel(odbierzob.listaOdebranych.get(0).get(2));					
			jlabelZdajacyUlica.setHorizontalAlignment(SwingConstants.CENTER);
			jlabelZdajacyUlica.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			jlabelZdajacyUlica.setBounds(36, 195, 231, 20);
			getContentPane().add(jlabelZdajacyUlica);

		

		} catch (Exception e1) {
			System.err.println(e1);
		}
		
		

		// combobox kategorie
		jcomboboxWolneKategorie = new JComboBox<String>();
		jcomboboxWolneKategorie.setBounds(338, 262, 130, 20);
		jcomboboxWolneKategorie.addItem("KATEGORIA AM");
		jcomboboxWolneKategorie.addItem("KATEGORIA A1");
		jcomboboxWolneKategorie.addItem("KATEGORIA A2");
		jcomboboxWolneKategorie.addItem("KATEGORIA A");
		jcomboboxWolneKategorie.addItem("KATEGORIA B1");
		jcomboboxWolneKategorie.addItem("KATEGORIA B");
		jcomboboxWolneKategorie.addItem("KATEGORIA C1");
		jcomboboxWolneKategorie.addItem("KATEGORIA C");
		jcomboboxWolneKategorie.addItem("KATEGORIA D1");
		jcomboboxWolneKategorie.addItem("KATEGORIA D");
		jcomboboxWolneKategorie.addItem("KATEGORIA BE");
		jcomboboxWolneKategorie.addItem("KATEGORIA C1E");
		jcomboboxWolneKategorie.addItem("KATEGORIA CE");
		jcomboboxWolneKategorie.addItem("KATEGORIA D1E");
		jcomboboxWolneKategorie.addItem("KATEGORIA DE");
		jcomboboxWolneKategorie.addItem("KATEGORIA T");
		getContentPane().add(jcomboboxWolneKategorie);
		jcomboboxWolneKategorie.addActionListener(this);
		


		// combobox wolne dni
		jcomboboxWolneDzien = new JComboBox<String>();
		jcomboboxWolneDzien.setBounds(485, 262, 130, 20);
		jcomboboxWolneDzien.addItem("1");
		jcomboboxWolneDzien.addItem("2");
		jcomboboxWolneDzien.addItem("3");
		jcomboboxWolneDzien.addItem("4");
		jcomboboxWolneDzien.addItem("5");
		jcomboboxWolneDzien.addItem("6");
		jcomboboxWolneDzien.addItem("7");
		jcomboboxWolneDzien.addItem("8");
		jcomboboxWolneDzien.addItem("9");
		jcomboboxWolneDzien.addItem("10");
		jcomboboxWolneDzien.addItem("11");
		jcomboboxWolneDzien.addItem("12");
		getContentPane().add(jcomboboxWolneDzien);
		jcomboboxWolneDzien.addActionListener(this);
		
		// combobox wybierz rok jcomboboxWybierzRok
		
		jcomboboxWybierzRok = new JComboBox<String>();
		jcomboboxWybierzRok.setBounds(633, 262, 130, 20);
		jcomboboxWybierzRok.addItem("2017");
		jcomboboxWybierzRok.addItem("2018");
		jcomboboxWybierzRok.addItem("2020");
		jcomboboxWybierzRok.addItem("2021");
		jcomboboxWybierzRok.addItem("2022");
		jcomboboxWybierzRok.addItem("2023");
		jcomboboxWybierzRok.addItem("2024");
		jcomboboxWybierzRok.addItem("2025");
		jcomboboxWybierzRok.addItem("2026");
		jcomboboxWybierzRok.addItem("2027");
		jcomboboxWybierzRok.addItem("2028");
		jcomboboxWybierzRok.addItem("2029");
		getContentPane().add(jcomboboxWybierzRok);
		jcomboboxWybierzRok.addActionListener(this);

		// przyciski
		jbuttonWolneSprawdz = new JButton("Sprawdz", imageiconKalendarz);
		jbuttonWolneSprawdz.setBounds(485, 293, 130, 50);
		getContentPane().add(jbuttonWolneSprawdz);
		jbuttonWolneSprawdz.addActionListener(this);

		jbuttonWyloguj = new JButton("Wyloguj", imageiconWyloguj);
		jbuttonWyloguj.setBounds(635, 10, 130, 50);
		getContentPane().add(jbuttonWyloguj);
		jbuttonWyloguj.addActionListener(this);

	}

	String jboxWybierzKategorie = null;
	String jboxWybierzMiesiac = null;
	String jboxWybierzRok = null;
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		// wylogowanie sie
		if (z == jbuttonWyloguj) {
			if (pWyboru == null) {
				pWyboru = new panelWyboru();
				pWyboru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pWyboru.setLocationRelativeTo(null);
				pWyboru.setVisible(true);
				setVisible(false);
			}
		} else if(z == jcomboboxWolneKategorie){
			jboxWybierzKategorie = jcomboboxWolneKategorie.getSelectedItem().toString();
			
		} else if(z == jcomboboxWolneDzien) {
			jboxWybierzMiesiac = jcomboboxWolneDzien.getSelectedItem().toString();
			
		} else if(z == jcomboboxWybierzRok){
			jboxWybierzRok = jcomboboxWybierzRok.getSelectedItem().toString();
			
		} else if(z == jbuttonWolneSprawdz){
			System.out.println("Sprawdzanie");
			
			if(jboxWybierzKategorie != null && jboxWybierzMiesiac != null && jboxWybierzRok != null){
				jlabelNieWybranoOpcji.setText("");
				try {

				Socket socket = new Socket("localhost", 8185);

				odbierz odbierzob = null;
				
				String zmPomRokPocz = jboxWybierzRok + "-" + jboxWybierzMiesiac + "-01";
				String zmPomRokKon =  jboxWybierzRok + "-12-31";
			
				wyslij wys = new wyslij("select DATA_EGZAMINU, GODZINA_EGZAMINU from EGZAMIN where DATA_EGZAMINU >= '" + zmPomRokPocz + "' AND DATA_EGZAMINU <= '" + zmPomRokKon +"'");
				
				odbierzob = zbindujWyslij(socket, wys); 

				socket.close();

				String tabKatDataGodz[][] = new String[odbierzob.getListaOdebranych().size()][2];
				
				for (int i = 0; i < odbierzob.getListaOdebranych().size(); i++) {
					for(int k = 0; k < 2; k++){
						if(k == 0){
					     tabKatDataGodz[i][k] = odbierzob.getListaOdebranych().get(i).get(k).substring(0,  10);
						} else {
					     tabKatDataGodz[i][k] = odbierzob.getListaOdebranych().get(i).get(k);	
						}
					   System.out.println(tabKatDataGodz[i][k]);
					}
				}
				
				Object[][] dWolneTerminy = new Object[31*10][2];
				
				int flaga = 0;
				int licznikWolne = 0;
				
		

				for(int i = 1; i < 31; i++){

					for(int k = 7; k < 17; k++){

						for(int m = 0; m < odbierzob.getListaOdebranych().size(); m++){
							//System.out.println("m = " + m + " : " + jboxWybierzRok+"-"+jboxWybierzMiesiac+"-"+i + " : " + k+":00");
							if(tabKatDataGodz[m][0].equals(String.valueOf(jboxWybierzRok+"-"+jboxWybierzMiesiac+"-"+i)) && tabKatDataGodz[m][1].equals(String.valueOf(k+":00"))){
								System.out.println("tabkat bylo");
							  flaga = 1;
							} 
						}
						
						if(flaga == 1){
							flaga = 0;
						} else {
							dWolneTerminy[licznikWolne][0] = jboxWybierzRok+"-"+jboxWybierzMiesiac+"-"+i;
							dWolneTerminy[licznikWolne][1] = k+":00";
							licznikWolne++;
						}
						
					}

					
					   //Informacje o wolnych terminach
					String[] columnsWolneTerminy = new String[] {
				            "Data", "Godzina"
				        };
					

					
			        jtTabelkaWolneTerminy = new JTable(dWolneTerminy, columnsWolneTerminy);
			        jtTabelkaWolneTerminy.setBounds(100, 100, 100, 100);
			         
			        jspPanelWolneTerminy = new JScrollPane(jtTabelkaWolneTerminy);
			        jspPanelWolneTerminy.setBounds(307, 381, 477, 174);
			 
			        getContentPane().add(jspPanelWolneTerminy);
					
					
				}
				

			} catch (Exception ep) {
				System.err.println(ep);
			}
			} else {
				jlabelNieWybranoOpcji.setText("Wybierz Kategorie, Rok, Miesiac");
				jlabelNieWybranoOpcji.setForeground(Color.RED);
			}
			

			
			
		}

	}	public static void main(String[] args) {

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

			ous.flush();
			ous.writeObject(w);

			while (true) {

				if (inStream.available() > 0) {
					odbierzob = (odbierz) ois.readObject();
					break;
				}

			}

			

		} catch (Exception e) {
			System.err.println(e);
		}

		return odbierzob;
	}

}