import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    "values(?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1,stock.getCustomer().getId());
            ps.setString(2, stock.getName());
            ps.setString(3, stock.getCurrency());
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

    public static List<StockInfo> selectStockInfoList(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<StockInfo> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from stockInfo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String name = rs.getString("s_name");
                String curr = rs.getString("s_curr");
                double price = rs.getDouble("c_price");

                StockInfo stock = new StockInfo(name, price,curr);
                list.add(stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return list;
    }


    public static StockInfo selectStockInfo(String name){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StockInfo stockInfo = null;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from stockInfo where s_name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            while(rs.next()){
                String sname = rs.getString("s_name");
                String curr = rs.getString("s_curr");
                double price = rs.getDouble("c_price");

                stockInfo = new StockInfo(name, price,curr);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return stockInfo;
    }

    public static StockInfo selectStockInfo(CustomerStock customerStock){
        return selectStockInfo(customerStock.getName());
    }

    public static List<CustomerStock> selectOwnedInfoList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CustomerStock> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from stock where c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getId());

            rs = ps.executeQuery();

            while(rs.next()){
                String name = rs.getString("s_name");
                int quantity = rs.getInt("s_quantity");
                double buyPrice = rs.getDouble("b_price");

                StockInfo info = selectStockInfo(name);
                CustomerStock stock = new CustomerStock(info, customer, quantity,buyPrice);
                list.add(stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return list;
    }

    public static StockPool<CustomerStock> selectCustomerStockList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StockPool<CustomerStock> list = new StockPool<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from stock where c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getId());

            rs = ps.executeQuery();

            while(rs.next()){
                String name = rs.getString("s_name");
                int quantity = rs.getInt("s_quantity");
                double buyPrice = rs.getDouble("b_price");

                StockInfo info = selectStockInfo(name);
                CustomerStock stock = new CustomerStock(info, customer, quantity,buyPrice);
                list.put(name, stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }
        return list;
    }

    public static StockPool<StockProfit> selectProfitList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StockPool<StockProfit> list = new StockPool<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from realizedStock where c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getId());

            rs = ps.executeQuery();

            while (rs.next()){
                String name = rs.getString("s_name");
                double val = rs.getDouble("r_profit");
                String curr = rs.getString("s_curr");
                StockProfit profit = new StockProfit(customer, name, val,curr);
                list.put(name, profit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
