package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactRepository implements IContactRepository {
    // Penyimpanan data kontak secara in-memory
    private final List<Contact> data = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int idCounter = 0;

    @Override
    public List<Contact> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Contact> findById(int id) {
        // Mengembalikan referensi langsung ke objek dalam list;
        // mutasi harus selalu diikuti pemanggilan update() agar kontrak repository terpenuhi
        return data.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public Contact save(String name, String phone, String email) {
        Contact contact = new Contact(++idCounter, name, phone, email);
        data.add(contact);
        return contact;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(c -> c.getId() == id);
    }

    @Override
    public void update(Contact contact) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == contact.getId()) {
                data.set(i, contact);
                return;
            }
        }
    }
}
