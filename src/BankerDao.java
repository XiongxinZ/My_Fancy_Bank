import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BankerDao {

    private static BankerDao bankerDao = new BankerDao();

    public static BankerDao getInstance(){
        return bankerDao;
    }

    public int insertCustomer(Banker banker) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String b_id = banker.getId();
            String b_name = banker.getName();
            String b_pswd = banker.getPassword();
            String b_email = banker.getEmail();


            String sql = "insert into banker(b_id, b_name, b_pswd, b_email) "
                    + "values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, b_id);
            ps.setString(2, b_name);
            ps.setString(3, b_pswd);
            ps.setString(4, b_email);
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }


    public int updateBankerPSW(String id, String pwd) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "update banker set b_pswd = ? where b_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, pwd);
            ps.setString(2, id);

            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);

        }
        return flag;
    }


    public int updateBankerPSW(Banker banker) {
        return updateBankerPSW(banker.getId(), banker.getPassword());
    }

    public int delBanker(String id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "delete from banker where b_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            System.out.println("delBanker(Banker banker)" + ps.toString());
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
            return flag;
        }
    }

    public int delBanker(Banker banker) {

        return delBanker(banker.getId());
    }

    public String getCustomerName(String id){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String userName = "-";
        if (id == null){
            return userName;
        }

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from banker where b_id = ?";

            System.out.println("selectBanker(Banker banker)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()){
                userName = rs.getString("b_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return userName;
    }

    public Banker selectBanker(String email, String pswd) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Banker user = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from banker where b_email = ? and b_PSWD = ?";

            System.out.println("selectBanker(Banker banker)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pswd);

            rs = ps.executeQuery();

            while(rs.next()) {
                user = new Banker(pswd, email);

                user.setId(rs.getString("b_id"));
                user.setName(rs.getString("b_Name"));
                user.setPassword(rs.getString("b_PSWD"));
                user.setEmail(rs.getString("b_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return user;
    }

    public Banker selectBanker(Banker banker) {
        return selectBanker(banker.getEmail(), banker.getPassword());
    }


    public Banker selectCustomerWithCid(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Banker user = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from customer where c_id = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                user = new Banker();

                user.setId(rs.getString("c_id"));
                user.setName(rs.getString("c_Name"));
                user.setPassword(rs.getString("c_PSWD"));
                user.setEmail(rs.getString("c_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
            return user;
        }
    }


    public List<Banker> selectBankerList(Banker banker){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Banker> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            String b_id = banker.getId();
            String b_name = banker.getName();
            String b_pswd = banker.getPassword();

            StringBuffer sql = new StringBuffer("select * from banker where 1 = 1");

            if(!"".equals(b_id) && b_id != null) {
                sql.append(" and b_id = '" + b_id + "'");
            }

            if(!("".equals(b_name)) && b_name != null) {
                sql.append(" and b_name = '" + b_name + "'");
            }

            if(!("".equals(b_pswd)) && b_pswd != null) {
                sql.append(" and b_pswd = '" + b_pswd + "'");
            }

            ps = conn.prepareStatement(String.valueOf(sql));

            rs = ps.executeQuery();

            while(rs.next()) {
                Banker user = new Banker();
                user.setId(rs.getString("b_id"));
                user.setName(rs.getString("b_Name"));
                user.setPassword(rs.getString("b_PSWD"));
                user.setEmail(rs.getString("b_email"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getBankers() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            StringBuilder sql = QueryUtil.getAllQuery("banker");
            ps = conn.prepareStatement(String.valueOf(sql));
            rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                // b_ID, b_Name, b_PSWD, b_email
                dataRow.add(rs.getString("b_ID"));
                dataRow.add(rs.getString("b_Name"));
                dataRow.add(rs.getString("b_PSWD"));
                dataRow.add(rs.getString("b_email"));
                list.add(dataRow);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getCustomers() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            StringBuilder sql = QueryUtil.getAllQuery("customer");
            ps = conn.prepareStatement(String.valueOf(sql));
            rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                // c_ID, c_Name, c_PSWD, c_email
                dataRow.add(rs.getString("c_ID"));
                dataRow.add(rs.getString("c_Name"));
                dataRow.add(rs.getString("c_PSWD"));
                dataRow.add(rs.getString("c_email"));
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