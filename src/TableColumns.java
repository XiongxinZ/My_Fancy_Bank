public class TableColumns {

    public static String[] getTransactionColumns() {
        return new String[] {
                "Date", "Transaction Type", "FromWho", "FromAccount", "ToWho", "ToAccount", "Amount",
                "FromBalance","FromCurrency","ToBalance","ToCurrency"
        };
    }

    public static String[] getStockPoolColumns(){
        return new String[] {
                "Stock Name", "Currency","Current Price", "Buy Price", "Quantity", "Amount", "Profit"
        };
    }

    public static String[] getLoanPoolColumns(){
        return new String[] {
                "Loan ID", "Customer","Collateral ID","Balance", "Currency"
        };
    }
}