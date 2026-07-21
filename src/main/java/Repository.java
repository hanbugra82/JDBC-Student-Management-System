import java.util.List;

public interface Repository<S, U> { //Farkli data tipinde objelerle calisabilsin diye jenerik yapalim

    // Repository başlı başına bir katman değil, Repository Katmanının önemli bir parçasıdır.
    // Bu katmanın sahip olması gereken metotları (CRUD işlemleri) tanımlayan arayüzdür.

    // AMAÇ: Veri erişim katmanının (Repository Katmanı) tüm sınıflarının uyması gereken
    // bir sözleşme (interface) tanımlar.
    // Bu arayüz, CRUD (Create, Read, Update, Delete) operasyonlarının temel yapısını belirler.
    // Generic (<S, U>) yapısı sayesinde, farklı veri modelleri (entity'ler)
    // ve ID tipleri için yeniden kullanılabilir.

    void createTable(); // Metot: Veritabanında bir tablo oluşturmak için kullanılır.

    void save(S entity); // Metot: Yeni bir entity (nesne) veritabanına kaydeder.
    // 'S entity' parametresi, kaydedilecek objeyi temsil eder (örn. Student objesi).

    List<S> findAll(); // Metot: Tablodaki tüm kayıtları listeler.
    // Geriye 'S' tipinde objelerin listesini döndürür (örn. List<Student>).

    void update(S entity, U id); // Metot: Veritabanındaki bir kaydı günceller.
    // 'S entity' güncel bilgileri, 'U id' ise hangi kaydın güncelleneceğini belirtir.

    void deletedById(U id); // Metot: Belirli bir ID'ye sahip kaydı siler.
    // 'U id' parametresi, silinecek kaydın ID'sini temsil eder.

    S findById(U id); // Metot: Belirli bir ID'ye sahip kaydı bulur.
    // Geriye bulunan 'S' tipindeki objeyi (örn. Student) döndürür.

}