package hw5.dialog;

/**
 * Makes it easier to show DialogOption objects in a list.
 * Sort of annoying that we need this class but it is what it is.
 *
 * THIS CLASS WORKS. You do not need to change anything. It's not part of the
 * dialog tree, it's part of the GUI for it, which should all work.
 *
 * @author baylor
 */
public class DialogOptionDisplayWrapper
{
	private DialogOption option;
	//--- The ID of the option, which is essentially it's place in the
	//---	State.responses collection. Need it to tell them what answer
	//---	the player picked.
	private int id;

	public DialogOptionDisplayWrapper(int id, DialogOption option)
	{
		this.id     = id;
		this.option = option;
	}

	public int getID()
	{
		return id;
	}

	public DialogOption getOption()
	{
		return option;
	}

	@Override
	public String toString()
	{
		//--- No one wants to see 0-based options so show the number as one higher
		return (id+1) + ". " + option;
	}
}
