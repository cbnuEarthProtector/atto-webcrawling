package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductDao extends Dao {
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS product (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "name TEXT,\n"
                + "category TEXT, \n"
                + "brand_id INTEGER ,\n"
                + "price INTEGER, \n"
                + "site_url TEXT, \n"
                + "photo_url TEXT, \n"
                + "foreign key(brand_id) references brand(id)\n"
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

    public void insert(Integer brandId, Product product) {
        String SQL = "INSERT INTO product VALUES (?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, product.getId()); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setInt(4, brandId);
            pstmt.setInt(5, product.getPrice());
            pstmt.setString(6, product.getSiteURL());
            pstmt.setString(7, product.getPhotoURL());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt);
        }
    }

    public void drop() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS product");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }
}