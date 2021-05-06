import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class TransactionDao {
    private static String dbName = "transactionLog";

    private static TransactionDao transactionDao = new TransactionDao();

    public static TransactionDao getInstance() {
        return transactionDao;
    }

    public int insertTransaction(AbstractTransaction transaction) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into transactionLog(t_date, t_type, f_id, f_account, t_id, t_account, t_money, f_balance,f_currency, t_balance, t_currency) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setDate(1, new Date(transaction.getTransTime().getTime()));
            ps.setString(2, transaction.getTransType());
            ps.setString(3, transaction.getFromWhom() == null ? "-" : transaction.getFromWhom());
            ps.setString(4, transaction.getFromAccount() == null ? "-" : transaction.getFromAccount());
            ps.setString(5, transaction.getToWhom() == null ? "-" : transaction.getToWhom());
            ps.setString(6, transaction.getToAccount() == null ? "-" : transaction.getToAccount());
            ps.setString(7, String.valueOf(transaction.getAmount()));
            ps.setString(8, transaction.getFromBalance() == null ? "-" : String.valueOf(transaction.getFromBalance()));
            ps.setString(9, transaction.getFromBalance() == null ? "-" : transaction.getCurrencyFrom());
            ps.setString(10, transaction.getToBalance() == null ? "-" : String.valueOf(transaction.getToBalance()));
            ps.setString(11, transaction.getToBalance() == null ? "-" : transaction.getCurrencyTo());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public List<Vector<String>> getTransactionList(Customer customer) {
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

            if (!"".equals(id) && id != null) {
                sql.append(" and");

                sql.append(" (");
                sql.append(QueryUtil.appendConstrain("f_id", id)).append(" or ").append(QueryUtil.appendConstrain("t_id", id));
                sql.append(")");

            }


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("f_currency"));
                dataRow.add(rs.getString("t_balance"));
                dataRow.add(rs.getString("t_currency"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getTransactionList(Customer customer, String type) {
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

            if (!"".equals(id) && id != null) {
                sql.append(" and");

                sql.append(" (");
                sql.append(QueryUtil.appendConstrain("f_id", id)).append(" or ").append(QueryUtil.appendConstrain("t_id", id));
                sql.append(")");

            }

            sql.append(" and");

            sql.append(QueryUtil.appendConstrain("t_type", type));


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("f_currency"));
                dataRow.add(rs.getString("t_balance"));
                dataRow.add(rs.getString("t_currency"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getTransactionList(Customer customer, String type, String direction) {
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

            if (!"".equals(id) && id != null) {
                sql.append(" and");
                if ("All".equalsIgnoreCase(direction)) {
                    sql.append(" (");
                    sql.append(QueryUtil.appendConstrain("f_id", id)).append(" or ").append(QueryUtil.appendConstrain("t_id", id));
                    sql.append(")");
                } else if ("out".equalsIgnoreCase(direction)) {
                    sql.append(QueryUtil.appendConstrain("f_id", id));
                } else if ("in".equalsIgnoreCase(direction)) {
                    sql.append(QueryUtil.appendConstrain("t_id", id));
                }
            }

            if (!"All".equalsIgnoreCase(type)) {
                sql.append(" and");
                sql.append(QueryUtil.appendConstrain("t_type", type));
            }


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("f_currency"));
                dataRow.add(rs.getString("t_balance"));
                dataRow.add(rs.getString("t_currency"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getTransactionList(Customer customer, String type, String direction, String year, String month, String day, boolean hide) {
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

            if (!"".equals(id) && id != null) {
                sql.append(" and");
                if ("All".equalsIgnoreCase(direction)) {
                    sql.append(" (");
                    sql.append(QueryUtil.appendConstrain("f_id", id)).append(" or ").append(QueryUtil.appendConstrain("t_id", id));
                    sql.append(")");
                } else if ("out".equalsIgnoreCase(direction)) {
                    sql.append(QueryUtil.appendConstrain("f_id", id));
                } else if ("in".equalsIgnoreCase(direction)) {
                    sql.append(QueryUtil.appendConstrain("t_id", id));
                }
            }

            if (!"All".equalsIgnoreCase(type)) {
                sql.append(" and");
                sql.append(QueryUtil.appendConstrain("t_type", type));
            }


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                Date date = rs.getDate("t_date");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int transYear = calendar.get(Calendar.YEAR);
                int transMonth = calendar.get(Calendar.MONTH);
                int transDay = calendar.get(Calendar.DAY_OF_MONTH);


                if (!"All".equalsIgnoreCase(year) && transYear != Integer.parseInt(year)) {
                    continue;
                }

                if (!"All".equalsIgnoreCase(month) && transMonth != SystemDatabase.monthMap.get(month)) {
                    continue;
                }

                if (!"All".equalsIgnoreCase(day) && transDay != Integer.parseInt(day)) {
                    continue;
                }

                if (hide && rs.getString("t_type").equalsIgnoreCase("Pay Interest")) {
                    continue;
                }

                dataRow.add(rs.getString("t_date"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("f_id")));
                dataRow.add(rs.getString("f_account"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("t_id")));
                dataRow.add(rs.getString("t_account"));
                dataRow.add(rs.getString("t_money"));
                dataRow.add(rs.getString("f_balance"));
                dataRow.add(rs.getString("f_currency"));
                dataRow.add(rs.getString("t_balance"));
                dataRow.add(rs.getString("t_currency"));

                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public int insertStockTransaction(StockTransaction transaction) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into stockTransactionLog(t_date, c_id, s_name,t_type, t_price, t_quantity, t_amount) "
                    + "values(?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setDate(1, new Date(transaction.getDate().getTime()));
            ps.setString(2, transaction.getAccount().getCustomer().getId());
            ps.setString(3, transaction.getStock().getName());
            ps.setString(4, transaction.getType());
            ps.setDouble(5, transaction.getPrice());
            ps.setInt(6, transaction.getStock().getQuantity());
            ps.setDouble(7, transaction.getPrice() * transaction.getStock().getQuantity());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }


    public List<Vector<String>> getStockTransactionList(Customer customer, String type, String year, String month, String day) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            String id = customer.getId();


            StringBuilder sql = QueryUtil.getPartAll("stockTransactionLog");

            if (!"All".equalsIgnoreCase(type)) {
                sql.append(" and");
                sql.append(QueryUtil.appendConstrain("t_type", type));
            }

            sql.append(" and");
            sql.append(QueryUtil.appendConstrain("c_id", id));


            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
//            int size = rs.getFetchSize();
            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                Date date = rs.getDate("t_date");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int transYear = calendar.get(Calendar.YEAR);
                int transMonth = calendar.get(Calendar.MONTH);
                int transDay = calendar.get(Calendar.DAY_OF_MONTH);


                if (!"All".equalsIgnoreCase(year) && transYear != Integer.parseInt(year)) {
                    continue;
                }

                if (!"All".equalsIgnoreCase(month) && transMonth != SystemDatabase.monthMap.get(month)) {
                    continue;
                }

                if (!"All".equalsIgnoreCase(day) && transDay != Integer.parseInt(day)) {
                    continue;
                }

                // t_date, c_id, s_name,t_type, t_price, t_quantity, t_amount
                dataRow.add(rs.getString("t_date"));
                dataRow.add(CustomerDao.getInstance().getCustomerName(rs.getString("c_id")));
                dataRow.add(rs.getString("s_name"));
                dataRow.add(rs.getString("t_type"));
                dataRow.add(rs.getString("t_price"));
                dataRow.add(rs.getString("t_quantity"));
                dataRow.add(rs.getString("t_amount"));
                list.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public int getNumberOfTransactions(String t_type, String dbName, boolean justToday) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer num = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            // select count(*) from transactionLog where t_type="Deposit";
            StringBuilder sql = new StringBuilder("select count(*) from " + dbName + " where ");
            sql.append(QueryUtil.appendConstrain("t_type", t_type));
            if (justToday) {
                sql.append(" and ");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                sql.append(QueryUtil.appendConstrain("t_date", dateFormat.format(date)));
            }
            sql.append(";");

            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();
            while(rs.next()) {
                num = Integer.parseInt(rs.getString("count(*)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return num;
    }

}
