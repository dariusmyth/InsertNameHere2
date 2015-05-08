package com.insertNameHere.robotFrameworkUtils.mainInterfaces;

import java.util.List;

public interface RobotLightInterfaceAPI {

    List<String> getKeywordNames();

    Object runKeyword(String name, List<String> arguments);



}
