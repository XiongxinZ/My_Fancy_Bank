import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CollateralValuationFrame extends CoreFrame{
    private  Banker banker;
    public CollateralValuationFrame(String request_id, Banker banker) {
        super("Collateral Valuation");
        this.banker = banker;
        setFrame(request_id);
        setVisible(true);
    }

    private void setFrame(String request_id) {
        CollateralValuation collateralValuation = CollateralDao.getInstance().selectCollateralEvaluationWithRid(request_id);
        setLayout(null);

        String filePath = System.getProperty("user.dir") + "/certificate/" + collateralValuation.getFileName();
        JLabel imgLabel = new JLabel("certificate img"){
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(filePath);
                g.drawImage(icon.getImage(), 0, 0, getWidth(),icon.getIconHeight()*getWidth()/icon.getIconWidth(),
                        icon.getImageObserver());
            }
        };

        imgLabel.setBounds(20,20,getWidth()-40,getHeight()/2-40);
        add(imgLabel);

        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

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
                    CollateralValuationFrame.this.dispose();
                    new BankerFrame(banker).setContextPanel(new CollateralEvalsPanel(banker));
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
                CollateralValuationFrame.this.dispose();
            }
        });
        jPanel.add(confirm);
        jPanel.add(back);

        jPanel.setBounds(0, getHeight()/2,getWidth(),getHeight()/2);
        add(jPanel);
    }
}
