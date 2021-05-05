import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CollateralValuationPanel extends JPanel {
    public CollateralValuationPanel(CollateralValuation collateralValuation) {
//        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setLayout(null);

        String filePath = System.getProperty("user.dir") + "/certificate/" + collateralValuation.getFileName();
        JLabel imgLabel = new JLabel("certificate img"){
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(filePath);
                g.drawImage(icon.getImage(), 0, 0, getWidth(),icon.getIconHeight()*getWidth()/icon.getIconWidth(),
                        icon.getImageObserver());
            }
        };

        imgLabel.setBounds(0,0,getWidth(),getHeight()/2);
        add(imgLabel);

        JPanel jPanel = new JPanel(new GridLayout(0,2));

        JLabel nameLabel = new JLabel("Collateral Name:");
        JLabel name = new JLabel(collateralValuation.getName());
        jPanel.add(nameLabel);
        jPanel.add(name);

        JLabel statusLabel = new JLabel("Decision:");
        JComboBox<String> status = new JComboBox<>(new String[]{"Approve","Reject"});
        jPanel.add(statusLabel);
        jPanel.add(status);

        JLabel priceLabel = new JLabel("Price");
        JTextField price = new JTextField();

        status.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String s = (String) status.getSelectedItem();
                if (s.equalsIgnoreCase("Reject")){
                    price.setText("0");
                }
            }
        });
        jPanel.add(priceLabel);
        jPanel.add(price);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String decision = (String) status.getSelectedItem();
                    if (decision.equalsIgnoreCase("Approve")) {
                        double valuationPrice = Double.parseDouble(price.getText());
                        collateralValuation.setPrice(valuationPrice);
                        collateralValuation.setApprove();
                    }else{
                        collateralValuation.setReject();

                    }
                    String message = collateralValuation.execute();
                    GuiUtil.getFrame(CollateralValuationPanel.this).dispose();
                    new MessageFrame("Success", message);
                }catch (NumberFormatException e2){
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiUtil.getFrame(CollateralValuationPanel.this).dispose();
            }
        });

        jPanel.setBounds(0, getHeight()/2,getWidth(),getHeight()/2);


    }
}
