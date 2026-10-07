package usecase;

import domain.entity.SortOption;
import domain.entity.Todo;
import domain.repository.ITodoRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TodoUseCase {
    private final ITodoRepository repository;

    public TodoUseCase(ITodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    public Todo addTodo(String title) {
        return repository.save(title);
    }

    public boolean removeTodo(int id) {
        return repository.deleteById(id);
    }

    public boolean markFinished(int id) {
        Optional<Todo> found = repository.findById(id);
        if (found.isEmpty()) {
            return false;
        }
        Todo todo = found.get();
        todo.markFinished();
        repository.update(todo);
        return true;
    }

    public boolean markUnfinished(int id) {
        Optional<Todo> found = repository.findById(id);
        if (found.isEmpty()) {
            return false;
        }
        Todo todo = found.get();
        todo.markUnfinished();
        repository.update(todo);
        return true;
    }

    public boolean editTitle(int id, String newTitle) {
        Optional<Todo> found = repository.findById(id);
        if (found.isEmpty()) {
            return false;
        }
        Todo todo = found.get();
        todo.changeTitle(newTitle);
        repository.update(todo);
        return true;
    }

    public List<Todo> searchTodos(String keyword) {
        String lowerKeyword = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(t -> t.getTitle().toLowerCase(Locale.ROOT).contains(lowerKeyword))
                .toList();
    }

    public List<Todo> sortTodos(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}
