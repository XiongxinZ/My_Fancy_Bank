import javax.swing.*;
import java.awt.*;

public class CollateralValuationPanel extends JPanel {
    public CollateralValuationPanel(CollateralValuation collateralValuation) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        String filePath = System.getProperty("user.dir") + "/certificate/" + collateralValuation.getFileName();
        System.out.println(filePath);
        JLabel nameLabel = new JLabel("<html><img src=\" " + filePath +"\" alt=\"some_text\">");
        jPanel.add(nameLabel);
    }
}
