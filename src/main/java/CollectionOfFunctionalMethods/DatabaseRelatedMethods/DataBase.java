package CollectionOfFunctionalMethods.DatabaseRelatedMethods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private final String URL = "jdbc:mysql://192.168.1.66:1234/macaca?characterEncoding=utf8&useSSL=true";
    private final String NAME = "hbjy6_java";
    private final String PASSWORD = "xwzKuBKTvC5znzfk";
    private Connection conn = null;

    public Connection  DatabaseSqlConect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("未能成功加载驱动程序，请检查是否导入驱动程序！");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(this.URL, this.NAME, this.PASSWORD);
            System.out.println("获取数据库连接成功！");
        } catch (SQLException e) {
            System.out.println("获取数据库连接失败！");
            e.printStackTrace();
        }
        return conn;
    }

    public void DatabaseSqlClose(Statement stmt,Connection conn) throws SQLException {
        if(stmt!=null) {
            stmt.close();
        }
        //数据库打开后就要关闭
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                conn = null;
            }
        }
    }

    public int InsertDatabaseSql(String insertsql) throws SQLException {
        Connection con=this.DatabaseSqlConect();
        PreparedStatement ps = con.prepareStatement(insertsql);
        int result = ps.executeUpdate();
        this.DatabaseSqlClose(ps,con);
        return result;
    }
    public List<String> QueryDatabaseSql(String query) throws SQLException {
       // String query="SELECT count(*) from platform_exception_informatin_table";
        Connection con=this.DatabaseSqlConect();
        List<String> datalist = new ArrayList<String>();
        System.out.println(query);
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // System.out.println("PLATFORM_NAME >> "+rs.getString("PLATFORM_NAME"));
            System.out.println("数量 >> "+rs.getInt(1));
            datalist.add(rs.getString(1));
        }
        this.DatabaseSqlClose(ps,con);
        return datalist;
    }
}
