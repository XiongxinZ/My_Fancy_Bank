// store the column name used in table panel
public class TableColumns {

    public static String[] getBankerColumns() {
        return new String[] {
                "Banker ID",  "Banker Name", "Password", "Email Address"
        };
    }

    public static String[] getCustomerColumns() {
        return new String[] {
               "Customer ID",  "Customer Name", "Password", "Email Address"
        };
    }

    public static String[] getTransactionColumns() {
        return new String[] {
                "Date", "Transaction Type", "FromWho", "FromAccount", "ToWho", "ToAccount", "Amount",
                "FromBalance","FromCurrency","ToBalance","ToCurrency"
        };
    }

    public static String[] getStockTransactionColumns() {
        return new String[] {
                "Date", "Customer", "Stock Name", "Type", "Price", "Quantity", "Amount"
        };
    }

    public static String[] getStockPoolColumns(){
        return new String[] {
                "Stock Name", "Currency","Current Price", "Buy Price", "Quantity", "Amount", "Profit"
        };
    }

    public static String[] getStockListColumns(){
        return new String[] {
                "Stock Name", "Currency","Current Price"
        };
    }

    public static String[] getLoanPoolColumns(){
        return new String[] {
                "Loan ID", "Customer","Collateral ID","Balance", "Currency"
        };
    }

    public static String[] getCollateralColumns(){
        return new String[] {
                "Collateral ID", "Customer","Value", "Currency","Used"
        };
    }

    public static String[] getCollateralRequestColumns(){
        return new String[]{
                "Request Date", "ID","Customer", "Name", "Status","Value","Solve Date"
        };
    }

    public static String[] getCollateralRequestColumns_bankerVersion(){
        return new String[]{
                "Request Date", "Customer ID","Request ID", "Collateral Name", "File Path", "Collateral value", "Request Status", "Solve Date"
        };
    }

    public static String[] getStockProfitColumns(){
        return new String[]{
                "Stock Name", "Currency", "Profit"
        };
    }

}