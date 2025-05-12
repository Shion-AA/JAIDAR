package ph.edu.usc.jaidar;

public class User {
    private String uid;
    private String name;
    private String email;
    private String gender;
    private int age;
    private String location;
    private String experience;
    private String background;
    public User(){};
    public User(String uid, String name, String email, String gender, int age, String location, String experience, String background) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.location = location;
        this.experience = experience;
        this.background = background;
    }
//    getter
    public String getUid(){
        return this.uid;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getGender() {
        return this.gender;
    }
    public int getAge() {
        return this.age;
    }
    public String getLocation() {
        return this.location;
    }
    public String getExperience() {
        return this.experience;
    }

    public String getBackground() {
        return this.background;
    }

//    setter
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
