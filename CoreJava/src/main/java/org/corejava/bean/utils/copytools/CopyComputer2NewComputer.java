package org.corejava.bean.utils.copytools;

import org.corejava.bean.utils.Computer;
import org.corejava.bean.utils.NewComputer;

public class  CopyComputer2NewComputer{

    public  void copyProperties(Computer src,NewComputer target) {
         target.setBuyTime(src.getBuyTime());
         target.setCorePart(src.getCorePart());
         target.setKeyboard(src.getKeyboard());
         target.setMouse(src.getMouse());
         target.setOthersParts(src.getOthersParts());
         target.setPrograms(src.getPrograms());

        /***the source do not have the value**/
        // target.setBand(src.getBand());
        // target.setType(src.getType());


        /***the target do not have the field**/

    }

}
