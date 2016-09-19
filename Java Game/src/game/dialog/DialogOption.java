package hw5.dialog;

import java.util.List;

/**
 * Represents a choice made by the user
 * @author
 */
public class DialogOption
{    
    String dialogOptionText;   
    String responseType;
    DialogOption next; 
    List<DialogOption> children;
    
    public DialogOption(){}            
    
    public DialogOption(String dialogOptionText, String responseType)
    {
        this.dialogOptionText = dialogOptionText; 
        this.responseType = responseType;
    }   
    
	public String getText()
	{		
            return dialogOptionText;
	}                     

	@Override
	public String toString()
	{
		return getText();
	}
}
