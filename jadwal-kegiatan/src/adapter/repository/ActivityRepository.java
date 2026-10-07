package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityRepository implements IActivityRepository {
    // Penyimpanan data kegiatan secara in-memory
    private final List<Activity> data = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int idCounter = 0;

    @Override
    public List<Activity> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Activity> findById(int id) {
        // Mengembalikan referensi langsung ke objek dalam list;
        // mutasi harus selalu diikuti pemanggilan update() agar kontrak repository terpenuhi
        return data.stream().filter(a -> a.getId() == id).findFirst();
    }

    @Override
    public Activity save(String title, String day, String time) {
        Activity activity = new Activity(++idCounter, title, day, time);
        data.add(activity);
        return activity;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(a -> a.getId() == id);
    }

    @Override
    public void update(Activity activity) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == activity.getId()) {
                data.set(i, activity);
                return;
            }
        }
    }
}
