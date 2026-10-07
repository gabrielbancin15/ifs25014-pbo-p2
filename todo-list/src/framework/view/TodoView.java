package framework.view;

import adapter.presenter.TodoPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.TodoUseCase;

public class TodoView {
    private final TodoUseCase useCase;
    private final TodoPresenter presenter;

    public TodoView(TodoUseCase useCase, TodoPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showTodos(useCase.getAllTodos());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addTodo();
                    case "2" -> markFinished();
                    case "3" -> markUnfinished();
                    case "4" -> editTodo();
                    case "5" -> searchTodo();
                    case "6" -> sortTodo();
                    case "7" -> removeTodo();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }
            if (running)
                System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Todo");
        System.out.println("2. Tandai Selesai");
        System.out.println("3. Tandai Belum Selesai");
        System.out.println("4. Ubah Judul");
        System.out.println("5. Cari");
        System.out.println("6. Urutkan");
        System.out.println("7. Hapus");
        System.out.println("x. Keluar");
    }

    private void addTodo() {
        System.out.println("[Tambah Todo]");
        String title = InputUtil.input("Judul (x Jika Batal)");
        if (title.equals("x"))
            return;
        if (title.isBlank()) {
            presenter.showError("Judul tidak boleh kosong!");
            return;
        }
        presenter.showAddSuccess(useCase.addTodo(title));
    }

    private void markFinished() {
        System.out.println("[Tandai Selesai]");
        String strId = InputUtil.input("ID Todo (x Jika Batal)");
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
        if (useCase.markFinished(id)) {
            presenter.showMarkFinishedSuccess();
        } else {
            presenter.showMarkFailed(id);
        }
    }

    private void markUnfinished() {
        System.out.println("[Tandai Belum Selesai]");
        String strId = InputUtil.input("ID Todo (x Jika Batal)");
        if (strId.equals("x"))
            return;
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }
        if (useCase.markUnfinished(id)) {
            presenter.showMarkUnfinishedSuccess();
        } else {
            presenter.showMarkFailed(id);
        }
    }

    private void editTodo() {
        System.out.println("[Ubah Judul]");
        String strId = InputUtil.input("ID Todo (x Jika Batal)");
        if (strId.equals("x"))
            return;
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }
        String newTitle = InputUtil.input("Judul Baru (x Jika Batal)");
        if (newTitle.equals("x"))
            return;
        if (newTitle.isBlank()) {
            presenter.showError("Judul tidak boleh kosong!");
            return;
        }
        if (useCase.editTitle(id, newTitle)) {
            presenter.showEditSuccess();
        } else {
            presenter.showEditFailed(id);
        }
    }

    private void searchTodo() {
        System.out.println("[Cari Todo]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchTodos(keyword), keyword);
        }
    }

    private void sortTodo() {
        System.out.println("[Urutkan Todo]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Judul (A-Z)");
        System.out.println("2. Judul (Z-A)");
        System.out.println("3. Selesai Dulu");
        System.out.println("4. Belum Selesai Dulu");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.equals("x"))
            return;
        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }
        presenter.showSortedTodos(useCase.sortTodos(option));
    }

    private void removeTodo() {
        System.out.println("[Hapus Todo]");
        String strId = InputUtil.input("ID Todo (x Jika Batal)");
        if (strId.equals("x"))
            return;
        int id;
        try {
            id = Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showError("ID tidak valid!");
            return;
        }
        if (useCase.removeTodo(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.TITLE_ASC;
            case "2" -> SortOption.TITLE_DESC;
            case "3" -> SortOption.STATUS_FINISHED_FIRST;
            case "4" -> SortOption.STATUS_UNFINISHED_FIRST;
            default -> null;
        };
    }
}
