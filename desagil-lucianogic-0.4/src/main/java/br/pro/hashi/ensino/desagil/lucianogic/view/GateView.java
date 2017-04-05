package br.pro.hashi.ensino.desagil.lucianogic.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;

import org.omg.CORBA.SystemException;

import br.pro.hashi.ensino.desagil.lucianogic.model.Gate;
import br.pro.hashi.ensino.desagil.lucianogic.model.LED;
import br.pro.hashi.ensino.desagil.lucianogic.model.Switch;
import br.pro.hashi.ensino.desagil.lucianogic.view.MainView;
import java.net.URL;

// Esta classe representa a interface de uma porta logica.
public class GateView extends FixedPanel implements ItemListener, ActionListener {

	// Necessario para serializar objetos desta classe.
	private static final long serialVersionUID = 1L;

	private Image image;
	private JCheckBox[] inBoxes;
	private JCheckBox outBox;

	private Switch[] switches;
	private Gate gate;
	private LED led;
	private JButton ledButton = new JButton();
	
	private Color color;
	


	public GateView(Gate gate) {
		super(370, 150);
		this.gate = gate;
		
		led = new LED(255,0,0);
		
		image =loadImage(gate.toString());
		// A componente JLabel representa simplesmente um texto fixo.
		// https://docs.oracle.com/javase/tutorial/uiswing/components/label.html
		color = new Color(led.getR(),led.getG(),led.getB());
	    ledButton.setBackground(color);
	    ledButton.setBorder(new RoundedBorder(50));
	    ledButton.addActionListener(this);
	   
	    
	 
		int size = gate.getSize();

		inBoxes = new JCheckBox[size];

		switches = new Switch[size];

		for(int i = 0; i < size; i++) {
			inBoxes[i] = new JCheckBox();

			// Esta linha garante que, sempre que o usuario clicar
			// na checkbox, o metodo itemStateChanged abaixo sera chamado.
			// https://docs.oracle.com/javase/tutorial/uiswing/components/button.html#checkbox
			inBoxes[i].addItemListener(this);

			switches[i] = new Switch();

			gate.connect(switches[i], i);
		}
			    

		   outBox = new JCheckBox();

		// Esta linha garante que outBox nao seja clicavel.
		outBox.setEnabled(false);
	
			 

	

		/* A PARTIR DESTE PONTO VOCE DEVE ENTENDER SOZINHO */

	
		int i =1;
		for(JCheckBox inBox: inBoxes) {
			if(gate.getSize()!=1){
			add(inBox,80,(i*78)/gate.getSize(),20,20);
			i++;
			}else{
				add(inBox,80,(i*56),20,20);
				i++;
				
			}
		}
		if(gate.getSize()==2){
		//add(outBox,250,(i*41)/gate.getSize(),20,20);
		add(ledButton,250,i*17,30,30);
		}else if(gate.getSize()==2){
		//add(outBox,250,(i*29)/gate.getSize(),20,20);
		add(ledButton,250,i*24,30,30);
		}else{
			add(ledButton,250,i*13,30,30);
		}
		//outBox.setSelected(gate.read());
		led.connect(gate, 0);
		System.out.println(led.isOn());
	
		ledButton.setEnabled(led.isOn());
		
		
		}

	public void makeColorGray(){
		led.setR(220);
		led.setG(220);
		led.setB(220);
		ledButton.setBackground(new Color(led.getR(),led.getG(),led.getB()));
	}
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		int i;
		for(i = 0; i < inBoxes.length; i++) {
			if(inBoxes[i] == event.getSource()) {
				break;
			}
		}

		switches[i].setOn(inBoxes[i].isSelected());
		//outBox.setSelected(gate.read());
		led.connect(gate, 0);
		System.out.println(led.isOn());
		ledButton.setEnabled(led.isOn());
		if(led.isOn()==true){
			ledButton.setBackground(color);
		}else{
			this.makeColorGray();
		}

		
	}
	
	// Necessario para carregar os arquivos de imagem.
		private Image loadImage(String filename) {
			URL url = getClass().getResource("/img/" + filename + ".png");
			ImageIcon icon = new ImageIcon(url);
			return icon.getImage();
		}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 120, 25, 120, 80, null);
		// Evita bugs visuais em alguns sistemas operacionais.
		getToolkit().sync();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		color = JColorChooser.showDialog(this, null, null);

		if(color != null) {
			ledButton.setBackground(color);
		}
	}

}
