package adapter.repository;

import domain.entity.Todo;
import domain.repository.ITodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoRepository implements ITodoRepository {
    // Penyimpanan data todo secara in-memory
    private final List<Todo> todos = new ArrayList<>();
    // ID auto-increment dimulai dari 1
    private int nextId = 1;

    @Override
    public List<Todo> findAll() {
        // Kembalikan salinan defensif agar caller tidak dapat memodifikasi list internal
        return new ArrayList<>(todos);
    }

    @Override
    public Optional<Todo> findById(int id) {
        // Cari elemen berdasarkan ID; findFirst() mengembalikan referensi asli
        // yang aman untuk dimutasi lalu dikirim kembali melalui update()
        return todos.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override
    public Todo save(String title) {
        Todo todo = new Todo(nextId++, title);
        todos.add(todo);
        return todo;
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(t -> t.getId() == id);
    }

    @Override
    public void update(Todo todo) {
        // Timpa elemen lama dengan objek yang telah diperbarui berdasarkan kecocokan ID
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todo.getId()) {
                todos.set(i, todo);
                return;
            }
        }
    }
}
