package domain.entity;

import java.util.Objects;

public class Contact {
    private final int id;
    private String name;
    private String phone;
    private String email;

    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void changeName(String name) { this.name = name; }
    public void changePhone(String phone) { this.phone = phone; }
    public void changeEmail(String email) { this.email = email; }

    /** Kesetaraan berdasarkan ID agar aman dikelola dalam koleksi. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
