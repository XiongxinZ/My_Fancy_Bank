public class TableColumns {

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

    public static String[] getStockProfitColumns(){
        return new String[]{
                "Stock Name", "Currency", "Profit"
        };
    }
}