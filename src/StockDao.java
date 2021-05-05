import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StockDao {

    private static StockDao stockDao = new StockDao();

    public static StockDao getInstance(){
        return stockDao;
    }

    public int updateStockPosition(CustomerStock stock) {
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
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public int updateStockInfo(StockInfo stock) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "update stockinfo set c_price = ? where s_name = ?";

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, stock.getCurrentPrice());
            ps.setString(2, stock.getName());

//            System.out.println("delCustomer(Customer customer)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public int removeCustomerStock(CustomerStock stock) {

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
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public int insertCustomerStock(CustomerStock stock){
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
            JDBCUtil.closeResource(ps,rs);
        }
        return flag;
    }

    public List<StockInfo> selectStockInfoList(){
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
            JDBCUtil.closeResource(ps,rs);
        }
        return list;
    }


    public StockInfo selectStockInfo(String name){
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
            JDBCUtil.closeResource(ps,rs);
        }
        return stockInfo;
    }

    public StockInfo selectStockInfo(CustomerStock customerStock){
        return selectStockInfo(customerStock.getName());
    }

    public List<CustomerStock> selectOwnedInfoList(Customer customer){
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
            JDBCUtil.closeResource(ps,rs);
        }
        return list;
    }

    public ValuePool<CustomerStock> selectCustomerStockList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ValuePool<CustomerStock> list = new ValuePool<>();

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
            JDBCUtil.closeResource(ps,rs);
        }
        return list;
    }

    public int updateProfit(StockProfit profit){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int flag = 0;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "update realizedStock set r_profit = ? where c_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, profit.getProfit());
            ps.setString(2, profit.getCustomer().getId());

            flag = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public int insertProfit(StockProfit profit){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int flag = 0;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into realizedStock(c_id,s_name,s_curr,r_profit) values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,profit.getCustomer().getId());
            ps.setString(2,profit.getName());
            ps.setString(3, profit.getCurrency());
            ps.setDouble(4, profit.getProfit());

            flag = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public int insertStockInfo(StockInfo stockInfo){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int flag = 0;

        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into stockInfo(s_name,s_curr,c_price) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,stockInfo.getName());
            ps.setString(2,stockInfo.getCurrency());
            ps.setDouble(3, stockInfo.getCurrentPrice());

            flag = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public ValuePool<StockProfit> selectProfitList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ValuePool<StockProfit> list = new ValuePool<>();

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

    public List<Vector<String>> getStocks() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            StringBuilder sql = QueryUtil.getAllQuery("stockInfo");
            ps = conn.prepareStatement(String.valueOf(sql));
            rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                // s_name, s_curr, c_price
                dataRow.add(rs.getString("s_name"));
                dataRow.add(rs.getString("s_curr"));
                dataRow.add(rs.getString("c_price"));
                list.add(dataRow);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }
}
