import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TransactionDao {
    private static String dbName = "transactionLog";

    public static int insertTransaction(Transaction transaction){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into customer(t_data, t_type, f_id, f_name, t_id, t_name, t_money, f_balance, t_balance) "
                    + "values(?,?,?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getTransTime());
            ps.setString(2, transaction.getTransType());
            ps.setString(3, transaction.getFromWhom()==null?"-": transaction.getFromWhom());
            ps.setString(4, transaction.getFromAccount()==null?"-": transaction.getFromAccount());
            ps.setString(5, transaction.getToWhom()==null?"-": transaction.getToWhom());
            ps.setString(6, transaction.getToAccount()==null?"-": transaction.getToAccount());
            ps.setDouble(7, transaction.getAmount());
            ps.setDouble(8, transaction.getFromBalance());
            ps.setDouble(9, transaction.getToBalance());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

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


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while(rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("t_balance"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }

    public static List<Vector<String>> getTransactionList(Customer customer, String type){
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

//            if(!("".equals(name)) && name != null) {
////                sql.append(" and (f_name = '").append(name).append("' or t_name = '").append(name).append("')");
//
//                sql.append(" and");
//
//                sql.append(" (");
//                sql.append(QueryUtil.appendConstrain("f_name",name)).append(" or ").append(QueryUtil.appendConstrain("t_name", name));
//                sql.append(")");
//            }

            sql.append(" and");

            sql.append(QueryUtil.appendConstrain("t_type",type));


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while(rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("t_balance"));

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
