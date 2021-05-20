import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PrintUtil {
    private static NumberFormat myFormatter = NumberFormat.getInstance();
    static {
        myFormatter.setMinimumFractionDigits(2);
        myFormatter.setMaximumFractionDigits(2);
    }
    public static String printDouble(double val){
        return myFormatter.format(val);
    }
}
