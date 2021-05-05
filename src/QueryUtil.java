import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    public static StringBuilder getAllQueryWithConstrain(String tableName, String... constrain){
        StringBuilder sql = getPartAll(tableName);
        sql.append(" ");
        assert constrain.length % 2 == 0;
        for (int i = 0; i < constrain.length; i = i + 2) {
            sql.append(" and ").append(constrain[i]).append("=").append("'").append(constrain[i+1]).append("'");
        }
        sql.append(";");
        return sql;
    }

    public static StringBuilder getAllQuery(String tableName){
        StringBuilder sql = getPartAll(tableName);
        sql.append(";");
        return sql;
    }

    public static StringBuilder getPartAll(String tableName){
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(tableName).append(" where 1 = 1");
        return sql;
    }

    public static StringBuilder appendConstrain(String... constrain){
        StringBuilder sql = new StringBuilder(" ");
        for (int i = 0; i < constrain.length; i = i + 2) {
            sql.append(constrain[i]).append("=").append("'").append(constrain[i+1]).append("' ");
        }
        return sql;
    }

    public static StringBuilder concatAndConstrain(StringBuilder s1, StringBuilder s2){
        StringBuilder s = new StringBuilder(s1);
        s.append(" and ").append(s2);
        return s;
    }

    public static StringBuilder concatOrConstrain(StringBuilder s1, StringBuilder s2){
        StringBuilder s = new StringBuilder(s1);
        s.append(" or ").append(s2);
        return s;
    }

    public static StringBuilder getQueryString(String tableName, String... constrain){
//        assert constrain.length % 2 == 1;
        StringBuilder sql = new StringBuilder("select");
        int i = 0;
        for ( i = 0; i < constrain.length; i++) {
            if (constrain[i].equals(".")){
                break;
            }
            sql.append(" ").append(constrain[i]);
        }
        sql.append(" from ").append(tableName).append(" where 1 = 1");
        for (i = i+1 ; i < constrain.length; i = i + 2) {
            sql.append(" and ").append(constrain[i]).append("=").append("'").append(constrain[i+1]).append("'");
        }
        sql.append(";");
        return sql;
    }

    public static ResultSet getResult(String query){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public static List<String[]> getResultString(String query){
        ResultSet rs = getResult(query);
        ArrayList<String[]> list = new ArrayList<>();
        try {
            while(rs.next()) {
                String[] dataRow = new String[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < dataRow.length; i++) {
                    dataRow[i] = rs.getString(i+1);
                }
                list.add(dataRow);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
