package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

public class ActivityView {
    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase useCase, ActivityPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showActivities(useCase.getAllActivities());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addActivity();
                    case "2" -> updateActivity();
                    case "3" -> searchActivity();
                    case "4" -> sortActivity();
                    case "5" -> removeActivity();
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
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    private void addActivity() {
        System.out.println("[Menambah Kegiatan]");
        String title = InputUtil.input("Judul (x Jika Batal)");
        if (title.equals("x"))
            return;
        if (title.isBlank()) {
            presenter.showError("Judul tidak boleh kosong!");
            return;
        }

        String day = InputUtil.input("Hari (x Jika Batal)");
        if (day.equals("x"))
            return;
        if (day.isBlank()) {
            presenter.showError("Hari tidak boleh kosong!");
            return;
        }

        String time = InputUtil.input("Waktu (x Jika Batal)");
        if (time.equals("x"))
            return;
        if (time.isBlank()) {
            presenter.showError("Waktu tidak boleh kosong!");
            return;
        }

        presenter.showAddSuccess(useCase.addActivity(title, day, time));
    }

    private void updateActivity() {
        System.out.println("[Mengubah Kegiatan]");
        String strId = InputUtil.input("ID Kegiatan yang diubah (x Jika Batal)");
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

        String newTitle = InputUtil.input("Judul Baru (Kosongkan jika tidak ingin mengubah)");
        String newDay = InputUtil.input("Hari Baru (Kosongkan jika tidak ingin mengubah)");
        String newTime = InputUtil.input("Waktu Baru (Kosongkan jika tidak ingin mengubah)");

        // null berarti field tidak diubah; string kosong/blank dilewati
        String title = newTitle.isBlank() ? null : newTitle;
        String day = newDay.isBlank() ? null : newDay;
        String time = newTime.isBlank() ? null : newTime;

        if (useCase.updateActivity(id, title, day, time)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchActivity() {
        System.out.println("[Mencari Kegiatan]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchActivities(keyword), keyword);
        }
    }

    private void sortActivity() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Hari (Senin -> Minggu)");
        System.out.println("2. Waktu (Awal -> Akhir)");
        System.out.println("3. Judul (A-Z)");
        System.out.println("4. Judul (Z-A)");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.equals("x"))
            return;

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedActivities(useCase.sortActivities(option));
    }

    private void removeActivity() {
        System.out.println("[Menghapus Kegiatan]");
        String strId = InputUtil.input("[ID Kegiatan] yang dihapus (x Jika Batal)");
        if (strId.equals("x"))
            return;

        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }

        if (useCase.removeActivity(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.DAY;
            case "2" -> SortOption.TIME;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
    }
}
