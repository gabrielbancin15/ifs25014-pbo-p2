package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements ITransactionRepository {
    // Penyimpanan data transaksi secara in-memory
    private final List<Transaction> data = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int idCounter = 0;

    @Override
    public List<Transaction> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Transaction> findById(int id) {
        // Mengembalikan referensi langsung ke objek dalam list;
        // mutasi harus selalu diikuti pemanggilan update() agar kontrak repository terpenuhi
        return data.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override
    public Transaction save(String description, double amount, TransactionType type) {
        Transaction t = new Transaction(++idCounter, description, amount, type);
        data.add(t);
        return t;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(t -> t.getId() == id);
    }

    @Override
    public void update(Transaction transaction) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == transaction.getId()) {
                data.set(i, transaction);
                return;
            }
        }
    }
}
