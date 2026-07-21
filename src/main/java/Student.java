public class Student {

    // domain-entity-model katmani
    // Student sınıfına karşılık gelecek bir tablo gereklidir

    // AMAÇ: Uygulamanın veri modelini (domain veya entity) temsil eder.
    // Her bir Student objesi, veritabanındaki karsiligi tablodaki bir kayıta karşılık gelir.
    // Diğer katmanlar (Repository, Service) bu objeyi veri taşımak için kullanır.

    private Integer id;
    private String name;
    private String lastname;
    private String city;
    private int age;

    //——-Parametresiz ve parametreli constructor

    public Student() {
    }

    public Student(String name, String lastname, String city, int age) {
        this.name = name;
        this.lastname = lastname;
        this.city = city;
        this.age = age;
    }

    //----------getter-setter-----------


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //------------toString()-----------

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }
}