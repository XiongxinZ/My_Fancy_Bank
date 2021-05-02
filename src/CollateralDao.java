import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollateralDao {

    public static List<Collateral> selectCollateralList(Customer customer) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Collateral> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            String id = customer.getId();
            String sql = "select * from collateral where c_id = ?";


            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {

                String coName = rs.getString("co_name");
                String coId = rs.getString("co_id");
                boolean used = rs.getInt("co_used") != 0;
                double price = rs.getDouble("co_value");


                Collateral collateral = new Collateral(customer, coName, price, used, coId);

                list.add(collateral);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }

    public static void insertCollateral(String id, String name, double price, int used, String coId){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into collateral(c_id,co_id,co_name,co_price,co_used) values(?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, coId);
            ps.setString(3, name);
            ps.setDouble(4,price);
            ps.setInt(5, used);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
    }

    public static List<Collateral> selectUnusedCollateralList(Customer customer) {
        List<Collateral> all = selectCollateralList(customer);
        List<Collateral> unused = new ArrayList<>();
        for (Collateral collateral : all) {
            if (!collateral.isUsed()) {
                unused.add(collateral);
            }
        }
        return unused;
    }

    public static int updateUsed(Collateral collateral) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "update collateral set co_used = " + (collateral.isUsed()?1:0) +
                    " where co_name = '" + collateral.getName() + "'";
            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

    public static void insertCollateralRequest(CollateralValuation collateralValuation){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into collateralValuation(c_id, co_name, f_path) values(?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, collateralValuation.getCustomerId());
            ps.setString(2, collateralValuation.getName());
            ps.setString(3, collateralValuation.getFileName());

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }

    }

    public static List<CollateralValuation> selectCollateralRequestList(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CollateralValuation> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from collateralValuation";

            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            while(rs.next()){
                String id = rs.getString("c_id");
                String coName = rs.getString("co_name");
                String filePath = rs.getString("f_path");

                CollateralValuation collateralValuation = new CollateralValuation(id, coName, new File(filePath));
                list.add(collateralValuation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }

}
