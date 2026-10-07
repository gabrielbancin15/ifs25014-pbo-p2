import adapter.presenter.ItemPresenter;
import adapter.repository.ItemRepository;
import domain.repository.IItemRepository;
import framework.view.ItemView;
import usecase.ItemUseCase;

/**
 * Titik masuk aplikasi inventaris barang (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        IItemRepository itemRepository = new ItemRepository();
        ItemUseCase itemUseCase = new ItemUseCase(itemRepository);
        ItemPresenter itemPresenter = new ItemPresenter();
        ItemView itemView = new ItemView(itemUseCase, itemPresenter);

        try {
            itemView.show();
        } catch (framework.util.EndOfInputException e) {
            System.out.println("\nProgram dihentikan.");
        } catch (Exception e) {
            System.out.println("\n[!] Terjadi kesalahan yang tidak terduga: " + e.getMessage());
        }
    }
}
