import java.sql.*;

public class ConnectDB {
    private static String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static String user = "scott";
    private static String password = "orcl";
    private static Connection con;

    //测试方法
    public static void main(String[] args) throws Exception {
        connect();
        insert("insert into student values (?)",2,con,1);
        select("select * from student", "id", con);
    }


    /*与数据库获取连接
     * */
    public static void connect() throws Exception {
        try {
            Class.forName("oracle.jdbc.OracleDriver");//加载ojdbc驱动，类加载
            con = DriverManager.getConnection(url, user, password);//获取链接
            System.out.println("连接结果: " + con);//打印连接结果
        } catch (Exception e) {
            System.out.println("连接失败");//异常返回
        }
    }

    /*查询数据库记录
    sql为查询语句
    index为数据库表中的索引
    * */
    public static void select(String sql, String index, Connection con) {
        ResultSet rs = null;//初始化结果集
        PreparedStatement ps; //定义接口
        try {
            ps = con.prepareStatement(sql);//执行sql语句
            rs = ps.executeQuery();//将查询结果存储到结果集
            while (rs.next()) {
                System.out.println("查询结果: " + rs.getString(index));//打印查询结果
            }
            /*异常无记录
            查询失败
            * */
            rs.close();
            ps.close();
            con.close();
            System.out.println("数据库关闭");
        } catch (Exception e) {
            if (rs == null) {
                System.out.println("表中没有记录");
            } else {
                System.out.println("查询失败");
            }
        }
    }



    public static void insert(String sql, int data, Connection con, int already) {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);//执行sql语句
            ps.setInt(already, data);//set方法确定插入参数位置
            ps.executeUpdate();//执行insert操作
            System.out.println("插入成功");
            con.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("操作失败");
        }

    }


}
