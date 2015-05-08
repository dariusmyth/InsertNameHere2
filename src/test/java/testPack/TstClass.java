package testPack;

import com.insertNameHere.rallyUtils.RallyID;
import com.insertNameHere.utils.CommonFileUtils;

public class TstClass {

	@RallyID(rallyid={""})
	public static void main(String[] args) {
		System.out.println("Value "+CommonFileUtils.getValueFromConfigFile("numeMagazin"));
	}
}
