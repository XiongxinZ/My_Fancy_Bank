import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TransactionDao {

    public static List<Vector<String>> getTransactionList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            String id = customer.getId();
            String name = customer.getName();
//            String c_pswd = customer.getPassword();

            StringBuffer sql = new StringBuffer("select * from transactionLog where 1 = 1");

            if(!"".equals(id) && id != null) {
                sql.append(" and (f_id = '" + id + "' or t_id = '" + id + "')");
            }

            if(!("".equals(name)) && name != null) {
                sql.append(" and (f_name = '").append(name).append("' or t_name = '").append(name).append("')");
            }

            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while(rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(rs.getString("f_ID"));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(rs.getString("t_ID"));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("c_balance"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }
}
