import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class InterestUpdate {
    public static Date newDate = new Date();
    public static void updateInterest(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            Date lastDate = getLastDate();
            if (lastDate == null){
                insertFirstDate();
                return;
            }
            int day = (int) ((newDate.getTime() - lastDate.getTime())/ (1000 * 60 * 60 * 24));
            List<String[]> savingList = AccountDao.getInstance().selectSavingList();
            for (String[] account : savingList) {
                for (int i = 0; i < SystemDatabase.currType.length; i++) {
                    String s = SystemDatabase.currType[i];
                    double bar = ConfigUtil.getConfigDouble("InterestBar") * ConfigUtil.getConfigDouble("USDTo"+s);
                    double amount = Double.parseDouble(account[i+1]);
                    if (amount >= bar){
//                        double amount = account.getAmount(s);
                        for (int j = 0; j < day; j++) {
                            Date transDate = new Date(lastDate.getTime() + 1000 * 60 * 60 * 24);
                            new PayInterest(account[0], amount,s, lastDate).execute();
                            amount = amount*(1+ConfigUtil.getConfigDouble("SavingRate")/365.0);
                        }
                    }
                }
            }

            updateLoanBalance(day);

            String sql = "update timer set u_date = ? where u_id = 1";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(newDate.getTime()));

            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
    }

    public static Date getLastDate(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Date date = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from timer where u_id = 1";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                date = rs.getDate("u_date");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return date;
    }

    public static int insertFirstDate(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into timer(u_id, u_date) values(?,?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1,1);
            ps.setDate(2, new java.sql.Date(newDate.getTime()));

            flag = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public static int updateLoanBalance(int day) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            for (int i = 0; i < day; i++) {
                String sql = "update loan set l_balance = l_balance*"+(1+ConfigUtil.getConfigDouble("LoanRate")/365.0);
                ps = conn.prepareStatement(sql);
                flag = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public static void main(String[] args) {
        updateInterest();
    }
}
