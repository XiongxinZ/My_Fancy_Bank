import javax.swing.*;
import java.awt.*;

public class CollateralValuationFrame extends PopupFrame{
    public CollateralValuationFrame(String request_id) {
        super("Collateral Valuation");
        setFrame(request_id);
        setVisible(true);
    }

    private void setFrame(String request_id) {
        add(new CollateralValuationPanel(CollateralDao.getInstance().selectCollateralEvaluationWithRid(request_id)));
    }
}
