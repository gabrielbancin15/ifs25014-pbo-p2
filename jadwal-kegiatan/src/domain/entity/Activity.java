package domain.entity;

import java.util.Objects;

public class Activity {
    private final int id;
    private String title;
    private String day;
    private String time;

    public Activity(int id, String title, String day, String time) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDay() { return day; }
    public String getTime() { return time; }

    public void changeTitle(String title) { this.title = title; }
    public void changeDay(String day) { this.day = day; }
    public void changeTime(String time) { this.time = time; }

    /** Kesetaraan berdasarkan ID agar aman dikelola dalam koleksi. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
