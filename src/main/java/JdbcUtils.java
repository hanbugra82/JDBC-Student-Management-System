import java.sql.*;

public class JdbcUtils {

    // Bu sınıf, doğrudan katmanlar arası veri akışına dahil değildir.
    // Yalnızca Repository katmanının veritabanı ile bağlantı kurmasına
    // ve bu bağlantıyı kapatmasına yardımcı olan bir araçtır.

    //AMAÇ:Veritabanı bağlantısı, Statement ve PreparedStatement nesnelerini yönetmek için yardımcı metotlar sağlar.
    // En alt katman olarak, diğer sınıflar bu sınıfı kullanarak veritabanına erişir.

    public static Connection connection; //Veritabanı bağlantı nesnesi. Bağlantıyı temsil eder.
    public static Statement statement; //SQL sorgularını çalıştırmak için kullanılan nesne.
    public static PreparedStatement prst; //Parametreli SQL sorguları (güvenlik için) için kullanılan nesne.

    //-----------------------------
    //a.connection oluşturma. Veritabanı ile bir bağlantı kurar.

    public static void setConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/student_management_390",
                    "techpro390",
                    "password"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //-----------------------------
    //b.statement oluşturma. Basit SQL sorguları için bir statement nesnesi oluşturur.

    public static void setStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //-----------------------------
    //c.prepareStatement oluşturma
    // Metot: Parametreli SQL sorguları için bir PreparedStatement nesnesi oluşturur.
    // 'sql' parametresi, çalıştırılacak sorguyu alır.

    public static void setPrst(String sql) {
        try {
            prst = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
