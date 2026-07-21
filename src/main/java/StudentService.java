import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class StudentService {

    //service katmani (business) mantıksal işlemler, kontroller
    //service classları’da repository classları ile görüşür

    // AMAÇ: İş mantığını (business logic) uygular.
    // Repository katmanını kullanarak veritabanı işlemlerini yapar ve kullanıcıdan alınan
    // veriler üzerinde kontroller gerçekleştirir.
    // Örneğin, öğrenci bilgilerini alma ve kaydetme gibi mantıksal adımları içerir.

    //Kullanicidan girdi almak icin kullanilacak
    private Scanner input = StudentController.inp;

    //Veritabani islemlerini yapmak icin kullanacagiz
    private Repository repository = new StudentRepository();

    //1-------------
    // Metot: Veritabanında öğrenci tablosunu oluşturmak için Repository katmanını çağırır.

    public void createTable() {
        //Kulanicinin table olusturmaya yetkisi var mi diye kontrol edildi....
        repository.createTable();
    }

    //2----------------
    //Metot:Kullanıcıdan öğrenci bilgilerini (ad, soyad, şehir, yaş) alarak yeni bir Student objesi oluşturur.
    public Student getStudentInfo() {

        System.out.println("AD : ");
        String name = input.nextLine();

        System.out.println("SOYAD : ");
        String lastname = input.nextLine();

        System.out.println("SEHIR : ");
        String city = input.nextLine();

        System.out.println("YAS : ");
        int age = input.nextInt();

        //Student class'taki parametreli constructor'a verilerimizi gonderelim
        return new Student(name, lastname, city, age);

    }

    //3----------------
    // Metot: Yeni oluşturulan öğrenci objesini veritabanına kaydetmek için Repository katmanına gönderir.
    public void saveStudent(Student newStudent) {
        //newStudent.getName().length()==0; gibi kontroller yapilabilir
        repository.save(newStudent);
    }

    //4----------------
    // Metot: Bu metodun temel amacı, veritabanındaki tüm öğrenci kayıtlarını alıp
    // konsolda kullanıcıya göstermektir. StudentService sınıfı, veriyi "nasıl" çekeceği (SQL sorgusu)
    // ile ilgilenmez, sadece StudentRepository'deki findAll() metodunu çağırarak veriyi talep eder.
    //Bu, katmanlı mimarinin temel prensibidir: her katmanın kendine ait bir sorumluluğu vardır.
    // StudentService iş mantığını yönetirken, veri erişim detaylarını StudentRepository'ye devreder.

    public void printAllStudents() {

        List<Student> studentList = repository.findAll();
        System.out.println("----------------TUM OGRENCILER--------------");
        for (Student s : studentList) {
            System.out.println("id : " + s.getId() +
                    "      adi :    " + s.getName() +
                    "      soyadi :   " + s.getLastname() +
                    "      sehir :    " + s.getCity() +
                    "      yas :     " + s.getAge());
        }
    }

    //5----------------
    // Metot: Belirli bir ID'ye sahip öğrenciyi Repository katmanından bulur.
    // Eğer öğrenci bulunamazsa konsola uyarı mesajı yazdırır.

    public Student getStudentById(int id) {
        Student student = (Student) repository.findById(id);
        if (student == null) {
            System.out.println("ID si verilen ogrenci bulunamadi");
        }
        return student;
    }

    //6----------------
    // Metot: Belirli bir ID'ye sahip öğrenciyi bulur ve bilgilerini konsolda yazdırır.
    // Bu metot, öncelikle getStudentById(id) metodunu çağırarak öğrencinin var olup olmadığını kontrol eder.

    public void printStudentById(int id) {
        Student foundStudent = getStudentById(id);
        if (foundStudent != null) {
            System.out.println(foundStudent);
        }
    }

    //7----------------
    // Metot: Belirli bir ID'ye sahip öğrencinin bilgilerini günceller.
    // Önce öğrencinin varlığını kontrol eder, ardından yeni bilgileri alıp Repository katmanını
    // kullanarak güncellemeyi yapar.

    public void updateStudent(int id) {

        Student found = getStudentById(id); //Girilen id'li ogrenci var mi diye kontrol ettik?
        if (found != null) {
            Student newStudent = getStudentInfo(); //Kullanicidan yeni bilgileri aldik
            repository.update(newStudent, id); //Guncelleme islemini id ile yaptik
        }
    }

    //8----------------
    // Metot: Belirli bir ID'ye sahip öğrenciyi siler.
    // Tıpkı güncelleme gibi, bu metot da silme işleminden önce bir kontrol mekanizması içerir.

    public void deleteStudent(int id) {
        getStudentById(id); //Ogrencinin gercekten var olup olmadigini kontrol ettik
        repository.deletedById(id);
    }

    //9----------------
    // Metot: Tüm öğrencilerin ad-soyad bilgilerini alıp bir metin dosyasına ("student_report.txt") yazar.
    //Bu işlem, uygulamanın ana akışını engellememesi için asenkron olarak
    // (başka bir iş parçacığında) çalıştırılmıştır.
    // Asenkron bir işlem gibi görünmesi için 10 saniye bekletme (`Thread.sleep`) içerir.

    public void generateReport() {

        List<Student> allStudent = repository.findAll();
        System.err.println("Rapor olusturuluyor...");

        try {
            Thread.sleep(10000);
            FileWriter writer = new FileWriter("student_report.txt");
            writer.write("**** Student Report ****\n");
            writer.write("------------------------------\n");

            for (Student student : allStudent) {
                writer.write("Ad : " + student.getName() + "----------- Soyad : " + student.getLastname() + "\n");
            }

            writer.close();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.err.println("Rapor olusturuldu ve student_report.txt dosyasina yazildi...");

    }


}