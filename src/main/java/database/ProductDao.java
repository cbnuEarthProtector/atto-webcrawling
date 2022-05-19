package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductDao extends Dao {
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS product (\n"
                + "id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "name text NOT NULL,\n"
                + "category text, \n"
                + "price integer, \n"
                + "site_url text, \n"
                + "photo_url text \n"
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

    public void insert(Product product) {
        String SQL = "INSERT INTO product VALUES (?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, product.getId()); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setInt(4, product.getPrice());
            pstmt.setString(5, product.getSiteURL());
            pstmt.setString(6, product.getPhotoURL());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt);
        }
    }

    public void reset() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM product");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }
}
