package domain.entity;

import java.util.Objects;

public class Todo {
    private final int id;
    private String title;
    // Nama field mengikuti spesifikasi: 'finished' (bukan 'done')
    private boolean finished;

    public Todo(int id, String title) {
        this.id = id;
        this.title = title;
        this.finished = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFinished() {
        return finished;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void markFinished() {
        this.finished = true;
    }

    public void markUnfinished() {
        this.finished = false;
    }

    /** Kesetaraan berdasarkan ID agar aman dikelola dalam koleksi. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
