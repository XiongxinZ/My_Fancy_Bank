import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDao {
    public static int updateStockPosition(CustomerStock stock) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "update stock set s_quantity = ?, b_price = ? where c_id = ? and s_name = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, stock.getQuantity());
            ps.setDouble(2, stock.getBuyPrice());
            ps.setString(3, stock.getCustomer().getId());
            ps.setString(4, stock.getName());

//            System.out.println("delCustomer(Customer customer)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

    public static int removeCustomerStock(CustomerStock stock) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "delete from stock where c_id = ? and s_name = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, stock.getCustomer().getId());
            ps.setString(2, stock.getName());

//            System.out.println("delCustomer(Customer customer)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

    public static int insertCustomerStock(CustomerStock stock){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into stock(c_id, s_name,s_curr,s_quantity,b_price,c_price) " +
                    "values(?,?,?,?,,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1,stock.getCustomer().getId());
            ps.setString(2, stock.getName());
            ps.setString(3, stock.getCurr());
            ps.setInt(4,stock.getQuantity());
            ps.setDouble(5,stock.getBuyPrice());
            ps.setDouble(6,stock.getCurrentPrice());

            flag = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return flag;
    }
}
