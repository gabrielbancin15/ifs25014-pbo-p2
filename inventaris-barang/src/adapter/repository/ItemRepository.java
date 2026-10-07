package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository implements IItemRepository {
    // Penyimpanan data barang secara in-memory
    private final List<Item> data = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int idCounter = 0;

    @Override
    public List<Item> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Item> findById(int id) {
        // Mengembalikan referensi langsung ke objek dalam list;
        // mutasi harus selalu diikuti pemanggilan update() agar kontrak repository terpenuhi
        return data.stream().filter(i -> i.getId() == id).findFirst();
    }

    @Override
    public Item save(String name, int quantity, String category) {
        Item item = new Item(++idCounter, name, quantity, category);
        data.add(item);
        return item;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(i -> i.getId() == id);
    }

    @Override
    public void update(Item item) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == item.getId()) {
                data.set(i, item);
                return;
            }
        }
    }
}
