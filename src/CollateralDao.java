import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
                Collateral collateral = new Collateral();

                collateral.setName(rs.getString("co_id"));
                collateral.setPrice(Double.parseDouble(rs.getString("co_value")));
                collateral.setCustomer(customer);
                collateral.setUsed(rs.getInt("co_used") != 0);

                list.add(collateral);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
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
                    " where co_id = '" + collateral.getId() + "'";
            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }
}
