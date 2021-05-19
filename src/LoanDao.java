import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class LoanDao {

    public static int updateLoanBalance(Loan loan) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "update loan set l_balance = ? where l_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, loan.getBalance());
            ps.setString(2, loan.getId());

//            System.out.println("delCustomer(Customer customer)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }


    public static int insertLoan(Loan loan){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into loan(l_id, c_id, co_id,l_currency,l_balance) " +
                    "values(?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1,loan.getId());
            ps.setString(2, loan.getCustomer().getId());
            ps.setString(3, loan.getCollateral().getId());
            ps.setString(4,loan.getCurrency());
            ps.setDouble(5,loan.getBalance());

            flag = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return flag;
    }

    public static ValuePool<Loan> selectCustomerLoanList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ValuePool<Loan> list = new ValuePool<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from loan where 1= 1";
            sql =sql +  " and c_id = " + customer.getId();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){
                String id = rs.getString("l_id");
                String coID = rs.getString("co_id");
                Collateral collateral = CollateralDao.selectCollateralWithId(coID, customer);
                String currency = rs.getString("l_currency");
                double balance = rs.getDouble("l_balance");

                Loan loan = new Loan(customer, collateral, balance, currency,id);
                list.put(id, loan);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return list;
    }

    public static int removeLoan(Loan loan) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "delete from loan where l_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, loan.getId());

//            System.out.println("delCustomer(Customer customer)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }
}
