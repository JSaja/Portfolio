package hw5.game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author baylor
 */
public class CharacterCreationForm
	extends javax.swing.JDialog
{
	protected Agent aCharacter = new Player("player");
        int timesEquippedButtonShirt = 0;        
        int timesEquippedAlienSuit = 0;
        int timesEquippedArgonianTorso = 0; 
	/**
	 * Creates new form CharacterCreationForm
	 */
	public CharacterCreationForm()
	{
		setModal(true);
		initComponents();
		setLocationRelativeTo(null);
		loadComboBoxes();
		repaint();
	}
        DefaultComboBoxModel headModel = new DefaultComboBoxModel();
        DefaultComboBoxModel headModel2 = new DefaultComboBoxModel();
        DefaultComboBoxModel torsoModel = new DefaultComboBoxModel();
        DefaultComboBoxModel torsoModel2 = new DefaultComboBoxModel();
        DefaultComboBoxModel torsoModel3 = new DefaultComboBoxModel();
		            
	private void loadComboBoxes()
	{
                DefaultComboBoxModel backgroundModel = new DefaultComboBoxModel();
		backgroundModel.addElement(null);
		backgroundModel.addElement(new EquipableItem("background scary forest", "background scary forest"));	
                backgroundModel.addElement(new EquipableItem("background forest", "background forest"));
                backgroundModel.addElement(new EquipableItem("background white", "background white"));	  
                backgroundModel.addElement(new EquipableItem("skyrim", "Skyrim"));	 
                backgroundModel.addElement(new EquipableItem("spacebackground", "Space"));                        
		backgroundComboBox.setModel(backgroundModel);
                
                DefaultComboBoxModel capeModel = new DefaultComboBoxModel();
                capeModel.addElement(null);
                capeModel.addElement(new EquipableItem("bluecape", "Blue Cape"));
                capeModel.addElement(new EquipableItem("redcape", "Red Cape"));
                capeModel.addElement(new EquipableItem("greencape", "Green Cape"));
                capeModel.addElement(new EquipableItem("orangecape", "Orange Cape"));
                capeModel.addElement(new EquipableItem("yellowcape", "Yellow Cape"));
                capeModel.addElement(new EquipableItem("blackcape", "Black Cape"));
                capeComboBox.setModel(capeModel);
                
		
		headModel.addElement(null);
		headModel.addElement(new EquipableItem("beanie", "Beanie of infinite wisdom"));
		headModel.addElement(new EquipableItem("helm", "Helmet of helmeting"));
                headModel.addElement(new EquipableItem("snapback", "Snapback of snapping"));
		
                
                headModel2.addElement(null);
		headModel2.addElement(new EquipableItem("beanie", "Beanie of infinite wisdom"));
		headModel2.addElement(new EquipableItem("helm", "Helmet of helmeting"));
                headModel2.addElement(new EquipableItem("snapback", "Snapback of snapping"));
                headModel2.addElement(new EquipableItem("spacehelmet", "Space Helmet"));
		                              
                 
                DefaultComboBoxModel characterModel = new DefaultComboBoxModel();
		characterModel.addElement("Human");    
                characterModel.addElement("Alien"); 
		characterModel.addElement("Lizard");
		typeComboBox.setModel(characterModel);
                
                DefaultComboBoxModel handsModel = new DefaultComboBoxModel();
		handsModel.addElement(null);
		handsModel.addElement(new EquipableItem("knife", "Shank"));	
                handsModel.addElement(new EquipableItem("sword blue", "Sword of Ice"));	
                handsModel.addElement(new EquipableItem("sword curvy", "Bent Sword"));	
                handsModel.addElement(new EquipableItem("sword red", "Red Sword"));
                handsModel.addElement(new EquipableItem("sword", "Generic RPG Shortsword"));	
		handsComboBox.setModel(handsModel);
                
                DefaultComboBoxModel feetModel = new DefaultComboBoxModel();
		feetModel.addElement(null);
		feetModel.addElement(new EquipableItem("boots", "boots"));	
                feetModel.addElement(new EquipableItem("crocs", "crocs"));	           
		feetComboBox.setModel(feetModel);
                                                                                                   
                
                
		torsoModel.addElement(null);
		torsoModel.addElement(new EquipableItem("button shirt", "Extremely Generic Shirt"));               	           
		torsoComboBox.setModel(torsoModel);                 
                             
		torsoModel2.addElement(null);
		torsoModel2.addElement(new EquipableItem("aliensuit", "Intergalactic Suit"));
                
                torsoModel3.addElement(null);
                torsoModel3.addElement(new EquipableItem("argoniantorso", "Robes"));                
	}

	/**
	 * This is the code we did in class. It is VERY badly done. It's way too
	 * long and contains lots of duplicate code. It's also not double-buffered,
	 * although the performance with this actually seems OK. Regardless,
	 * you should practice refactoring code by cleaning this up. If you need
	 * help, see how similar methods are written in other forms.
	 * @param g
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		//--- Load image of a person 
		String fileName = "";
		Image backgroundImage = null, paperDoll = null, hatImage=null, handsImage=null, feetImage=null, torsoImage = null, capeImage = null;
		try
		{
                        if (typeComboBox.getSelectedIndex() == 0)
                        {
			fileName = "images/body masked.png";
                        headComboBox.setModel(headModel);
                        torsoComboBox.setModel(torsoModel);
                        }
                        else if (typeComboBox.getSelectedIndex() == 1)
                        {
                            fileName = "images/alien.png";     
                            headComboBox.setModel(headModel2);
                            torsoComboBox.setModel(torsoModel2);
                        }
                        else if (typeComboBox.getSelectedIndex() == 2)
                        {
                            fileName = "images/lizard.png";
                            headComboBox.setModel(headModel);
                            torsoComboBox.setModel(torsoModel3);
                        }
			paperDoll = ImageIO.read(new File(fileName));
			paperDoll = paperDoll.getScaledInstance(
					drawingPanel.getWidth(),
					drawingPanel.getHeight(), Image.SCALE_SMOOTH);

	        EquipableItem hat = (EquipableItem)headComboBox.getSelectedItem();                
			if (null != hat)
			{
				fileName = "images/head/" + hat.id + ".png";
				hatImage = ImageIO.read(new File(fileName));
				hatImage = hatImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);
                        }        
                        
                EquipableItem cape = (EquipableItem)capeComboBox.getSelectedItem();                
			if (null != cape)
			{
				fileName = "images/capes/" + cape.id + ".png";
				capeImage = ImageIO.read(new File(fileName));
				capeImage = capeImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);
                        }        
                 EquipableItem sword = (EquipableItem)handsComboBox.getSelectedItem();                
			if (null != sword)
			{
				fileName = "images/hands/" + sword.id + ".png";
				handsImage = ImageIO.read(new File(fileName));
				handsImage = handsImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);
                                
			}
                  EquipableItem shoes = (EquipableItem)feetComboBox.getSelectedItem();                
			if (null != shoes)
			{
				fileName = "images/feet/" + shoes.id + ".png";
				feetImage = ImageIO.read(new File(fileName));
				feetImage = feetImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);
                                
			} 
                 EquipableItem background = (EquipableItem)backgroundComboBox.getSelectedItem();                
			if (null != background)
			{
				fileName = "images/backgrounds/" + background.id + ".png";
				backgroundImage = ImageIO.read(new File(fileName));
				backgroundImage = backgroundImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);
                                
			}
                        
                EquipableItem torso = (EquipableItem)torsoComboBox.getSelectedItem();                
			if (null != torso)
			{
				fileName = "images/torso/" + torso.id + ".png";
				torsoImage = ImageIO.read(new File(fileName));
				torsoImage = torsoImage.getScaledInstance(
						drawingPanel.getWidth(),
						drawingPanel.getHeight(), Image.SCALE_SMOOTH);                                
			} 
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			System.out.println("Can't find image"+ fileName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("i fell and i can't get up");
		}

		Graphics canvas = drawingPanel.getGraphics();
                canvas.drawImage(backgroundImage, 0, 0, null);
                canvas.drawImage(capeImage, 0, 0, null);
		canvas.drawImage(paperDoll, 0, 0, null);
                canvas.drawImage(torsoImage, 0, 0, null);
		canvas.drawImage(hatImage, 0, 0, null);
                canvas.drawImage(handsImage, 0, 0, null);
                canvas.drawImage(feetImage, 0, 0, null);
                
	}

	public void buildCharacter()
	{
		aCharacter.name = nameTextField.getText();
                if (typeComboBox.getSelectedIndex() == 0)
                {
                    aCharacter.race = "Human";
                }
                else if (typeComboBox.getSelectedIndex() == 1)
                {
                    aCharacter.race = "Alien";
                }
                else if (typeComboBox.getSelectedIndex() == 2)
                {
                    aCharacter.race = "Lizard";
                }
		try
		{
			String strengthValue = strTextField.getText();
			int value = Integer.parseInt(strengthValue);
			aCharacter.strength = value;          
                        if (aCharacter.race.equals("Lizard"))
                        {
                            if (timesEquippedArgonianTorso >= 1)
                            {
                                aCharacter.strength += 2;
                            } 
                        }                                                
		}
		catch (java.lang.NumberFormatException ex)
		{
			System.out.println("That strength ain't a number");
		}

		try
		{
			String stringValue = hpTextField.getText();
			int value = Integer.parseInt(stringValue);
			aCharacter.health = value;
                        if (aCharacter.race.equals("Human"))
                        {
                            if (timesEquippedButtonShirt >= 1)
                            {
                                aCharacter.health += 45;
                            }
                        }
                        if (aCharacter.race.equals("Alien"))
                        {
                            if (timesEquippedAlienSuit >= 1)
                            {
                                aCharacter.health += 70;
                            }
                        }
		}
		catch (java.lang.NumberFormatException ex)
		{
			System.out.println("That health ain't a number");
		}
                                
	}

	public Agent getAgent()
	{
		return aCharacter;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        label4 = new java.awt.Label();
        jLabel1 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        strTextField = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        hpTextField = new javax.swing.JTextField();
        drawingPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        headComboBox = new javax.swing.JComboBox();
        typeComboBox = new javax.swing.JComboBox();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        torsoComboBox = new javax.swing.JComboBox();
        label3 = new java.awt.Label();
        handsComboBox = new javax.swing.JComboBox();
        label5 = new java.awt.Label();
        feetComboBox = new javax.swing.JComboBox();
        label6 = new java.awt.Label();
        backgroundComboBox = new javax.swing.JComboBox();
        label7 = new java.awt.Label();
        capeComboBox = new javax.swing.JComboBox();
        label9 = new java.awt.Label();
        shirtLabel = new java.awt.Label();
        label10 = new java.awt.Label();

        label4.setText("label4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Character Creator");

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel1.setText("Name");

        nameTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Plantagenet Cherokee", 1, 14)); // NOI18N
        jLabel2.setText("Strength");

        strTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        strTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                strTextFieldActionPerformed(evt);
            }
        });

        okButton.setText("Done");
        okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                okButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Plantagenet Cherokee", 1, 14)); // NOI18N
        jLabel8.setText("Health");

        hpTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        hpTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                hpTextFieldActionPerformed(evt);
            }
        });

        drawingPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setText("Head");

        headComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        headComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                headComboBoxActionPerformed(evt);
            }
        });

        typeComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeComboBox.setToolTipText("");
        typeComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                typeComboBoxActionPerformed(evt);
            }
        });

        label1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label1.setText("Character Type");

        label2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label2.setText("Torso");

        torsoComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        torsoComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        torsoComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                torsoComboBoxActionPerformed(evt);
            }
        });

        label3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label3.setText("Hands");

        handsComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        handsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        handsComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                handsComboBoxActionPerformed(evt);
            }
        });

        label5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label5.setText("Feet");

        feetComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        feetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        feetComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                feetComboBoxActionPerformed(evt);
            }
        });

        label6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label6.setText("Backgrounds");

        backgroundComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        backgroundComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        backgroundComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                backgroundComboBoxActionPerformed(evt);
            }
        });

        label7.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label7.setText("Capes");

        capeComboBox.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        capeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        capeComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                capeComboBoxActionPerformed(evt);
            }
        });

        label9.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        label9.setText("Pick Number 1-200");

        shirtLabel.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        shirtLabel.setText("Hi");

        label10.setText("Must be 1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(hpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(strTextField))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(drawingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 9, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(handsComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
                                    .addComponent(label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(torsoComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(headComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(capeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(feetComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(backgroundComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(shirtLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(strTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(hpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(feetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(headComboBox)
                            .addComponent(capeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backgroundComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(torsoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shirtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_okButtonActionPerformed
    {//GEN-HEADEREND:event_okButtonActionPerformed
		buildCharacter();
		if (aCharacter.name.equals("") ||
			aCharacter.strength <= 0 ||aCharacter.health <= 0)
		{
			System.out.println("You forgot to fill him in");
			return;
		}
                else if (aCharacter.health > 200 || aCharacter.strength > 10)
                {
                    if (timesEquippedButtonShirt == 0 && timesEquippedAlienSuit == 0) 
                    {
                    System.out.println("Hey, don't try to cheat!");
                    return;
                    }
                    else if (aCharacter.health >= 300)
                    {
                    System.out.println("Hey, don't try to cheat!");
                    return; 
                    }
                    else if (aCharacter.strength >= 4)
                    {
                    System.out.println("Hey, don't try to cheat!");
                    return;  
                    }
                }                
		dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void headComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_headComboBoxActionPerformed
    {//GEN-HEADEREND:event_headComboBoxActionPerformed
        EquipableItem hat = (EquipableItem)headComboBox.getSelectedItem();
		System.out.println("you are now wearing "+hat);
		repaint();
    }//GEN-LAST:event_headComboBoxActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_typeComboBoxActionPerformed
    {//GEN-HEADEREND:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
        repaint();
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void torsoComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_torsoComboBoxActionPerformed
    {//GEN-HEADEREND:event_torsoComboBoxActionPerformed
        // TODO add your handling code here:        
        EquipableItem torso = (EquipableItem)torsoComboBox.getSelectedItem();        
		System.out.println("you are now wearing "+torso);
        
                if (torso != null)
                {
                    if (torso.id.equals("button shirt"))
                    {
                        timesEquippedButtonShirt ++;                        
                        if (timesEquippedButtonShirt > 1)
                        {
                        System.out.println("You cannot keep gaining the health bonus"); 
                        }
                        shirtLabel.setText("Well, it's better than no armor. You will spawn with an extra 45 health.");                         
                    }
                    else if (torso.id.equals("aliensuit"))
                    {
                        timesEquippedAlienSuit++;
                        if (timesEquippedAlienSuit > 1)
                        {
                        System.out.println("You cannot keep gaining the health bonus"); 
                        }
                        shirtLabel.setText("You are now a true Alien Overlord. Gain 70 extra health when you spawn");
                    }
                    else if (torso.id.equals("argoniantorso"))
                    {
                        timesEquippedArgonianTorso++;
                        if (timesEquippedArgonianTorso > 1)
                        {
                            System.out.println("You cannot keep gaining the health bonus"); 
                        }
                        shirtLabel.setText("The magical properties of these robes increase your Strength by 2");
                    }
                }
		repaint(); 
    }//GEN-LAST:event_torsoComboBoxActionPerformed

    private void handsComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_handsComboBoxActionPerformed
    {//GEN-HEADEREND:event_handsComboBoxActionPerformed
            EquipableItem hands = (EquipableItem)handsComboBox.getSelectedItem();
		System.out.println("you are now wearing "+hands);
		repaint();
    }//GEN-LAST:event_handsComboBoxActionPerformed

    private void feetComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_feetComboBoxActionPerformed
    {//GEN-HEADEREND:event_feetComboBoxActionPerformed
        EquipableItem feet = (EquipableItem)feetComboBox.getSelectedItem();
		System.out.println("you are now wearing "+ feet);
		repaint();
    }//GEN-LAST:event_feetComboBoxActionPerformed

    private void strTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_strTextFieldActionPerformed
    {//GEN-HEADEREND:event_strTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_strTextFieldActionPerformed

    private void hpTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_hpTextFieldActionPerformed
    {//GEN-HEADEREND:event_hpTextFieldActionPerformed
        // TODO add your handling code here:                               
    }//GEN-LAST:event_hpTextFieldActionPerformed

    private void backgroundComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_backgroundComboBoxActionPerformed
    {//GEN-HEADEREND:event_backgroundComboBoxActionPerformed
        // TODO add your handling code here:
        EquipableItem background = (EquipableItem)backgroundComboBox.getSelectedItem();
		System.out.println("you are now wearing "+ background);
		repaint();
    }//GEN-LAST:event_backgroundComboBoxActionPerformed

    private void capeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_capeComboBoxActionPerformed
    {//GEN-HEADEREND:event_capeComboBoxActionPerformed
        // TODO add your handling code here:
        EquipableItem cape = (EquipableItem)capeComboBox.getSelectedItem();
		System.out.println("you are now wearing "+ cape);
		repaint();
    }//GEN-LAST:event_capeComboBoxActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(CharacterCreationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(CharacterCreationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(CharacterCreationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(CharacterCreationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new CharacterCreationForm().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox backgroundComboBox;
    private javax.swing.JComboBox capeComboBox;
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JComboBox feetComboBox;
    private javax.swing.JComboBox handsComboBox;
    private javax.swing.JComboBox headComboBox;
    private javax.swing.JTextField hpTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label9;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton okButton;
    public java.awt.Label shirtLabel;
    private javax.swing.JTextField strTextField;
    private javax.swing.JComboBox torsoComboBox;
    private javax.swing.JComboBox typeComboBox;
    // End of variables declaration//GEN-END:variables
}
