import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//repository katmani: Veritabanı ile ilgili işlemler

// AMAÇ: Veritabanı ile doğrudan iletişimi sağlar.
//Bu katman, veritabanı sorgularını (SQL) çalıştırır, veriyi alır ve Student objelerine dönüştürür.
// İş mantığı içermez, sadece veri erişimini yönetir.

public class StudentRepository implements Repository<Student, Integer> { //S --> Student, U --> Integer

    //1-----------------
    // Metot: Veritabanında t_student tablosunu oluşturur.
    // 'CREATE TABLE IF NOT EXISTS' ifadesi, tablonun zaten var olup olmadığını
    // kontrol ederek yeniden oluşturulmasını engeller.

    @Override
    public void createTable() {

        JdbcUtils.setConnection();
        JdbcUtils.setStatement();

        try {
            JdbcUtils.statement.execute(
                    "CREATE TABLE IF NOT EXISTS t_student(" +
                            "id SERIAL UNIQUE," +
                            "name VARCHAR(50) NOT NULL CHECK(LENGTH(name) > 0)," +
                            "lastname VARCHAR(50) NOT NULL CHECK(LENGTH(lastname)>0)," +
                            "city VARCHAR(50) NOT NULL," +
                            "age INTEGER NOT NULL CHECK(age>0) )");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //2-----------------
    // Metot: Yeni bir Student objesini veritabanına kaydeder.

    @Override
    public void save(Student student) {

        JdbcUtils.setConnection();
        JdbcUtils.setPrst("INSERT INTO t_student(name, lastname, city, age) VALUES(?,?,?,?)");

        try {
            JdbcUtils.prst.setString(1, student.getName());
            JdbcUtils.prst.setString(2, student.getLastname());
            JdbcUtils.prst.setString(3, student.getCity());
            JdbcUtils.prst.setInt(4, student.getAge());

            JdbcUtils.prst.executeUpdate(); // Sorguyu calistirir
            System.out.println("Ogrenci kaydetme basarili !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //3-----------------
    // Metot: Veritabanındaki tüm student kayıtlarını çekip bir List olarak döndürür.

    @Override
    public List<Student> findAll() {

        JdbcUtils.setConnection();
        JdbcUtils.setStatement();

        List<Student> allStudent = new ArrayList<>();

        try {
            ResultSet rs = JdbcUtils.statement.executeQuery("SELECT * FROM t_student");
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("city"),
                        rs.getInt("age"));
                student.setId(rs.getInt("id"));
                allStudent.add(student);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        //Doldurulmus olan ogrenci listesini dondurelim
        return allStudent;

    }

    //4-----------------
    // Metot: Belirli bir ID'ye sahip öğrencinin bilgilerini günceller.

    @Override
    public void update(Student entity, Integer id) {

        JdbcUtils.setConnection();
        JdbcUtils.setPrst("UPDATE t_student SET name=?, lastname=?, city=?, age=? WHERE id=?");

        try {
            JdbcUtils.prst.setString(1, entity.getName());
            JdbcUtils.prst.setString(2, entity.getLastname());
            JdbcUtils.prst.setString(3, entity.getCity());
            JdbcUtils.prst.setInt(4, entity.getAge());
            JdbcUtils.prst.setInt(5, id); //id parametre ile gelecek
            int updated = JdbcUtils.prst.executeUpdate(); //Sorgu calistirildi ve etkilenen kayit sayisi alindi
            if (updated > 0) {
                System.out.println("Guncelleme basarili");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //5-----------------
    // Metot: Belirli bir ID'ye sahip öğrenciyi veritabanından siler.

    @Override
    public void deletedById(Integer id) {

        JdbcUtils.setConnection();
        JdbcUtils.setPrst("DELETE FROM t_student WHERE id=?");

        try {
            JdbcUtils.prst.setInt(1, id); //Silinecek kaydin id'si atandi
            int deleted = JdbcUtils.prst.executeUpdate(); //Sorgu calistirildi
            if (deleted > 0) {
                System.out.println("Silme islemi basarili...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    //6-----------------
    // Metot: Veritabanında belirli bir ID'ye sahip kaydı arar ve bulursa Student objesi olarak döndürür.

    @Override
    public Student findById(Integer id) {
        JdbcUtils.setConnection();
        JdbcUtils.setPrst("SELECT * FROM t_student WHERE id=?");
        Student student = null;

        try {
            JdbcUtils.prst.setInt(1, id);
            ResultSet rs = JdbcUtils.prst.executeQuery();
            if (rs.next()) { //Eger kayit bulunduysa, ResultSet bos degilse, orn. id yanlis girildi
                student = new Student(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("city"),
                        rs.getInt("age"));
                student.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtils.statement.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return student; //Bulunan ogrenci objesini veya null degerini return ettik
    }


}