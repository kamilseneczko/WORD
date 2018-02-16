package wordprojekt.pracownikbiura;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import java.awt.Point;
import java.awt.Cursor;
import javax.swing.DropMode;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;

import wordprojekt.panellogowaniaiwyboru.panelWyboru;
import wordprojekt.server.Bindable;
import wordprojekt.server.odbierz;
import wordprojekt.server.wyslij;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class panelGlownyPracownikBiura extends JFrame implements ActionListener, Bindable {
	
	private String nazwaPracownikaBiura;
	private static final long serialVersionUID = 1L;
		 
	private JLabel lblZarzdzanieUytkownikami, lblPesel, lblImi, lblAdres, label, lblImi_1, label_2, 
	lblInformacjeOEgzaminach, lblKategoria, lblData, lblGodzina, lblEgzaminator, lblEgzaminyUytkownika,
	lblZalogowanyJako, lblWyszukiwanieUytkownika, lblDodawanieNowegoUytkownika, lblDodawanieEgzaminu,
	lblNazwisko, lblNewLabel_1, lblBladWyszukiwania, lblPesel_1, lblIdpojazdu;
	
	private JTextField tFPeselWyszukiwanie, tFPESEL, tFTelAdresWyszukiwanie, 
	tFPeselDodawanie, tFImieNazwiskoDodawanie, tFTelAdresDodawanie, tFDataDodawanie;        
	
	
	private panelWyboru pWyboru;
	
	private JButton btnWyloguj, btnDodawanieEgzaminu, btnWyszukiwanieUytkownika, btnDodawanieNowegoUytkownika;
	
	private JComboBox JcomboBoxKategorie, JcomboBoxEgzaminatorzy, JcomboBoxGodzina, comboBoxPeselEgzamin, comboBoxIdPojazdu;
	
	private JTable table;
	
	private JScrollPane scrollPane;

	private JTextField textField;
	private JTextField textDodawanieU;
	private JLabel lblNewLabel;
	private JTextField textFieldHaslo;
	private JLabel lblbladdanych;
	private JPanel panel, panel_1;
	private JTable table_1;
	
	private int Poprawne, maxIdEgzaminu, OdIdZdajacego, obIdEgzaminatora, obIdEPracownikaBiura, Istnieje;
	private JLabel lblNewLabel_2;


	Container c;
	
	

	public panelGlownyPracownikBiura(String nazwaP){
		System.out.println("Witaj");
		setSize(800, 600);
		setTitle("WORD");
		getContentPane().setLayout(null);
		nazwaPracownikaBiura = nazwaP;  // uzywane przy dodaj egzamin
		
		/*
		String[] columnsTwojeEgzaminy = new String[] {
	            "Kategoria", "Status", "Data", "Godzina", "Nazwisko egzaminatora", "Imie egzaminatora", "Nr pojazdu"
	        };
		
		Object[][] dataTwojeEgzaminy = new Object[10][7];
		*/

		c = getContentPane();
		
	    table = new JTable();
		
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(316, 392, 469, 106);
		c.add(scrollPane);
		
		lblZarzdzanieUytkownikami = new JLabel("Zarz\u0105dzanie u\u017Cytkownikami");
		lblZarzdzanieUytkownikami.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZarzdzanieUytkownikami.setHorizontalAlignment(SwingConstants.CENTER);
		lblZarzdzanieUytkownikami.setHorizontalTextPosition(SwingConstants.LEADING);
		lblZarzdzanieUytkownikami.setBounds(55, 59, 220, 47);
		getContentPane().add(lblZarzdzanieUytkownikami);
		
		btnWyszukiwanieUytkownika = new JButton("Wyszukaj u\u017Cytkownika");
		btnWyszukiwanieUytkownika.setBounds(148, 259, 154, 28);
		getContentPane().add(btnWyszukiwanieUytkownika);
		btnWyszukiwanieUytkownika.addActionListener(this);
		
		lblPesel = new JLabel("Pesel:");
		lblPesel.setBounds(55, 208, 80, 14);
		getContentPane().add(lblPesel);
		
		lblImi = new JLabel("Imi\u0119:");
		lblImi.setBounds(55, 156, 88, 14);
		getContentPane().add(lblImi);
		
		lblAdres = new JLabel("adres:");
		lblAdres.setBounds(55, 233, 80, 14);
		getContentPane().add(lblAdres);
		
		tFPeselWyszukiwanie = new JTextField();
		tFPeselWyszukiwanie.setBounds(153, 153, 122, 20);
		getContentPane().add(tFPeselWyszukiwanie);
		tFPeselWyszukiwanie.setColumns(10);
		
		label = new JLabel("Pesel:");
		label.setBounds(55, 420, 80, 14);
		getContentPane().add(label);
		
		lblImi_1 = new JLabel("Imi\u0119:");
		lblImi_1.setBounds(55, 367, 88, 14);
		getContentPane().add(lblImi_1);
		
		label_2 = new JLabel("adres:");
		label_2.setBounds(55, 445, 88, 14);
		getContentPane().add(label_2);
		
		tFPESEL = new JTextField();
		tFPESEL.setBounds(153, 205, 122, 20);
		getContentPane().add(tFPESEL);
		tFPESEL.setColumns(10);
		
		tFTelAdresWyszukiwanie = new JTextField();
		tFTelAdresWyszukiwanie.setBounds(153, 231, 122, 20);
		getContentPane().add(tFTelAdresWyszukiwanie);
		tFTelAdresWyszukiwanie.setColumns(10);
		
		tFPeselDodawanie = new JTextField();
		tFPeselDodawanie.setColumns(10);
		tFPeselDodawanie.setBounds(153, 417, 122, 20);
		getContentPane().add(tFPeselDodawanie);
		
		tFImieNazwiskoDodawanie = new JTextField();
		tFImieNazwiskoDodawanie.setColumns(10);
		tFImieNazwiskoDodawanie.setBounds(153, 390, 122, 20);
		getContentPane().add(tFImieNazwiskoDodawanie);
		
		tFTelAdresDodawanie = new JTextField();
		tFTelAdresDodawanie.setColumns(10);
		tFTelAdresDodawanie.setBounds(153, 442, 122, 20);
		getContentPane().add(tFTelAdresDodawanie);
		
		lblInformacjeOEgzaminach = new JLabel("Informacje o egzaminach");
		lblInformacjeOEgzaminach.setBounds(492, 11, 179, 20);
		getContentPane().add(lblInformacjeOEgzaminach);
		
		btnDodawanieEgzaminu = new JButton("Dodaj egzamin");
		btnDodawanieEgzaminu.setBounds(546, 240, 154, 28);
		getContentPane().add(btnDodawanieEgzaminu);
		btnDodawanieEgzaminu.addActionListener(this); 
		
		lblKategoria = new JLabel("kategoria");
		lblKategoria.setBounds(428, 76, 100, 14);
		getContentPane().add(lblKategoria);
		
		lblData = new JLabel("data YYYY-MM-DD");
		lblData.setBounds(428, 101, 100, 14);
		getContentPane().add(lblData);
		
		lblGodzina = new JLabel("godzina");
		lblGodzina.setBounds(428, 124, 100, 14);
		getContentPane().add(lblGodzina);
		
		lblEgzaminator = new JLabel("egzaminator");
		lblEgzaminator.setBounds(428, 153, 100, 14);
		getContentPane().add(lblEgzaminator);
		
		btnDodawanieNowegoUytkownika = new JButton("Dodaj nowego u\u017Cytkownika");
		btnDodawanieNowegoUytkownika.setBounds(123, 501, 179, 35);
		getContentPane().add(btnDodawanieNowegoUytkownika);
		btnDodawanieNowegoUytkownika.addActionListener(this);
		
		btnWyloguj = new JButton("wyloguj");
		btnWyloguj.setBounds(685, 0, 89, 23);
		getContentPane().add(btnWyloguj);
		btnWyloguj.addActionListener(this);
		
		
		
		tFDataDodawanie = new JTextField();
		tFDataDodawanie.setColumns(10);
		tFDataDodawanie.setBounds(559, 97, 137, 20);
		getContentPane().add(tFDataDodawanie);
		
		lblEgzaminyUytkownika = new JLabel("Egzaminy u\u017Cytkownika");
		lblEgzaminyUytkownika.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEgzaminyUytkownika.setBounds(475, 356, 180, 25);
		getContentPane().add(lblEgzaminyUytkownika);
		
		lblZalogowanyJako = new JLabel("Zalogowany jako: " + nazwaP);
		lblZalogowanyJako.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZalogowanyJako.setBounds(10, 11, 314, 34);
		getContentPane().add(lblZalogowanyJako);
		lblZalogowanyJako.setBackground(Color.GREEN);
		lblZalogowanyJako.setHorizontalAlignment(SwingConstants.CENTER);
		lblZalogowanyJako.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		lblWyszukiwanieUytkownika = new JLabel("Wyszukiwanie u\u017Cytkownika");
		lblWyszukiwanieUytkownika.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWyszukiwanieUytkownika.setBounds(79, 117, 196, 28);
		getContentPane().add(lblWyszukiwanieUytkownika);
		
		lblDodawanieNowegoUytkownika = new JLabel("Dodawanie nowego u\u017Cytkownika");
		lblDodawanieNowegoUytkownika.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDodawanieNowegoUytkownika.setBounds(66, 333, 209, 28);
		getContentPane().add(lblDodawanieNowegoUytkownika);
		
		lblDodawanieEgzaminu = new JLabel("Dodawanie egzaminu");
		lblDodawanieEgzaminu.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDodawanieEgzaminu.setBounds(492, 42, 163, 31);
		getContentPane().add(lblDodawanieEgzaminu);
		
		JcomboBoxKategorie = new JComboBox();
		JcomboBoxKategorie.setBounds(559, 72, 138, 20);
		
		JcomboBoxKategorie.addItem("AM");
		JcomboBoxKategorie.addItem("A1");
		JcomboBoxKategorie.addItem("A2");
		JcomboBoxKategorie.addItem("A");
		JcomboBoxKategorie.addItem("B1");
		JcomboBoxKategorie.addItem("B");
		JcomboBoxKategorie.addItem("C1");
		JcomboBoxKategorie.addItem("C");
		JcomboBoxKategorie.addItem("D1");
		JcomboBoxKategorie.addItem("D");
		JcomboBoxKategorie.addItem("BE");
		JcomboBoxKategorie.addItem("C1E");
		JcomboBoxKategorie.addItem("CE");
		JcomboBoxKategorie.addItem("D1E");
		JcomboBoxKategorie.addItem("DE");
		JcomboBoxKategorie.addItem("T");
		getContentPane().add(JcomboBoxKategorie);
		
		
		try{
			JcomboBoxEgzaminatorzy = new JComboBox();
			JcomboBoxEgzaminatorzy.setBounds(559, 149, 138, 20);
			
			int maxIdEgzaminatora = 0;
			Socket socket = new Socket("localhost", 8185);
			odbierz odbierzMaxEgzaminatora = null;
			wyslij wys = new wyslij("SELECT max(ID_EGZAMINATORA) from EGZAMINATOR");
			odbierzMaxEgzaminatora = this.zbindujWyslij(socket, wys);
			socket.close();
			maxIdEgzaminatora = Integer.valueOf(odbierzMaxEgzaminatora.listaOdebranych.get(0).get(0)); // maksymalne ID_ZDAJACEGO
			
			for(int i = 1 ; i <= maxIdEgzaminatora ; i++){
				
				Socket socket1 = new Socket("localhost", 8185);
				odbierz odbierzEgzaminatora = null;
				wyslij wys1 = new wyslij("SELECT NAZWISKO_EGZAMINATORA FROM EGZAMINATOR WHERE ID_EGZAMINATORA = " + i +"");
				odbierzEgzaminatora = this.zbindujWyslij(socket1, wys1);
				socket1.close();
				
				JcomboBoxEgzaminatorzy.addItem(odbierzEgzaminatora.listaOdebranych.get(0).get(0));
			}
			getContentPane().add(JcomboBoxEgzaminatorzy);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		JcomboBoxGodzina = new JComboBox();
		JcomboBoxGodzina.setBounds(559, 120, 138, 20);
		JcomboBoxGodzina.addItem("8:00");
		JcomboBoxGodzina.addItem("9:00");
		JcomboBoxGodzina.addItem("10:00");
		JcomboBoxGodzina.addItem("11:00");
		JcomboBoxGodzina.addItem("12:00");
		JcomboBoxGodzina.addItem("13:00");
		JcomboBoxGodzina.addItem("14:00");
		JcomboBoxGodzina.addItem("15:00");
		getContentPane().add(JcomboBoxGodzina);
		
		lblNazwisko = new JLabel("Nazwisko:");
        lblNazwisko.setBounds(55, 181, 80, 14);
        getContentPane().add(lblNazwisko);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(153, 178, 122, 20);
        getContentPane().add(textField);
        
        textDodawanieU = new JTextField(null);
        textDodawanieU.setColumns(10);
        textDodawanieU.setBounds(153, 364, 122, 20);
        getContentPane().add(textDodawanieU);
        
        lblNewLabel = new JLabel("Nazwisko:");
        lblNewLabel.setBounds(53, 392, 88, 14);
        getContentPane().add(lblNewLabel);
        
        textFieldHaslo = new JTextField();
        textFieldHaslo.setColumns(10);
        textFieldHaslo.setBounds(153, 470, 122, 20);
        getContentPane().add(textFieldHaslo);
        
        lblNewLabel_1 = new JLabel("has\u0142o");
        lblNewLabel_1.setBounds(55, 470, 80, 14);
        getContentPane().add(lblNewLabel_1);
        
        lblbladdanych = new JLabel("");
		lblbladdanych.setBounds(10, 501, 113, 35);
		getContentPane().add(lblbladdanych);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 205, 170));
		panel_1.setBounds(10, 325, 296, 214);
		getContentPane().add(panel_1);
		
		lblBladWyszukiwania = new JLabel("");
		lblBladWyszukiwania.setBounds(10, 259, 128, 28);
		getContentPane().add(lblBladWyszukiwania);
		
		panel = new JPanel();
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(10, 117, 296, 177);
		getContentPane().add(panel);
		
		try{
			comboBoxPeselEgzamin = new JComboBox();
			comboBoxPeselEgzamin.setBounds(559, 177, 137, 20);
			
			Socket socket = new Socket("localhost", 8185);
			odbierz odbierzIdZdajacego = null;
			wyslij wys = new wyslij("SELECT PESEL_ZDAJACEGO FROM ZDAJACY");
			odbierzIdZdajacego = this.zbindujWyslij(socket, wys);
			socket.close();
			
			for(int i = 0 ; i < odbierzIdZdajacego.listaOdebranych.size(); i++){
				comboBoxPeselEgzamin.addItem(odbierzIdZdajacego.listaOdebranych.get(i).get(0));
			}
			getContentPane().add(comboBoxPeselEgzamin);
			
			comboBoxIdPojazdu = new JComboBox();
			comboBoxIdPojazdu.setBounds(559, 205, 137, 20);
			
			Socket socket1 = new Socket("localhost", 8185);
			odbierz odbierzIdPojazdu = null;
			wyslij wys3 = new wyslij("SELECT ID_POJAZDU FROM POJAZD");
			odbierzIdPojazdu = this.zbindujWyslij(socket1, wys3);
			socket1.close();
			
			for(int j = 0 ; j < odbierzIdPojazdu.listaOdebranych.size(); j++){
				comboBoxIdPojazdu.addItem(odbierzIdPojazdu.listaOdebranych.get(j).get(0));
			}
			getContentPane().add(comboBoxIdPojazdu);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		lblPesel_1 = new JLabel("Pesel");
		lblPesel_1.setBounds(428, 181, 100, 14);
		getContentPane().add(lblPesel_1);
		
		lblIdpojazdu = new JLabel("ID_Pojazdu");
		lblIdpojazdu.setBounds(428, 208, 121, 14);
		getContentPane().add(lblIdpojazdu);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(428, 233, 108, 28);
		getContentPane().add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(102, 205, 170));
		panel_2.setBounds(415, 42, 303, 230);
		getContentPane().add(panel_2);
		


	}
	
	
	
	@Override
	
	public void actionPerformed(ActionEvent e) {
		Object klawisz = e.getSource();
		//wylogowywanie sie
		if (klawisz == btnWyloguj){
			//JOptionPane.showMessageDialog(null, "Blad zapisu", "Aplikacja", JOptionPane.INFORMATION_MESSAGE);
			if (pWyboru == null) {
				pWyboru = new panelWyboru();
				pWyboru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pWyboru.setLocationRelativeTo(null);
				pWyboru.setVisible(true);
				setVisible(false);
			}
		}
		//dodawanie zdajacego
		else if(klawisz == btnDodawanieNowegoUytkownika){
			
			boolean TylkoLiczby=true;
			int iii = 0;
			while (TylkoLiczby == true && iii < tFPeselDodawanie.getText().length()) 
			{
				if (tFPeselDodawanie.getText().charAt(iii) <= ('0'-1)|| tFPeselDodawanie.getText().charAt(iii) >= 'a')
				{
					TylkoLiczby = false;
					JOptionPane.showMessageDialog(null,"W polu PESEL prosze wprowadzic cyfry bez separatorow","b³êdne dane", JOptionPane.ERROR_MESSAGE);
				}
				++iii;
			}
			if(TylkoLiczby){
			if((!textDodawanieU.getText().equals("")) && (!tFImieNazwiskoDodawanie.getText().equals("")) && (!tFPeselDodawanie.getText().equals(""))&&
					(!tFTelAdresDodawanie.getText().equals("")) && (!textFieldHaslo.getText().equals(""))){
					lblbladdanych.setText("");
						try{
							
							//SPRAWDZENIE CZY NIE ISTANIEJE TAKI UZYTKOWNIK
							Socket socket5 = new Socket("localhost", 8185);
							odbierz odbierz5 = null;
							wyslij wys5 = new wyslij("SELECT IMIE_ZDAJACEGO, NAZWISKO_ZDAJACEGO, PESEL_ZDAJACEGO FROM ZDAJACY");
							odbierz5 = this.zbindujWyslij(socket5, wys5);
							socket5.close();
							Istnieje = 0;
							for(int i = 0 ; i < odbierz5.listaOdebranych.size() ; i++){
								if(((odbierz5.listaOdebranych.get(i).get(0)).equals(textDodawanieU.getText())) && 
									((odbierz5.listaOdebranych.get(i).get(1)).equals(tFImieNazwiskoDodawanie.getText())) &&
									((odbierz5.listaOdebranych.get(i).get(2)).equals(tFPeselDodawanie.getText()))){
									Istnieje = 1;
								}
							}
							if(Istnieje == 1){
								lblbladdanych.setText("Uzytkownik \nistnieje");
								lblbladdanych.setForeground(Color.RED);
							}else{
								lblbladdanych.setText("Uzytkownik \ndodany");
								lblbladdanych.setForeground(Color.RED);
								int maxID = 0; //aby dodac 1
								String maxIDdoUzycia = ""; // do zapytania
						
								Socket socket = new Socket("localhost", 8185);
								odbierz odbierzMaxID = null;
								wyslij wysMaxID = new wyslij("SELECT max(ID_ZDAJACEGO) from ZDAJACY");
								odbierzMaxID = this.zbindujWyslij(socket, wysMaxID);
								socket.close();
							
								maxID = Integer.valueOf(odbierzMaxID.listaOdebranych.get(0).get(0)); 	//maksymalne ID_ZDAJACEGO
								maxID = maxID + 1; 	
								maxIDdoUzycia = String.valueOf(maxID);//dodajemy +1 dla nowego
								System.out.println("przycisk imienia: " +maxIDdoUzycia);
						
						
								Socket socket1 = new Socket("localhost", 8185);
								odbierz odbierzWstawZdajacego = null;

								wyslij wys = new wyslij("INSERT INTO ZDAJACY VALUES (" + maxID +",'" + textDodawanieU.getText() + "','" +
										tFImieNazwiskoDodawanie.getText() +"'," + tFPeselDodawanie.getText() + ",'" + tFTelAdresDodawanie.getText() +
										"','" + textFieldHaslo.getText() + "')");
							
								odbierzWstawZdajacego = this.zbindujWyslij(socket1, wys);
					
								socket1.close();
							}
							
				
				}catch(Exception e1){
				e1.printStackTrace();
				}
						
			}else{
				lblbladdanych.setText("bledne dane");
				lblbladdanych.setForeground(Color.RED);
				
			}
		}
				
				
		}
		else if(klawisz == btnDodawanieEgzaminu){
			if(tFDataDodawanie.getText().equals("")){
				lblNewLabel_2.setText("BRAK DATY");
				lblNewLabel_2.setForeground(Color.RED);
			}else{
				lblNewLabel_2.setText("Egzamin dodany");
				lblNewLabel_2.setForeground(Color.RED);
				try{
					//JcomboBoxGodzina.getSelectedItem();
					maxIdEgzaminu = 0;
					//odebranie max ID_EGZAMINU
					Socket socket = new Socket("localhost", 8185);
					odbierz odbierzob= null;
					wyslij wys = new wyslij("SELECT max(ID_EGZAMINU) FROM EGZAMIN");
					odbierzob = this.zbindujWyslij(socket, wys);
					socket.close();
					maxIdEgzaminu = Integer.valueOf(odbierzob.listaOdebranych.get(0).get(0));
					maxIdEgzaminu = maxIdEgzaminu + 1;
					
					//OTTRZYMANIE ID ZDAJACEGO PO PESELU WYBRANYM
					Socket socket2 = new Socket("localhost", 8185);
					odbierz odbierzob2= null;
					wyslij wys2 = new wyslij("SELECT ID_ZDAJACEGO FROM ZDAJACY WHERE PESEL_ZDAJACEGO = " + comboBoxPeselEgzamin.getSelectedItem());
					odbierzob2 = this.zbindujWyslij(socket2, wys2);
					socket2.close();
					OdIdZdajacego = Integer.valueOf(odbierzob2.listaOdebranych.get(0).get(0)); // do zmiennnej trafia ID_ZDAJACEGO
					
					//OTRZYMAMY ID EGZAMINATORA DZIEKI NAZWISKU WYBRANEMU
					Socket socket3 = new Socket("localhost", 8185);
					odbierz odbierzob3= null;
					wyslij wys3 = new wyslij("SELECT ID_EGZAMINATORA FROM EGZAMINATOR WHERE NAZWISKO_EGZAMINATORA LIKE '" + JcomboBoxEgzaminatorzy.getSelectedItem() +"'");
					odbierzob3 = this.zbindujWyslij(socket3, wys3);
					socket3.close();
					obIdEgzaminatora = Integer.valueOf(odbierzob3.listaOdebranych.get(0).get(0)); // otrzymujemy ID_EGZAMINATORA
					
					//OTRZYMAMY ID_PRACOWNIKA_BIURA
					Socket socket4 = new Socket("localhost", 8185);
					odbierz odbierzob4= null;
					wyslij wys4 = new wyslij("SELECT ID_PRACOW_BIURA FROM PRACOWNIK_BIURA WHERE PESEL_PRACOW_BIURA =" + nazwaPracownikaBiura );
					odbierzob4 = this.zbindujWyslij(socket4, wys4);
					socket4.close();
					obIdEPracownikaBiura = Integer.valueOf(odbierzob4.listaOdebranych.get(0).get(0)); // otrzymujemy ID_EGZAMINATORA
					
					//SOCKET OD TWORZENIA EGZAMINU
					Socket socket1 = new Socket("localhost", 8185);
					odbierz odbierzob1= null;
					wyslij wys1 = new wyslij("INSERT INTO EGZAMIN VALUES(" + maxIdEgzaminu + ",'" + tFDataDodawanie.getText() + 
					"','" + JcomboBoxGodzina.getSelectedItem() + "'," + OdIdZdajacego + ",'" + JcomboBoxKategorie.getSelectedItem() +
					"','W trakcie'," + obIdEPracownikaBiura + "," + comboBoxIdPojazdu.getSelectedItem() + "," + obIdEgzaminatora +")");
					odbierzob1 = this.zbindujWyslij(socket1, wys1);
					socket1.close();
					
				}catch(Exception e4){
					e4.printStackTrace();
				}
			}
			
			
			
		}
		else if(klawisz == btnWyszukiwanieUytkownika){
			boolean TylkoLiczby=true;
			int iii = 0;
			while (TylkoLiczby == true && iii < tFPESEL.getText().length()) 
			{
				if (tFPESEL.getText().charAt(iii) <= ('0'-1)|| tFPESEL.getText().charAt(iii) >= 'a')
				{
					TylkoLiczby = false;
					JOptionPane.showMessageDialog(null,"W polu PESEL prosze wprowadzic cyfry bez separatorow","b³êdne dane", JOptionPane.ERROR_MESSAGE);
				}
				++iii;
			}
			if(TylkoLiczby){
			
			if((!tFPeselWyszukiwanie.getText().equals("")) && (!textField.getText().equals("")) && (!tFPESEL.getText().equals("")) 
					&& (!tFTelAdresWyszukiwanie.getText().equals(""))){
				lblBladWyszukiwania.setText("");
				try{
					
					Socket socketW = new Socket("localhost", 8185);
					odbierz odbierzW = null;
					wyslij wysW = new wyslij("SELECT IMIE_ZDAJACEGO, NAZWISKO_ZDAJACEGO, PESEL_ZDAJACEGO, ADRES_ZDAJACEGO FROM ZDAJACY");
					odbierzW = this.zbindujWyslij(socketW, wysW);
					socketW.close();
					
					System.out.println("sprawdzam poprawnosc: \n ------------------------------------");
					System.out.println(odbierzW.listaOdebranych.get(3).get(0));
					System.out.println(odbierzW.listaOdebranych.get(3).get(1));
					System.out.println(odbierzW.listaOdebranych.get(3).get(2));
					System.out.println(odbierzW.listaOdebranych.get(3).get(3));
					System.out.println(tFPeselWyszukiwanie.getText());
					System.out.println(textField.getText());
					System.out.println(tFPESEL.getText());
					System.out.println(tFTelAdresWyszukiwanie.getText());
					System.out.println("=======================================================");
					
					Poprawne = 0;
					// sprawdzam poprawnosc danych z baza danych czy sa zgodne
					for(int j = 0 ; j < odbierzW.listaOdebranych.size() ; j++){
						if(((odbierzW.listaOdebranych.get(j).get(0)).equals(tFPeselWyszukiwanie.getText())) && 
						((odbierzW.listaOdebranych.get(j).get(1)).equals(textField.getText())) &&
						((odbierzW.listaOdebranych.get(j).get(2)).equals(tFPESEL.getText())) &&
						((odbierzW.listaOdebranych.get(j).get(3)).equals(tFTelAdresWyszukiwanie.getText()))){
							Poprawne = 1;
						}
					}
					
					if(Poprawne == 1){
						lblBladWyszukiwania.setText("");
						
						// Informacje o egzaminach
						String[] YourExams = new String[] {
					            "Kategoria", "Status", "Data", "Godzina", "Nazwisko egzaminatora", "Imie egzaminatora", "Nr pojazdu"
					        };
						
						try {
							odbierz odbierzobEgzamin = null;
							odbierz odbierzobEgzaminator = null;
							odbierz odbierzobPojazd = null;
				           

							//pobieramy dane o egzaminie
							
							Socket socket = new Socket("localhost", 8185);
							
							wyslij wys = new wyslij("select KATEGORIA, STATUS_EGZAMINU, DATA_EGZAMINU, GODZINA_EGZAMINU from EGZAMIN where ID_ZDAJACEGO = (SELECT ID_ZDAJACEGO FROM ZDAJACY where PESEL_ZDAJACEGO LIKE '" + tFPESEL.getText() + "')");
							
							odbierzobEgzamin = this.zbindujWyslij(socket, wys);
							
							socket.close();


							//pobieramy dane o egzminatorze
							Socket socket1 = new Socket("localhost", 8185);
							
							wyslij wys1 = new wyslij("select NAZWISKO_EGZAMINATORA, IMIE_EGZAMINATORA from EGZAMINATOR, EGZAMIN, ZDAJACY where EGZAMINATOR.ID_EGZAMINATORA = EGZAMIN.ID_EGZAMINATORA AND EGZAMIN.ID_ZDAJACEGO = ZDAJACY.ID_ZDAJACEGO AND ZDAJACY.PESEL_ZDAJACEGO LIKE '" + tFPESEL.getText() + "'");

							odbierzobEgzaminator = this.zbindujWyslij(socket1, wys1);
							
							socket1.close();

							

							//pobieramy dane o pojezdzie
							Socket socket2 = new Socket("localhost", 8185);
							
							wyslij wys2 = new wyslij("select NR_REJESTRACYJNY from POJAZD, EGZAMIN, ZDAJACY where POJAZD.ID_POJAZDU = EGZAMIN.ID_POJAZDU AND EGZAMIN.ID_ZDAJACEGO = ZDAJACY.ID_ZDAJACEGO AND ZDAJACY.PESEL_ZDAJACEGO LIKE '" + tFPESEL.getText() + "'");

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
				
						    table = new JTable(dataTwojeEgzaminy, YourExams);
									
							scrollPane = new JScrollPane(table);
							scrollPane.setBounds(316, 392, 469, 106);
							c.add(scrollPane);
							c.revalidate();
					        
					      

						} catch (Exception e1) {
							System.err.println(e1);
						}
						//KONIEC TRY CATCH
						
						
					}		
					
				}catch(Exception e2){
					e2.printStackTrace();
				} // KONIEC DUZEGO TRY CATCH
				
				
			}else{
				lblBladWyszukiwania.setText("BRAK DANYCH");
				lblBladWyszukiwania.setForeground(Color.RED);
				
			}
			}
			
			if(Poprawne == 0){
				lblBladWyszukiwania.setText("NIEPOPRAWNE DANE");
				lblBladWyszukiwania.setForeground(Color.RED);
			}
			
		} // KONIEC KLAWISZA WUSZIKIWANIE UZYTKOWNIKA
		
		
		
	} // KONIEC ACTIONPERFORMED
	
	

	
	public static void main(String[] args) { 

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