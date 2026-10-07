package framework.view;

import adapter.presenter.GuestPresenter;
import framework.util.InputUtil;
import usecase.GuestUseCase;

public class GuestView {
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;

    public GuestView(GuestUseCase useCase, GuestPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showGuests(useCase.getAllGuests());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addGuest();
                    case "2" -> searchGuest();
                    case "3" -> removeGuest();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }
            if (running)
                System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Daftarkan");
        System.out.println("2. Cari");
        System.out.println("3. Hapus");
        System.out.println("x. Keluar");
    }

    private void addGuest() {
        System.out.println("[Mendaftarkan Tamu]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x"))
            return;
        if (name.isBlank()) {
            presenter.showError("Nama tidak boleh kosong!");
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (purpose.equals("x"))
            return;
        if (purpose.isBlank()) {
            presenter.showError("Tujuan kunjungan tidak boleh kosong!");
            return;
        }

        presenter.showAddSuccess(useCase.addGuest(name, purpose));
    }

    private void searchGuest() {
        System.out.println("[Mencari Tamu]");
        String keyword = InputUtil.input("Nama (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchGuests(keyword), keyword);
        }
    }

    private void removeGuest() {
        System.out.println("[Menghapus Tamu]");
        String strId = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");
        if (strId.equals("x"))
            return;

        // Parsing ID dipusatkan di InputUtil untuk menghindari duplikasi
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        if (useCase.removeGuest(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }
}
