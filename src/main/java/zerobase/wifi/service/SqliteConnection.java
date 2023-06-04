package zerobase.wifi.service;

import java.sql.*;

public class SqliteConnection {
    	
	static {
        try {
            Class.forName("org.sqlite.JDBC"); // SQLite JDBC 드라이버를 로드합니다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 데이터베이스 연결을 가져옵니다.
    public static Connection getConnect() {

        final String dbPath = "C:\\dev\\sqlite-tools-win32-x86-3420000";
        final String fileLocation = dbPath + "/sqlite3db.db";

        // SQLite 연결 문자열을 생성합니다.
        String url = "jdbc:sqlite:" + fileLocation;
        Connection connection = null;

        try {
            // 지정된 URL로 데이터베이스에 연결합니다.
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    // ResultSet, PreparedStatement 및 Connection을 닫습니다.
    public static void close(ResultSet rs, PreparedStatement preparedStatement, Connection connection) {

        try {
            // ResultSet이 열려있고 닫혀있지 않은 경우 닫습니다.
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // PreparedStatement가 열려있고 닫혀있지 않은 경우 닫습니다.
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // Connection이 열려있고 닫혀있지 않은 경우 닫습니다.
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
