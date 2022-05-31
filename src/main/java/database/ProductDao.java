package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductDao extends Dao {
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS Product (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "name TEXT,\n"
                + "category TEXT, \n"
                + "brand_id INTEGER ,\n"
                + "price INTEGER, \n"
                + "site_url TEXT unique, \n"
                + "photo_url TEXT, \n"
                + "is_bookmarked INTEGER , \n"
                + "memo TEXT, \n"
                + "foreign key(brand_id) references Brand(id)\n"
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
        // 동일한 상품이 존재하는 경우
        if(findBySiteURL(product.getSiteURL())) return;

        String SQL = "INSERT or ignore INTO product VALUES (?,?,?,?,?,?,?,?,?)";

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
            pstmt.setInt(8, 0);
            pstmt.setString(9, null);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt);
        }
    }

    public Boolean findBySiteURL(String siteURL) {
        String SQL = "SELECT *\n" +
                "FROM product\n" +
                "WHERE site_url = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, siteURL);

            rs = pstmt.executeQuery();

            return rs.next();
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
            stmt.execute("DROP TABLE IF EXISTS product");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }
}
