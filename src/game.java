import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.*;





public class game extends JFrame implements ActionListener, ChangeListener {
	int R1Int =10;
	int R2Int =-10;
	int DInt =10;
	

	double n = 1.52;
	double f = Math.floor(1.0/((n-1)*(1.0/R1Int-1.0/R2Int+(n-1)*DInt*0.1/(n*R1Int*R2Int)))*100)/100;
	int R1old=0;
	int R2old=0;
	int DOld;
	
	public int xCoordinate = 400;
	public int yCoordinate = 100-Math.abs(R1Int);
		   
	public int xCoordinate2 = xCoordinate-DInt;
	public int yCoordinate2 = 100-Math.abs(R2Int);
	
	//public JButton modelButton = new JButton("Model");
	public JButton clearButton = new JButton("Clear");
	JSlider R1slider = new JSlider(JSlider.HORIZONTAL, -500, 500, R1Int);
	JSlider R2slider = new JSlider(JSlider.HORIZONTAL, -500, 500, R2Int);
	JSlider Dslider = new JSlider(JSlider.HORIZONTAL, 0, 500, DInt);
	
	JLabel refInd = new JLabel("Lens refractive index");			
	public JTextField nInd = new JTextField(Double.toString(n));
	
	JLabel fInd = new JLabel("Lens focal length, cm");			
	public JTextField fValue = new JTextField(Double.toString(f));
	
	JLabel Radius1 = new JLabel ("First surface radius, R1 = " + R1Int + "cm");
	JLabel Radius2 = new JLabel ("Second surface radius,R2 = " + R2Int + "cm");
	JLabel D = new JLabel ("Lens thickness, d = " + DInt + "mm");

    public GPanel imagePanel = new GPanel(); 
	public JPanel resultPanel = new JPanel();
	
	public game () {
		
        setDefaultLookAndFeelDecorated(true);
		
		this.setTitle("Lens makers game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		Border margins = BorderFactory.createEmptyBorder(10, 20, 20, 10);
		mainPanel.setBorder(margins);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints mainPanelGBC = new GridBagConstraints();
		mainPanelGBC.insets = new Insets(10, 10, 10, 10);
		
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new GridBagLayout());
		GridBagConstraints ioPanelGBC = new GridBagConstraints();
		ioPanelGBC.insets = new Insets(5, 5, 5, 5);
		ioPanelGBC.gridy =0;
		ioPanelGBC.gridx =0;
		//ioPanel.add(modelButton,ioPanelGBC);
		//modelButton.addActionListener(this);
		//ioPanelGBC.gridy =0;
		//ioPanelGBC.gridx =1;
		ioPanel.add(clearButton,ioPanelGBC);
		clearButton.addActionListener(new ActionListener(){

	    	   public void actionPerformed(ActionEvent ev){
	    		  R1Int =10;
	    		  R2Int =-10;
	    		  DInt =10;
	    		
	    		  xCoordinate = 400;
	    		  yCoordinate = 100-Math.abs(R1Int);
	    		  xCoordinate2 = xCoordinate-DInt;
	    		  yCoordinate2 = 100-Math.abs(R2Int);

	    		  n = 1.52;	    		  
	    		  f = Math.floor(1.0/((n-1)*(1.0/R1Int-1.0/R2Int+(n-1)*DInt*0.1/(n*R1Int*R2Int)))*100)/100;
	    		  
	    		  nInd.setText(Double.toString(n));
	    		  R1slider.setValue(R1Int);
	    		  R2slider.setValue(R2Int);
	    		  Dslider.setValue(DInt);
	    		  	    		 
	    		  fValue.setText(Double.toString(f));
	              imagePanel.removeAll();
	  			  imagePanel.repaint();
	    	   }
	    	 });
		nInd.addActionListener(new ActionListener(){
			
	    	   public void actionPerformed(ActionEvent ev){
	    		 
	    		   fCalc();
	    		   fValue.setText(Double.toString(f));
	    		     
	    		   
	    	   }
		});
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints inputPanelGBC = new GridBagConstraints();
		inputPanelGBC.insets = new Insets(5, 5, 5, 5);
		inputPanelGBC.gridy =0;
		inputPanelGBC.gridx =0;
		
		JPanel refPanel = new JPanel();
		refPanel.setLayout(new GridBagLayout());
		GridBagConstraints refPanelGBC = new GridBagConstraints();
		refPanelGBC.insets = new Insets(5, 5, 5, 5);
		refPanelGBC.gridy =0;
		refPanelGBC.gridx =0;
		
		nInd.setEditable(true);
		nInd.setPreferredSize( new Dimension(50, 24 ) );
		refPanel.add(refInd,refPanelGBC);
		
		refPanelGBC.gridy =0;
		refPanelGBC.gridx =1;

		refPanel.add(nInd,refPanelGBC);	
		
		inputPanel.add(refPanel,inputPanelGBC);
		
		inputPanelGBC.gridy =1;
		inputPanelGBC.gridx =0;
		
		
		
		Radius1.setPreferredSize(new Dimension(200, 25));
		inputPanel.add(Radius1,inputPanelGBC);
		
		inputPanelGBC.gridy =1;
		inputPanelGBC.gridx =1;
		
		R1slider.setMinorTickSpacing(10);
		R1slider.setMajorTickSpacing(100);
		R1slider.setPaintTicks(true);
		R1slider.setPaintLabels(true);
		R1slider.setPreferredSize(new Dimension(400, 100));
		R1slider.addChangeListener(this);		
		inputPanel.add(R1slider,inputPanelGBC);
		
		
		inputPanelGBC.gridy =2;
		inputPanelGBC.gridx =0;
		Radius2.setPreferredSize(new Dimension(200, 25));
		inputPanel.add(Radius2,inputPanelGBC);
		inputPanelGBC.gridy =2;
		inputPanelGBC.gridx =1;
		
		R2slider.setMinorTickSpacing(10);
		R2slider.setMajorTickSpacing(100);
		R2slider.setPaintTicks(true);
		R2slider.setPaintLabels(true);
		R2slider.setPreferredSize(new Dimension(400, 100));
		R2slider.addChangeListener(this);		
		inputPanel.add(R2slider,inputPanelGBC);
		
		inputPanelGBC.gridy =3;
		inputPanelGBC.gridx =0;
		D.setPreferredSize(new Dimension(200, 25));
		inputPanel.add(D,inputPanelGBC);
		inputPanelGBC.gridy =3;
		inputPanelGBC.gridx =1;
		
		Dslider.setMinorTickSpacing(10);
		Dslider.setMajorTickSpacing(100);
		Dslider.setPaintTicks(true);
		Dslider.setPaintLabels(true);
		Dslider.setPreferredSize(new Dimension(400, 100));
		Dslider.addChangeListener(this);		
		inputPanel.add(Dslider,inputPanelGBC);
		
		 
	        resultPanel.setLayout(new GridBagLayout());
			GridBagConstraints resultPanelGBC = new GridBagConstraints();
			resultPanelGBC.insets = new Insets(5, 5, 5, 5);
			resultPanelGBC.gridy =0;
			resultPanelGBC.gridx =0;
			resultPanel.add(fInd,resultPanelGBC);
			
			resultPanelGBC.gridy =0;
			resultPanelGBC.gridx =1;
			
			fValue.setEditable(false);
			fValue.setPreferredSize( new Dimension( 50, 24 ) );
			fValue.setText(Double.toString(f));
			resultPanel.add(fValue,resultPanelGBC);		
			
			inputPanelGBC.gridy =4;
			inputPanelGBC.gridx =0;
			inputPanel.add(resultPanel,inputPanelGBC);	
				  	
		mainPanelGBC.gridy =0;
		mainPanelGBC.gridx =0;
		mainPanel.add(inputPanel, mainPanelGBC);
		mainPanelGBC.gridy =1;
		mainPanelGBC.gridx =0;
		mainPanel.add(ioPanel, mainPanelGBC);
		//mainPanelGBC.gridy =2;
		//mainPanelGBC.gridx =0;
		//mainPanel.add(imagePanel, mainPanelGBC);		
     
    	 
		
		this.add(mainPanel,BorderLayout.PAGE_START);
		this.add(imagePanel,BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(800, 800);
		this.setVisible(true);
		
	}
	class GPanel extends JPanel {
	    
	    public void paintComponent(Graphics g) {
	      
	    	
	    	g.setColor(Color.blue);
	        //rectangle originates at 10,10 and ends at 240,240
	    	super.paintComponent(g);
	    	g.drawLine(0, 100, 800, 100);
	    	g.drawLine(400, 0, 400,400);
	    	if (R1Int>0) {
	        g.drawArc(xCoordinate,yCoordinate,2*Math.abs(R1Int),2*Math.abs(R1Int),90,180);
	    		    	
	    	g.fillOval(xCoordinate+Math.abs(R1Int)+5/2, yCoordinate+Math.abs(R1Int)-5/2, 5,5);
	    	
	    	}
	    	else if (R1Int<0) {
		        g.drawArc(xCoordinate,yCoordinate,2*Math.abs(R1Int),2*Math.abs(R1Int),90,-180);
		    		    	
		    	g.fillOval(xCoordinate+Math.abs(R1Int)+5/2, yCoordinate+Math.abs(R1Int)-5/2, 5,5);
		    	
		    	}
	    	
	    	g.setColor(Color.red);
	    	
	    	if (R2Int>0 & R1Int>0) {
		        g.drawArc(xCoordinate2,yCoordinate2,2*Math.abs(R2Int),2*Math.abs(R2Int),90,180);
		    		    	
		    	g.fillOval(xCoordinate2+Math.abs(R2Int)+5/2, yCoordinate2+Math.abs(R2Int)-5/2, 5,5);
		    	
		    	}
		    	else if (R2Int<0  & R1Int>0) {
			        g.drawArc(xCoordinate2,yCoordinate2,2*Math.abs(R2Int),2*Math.abs(R2Int),90,-180);
			    		    	
			    	g.fillOval(xCoordinate2+Math.abs(R2Int)+5/2, yCoordinate2+Math.abs(R2Int)-5/2, 5,5);
			    	
			    	}
		    	else if (R2Int>0  & R1Int<0) {
			        g.drawArc(xCoordinate2,yCoordinate2,2*Math.abs(R2Int),2*Math.abs(R2Int),90,180);
			    		    	
			    	g.fillOval(xCoordinate2+Math.abs(R2Int)+5/2, yCoordinate2+Math.abs(R2Int)-5/2, 5,5);
			    	
			    	}
		    	else if (R2Int<0  & R1Int<0) {
			        g.drawArc(xCoordinate2,yCoordinate2,2*Math.abs(R2Int),2*Math.abs(R2Int),90,-180);
			    		    	
			    	g.fillOval(xCoordinate2+Math.abs(R2Int)+5/2, yCoordinate2+Math.abs(R2Int)-5/2, 5,5);
			    	
			    	}
	    }
	    
	}
	
	public double fCalc() {
		try
	    {
			n =  Double.parseDouble(nInd.getText()); //if value cannot be parsed, a NumberFormatException will be thrown
	    }
	    catch (final NumberFormatException e1)
	    {
	        n = 1.52;
	        nInd.setText("1.52");    };
	        
		return f = Math.floor(1.0/(((n-1)*(1.0/R1Int-1.0/R2Int+(n-1)*DInt*0.1/(n*R1Int*R2Int))))*100)/100;
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == R1slider) {
			R1old =R1Int;			
			R1Int =R1slider.getValue();  
			if (R1Int==0) {R1Int=1;}
			Radius1.setText("First surface radius, R1 = " + R1Int + "cm");			
			
			//xCoordinate = xCoordinate;
			if ((R1Int>0) & (R1old>0)) {
			xCoordinate=400;
            yCoordinate = yCoordinate-(Math.abs(R1Int)-Math.abs(R1old));}
			else if ((R1Int<0) & (R1old>0))	{
		    xCoordinate = 400-2*Math.abs(R1Int);
		    yCoordinate = yCoordinate-(Math.abs(R1Int)-Math.abs(R1old));
			}
			else if ((R1Int<0) & (R1old<0))	{
			    xCoordinate = 400-2*(Math.abs(R1Int));
			    yCoordinate = yCoordinate-(Math.abs(R1Int)-Math.abs(R1old));
				}
			else if ((R1Int>0) & (R1old<0))	{
			    xCoordinate = 400;
			    yCoordinate = yCoordinate-(Math.abs(R1Int)-Math.abs(R1old));
				}
			fCalc();
            this.fValue.setText(Double.toString(f));
          	this.imagePanel.removeAll();
			this.imagePanel.repaint();
			

		}
		
		else if (e.getSource() == R2slider) {
			R2old = R2Int;
			R2Int = R2slider.getValue();
			if (R2Int==0) {R2Int=1;}
			Radius2.setText("Second surface radius,R2 = " + R2Int + "cm");						
	if ((R2Int<0) & (R2old<0)) {
			xCoordinate2 = 400+DInt-2*(Math.abs(R2Int));			
            yCoordinate2 = yCoordinate2-(Math.abs(R2Int)-Math.abs(R2old));}	
	else if ((R2Int>0) & (R2old<0)) {
		xCoordinate2 = 400+DInt;			
        yCoordinate2 = yCoordinate2-(Math.abs(R2Int)-Math.abs(R2old));}
	else if ((R2Int<0) & (R2old>0)) {
		xCoordinate2 = xCoordinate2-2*Math.abs(R2Int);			
        yCoordinate2 = yCoordinate2-(Math.abs(R2Int)-Math.abs(R2old));}
	else if ((R2Int>0) & (R2old>0)) {
		xCoordinate2 = 400+DInt;			
        yCoordinate2 = yCoordinate2-(Math.abs(R2Int)-Math.abs(R2old));}
            
	        fCalc();
            this.fValue.setText(Double.toString(f));
			this.imagePanel.removeAll();
			this.imagePanel.repaint();
		}
		
		else if (e.getSource() == Dslider) {
			DOld=DInt;
			DInt = Dslider.getValue();
			D.setText("Lens thickness, d = " + DInt + "mm");			
			xCoordinate2 = xCoordinate2+(DInt-DOld);
			
			fCalc();
            this.fValue.setText(Double.toString(f));
			this.imagePanel.removeAll();
			this.imagePanel.repaint();
			
		}
	}
	

	

	
	public static void main(String[] args) {
		game gameWindow = new game();
		
		
		
	}
	
}
