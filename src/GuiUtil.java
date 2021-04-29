import javax.swing.*;
import java.awt.*;

public class GuiUtil {
    public static JFrame getFrame(Container container){
        Container c = container;
        while((c = c.getParent())!=null)
        {
            if(c instanceof JFrame)
            {
                return (JFrame) c;
            }
        }
        return null;
    }
}
