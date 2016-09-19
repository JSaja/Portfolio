package hw5.dialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DialogTree
{   
    DialogOption root;        
    DialogOption current;
    Map<String, String> parentage = new HashMap<>();
    Map<String, DialogOption> optionNodes = new HashMap<>();   
    
        public DialogTree()
        {
            
        }
        
	public DialogTree(List<String> dialogData)
	{
		loadData(dialogData);                    
	} 

	protected void loadData(List<String> dialogData)
	{
		// TODO: Write loadData()

		// childID, parentID, type, text
		// type = S state, R response

		for (String line : dialogData)
		{
			String[] tokens = line.split(":");
			String childID  = tokens[0].trim().toLowerCase();
			String parentID = tokens[1].trim().toLowerCase();
			String type     = tokens[2].trim().toLowerCase();
			String text     = tokens[3].trim();
                        
                        parentage.put(childID, parentID);                                            
                       
                        DialogOption aDialogOption = new DialogOption(text, type);
                        optionNodes.put(childID,aDialogOption);                                                                                                                             
                                        
		//--- The root node is always ID 0
	         }
                createTree(); 
        }        
        
        public void createTree()
        {                        
            for (String childID : parentage.keySet())
            {  
                DialogOption currentOption = optionNodes.get(childID);  
                String parentID = parentage.get(childID);
                DialogOption parentOption =  optionNodes.get(parentID);
                                
                if (parentID.equals(""))
                {
                    root = currentOption;
                }
                else
                { 
                    switch (parentOption.responseType)
                    {
                        case "s":
                            if (parentOption.children == null)
                            {
                                List<DialogOption> dialogOptionList = new LinkedList();
                                dialogOptionList.add(currentOption);
                                parentOption.children = dialogOptionList;
                            }
                            else
                            {
                                parentOption.children.add(currentOption);
                            }   break;
                        case "r":
                            parentOption.next = currentOption;
                            break;
                    }
                }
             }
        }

	public List<DialogOption> getOptions()
        {	               
            return root.children;
	}

	public String getText()
	{    		           
            return root.getText();
	}

	public void giveAnswer(int answerIndex)
	{          
             root = root.children.get(answerIndex);            
             if (isConversationOver())
             {
                 root.dialogOptionText = null;
             }
             else
             {
                 root = root.next;
             }
	}

	public boolean isConversationOver()
	{                   
            return root.next == null && root.children == null;           
	}
}
