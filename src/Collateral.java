import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class Collateral implements Serializable {
    @Serial
    private static final long serialVersionUID = -1636951387557509685L;
    private double price;

    private boolean used = false;

    public Collateral(double price) {
        this.price = price;
    }

    public static String valuateCollateral(Customer customer, File file){
        if (isImage(file)){
            SystemDatabase.getOrderList().add(new CollateralValuation(customer,file));
            return "Certificate uploaded! Manager will valuate it later.";
        }else{
            return "Please upload an image file.";
        }
    }

    public double getPrice() {
        return price;
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
}
