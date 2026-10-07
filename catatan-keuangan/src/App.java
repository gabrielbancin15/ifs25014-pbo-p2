import adapter.presenter.FinancePresenter;
import adapter.repository.TransactionRepository;
import domain.repository.ITransactionRepository;
import framework.view.FinanceView;
import usecase.FinanceUseCase;

/** Composition Root aplikasi catatan keuangan. */
public class App {
    public static void main(String[] args) {
        ITransactionRepository repository = new TransactionRepository();
        FinanceUseCase useCase = new FinanceUseCase(repository);
        FinancePresenter presenter = new FinancePresenter();
        FinanceView view = new FinanceView(useCase, presenter);
        try {
            view.show();
        } catch (framework.util.EndOfInputException e) {
            System.out.println("\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nTerjadi kesalahan yang tidak terduga: " + e.getMessage());
        }
    }
}