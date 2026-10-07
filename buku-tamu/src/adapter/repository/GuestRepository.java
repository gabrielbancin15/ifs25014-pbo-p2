package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuestRepository implements IGuestRepository {
    // Penyimpanan data tamu secara in-memory
    private final List<Guest> data = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int idCounter = 0;

    @Override
    public List<Guest> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Guest> findById(int id) {
        // Mengembalikan referensi langsung ke objek dalam list;
        // mutasi harus selalu diikuti pemanggilan update() agar kontrak repository terpenuhi
        return data.stream().filter(g -> g.getId() == id).findFirst();
    }

    @Override
    public Guest save(String name, String purpose) {
        Guest g = new Guest(++idCounter, name, purpose);
        data.add(g);
        return g;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(g -> g.getId() == id);
    }

    @Override
    public void update(Guest guest) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == guest.getId()) {
                data.set(i, guest);
                return;
            }
        }
    }
}
