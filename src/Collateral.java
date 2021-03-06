import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Collateral implements  Valuable {
    private double price;
    private String name;
    private Customer customer;
    private String id;

    private boolean used;
    private boolean isDirty = false;

    public Collateral(Customer customer,String name, double price, boolean used, String id) {
        this.customer = customer;
        this.name = name;
        this.price = price;
        this.used = used;
        this.id = id;
    }

    public static String valuateCollateral(Customer customer, String name, File file){
        return new CollateralValuation(customer.getId(), name, file).apply();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isUsed() {
        return used;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final boolean isImage(File file){
        boolean flag = false;
        try
        {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if(width==0 || height==0){
                flag = false;
            }else {
                flag = true;
            }
        }
        catch (IOException e)
        {
            flag = false;
        }catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public String toString() {
        return "price=" + price +
                "USD, name='" + name;
    }

    @Override
    public double getValue() {
        return price;
    }

    @Override
    public String getCurrency() {
        return "USD";
    }
}
