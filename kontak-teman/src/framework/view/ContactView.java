package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ContactUseCase;

public class ContactView {
    private final ContactUseCase useCase;
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase useCase, ContactPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showContacts(useCase.getAllContacts());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addContact();
                    case "2" -> updateContact();
                    case "3" -> searchContact();
                    case "4" -> sortContact();
                    case "5" -> removeContact();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }
            if (running) System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    private void addContact() {
        System.out.println("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) return;
        if (name.isBlank()) {
            presenter.showError("Nama tidak boleh kosong!");
            return;
        }

        String phone = InputUtil.input("Telepon (x Jika Batal)");
        if (phone.equals("x")) return;
        if (phone.isBlank()) {
            presenter.showError("Nomor telepon tidak boleh kosong!");
            return;
        }

        String email = InputUtil.input("Email (x Jika Batal)");
        if (email.equals("x")) return;
        if (email.isBlank()) {
            presenter.showError("Email tidak boleh kosong!");
            return;
        }

        presenter.showAddSuccess(useCase.addContact(name, phone, email));
    }

    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        String strId = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
        if (strId.equals("x")) return;

        // Parsing ID dipusatkan di InputUtil untuk menghindari duplikasi
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        String newName = InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)");
        String newPhone = InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)");
        String newEmail = InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)");

        // null berarti field tidak diubah; string kosong/blank dilewati
        String name = newName.isBlank() ? null : newName;
        String phone = newPhone.isBlank() ? null : newPhone;
        String email = newEmail.isBlank() ? null : newEmail;

        if (useCase.updateContact(id, name, phone, email)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchContact() {
        System.out.println("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchContacts(keyword), keyword);
        }
    }

    private void sortContact() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.equals("x")) return;

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedContacts(useCase.sortContacts(option));
    }

    private void removeContact() {
        System.out.println("[Menghapus Kontak]");
        String strId = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (strId.equals("x")) return;

        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        if (useCase.removeContact(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            default -> null;
        };
    }
}
