package org.example.Control;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // Cấu hình thông tin kết nối MySQL
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "3306";
    private static final String DB_NAME = "quanlytaisan";
    private static final String USER = "root";
    private static final String PASSWORD = "061006";

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
                + "?useUnicode=true&characterEncoding=UTF-8"
                + "&useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            System.out.println("Kết nối CSDL thành công: " + con.getCatalog());
        } catch (Exception e) {
            System.err.println("Kết nối CSDL thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
