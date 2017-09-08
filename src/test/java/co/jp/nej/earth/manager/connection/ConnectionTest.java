//package co.jp.nej.earth.manager.connection;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//
//import org.junit.Test;
//
//import co.jp.nej.earth.BaseTest;
//
///**
// * Created by minhtv on 3/16/2017.
// */
//
//public class ConnectionTest extends BaseTest {
//
//    @Test
//    public void testConnectionOracle() {
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            String URL = "jdbc:oracle:thin:@172.20.108.60:1521:earth";
//            String USER = "c##earth";
//            String PASS = "earth123";
//            Connection conn = DriverManager.getConnection(URL, USER, PASS);
//            Statement stmt = null;
//            if (conn != null) {
//                System.out.println("CONNECT ORACLE: SUCCESS!");
//                stmt = conn.createStatement();
//                String sql = "CREATE SCHEMA AUTHORIZATION 'schema_name' ;";
//                stmt.executeUpdate(sql);
//                System.out.println("Created table in given database...");
//            }
//
//        } catch (Exception ex) {
//            System.out.println("CONNECT ORACLE: " + ex.getMessage().toString());
//        }
//    }
//
//    @Test
//    public void testConnectionSQLServer() {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String URL = "jdbc:sqlserver://172.20.108.60:1433;databaseName=WORKSPACE1;integratedSecurity=false;";
//            String USER = "earth";
//            String PASS = "earth123";
//            Connection conn = DriverManager.getConnection(URL, USER, PASS);
//            if (conn != null) {
//                System.out.println("CONNECT SQL SERVER: SUCCESS!");
//            }
//
//        } catch (Exception ex) {
//            System.out.println("CONNECT  SQL SERVER: " + ex.getMessage().toString());
//        }
//    }
//}
