public class TableColumns {

    public static String[] getCustomerColumns() {
        String[] columns = new String[] {
                "账号", "密码", "姓名", "余额", "状态", "开户日期", "籍贯", "身份证"
        };
        return columns;
    }

    public static String[] getTransactionColumns() {
        String[] columns = new String[] {
                "Date", "Transaction Type", "FromWho", "FromAccount", "ToWho", "ToAccount", "Amount", "FromBalance","ToBalance"
        };
        return columns;
    }

    public static String[] getDepositColumns() {
        String[] columns = new String[] {
                "处理人员", "账号", "存款日期", "存款金额", "账户余额"
        };
        return columns;
    }

    public static String[] getWithdrawalColumns() {
        String[] columns = new String[] {
                "处理人员", "账号", "取款日期", "取款金额", "账户余额"
        };
        return columns;
    }

    public static String[] getTransferColumns() {
        String[] columns = new String[] {
                "处理人员", "账户", "接收方账户", "转账日期", "转账金额", "账户余额"
        };
        return columns;
    }
}