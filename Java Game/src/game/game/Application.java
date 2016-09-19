package hw5.game;

public class Application
{
    public static void main(String[] args)
	{
		Game game = Game.getInstance();
		MapForm form = new MapForm(game);
		form.setVisible(true);
    }

	private void enterTheThunderDome()
	{
		Agent player1 = createCharacter();
		Agent player2 = createCharacter();
		UserOutputManager.getInstance().announce("Welcome to the game");

		CombatForm player1Form = new CombatForm(player1, player2);
		player1Form.setVisible(true);
		player1Form.setPlayer(player1);

//		CombatForm player2Form = new CombatForm();
//		player2Form.setVisible(true);
//		player2Form.setPlayer(player2);

		//		while (!player1.isDead() && !player2.isDead())
//		{
//			int damage = player1.getAttackDamage();
//			System.out.println("player 1 did "+damage+ " points of dmg");
//			player2.health -= damage;
//			damage = player2.getAttackDamage();
//			System.out.println("player 2 did "+damage+ " points of dmg");
//			player1.health -= damage;
//		}
//		if (player1.isDead())
//		{
//			System.out.println(player2 + " wins");
//		}
//		else
//		{
//			System.out.println(player1 + " wins");
//		}
	}

	private Agent createCharacter()
	{
		CharacterCreationForm form = new CharacterCreationForm();
		form.setVisible(true);

		Agent agent = form.getAgent();
		System.out.println("Created "+ agent);
		return agent;
	}
}
