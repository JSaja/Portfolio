package hw5.dialog;

public class Application
{
    public static void main(String[] args)
	{
        Application app = new Application();
		app.runBobsStoreDemo();
    }

	private void runBobsStoreDemo()
	{
		DialogForm form = new DialogForm();
		form.setVisible(true);
	}
}
