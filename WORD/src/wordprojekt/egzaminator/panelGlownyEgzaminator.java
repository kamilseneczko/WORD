package wordprojekt.egzaminator;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import wordprojekt.panellogowaniaiwyboru.panelWyboru;
import wordprojekt.server.odbierz;
import wordprojekt.server.wyslij;

public class panelGlownyEgzaminator extends JFrame implements ActionListener {

	//da³em jakiœ na odwal aby warningi sie nie jara³y
	private static final long serialVersionUID = 1L;
	
	
	private JLabel jlabel_InfoOdpImie, jlabel_InfoOdpNazwisko, jlabel_InfoOdpID, jlabel_InfoOdpUprawnienia, jlabel_InfoOdpPESEL,
			jlabel_InfoImie, jlabel_InfoNazwisko, jlabel_InfoID, jlabel_InfoUprawnienia, jlabel_InfoPESEL,
			jlabel_IdZalogowanego, jlabel_Grafik, jlabel_GrafikDzienOd, jlabel_GrafikOd ,lblStatusEgz,
			jlabel_GrafikDo, jlabel_GrafikOdMiesiac, jlabel_GrafikDoDzien, jlabel_GrafikDoMiesiac;
	JComboBox<String> jcombobox_GrafikOdMiesiac,jcombobox_GrafikOdDzien, jcombobox_GrafikDoMiesiac, jcombobox_GrafikDoDzien;
	private JButton jbutton_GrafikSprawdz, jbutton_Wyloguj, btnZmien ;
	private ImageIcon imageicon_Kalendarz,imageicon_Wyloguj;
	private panelWyboru pWyboru;
	private JPanel jpanel_RamkaInfoGora, jpanel_RamkaInfoDol, jpanel_RamkaInfoLewo, jpanel_RamkaInfoPrawo,
			jpanel_RamkaGrafikGora, jpanel_RamkaGrafikDol, jpanel_RamkaGrafikLewo, jpanel_RamkaGrafikPrawo;
	private Font font_NaglowkiD = new Font("Tahoma",Font.BOLD,18);
	private Font font_NaglowkiS = new Font("Tahoma",Font.BOLD,12);
	private Font font_Naglowki = new Font("Tahoma",Font.BOLD,14);
	private Font font_Pogrubienie = new Font("Garamond",Font.BOLD,24);
	private int dzien, miesiac, rok, IdEgzaminatora;
	private String id_egzaminatora;
	private JTable jtTabelkaTwojeEgzaminy;
	JScrollPane jspPanelTwojeEgzaminy;
	
	private JComboBox JcomStatusId, JcomIdEgz;
	private JLabel lblNewLabel;
	
	
	//tymczasowe
	public static void main(String[] args) { // pPB panelPracownikBiura
		panelGlownyEgzaminator pPB = new panelGlownyEgzaminator("tester");
		pPB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pPB.setLocationRelativeTo(null);
		pPB.setVisible(true);

		}
	//tworzenie okienka
	public panelGlownyEgzaminator(String nazwaUz)
	{		
		
		//tworzenie okna
		this.setTitle("Okno Egzaminatora");
		setSize(800,500);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		//wgrywanie ikonek
		imageicon_Kalendarz = new ImageIcon("Kalendarz.png");
		imageicon_Wyloguj = new ImageIcon("wyloguj.png");
		
		
		//odczytanie aktualnej daty
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String dateString = dateFormat.format(currentDate);
		miesiac = Integer.valueOf(dateString);
		dateFormat = new SimpleDateFormat("yyyy");
		dateString = dateFormat.format(currentDate);
		rok = Integer.valueOf(dateString);
		dateFormat = new SimpleDateFormat("dd");
		dateString = dateFormat.format(currentDate);
		dzien = Integer.valueOf(dateString);		
		
		
		//ramka info
		jpanel_RamkaInfoLewo = new JPanel();
		jpanel_RamkaInfoLewo.setBackground(new Color(0, 0, 150));
		jpanel_RamkaInfoLewo.setBounds(10, 75, 3, 380);
		getContentPane().add(jpanel_RamkaInfoLewo);
		jpanel_RamkaInfoPrawo = new JPanel();
		jpanel_RamkaInfoPrawo.setBackground(new Color(0, 0, 150));
		jpanel_RamkaInfoPrawo.setBounds(300, 75, 3, 380);
		getContentPane().add(jpanel_RamkaInfoPrawo);
		jpanel_RamkaInfoGora = new JPanel();
		jpanel_RamkaInfoGora.setBackground(new Color(0, 0, 150));
		jpanel_RamkaInfoGora.setBounds(10, 75, 290, 3);
		getContentPane().add(jpanel_RamkaInfoGora);
		jpanel_RamkaInfoDol = new JPanel();
		jpanel_RamkaInfoDol.setBackground(new Color(0, 0, 150));
		jpanel_RamkaInfoDol.setBounds(10, 455, 293, 3);
		getContentPane().add(jpanel_RamkaInfoDol);
		//ramka grafik
		jpanel_RamkaGrafikGora = new JPanel();
		jpanel_RamkaGrafikGora.setBackground(new Color(230, 10, 10));
		jpanel_RamkaGrafikGora.setBounds(325, 75, 450, 3);
		getContentPane().add(jpanel_RamkaGrafikGora);
		jpanel_RamkaGrafikDol = new JPanel();
		jpanel_RamkaGrafikDol.setBackground(new Color(230, 10, 10));
		jpanel_RamkaGrafikDol.setBounds(325, 240, 450, 3);
		getContentPane().add(jpanel_RamkaGrafikDol);
		jpanel_RamkaGrafikLewo = new JPanel();
		jpanel_RamkaGrafikLewo.setBackground(new Color(230, 10, 10));
		jpanel_RamkaGrafikLewo.setBounds(325, 75, 3, 165);
		getContentPane().add(jpanel_RamkaGrafikLewo);
		jpanel_RamkaGrafikPrawo = new JPanel();
		jpanel_RamkaGrafikPrawo.setBackground(new Color(230, 10, 10));
		jpanel_RamkaGrafikPrawo.setBounds(775, 75, 3, 168);
		getContentPane().add(jpanel_RamkaGrafikPrawo);

		//jlabely
		jlabel_InfoImie = new JLabel("IMIÊ:");
		jlabel_InfoImie.setBounds(50, 105, 50, 25);
		jlabel_InfoImie.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoImie);
		jlabel_InfoNazwisko = new JLabel("NAZWISKO:");
		jlabel_InfoNazwisko.setBounds(30, 180, 90, 25);
		jlabel_InfoNazwisko.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoNazwisko);
		jlabel_InfoID = new JLabel("ID:");
		jlabel_InfoID.setBounds(60, 255, 30, 25);
		jlabel_InfoID.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoID);
		jlabel_InfoUprawnienia = new JLabel("UPRAWNIENIA:");
		jlabel_InfoUprawnienia.setBounds(20, 330, 120, 25);
		jlabel_InfoUprawnienia.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoUprawnienia);
		jlabel_InfoPESEL = new JLabel("PESEL:");
		jlabel_InfoPESEL.setBounds(45, 410, 90, 25);
		jlabel_InfoPESEL.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoPESEL);
		
		jlabel_InfoOdpImie = new JLabel();
		jlabel_InfoOdpImie.setBounds(120, 105, 220, 25);
		jlabel_InfoOdpImie.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoOdpImie);
		jlabel_InfoOdpNazwisko = new JLabel();
		jlabel_InfoOdpNazwisko.setBounds(120, 180, 220, 25);
		jlabel_InfoOdpNazwisko.setFont(font_NaglowkiS);
		getContentPane().add(jlabel_InfoOdpNazwisko);
		jlabel_InfoOdpID = new JLabel();
		jlabel_InfoOdpID.setBounds(120, 255, 200, 25);
		jlabel_InfoOdpID.setFont(font_Pogrubienie);
		getContentPane().add(jlabel_InfoOdpID);
		jlabel_InfoOdpUprawnienia = new JLabel();
		jlabel_InfoOdpUprawnienia.setBounds(120, 330, 220, 25);
		jlabel_InfoOdpUprawnienia.setFont(font_Pogrubienie);
		getContentPane().add(jlabel_InfoOdpUprawnienia);
		jlabel_InfoOdpPESEL = new JLabel();
		jlabel_InfoOdpPESEL.setBounds(120, 410, 220, 25);
		jlabel_InfoOdpPESEL.setFont(font_Pogrubienie);
		getContentPane().add(jlabel_InfoOdpPESEL);
		
		jlabel_IdZalogowanego = new JLabel("Jesteœ zalogowany jako Egzaminator");
		jlabel_IdZalogowanego.setBounds(15, 25, 400, 25);
		jlabel_IdZalogowanego.setFont(font_NaglowkiD);
		getContentPane().add(jlabel_IdZalogowanego);
		//jlabely grafiku
		jlabel_Grafik = new JLabel("Grafik Egzaminów");
		jlabel_Grafik.setBounds(465, 80, 220, 25);
		jlabel_Grafik.setFont(font_Naglowki);
		getContentPane().add(jlabel_Grafik);
		jlabel_GrafikOd = new JLabel("Od:");
		jlabel_GrafikOd.setBounds(345, 125, 50, 25);
		jlabel_GrafikOd.setFont(font_NaglowkiD);
		getContentPane().add(jlabel_GrafikOd);
		jlabel_GrafikOdMiesiac = new JLabel("Wybierz miesica");
		jlabel_GrafikOdMiesiac.setBounds(385, 110, 220, 25);
		getContentPane().add(jlabel_GrafikOdMiesiac);
		jlabel_GrafikDzienOd = new JLabel("Wybierz dzieñ");
		jlabel_GrafikDzienOd.setBounds(525, 110, 220, 25);
		getContentPane().add(jlabel_GrafikDzienOd);
		jlabel_GrafikDo = new JLabel("Do:");
		jlabel_GrafikDo.setBounds(345, 195, 50, 25);
		jlabel_GrafikDo.setFont(font_NaglowkiD);
		getContentPane().add(jlabel_GrafikDo);
		jlabel_GrafikDoMiesiac = new JLabel("Wybierz miesica");
		jlabel_GrafikDoMiesiac.setBounds(385, 180, 220, 25);
		getContentPane().add(jlabel_GrafikDoMiesiac);
		jlabel_GrafikDoDzien = new JLabel("Wybierz dzieñ");
		jlabel_GrafikDoDzien.setBounds(525, 180, 220, 25);
		getContentPane().add(jlabel_GrafikDoDzien);
		
		//combobox grafiku
		jcombobox_GrafikOdMiesiac = new JComboBox<String>();
		jcombobox_GrafikOdMiesiac.setBounds(385, 140, 90, 20);
		jcombobox_GrafikOdMiesiac.addItem("");
		for(int i=miesiac;i<=12; i++)
		{
			jcombobox_GrafikOdMiesiac.addItem(rok+"-" + i);
		}
		for(int i=1; i<miesiac; i++)
		{
			jcombobox_GrafikOdMiesiac.addItem(rok+1+"-" + i);
		}
		getContentPane().add(jcombobox_GrafikOdMiesiac);
		jcombobox_GrafikOdMiesiac.addActionListener(this);
		jcombobox_GrafikOdDzien = new JComboBox<String>();
		jcombobox_GrafikOdDzien.setBounds(525, 140, 90, 20);
		jcombobox_GrafikOdDzien.addItem(" ");
		jcombobox_GrafikOdDzien.setEnabled(false);
		getContentPane().add(jcombobox_GrafikOdDzien);
		jcombobox_GrafikOdDzien.addActionListener(this);
		jcombobox_GrafikDoMiesiac = new JComboBox<String>();
		jcombobox_GrafikDoMiesiac.setBounds(385, 210, 90, 20);
		jcombobox_GrafikDoMiesiac.addItem(" ");
		jcombobox_GrafikDoMiesiac.setEnabled(false);
		getContentPane().add(jcombobox_GrafikDoMiesiac);
		jcombobox_GrafikDoMiesiac.addActionListener(this);
		jcombobox_GrafikDoDzien = new JComboBox<String>();
		jcombobox_GrafikDoDzien.setBounds(525, 210, 90, 20);
		jcombobox_GrafikDoDzien.addItem("");
		jcombobox_GrafikDoDzien.setEnabled(false);
		getContentPane().add(jcombobox_GrafikDoDzien);
		jcombobox_GrafikDoDzien.addActionListener(this);
		
		
		//przyciski
		jbutton_GrafikSprawdz = new JButton("Sprawdz",imageicon_Kalendarz);
		jbutton_GrafikSprawdz.setBounds(635, 150, 130, 50);
		getContentPane().add(jbutton_GrafikSprawdz);
		jbutton_GrafikSprawdz.setEnabled(false);
		jbutton_GrafikSprawdz.addActionListener(this);
		jbutton_Wyloguj = new JButton("Wyloguj",imageicon_Wyloguj);
		jbutton_Wyloguj.setBounds(635, 10, 130, 50);
		getContentPane().add(jbutton_Wyloguj);
		jbutton_Wyloguj.addActionListener(this);
		
		//odczytanie informacji o u¿ytkowniku
		try {
			Socket socket = new Socket("localhost", 8185);
			odbierz odbierzob = null;
			wyslij wys = new wyslij("SELECT IMIE_EGZAMINATORA, NAZWISKO_EGZAMINATORA, ID_EGZAMINATORA, UPRAWNIENA_NA_KATEGORIE, PESEL_EGZAMINATORA  from EGZAMINATOR where PESEL_EGZAMINATORA LIKE " + nazwaUz);	
			odbierzob = this.zbindujWyslij(socket, wys);
			socket.close();
			jlabel_InfoOdpImie.setText(odbierzob.listaOdebranych.get(0).get(0));
			jlabel_InfoOdpNazwisko.setText(odbierzob.listaOdebranych.get(0).get(1));
			jlabel_InfoOdpID.setText(odbierzob.listaOdebranych.get(0).get(2));
			jlabel_InfoOdpUprawnienia.setText(odbierzob.listaOdebranych.get(0).get(3));
			jlabel_InfoOdpPESEL.setText(odbierzob.listaOdebranych.get(0).get(4));
			
			lblStatusEgz = new JLabel("ID Egzaminu:");
			lblStatusEgz.setBounds(335, 249, 76, 14);
			getContentPane().add(lblStatusEgz);
			try{
				JcomStatusId = new JComboBox();
				JcomStatusId.setBounds(613, 246, 90, 20);
				JcomStatusId.addItem("w trakcie");
				JcomStatusId.addItem("pozytywny");
				JcomStatusId.addItem("negatywny");
				getContentPane().add(JcomStatusId);		
				
				JcomIdEgz = new JComboBox();
				JcomIdEgz.setBounds(423, 246, 39, 20);
				//pobieram ID_EGZAMINATORA Z JEGO PESELU
				IdEgzaminatora = 0;
				Socket socket2 = new Socket("localhost", 8185);
				odbierz odbierzIdEgzaminatora = null;
				wyslij wys2 = new wyslij("SELECT ID_EGZAMINATORA FROM EGZAMINATOR WHERE PESEL_EGZAMINATORA = " + nazwaUz + "");
				odbierzIdEgzaminatora = this.zbindujWyslij(socket2, wys2);
				socket2.close();
				IdEgzaminatora = Integer.valueOf(odbierzIdEgzaminatora.listaOdebranych.get(0).get(0));
				
				
				Socket socket1 = new Socket("localhost", 8185);
				odbierz odbierzIdEgzaminu = null;
				wyslij wys1 = new wyslij("SELECT ID_EGZAMINU FROM EGZAMIN WHERE ID_EGZAMINATORA = " + IdEgzaminatora + "" );
				odbierzIdEgzaminu = this.zbindujWyslij(socket1, wys1);
				socket1.close();
				for(int i = 0 ; i < odbierzIdEgzaminu.listaOdebranych.size(); i++){
					JcomIdEgz.addItem(odbierzIdEgzaminu.listaOdebranych.get(i).get(0));
				}
				getContentPane().add(JcomIdEgz);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			btnZmien = new JButton("Zmie\u0144");
			btnZmien.setBounds(713, 245, 71, 23);
			getContentPane().add(btnZmien);
			
			lblNewLabel = new JLabel("Status Egzaminu:");
			lblNewLabel.setBounds(495, 249, 110, 14);
			getContentPane().add(lblNewLabel);
			btnZmien.addActionListener(this);
			id_egzaminatora=odbierzob.listaOdebranych.get(0).get(2);
		}
		catch (Exception e1) {
			System.err.println(e1);
		}
	}
	
	//metoda wykorzystywana do komunikacji z baza danych
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
	
	//metoda do okreslania dni w danym miesiacu
	public int iledni(String miesiac)
	{
		if(miesiac.equals(rok+"-1") || miesiac.equals((rok+1)+"-1") ||miesiac.equals(rok+"-3") || miesiac.equals((rok+1)+"-3") || miesiac.equals(rok+"-5") || miesiac.equals((rok+1)+"-5") || miesiac.equals(rok+"-7") || miesiac.equals((rok+1)+"-7") || miesiac.equals(rok+"-8") || miesiac.equals((rok+1)+"-8") || miesiac.equals(rok+"-10") || miesiac.equals((rok+1)+"-10") || miesiac.equals(rok+"-12") || miesiac.equals((rok+1)+"-12"))
		{
			return 31;
		}
		else if(miesiac.equals(rok+"-2"))
		{
			if(rok==2020 || rok==2024)
				return 29;
			else
				return 28;
		}
		else if(miesiac.equals((rok+1)+"-2"))
		{
			if(rok==2019 || rok==2023)
				return 29;
			else
				return 28;
		}
		else
			return 30;
	}
	
	
	//metoda resetujaca wybrane decyzje gdy przerwiewmy wczescijsza sesje
	public void reste ()
	{
		jcombobox_GrafikOdDzien.setEnabled(false);
		jbutton_GrafikSprawdz.setEnabled(false);
	}
	
	//obsluga przyciskow
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		//wylogowanie sie
		if (z==jbutton_Wyloguj)
		{
			if (pWyboru == null) {
				pWyboru = new panelWyboru();
				pWyboru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pWyboru.setLocationRelativeTo(null);
				pWyboru.setVisible(true);
				setVisible(false);
			}
		}
		//obsluga jcombobox_GrafikOdMiesiac
		if (z==jcombobox_GrafikOdMiesiac)
		{
			jcombobox_GrafikOdDzien.removeAllItems();
			jcombobox_GrafikOdDzien.addItem("");
			jcombobox_GrafikOdDzien.setEnabled(false);
			if(jcombobox_GrafikOdMiesiac.getSelectedIndex()!=0)
			{
				
				if(jcombobox_GrafikOdMiesiac.getSelectedIndex()==1)
				{
					for(int i=dzien; i<=iledni((String)jcombobox_GrafikOdMiesiac.getSelectedItem());i++)
					{
						jcombobox_GrafikOdDzien.addItem(Integer.toString(i));
					}
				}
				else
				{
					System.out.println(iledni((String)jcombobox_GrafikOdMiesiac.getSelectedItem()));
					for(int i=1; i<=iledni((String)jcombobox_GrafikOdMiesiac.getSelectedItem());i++)
					{
						jcombobox_GrafikOdDzien.addItem(Integer.toString(i));
					}
				}
				jcombobox_GrafikOdDzien.setEnabled(true);
			}
		}
		//obsluga jcombobox_GrafikOdDzien
		if (z==jcombobox_GrafikOdDzien)
		{
			jcombobox_GrafikDoMiesiac.removeAllItems();
			jcombobox_GrafikDoMiesiac.addItem("");
			jcombobox_GrafikDoMiesiac.setEnabled(false);
			if(jcombobox_GrafikOdDzien.getSelectedIndex()!=0)
			{
				jcombobox_GrafikDoMiesiac.addItem((String)jcombobox_GrafikOdMiesiac.getSelectedItem());
				if(jcombobox_GrafikOdMiesiac.getSelectedIndex()!=12)
					jcombobox_GrafikDoMiesiac.addItem((String)jcombobox_GrafikOdMiesiac.getItemAt(jcombobox_GrafikOdMiesiac.getSelectedIndex()+1));
				else
					jcombobox_GrafikDoMiesiac.addItem(rok+1+"-" + miesiac);
				jcombobox_GrafikDoMiesiac.setEnabled(true);
			}
		}
		//obsluga jcombobox_GrafikDoMiesiac
		if (z==jcombobox_GrafikDoMiesiac)
		{
			
			jcombobox_GrafikDoDzien.removeAllItems();
			jcombobox_GrafikDoDzien.addItem("");
			jcombobox_GrafikDoDzien.setEnabled(false);
			if(jcombobox_GrafikDoMiesiac.getSelectedIndex()!=0)
			{
				
				if(jcombobox_GrafikDoMiesiac.getSelectedIndex()==1)
				{
					for(int i=Integer.valueOf((String)jcombobox_GrafikOdDzien.getSelectedItem()); i<=iledni((String)jcombobox_GrafikDoMiesiac.getSelectedItem()); i++)
					{
						jcombobox_GrafikDoDzien.addItem(Integer.toString(i));
					}
				}
				else if(jcombobox_GrafikDoMiesiac.getSelectedIndex()==2)
				{
					for(int i=1; i<=iledni((String)jcombobox_GrafikDoMiesiac.getSelectedItem()); i++)
					{
						jcombobox_GrafikDoDzien.addItem(Integer.toString(i));
					}
				}
				jcombobox_GrafikDoDzien.setEnabled(true);
			}		
		}
		//obsluga jcombobox_GrafikDoDzien
		if (z==jcombobox_GrafikDoDzien)
		{
			if(jcombobox_GrafikDoDzien.getSelectedIndex()!=0)
				jbutton_GrafikSprawdz.setEnabled(true);
			else
				jbutton_GrafikSprawdz.setEnabled(false);
		}
		//obs³uga przycisku sprawdzania
		if (z==jbutton_GrafikSprawdz)
		{
			try {
				Socket socket = new Socket("localhost", 8185);
				odbierz odbierzob = null;
				wyslij wys = new wyslij("SELECT DATA_EGZAMINU, GODZINA_EGZAMINU, KATEGORIA ,ID_ZDAJACEGO, NR_REJESTRACYJNY from EGZAMIN join POJAZD on POJAZD.ID_POJAZDU = EGZAMIN.ID_POJAZDU where ID_EGZAMINATORA LIKE " + id_egzaminatora +" AND DATA_EGZAMINU BETWEEN '" + (String)jcombobox_GrafikOdMiesiac.getSelectedItem() + "-"+ (String)jcombobox_GrafikOdDzien.getSelectedItem() +"' AND '" + (String)jcombobox_GrafikDoMiesiac.getSelectedItem() + "-" + (String)jcombobox_GrafikDoDzien.getSelectedItem() + "' ORDER BY EGZAMIN.DATA_EGZAMINU, EGZAMIN.GODZINA_EGZAMINU");
				odbierzob = this.zbindujWyslij(socket, wys);
				socket.close();
				String[] columnsTwojeEgzaminy = new String[] 
						{
			            "Data", " Godzina ", " Kategoria ", " ID Zdaj¹cego ", " nr. Rejestracyjny "
			            };
				System.out.println("egzamin = " + odbierzob.listaOdebranych.size());
				Object[][] dataTwojeEgzaminy = new Object[odbierzob.listaOdebranych.size()][5];
				for(int i = 0; i < odbierzob.listaOdebranych.size(); i++){
					for(int k = 0; k < 5; k++){
						dataTwojeEgzaminy[i][k] = odbierzob.listaOdebranych.get(i).get(k);
					}
				}				
				jtTabelkaTwojeEgzaminy = new JTable(dataTwojeEgzaminy, columnsTwojeEgzaminy);
		        jtTabelkaTwojeEgzaminy.setBounds(100, 100, 100, 100);
		        jspPanelTwojeEgzaminy = new JScrollPane(jtTabelkaTwojeEgzaminy);
		        jspPanelTwojeEgzaminy.setBounds(325, 265, 455, 193);
		        jspPanelTwojeEgzaminy.setEnabled(false);
		        getContentPane().add(jspPanelTwojeEgzaminy);
			}
			catch (Exception e1) {
				System.err.println(e1);
			}
		}
		//ubsluga zmiany statusu egzaminu
		if(z == btnZmien){
			try{
				
				Socket socket = new Socket("localhost", 8185);
				odbierz odbierzob = null;
				wyslij wys = new wyslij("UPDATE EGZAMIN SET STATUS_EGZAMINU = '" + JcomStatusId.getSelectedItem() + "' where ID_EGZAMINU = " + JcomIdEgz.getSelectedItem() +"");
				odbierzob = this.zbindujWyslij(socket, wys);
				socket.close();
				
				
			}catch(Exception e2){
				System.out.println(e2);
			}
		}
	}
}