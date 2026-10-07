package domain.entity;

import java.util.Objects;

public class Guest {
    private final int id;
    private String name;
    private String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPurpose() { return purpose; }

    public void changeName(String name) { this.name = name; }
    public void changePurpose(String purpose) { this.purpose = purpose; }

    /** Kesetaraan berdasarkan ID agar aman dikelola dalam koleksi. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
