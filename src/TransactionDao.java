import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TransactionDao {
    private static String dbName = "transactionLog";

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

            StringBuilder sql = QueryUtil.getPartAll(dbName);

            if(!"".equals(id) && id != null) {
                sql.append(" and");

                sql.append(" (");
                sql.append (QueryUtil.appendConstrain("f_id",id)).append(" or ").append(QueryUtil.appendConstrain("t_id", id));
                sql.append(")");

            }

            if(!("".equals(name)) && name != null) {
//                sql.append(" and (f_name = '").append(name).append("' or t_name = '").append(name).append("')");

                sql.append(" and");

                sql.append(" (");
                sql.append(QueryUtil.appendConstrain("f_name",name)).append(" or ").append(QueryUtil.appendConstrain("t_name", name));
                sql.append(")");
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
