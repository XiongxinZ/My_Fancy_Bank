import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CollateralDao {

    private static CollateralDao collateralDao = new CollateralDao();

    public static CollateralDao getInstance(){
        return collateralDao;
    }

    public List<Collateral> selectCollateralList(Customer customer) {
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
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public void insertCollateral(String id, String name, double price, int used, String coId){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "insert into collateral(c_id,co_id,co_name,co_value,co_used) values(?,?,?,?,?)";

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
            JDBCUtil.closeResource(ps, rs);
        }
    }

    public List<Collateral> selectUnusedCollateralList(Customer customer) {
        List<Collateral> all = selectCollateralList(customer);
        List<Collateral> unused = new ArrayList<>();
        for (Collateral collateral : all) {
            if (!collateral.isUsed()) {
                unused.add(collateral);
            }
        }
        return unused;
    }

    public int updateUsed(Collateral collateral) {

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
            JDBCUtil.closeResource(ps, rs);
        }
        return flag;
    }

    public void insertCollateralRequest(CollateralValuation collateralValuation){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtil.getConnection();

            String sql = "insert into collateralValuation(r_date,c_id,cv_id, co_name, f_path,co_value,cv_status,s_date) " +
                    "values(?,?,?,?,?,?,?,?)";


            ps = conn.prepareStatement(sql);
            ps.setDate(1,new Date(collateralValuation.getRequestDate().getTime()));
            ps.setString(2, collateralValuation.getCustomerId());
            ps.setString(3, collateralValuation.getCvId());
            ps.setString(4, collateralValuation.getName());
            ps.setString(5, collateralValuation.getFileName());
            ps.setNull(6, Types.DOUBLE);
            ps.setString(7,collateralValuation.getStatus());
            ps.setNull(8, Types.DATE);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
    }

    public void updateCollateralRequest(CollateralValuation collateralValuation){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtil.getConnection();

            String sql = "update collateralValuation set co_value = ?, cv_status = ?, s_date = ?  where cv_id = ?";

            ps = conn.prepareStatement(sql);
            if (collateralValuation.getPrice() == null){
                ps.setNull(1, Types.DOUBLE);
            }else{
                ps.setDouble(1, collateralValuation.getPrice());
            }
            ps.setString(2, collateralValuation.getStatus());
            ps.setDate(3, new Date(collateralValuation.getSolveDate().getTime()));
            ps.setString(4, collateralValuation.getCvId());

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
    }



    public Collateral selectCollateralWithId(String id, Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Collateral collateral = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from collateral where co_id = ?";


            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {

                String coName = rs.getString("co_name");
                String coId = rs.getString("co_id");
                boolean used = rs.getInt("co_used") != 0;
                double price = rs.getDouble("co_value");

                collateral = new Collateral(customer, coName, price, used, coId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return collateral;
    }

    public List<CollateralValuation> selectUnsolvedCollateralRequestList(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CollateralValuation> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from collateralValuation";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String cvID = rs.getString("cv_id");
                String id = rs.getString("c_id");
                String coName = rs.getString("co_name");
                String filePath = rs.getString("f_path");
                Double price = rs.getDouble("co_value");
                String approve = rs.getString("cv_status");
                Date rDate = rs.getDate("r_date");
                Date sDate = rs.getDate("s_date");

                if (sDate != null){
                    continue;
                }

                CollateralValuation collateralValuation = new CollateralValuation(id, coName, new File(filePath),cvID,rDate);
                collateralValuation.setSolveDate(sDate);
                collateralValuation.setStatus(approve);
                if (approve.equals("Approve")){
                    collateralValuation.setPrice(price);
                }

                list.add(collateralValuation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<CollateralValuation> selectCollateralRequestList(String c_id){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<CollateralValuation> list = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            String sql = "select * from collateralValuation where 1 = 1";

            if (c_id!=null && !"".equals(c_id)){
                sql = sql + " and c_id = " + c_id;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String cvID = rs.getString("cv_id");
                String id = rs.getString("c_id");
                String coName = rs.getString("co_name");
                String filePath = rs.getString("f_path");
                Double price = rs.getDouble("co_value");
                String approve = rs.getString("cv_status");
                Date rDate = rs.getDate("r_date");
                Date sDate = rs.getDate("s_date");

                CollateralValuation collateralValuation = new CollateralValuation(id, coName, new File(filePath),cvID,rDate);
                collateralValuation.setSolveDate(sDate);
                collateralValuation.setStatus(approve);
                if (approve.equals("Approve")){
                    collateralValuation.setPrice(price);
                }

                list.add(collateralValuation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public List<Vector<String>> getCollateralRequests() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Vector<String>> list = new ArrayList<>();

        try {
            conn = JDBCUtil.getConnection();

            StringBuilder sql = QueryUtil.getAllQuery("collateralValuation");
            ps = conn.prepareStatement(String.valueOf(sql));
            rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> dataRow = new Vector<>();
                // r_date, c_id, cv_id, co_name, f_path, co_value, cv_status, s_date
                dataRow.add(rs.getString("r_date"));
                dataRow.add(rs.getString("c_id"));
                dataRow.add(rs.getString("cv_id"));
                dataRow.add(rs.getString("co_name"));
                dataRow.add(rs.getString("f_path"));
                dataRow.add(rs.getString("co_value"));
                dataRow.add(rs.getString("cv_status"));
                dataRow.add(rs.getString("s_date"));
                list.add(dataRow);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return list;
    }

    public CollateralValuation selectCollateralEvaluationWithRid(String request_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CollateralValuation cv = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from collateralValuation where cv_id = ?";

            System.out.println("selectCollateralEvaluationWithRid(String request_id)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, request_id);

            rs = ps.executeQuery();

            while(rs.next()) {
                String filePath = System.getProperty("user.dir") + "/certificate/" + rs.getString("f_path");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String sDate = rs.getString("r_date");
                java.util.Date date = formatter.parse(sDate);
                File in = new File(filePath);
                cv = new CollateralValuation(rs.getString("c_id"),
                        rs.getString("co_name"), in, rs.getString("cv_id"), date);
                // System.out.println(cv.getFileName());
                // System.out.println(cv.getRequestDate());
            }

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.closeResource(ps, rs);
        }
        return cv;
    }
}
