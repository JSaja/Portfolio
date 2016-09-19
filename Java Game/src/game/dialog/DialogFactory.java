package hw5.dialog;

import java.util.LinkedList;
import java.util.List;

public class DialogFactory
{
	static public DialogTree getBobDialog()
	{
		// childID, parentID, type, text
		List<String> dialogData = new LinkedList<>();
		dialogData.add("0::S:Welcome to my shop. How can I help you?");
		dialogData.add("1:0:R:Give me work");
		dialogData.add("2:0:R:Jaqen sent me.");
		dialogData.add("3:0:R:I want you to sell me something.");
		dialogData.add("4:1:S:Take this to the blacksmith and I'll give you 5 bells.");
		dialogData.add("5:2:S:I have nothing to say to you. Leave!<br>" +
				"[He closes the door]");
		dialogData.add("6:3:S:No, you look poor.");
		dialogData.add("7:4:R:OK.");
		dialogData.add("8:4:R:No.");
		dialogData.add("9:8:S:You ask for work then turn it down? Get out.<br>"+
				"[He turns his back to you]");
		dialogData.add("10:5:R:Jaqen said that if you said that i was to give "+
				"you a boot to the head");
		dialogData.add("11:5:R:I was kidding. I want to buy a pony.");
		dialogData.add("12:10:S:[You hear the back door open and the sound of running feet]");
		dialogData.add("13:11:S:No! No bronies! Out! Out!");                
                

		DialogTree dialog = new DialogTree(dialogData);
		return dialog;
	}
             
}
