package ph.edu.usc.jaidar;

public class JobPost {
    private String id;
    private String title;
    private String description;
    private int headcount;
    private double rate;
    private String userPost;

    public JobPost() {}

    public JobPost(String id, String title, String description, int headcount, double rate, String userPost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.headcount = headcount;
        this.rate = rate;
        this.userPost = userPost;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getHeadcount() { return headcount; }
    public double getRate() { return rate; }
    public String getUserPost() { return userPost; }
}
