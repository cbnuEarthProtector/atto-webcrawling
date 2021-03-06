package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BrandDao extends Dao {
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS Brand (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "name TEXT,\n"
                + "photo_url TEXT \n"
                + ");";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    public void insert(Brand brand) {
        // 동일한 브랜드명 중복 저장 방지
        if(findByName(brand.getName()) != null) return;

        String SQL = "INSERT INTO brand VALUES (?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, brand.getId()); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setString(2, brand.getName());
            pstmt.setString(3, brand.getPhotoURL());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt);
        }
    }

    public Integer findByName(String brandName) {
        String SQL = "SELECT id\n" +
                "FROM brand\n" +
                "WHERE name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, brandName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    public void drop() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS brand");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }
}
