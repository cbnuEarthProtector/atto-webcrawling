package database;

import java.sql.*;

public class Dao {
    final String dbURL;

    protected Dao() {
        this.dbURL = "jdbc:sqlite:./database/app.db";
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected void close(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        close(conn, pstmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
