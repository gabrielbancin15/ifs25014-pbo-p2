package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ItemUseCase;

public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showItems(useCase.getAllItems());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addItem();
                    case "2" -> updateItem();
                    case "3" -> searchItem();
                    case "4" -> sortItem();
                    case "5" -> removeItem();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }
            if (running)
                System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah Stok");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    private void addItem() {
        System.out.println("[Menambah Barang]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x"))
            return;
        if (name.isBlank()) {
            presenter.showError("Nama barang tidak boleh kosong!");
            return;
        }

        String strQuantity = InputUtil.input("Jumlah");
        if (strQuantity.equals("x"))
            return;

        // Parsing jumlah dipusatkan di InputUtil
        int quantity;
        try {
            quantity = Integer.parseInt(strQuantity.trim());
            if (quantity <= 0) {
                presenter.showError("Jumlah stok tidak valid!");
                return;
            }
        } catch (NumberFormatException e) {
            presenter.showError("Jumlah stok tidak valid!");
            return;
        }

        String category = InputUtil.input("Kategori (x Jika Batal)");
        if (category.equals("x"))
            return;
        if (category.isBlank()) {
            presenter.showError("Kategori tidak boleh kosong!");
            return;
        }

        presenter.showAddSuccess(useCase.addItem(name, quantity, category));
    }

    private void updateItem() {
        System.out.println("[Mengubah Stok]");
        String strId = InputUtil.input("ID Barang yang diubah (x Jika Batal)");
        if (strId.equals("x"))
            return;

        // Parsing ID dipusatkan di InputUtil
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        String strQuantity = InputUtil.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)");
        Integer quantity = null;
        if (!strQuantity.isBlank()) {
            try {
                quantity = Integer.parseInt(strQuantity.trim());
                if (quantity <= 0) {
                    presenter.showError("Jumlah stok tidak valid!");
                    return;
                }
            } catch (NumberFormatException e) {
                presenter.showError("Jumlah stok tidak valid!");
                return;
            }
        }

        if (useCase.updateItem(id, quantity)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchItem() {
        System.out.println("[Mencari Barang]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchItems(keyword), keyword);
        }
    }

    private void sortItem() {
        System.out.println("[Mengurutkan Barang]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah (Terkecil -> Terbesar)");
        System.out.println("4. Jumlah (Terbesar -> Terkecil)");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.equals("x"))
            return;

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedItems(useCase.sortItems(option));
    }

    private void removeItem() {
        System.out.println("[Menghapus Barang]");
        String strId = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");
        if (strId.equals("x"))
            return;

        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        if (useCase.removeItem(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> null;
        };
    }
}
