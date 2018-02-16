package wordprojekt.panellogowaniaiwyboru;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class panelWyboru extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JButton bZdajacy, bPracownikBiura, bEgzaminator;

	
	public panelWyboru(){

		setSize(588,248);
		setTitle("Panel wyboru");
		getContentPane().setLayout(null);
		
		bZdajacy = new JButton("Zdajacy");
		bZdajacy.setBounds(23, 31, 170, 156);
		getContentPane().add(bZdajacy);
		bZdajacy.addActionListener(this);
		
		bPracownikBiura = new JButton("Pracownik biura");
		bPracownikBiura.setBounds(203, 31, 170, 156);
		getContentPane().add(bPracownikBiura);
		bPracownikBiura.addActionListener(this);
		
		bEgzaminator = new JButton("Egzaminator");
		bEgzaminator.setBounds(386, 31, 170, 156);
		getContentPane().add(bEgzaminator);
		bEgzaminator.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object zrodlo = e.getSource();
		
		if(zrodlo == bZdajacy){
			panelLogowania plogowania = new panelLogowania("Zdajacy");
			plogowania.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			plogowania.setLocationRelativeTo(null);
			plogowania.setVisible(true);
			
			this.setVisible(false);
			
		} else if(zrodlo == bPracownikBiura ){
			panelLogowania plogowania = new panelLogowania("Pracownik biura");
			plogowania.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			plogowania.setLocationRelativeTo(null);
			plogowania.setVisible(true);
			
			this.setVisible(false);
			
		} else if(zrodlo == bEgzaminator){
			panelLogowania plogowania = new panelLogowania("Egzaminator");
			plogowania.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			plogowania.setLocationRelativeTo(null);
			plogowania.setVisible(true);
			
			this.setVisible(false);
		}
		
	}
	
	
	public static void main(String[] args) {
		
		panelWyboru aplikacja = new panelWyboru();
		aplikacja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aplikacja.setLocationRelativeTo(null);
		aplikacja.setVisible(true);
		

		

	}


}
