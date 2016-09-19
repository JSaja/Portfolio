package hw5.game;

import hw5.dialog.DialogFactory;
import hw5.dialog.DialogForm;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author baylor
 */
public class MapForm extends javax.swing.JFrame
{
	//<editor-fold defaultstate="collapsed" desc="properties and constants">
	int originalTileWidth = 32, originalTileHeight = 32;
	int tileWidth = originalTileWidth, tileHeight = originalTileHeight;
	float scalingFactor = 1f;

	BufferedImage backBufferContainer;
	ImageLibrary imageLibrary;
	HashMap<String, Image> scaledImages = new HashMap<>();

	Game game;	// We are the view and controller, game is the model
				// This isn't needed if Game is a singleton
        CharacterCreationForm character = new CharacterCreationForm();            
       // int stepCount;      
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="constructors and initialization">
	public MapForm(Game game)
	{
		this.game = game;                
                character.setVisible(true);
                
                game.cutPP = 8;
                game.splashPP = 20;
                game.selfDestructPP = 1;
                game.leechSeedPP = 8;
                game.autoKillPP = 1;
                
                game.player.health = character.aCharacter.health;
                if (game.player.health == 0)
                {
                    game.player.health = 100;
                }
                game.player.strength = character.aCharacter.strength;
                if (game.player.strength == 0)
                {
                    game.player.strength = 65;
                }
                game.player.name = character.aCharacter.name;
                game.player.race = character.aCharacter.race;
		initComponents();
		setLocationRelativeTo(null); // set relative to player

		loadKeyBindings();

		backBufferContainer  = new BufferedImage(
				drawingPanel.getWidth(), drawingPanel.getHeight(),
				BufferedImage.TYPE_INT_RGB);


		tileWidth  *= scalingFactor;
		tileHeight *= scalingFactor;
		loadImages();               
		repaint();                
	}               
        

	private void loadImages()
	{
		imageLibrary = new ImageLibrary();
		resizeImages();
	}

	private void loadKeyBindings()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				boolean keyWasHandled = processKeyPress(e);
				return keyWasHandled;
			}
		});
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="event handling">        
		           	
	private boolean processKeyPress(KeyEvent e)
	{
		if (e.getID() == KeyEvent.KEY_PRESSED)
		{                                                                                                
			switch(e.getKeyCode())
			{                                            
                            
				case KeyEvent.VK_LEFT:     if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Left);
                                                           game.moveEnemies();
                                                           if(game.shouldFight())
                                                           {                                                            
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);                                                               
                                                           }                                                         
                                repaint();
                                break;                                    
                                    
				case KeyEvent.VK_KP_LEFT: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Left);
                                                           game.moveEnemies(); 
                                                           if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                         
                                repaint();
                                break;
                                    
				case KeyEvent.VK_A:   if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Left); 
                                                           game.moveEnemies(); 
                                                           if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                        
                                repaint();
                                break;                               

				case KeyEvent.VK_UP: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                     game.move(Direction.Up); 
                                                     game.moveEnemies();
                                                     if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                     
                                repaint();
                                break;
                                    
				case KeyEvent.VK_KP_UP: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                        game.move(Direction.Up); 
                                                        game.moveEnemies();
                                                        if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }
                                                       
                                repaint();
                                break;
                                    
				case KeyEvent.VK_W: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                    game.move(Direction.Up);     
                                                    game.moveEnemies();     
                                                    if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                   
                                repaint();
				break;

				case KeyEvent.VK_RIGHT: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Right);  
                                                           game.moveEnemies();
                                                           if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                        
                                repaint();
                                break;
                                    
				case KeyEvent.VK_KP_RIGHT: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Right);
                                                           game.moveEnemies();
                                                           if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                         
                                repaint();
                                break;
                                    
				case KeyEvent.VK_D:      if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                           game.move(Direction.Right); 
                                                           game.moveEnemies();
                                                           if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                           
                                repaint();
				break;

				case KeyEvent.VK_DOWN:   if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                          game.move(Direction.Down);
                                                          game.moveEnemies();
                                                          if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                          
                                repaint();
                                break;
				case KeyEvent.VK_KP_DOWN: if (game.shouldFight())
                                                          {
                                                          break;
                                                          }
                                                          game.move(Direction.Down);
                                                          game.moveEnemies();
                                                          if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }
                                                        
                                repaint();
                                break;
				case KeyEvent.VK_S:      if (game.shouldFight())
                                                          {
                                                          break; 
                                                          }
                                                          game.move(Direction.Down);
                                                          game.moveEnemies(); 
                                                          if(game.shouldFight())
                                                           {
                                                               CombatForm c = new CombatForm(game.player, game.someEnemy);
                                                               repaint();
                                                               c.setVisible(true);
                                                           }                                                          
				repaint();
				break;                                                               

				//--- does not resize scaledImages, they're still original size
				//--- either clips image or padds it with blank
				case KeyEvent.VK_PLUS:
				case KeyEvent.VK_EQUALS:
				case KeyEvent.VK_ADD:
					scalingFactor *= 1.5f;
					tileWidth  = (int)(originalTileWidth  * scalingFactor);
					tileHeight = (int)(originalTileHeight * scalingFactor);
					resizeImages();
					repaint();
					return true;
				case KeyEvent.VK_MINUS:
				case KeyEvent.VK_SUBTRACT:
					scalingFactor *= 0.75f;
					tileWidth  = (int)(originalTileWidth  * scalingFactor);
					tileHeight = (int)(originalTileHeight * scalingFactor);
					resizeImages();
					repaint();
					return true;                                    
			}                      
		}
                
        
		//--- This is stupid but it's needed to keep hot keys like the
		//---	+ button from showing up in a text box. If you have both
		//---	a map you move around on and text boxes on the same form,
		//---	you need this
		else if (e.getID() == KeyEvent.KEY_TYPED)
		{
			switch(e.getKeyChar())
			{
				case '+':
				case '=':
				case '-':
				case 'w':
				case 'a':
				case 's':
				case 'd':
					return true;
			}
		}
                
                // Update the variables after the move, if the new stats can be displayed, do so 
                 game.didPlayerWin();
                 game.isPlayerDead();      
                 game.isEnemyDead();
                 nameLabel.setText("Hero's Name: " + character.aCharacter.name);
                 raceLabel.setText("Race: " + character.aCharacter.race);
                 stepsLabel.setText("Steps: " + game.stepCount);          
                 if (game.stepCount >= 50)
                 {
                     achievement1.setText("1. 50 steps");                     
                 }                 
                  if (game.stepCount >= 175)
                 {
                     achievement4.setText("4. 175 steps");
                 }
                 game.updateTerrainHealth(); 
                 game.updatePotionsAndOrbs();
                 if (game.orbCount >= 1)
                 {
                     achievement7.setText("7. Zap!");
                 }
                 game.updateKeys();
                 if (game.keyCount >= 1)
                 {
                     achievement5.setText("5. What's a key good for?");
                 }
                 if (game.keyCount == 2)
                 {
                     achievement6.setText("6. Key Kollector");
                 }
                 keyLabel.setText("Keys Collected: " + game.keyCount + " / 2");
                 healthLabel.setText("Health: " + game.player.health);       
                 strengthLabel.setText("Strength: " + game.player.strength);
                 game.updateCoin();
                 coinsLabel.setText("Coins collected: " + game.coinCount + " " + "/ 18");
                 enemiesDefeatedLabel.setText("Enemies Defeated: " + game.killCount);
                 if (game.coinCount >= 9)
                 {
                     achievement2.setText("2. 50 % Coins Collected");
                 }
                 if (game.coinCount == 18)
                 {
                     achievement3.setText("3. All Coins Collected");
                 }                 
                 if (game.killCount >= game.agents.size())
                 {
                     achievement8.setText("8. Hero of the land");
                 }
                 if (game.coinCount >= 18 && game.killCount >= game.agents.size() && game.orbCount >= 2 && game.potionCount >= 5)
                 {
                    achievement9.setText("9. Super Player Award!");
                 }
                 //game.shouldTeleport();
                 Game.getInstance().shouldTeleport();

		//--- Tell the computer that whatever key it is they pressed, it wasn't
		//---	one that we cared about so we didn't do anything with it.
		return false;                
	}

	/**
	 * The MouseClickHandler will call this method when a mouse button is clicked.
	 * It will also tell us which part of the form they clicked on. We'll
	 * call different methods depending on which thing they clicked on.
	 * @param x X coordinate of the pixel they clicked on, relative to the top left corner
	 * @param y Y coordinate of the pixel they clicked on, relative to the top left corner
	 */
	public void processMouseClick(int x, int y, String componentID)
	{
		if (componentID.equals("map panel"))
		{
			onClickedOnMap(x,y);
		}
	}

	/**
	 * They clicked on the main map panel.
	 * @param x X coordinate of the pixel they clicked on, relative to the top left corner
	 * @param y Y coordinate of the pixel they clicked on, relative to the top left corner
	 */
	private void onClickedOnMap(int x, int y)
	{
		int tileX = (int)(x / tileWidth);
		int tileY = (int)(y / tileHeight);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="drawing">
	private void drawTerrain()
	{
        	//throw new UnsupportedOperationException("Not yet implemented");         
            for (int y = 0; y < game.map.terrain[0].length; y++)
            {
                for (int x = 0; x < game.map.terrain.length; x++)
                {
                    String tileType = game.map.terrain[x][y];
                    if (null != tileType)
				{
					Image image = scaledImages.get(tileType);
					// hmm, this doesn't quite seem to draw them in the right place
					backBufferContainer.getGraphics().drawImage(
							image,
							x*32, y*32,
							null);
				}
                }
            }    
	}

	/**
	 * Show items scattered across the map such as treasure and potions
	 */
	private void drawItems()
	{
		for (int y = 0; y < game.map.getHeight(); y++)
		{
			for (int x = 0; x< game.map.getWidth(); x++)
			{
				String tileType = game.map.items[x][y];
				if (null != tileType)
				{
					Image image = scaledImages.get(tileType);
					// hmm, this doesn't quite seem to draw them in the right place
					backBufferContainer.getGraphics().drawImage(
							image,
							x*32, y*32,
							null);
				}
			}
		}
	}

	private void drawAgents()
	{          
            for (Agent agent : game.agents)                
            {                                            
                Image image = scaledImages.get(agent.type);
		backBufferContainer.getGraphics().drawImage(
				image,
				agent.x*tileWidth, agent.y*tileHeight,
				null);                
            }                      
	}

	private void drawPlayer()
	{           
            
		Image image = null;// = scaledImages.get("player");

                if (game.player.race.equals("Human"))
                {
                    image = scaledImages.get("human");
                }
                else if (game.player.race.equals("Alien"))
                {
                    image = scaledImages.get("alien");
                }
                else if (game.player.race.equals("Lizard"))
                {
                    image = scaledImages.get("lizard");
                }
                backBufferContainer.getGraphics().drawImage(
				image,
				game.player.x*tileWidth, game.player.y*tileHeight,
				null);
                
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		backBufferContainer = new BufferedImage(
				drawingPanel.getWidth(), drawingPanel.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		//--- Clear out the old picture we made
		backBufferContainer.getGraphics().setColor(Color.blue);
		backBufferContainer.getGraphics().fillRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());

		drawTerrain();
		drawItems();
		drawAgents();
		drawPlayer();

		//--- We drew everything to an offscreen image to avoid flicker,
		//---	now we copy that offscreen image to the screen
		drawingPanel.getGraphics().drawImage(backBufferContainer, 0, 0, null);
	}

	private Image resize(Image original)
	{
		Image scaled  = original.getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH);
		return scaled;
	}

	private void resizeImages()
	{
		for (String key : imageLibrary.images.keySet())
		{
			Image original = imageLibrary.images.get(key);
			Image scaled   = resize(original);
			scaledImages.put(key, scaled);
		}
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="auto-generated stuff">
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        label1 = new java.awt.Label();
        drawingPanel = new javax.swing.JPanel();
        stepsLabel = new java.awt.Label();
        label2 = new java.awt.Label();
        healthLabel = new java.awt.Label();
        label3 = new java.awt.Label();
        achievement1 = new java.awt.Label();
        coinsLabel = new java.awt.Label();
        achievement2 = new java.awt.Label();
        nameLabel = new java.awt.Label();
        strengthLabel = new java.awt.Label();
        keyLabel = new java.awt.Label();
        achievement3 = new java.awt.Label();
        raceLabel = new java.awt.Label();
        enemiesDefeatedLabel = new java.awt.Label();
        achievement4 = new java.awt.Label();
        achievement5 = new java.awt.Label();
        achievement6 = new java.awt.Label();
        achievement7 = new java.awt.Label();
        achievement8 = new java.awt.Label();
        achievement9 = new java.awt.Label();

        label1.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("1902 PokeExample");

        drawingPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        stepsLabel.setText("Steps: ");

        label2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        label2.setText("Player Statistics");

        healthLabel.setText("Health: ");

        label3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        label3.setText("Achievements");

        achievement1.setText("1.");

        coinsLabel.setText("Coins Collected: 0 / 18");

        achievement2.setText("2.");

        nameLabel.setText("Hero's Name: ");

        strengthLabel.setText("Strength: ");

        keyLabel.setText("Keys Collected: 0 / 2");

        achievement3.setText("3.");

        raceLabel.setText("Race: ");

        enemiesDefeatedLabel.setText("Enemies Defeated: ");

        achievement4.setText("4.");

        achievement5.setText("5.");

        achievement6.setText("6.");

        achievement7.setText("7. ");

        achievement8.setText("8. ");

        achievement9.setText("9. ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stepsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(strengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(raceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(achievement1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(achievement2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(achievement3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(achievement4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(achievement5, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(achievement9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(achievement8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(enemiesDefeatedLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(keyLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(achievement7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(achievement6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)))
                                .addGap(0, 126, Short.MAX_VALUE))
                            .addComponent(coinsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(healthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(raceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(stepsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(strengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(coinsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemiesDefeatedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(achievement9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 32, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
//	public static void main(String args[])
//	{
//		/* Set the Nimbus look and feel */
//		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//		 */
//		try
//		{
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//			{
//				if ("Nimbus".equals(info.getName()))
//				{
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		}
//		catch (ClassNotFoundException MapForm{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (InstantiationException MapForm{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (IllegalAccessException MapForm{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		catch (javax.swing.UnsupportedLookAndFeelException MapForm{
//			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		//</editor-fold>
//
//		/* Create and display the form */
//		java.awt.EventQueue.invokeLater(new RunnablMapForm{
//			public void run()
//			{
//				new MainForm().setVisible(true);
//			}
//		});
//	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label achievement1;
    private java.awt.Label achievement2;
    private java.awt.Label achievement3;
    private java.awt.Label achievement4;
    private java.awt.Label achievement5;
    private java.awt.Label achievement6;
    private java.awt.Label achievement7;
    private java.awt.Label achievement8;
    private java.awt.Label achievement9;
    private java.awt.Label coinsLabel;
    private javax.swing.JPanel drawingPanel;
    private java.awt.Label enemiesDefeatedLabel;
    private java.awt.Label healthLabel;
    private java.awt.Label keyLabel;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label nameLabel;
    private java.awt.Label raceLabel;
    private java.awt.Label stepsLabel;
    private java.awt.Label strengthLabel;
    // End of variables declaration//GEN-END:variables

	//</editor-fold>

}
