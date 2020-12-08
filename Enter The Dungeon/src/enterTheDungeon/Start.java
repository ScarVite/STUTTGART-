package enterTheDungeon;

import java.net.URISyntaxException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import enterTheDungeon.api.Networking;
import enterTheDungeon.api.Setup;
import enterTheDungeon.game.Mainmenu;
import enterTheDungeon.game.User;
import enterTheDungeon.resource.Filesystem;

public class Start {

	private static Filesystem filesystem = new Filesystem();

	public static void main(String args[]) {
		//Networking.login("Email", "Peter");
		initSystems();
		if (filesystem.checkForFile("/EnterTheDungeon-Files/User.json"))
			User.setUser(filesystem.readJsonFileasObject("/EnterTheDungeon-Files/User.json"));
		if (filesystem.compareFileContent("/EnterTheDungeon-Files/KeyValid.txt", "Hiermit-wird-das-Spiel-aktiviert"))
			new Mainmenu();
		else {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new Setup().setVisible(true);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	private static void initSystems() {
		try {
			Filesystem.SetMainPath(Setup.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			filesystem.createFolderIfNotExist("/EnterTheDungeon-Files");
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();
			if (!filesystem.checkForFile("/EnterTheDungeon-Files/Settings.json")) {
				obj.put("music", true);
				filesystem.writeJsonObjectToFile("/EnterTheDungeon-Files/Settings.json", obj);
			}
			obj.clear();
			if (!filesystem.checkForFile("/EnterTheDungeon-Files/Save.json")) {
				filesystem.writeJsonArrayToFile("/EnterTheDungeon-Files/Save.json", arr);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
