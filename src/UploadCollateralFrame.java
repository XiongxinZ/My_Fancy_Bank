import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.CubicCurve2D;
import java.io.File;

public class UploadCollateralFrame extends PopupFrame{
    private Customer customer;
    public UploadCollateralFrame(Customer customer) {
        super("Upload collateral");
        this.customer = customer;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel nameLabel = new JLabel("Collateral Name: ");
        jPanel.add(nameLabel);

        JTextField collName = new JTextField();
        jPanel.add(collName);

        JLabel collLabel = new JLabel("Certificate: ");
        jPanel.add(collLabel);

        JFileChooser file = new JFileChooser();
        jPanel.add(file);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = collName.getText().trim();
                    String message = Collateral.valuateCollateral(customer, name, file.getSelectedFile());
                    UploadCollateralFrame.this.dispose();
                    new MessageFrame("Request upload", message);
                    new CustomerFrame(customer).setContextPanel(new MultiCurrAccountPanel(customer.getAccount("Security")));
//                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(UploadCollateralFrame.this)).getContextPanel()).repaintPanel();
                }catch (NumberFormatException e1){
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        jPanel.add(ok);

        JButton back = new JButton("Cancel");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerFrame(customer).setContextPanel(new MultiCurrAccountPanel(customer.getAccount("Security")));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
