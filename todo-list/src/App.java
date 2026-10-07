import adapter.presenter.TodoPresenter;
import adapter.repository.TodoRepository;
import domain.repository.ITodoRepository;
import framework.view.TodoView;
import usecase.TodoUseCase;

public class App {
    public static void main(String[] args) {
        ITodoRepository repository = new TodoRepository();
        TodoUseCase useCase = new TodoUseCase(repository);
        TodoPresenter presenter = new TodoPresenter();
        TodoView view = new TodoView(useCase, presenter);
        try {
            view.show();
        } catch (framework.util.EndOfInputException e) {
            System.out.println("\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nTerjadi kesalahan yang tidak terduga: " + e.getMessage());
        }
    }
}
