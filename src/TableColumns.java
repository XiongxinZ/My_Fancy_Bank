public class TableColumns {

    public static String[] getCustomerColumns() {
        String[] columns = new String[] {
                "�˺�", "����", "����", "���", "״̬", "��������", "����", "���֤"
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
                "������Ա", "�˺�", "�������", "�����", "�˻����"
        };
        return columns;
    }

    public static String[] getWithdrawalColumns() {
        String[] columns = new String[] {
                "������Ա", "�˺�", "ȡ������", "ȡ����", "�˻����"
        };
        return columns;
    }

    public static String[] getTransferColumns() {
        String[] columns = new String[] {
                "������Ա", "�˻�", "���շ��˻�", "ת������", "ת�˽��", "�˻����"
        };
        return columns;
    }
}