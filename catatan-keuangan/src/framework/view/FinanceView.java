package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showTransactions(useCase.getAllTransactions(), useCase.getBalance());
            printMenu();
            String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addTransaction(TransactionType.INCOME);
                    case "2" -> addTransaction(TransactionType.EXPENSE);
                    case "3" -> searchTransaction();
                    case "4" -> sortTransaction();
                    case "5" -> showBalance();
                    case "6" -> removeTransaction();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }
            if (running)
                System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    private void addTransaction(TransactionType type) {
        System.out.println(type == TransactionType.INCOME ? "[Tambah Pemasukan]" : "[Tambah Pengeluaran]");
        String description = InputUtil.input("Keterangan (x Jika Batal)");
        if (description.equals("x"))
            return;
        if (description.isBlank()) {
            presenter.showError("Keterangan tidak boleh kosong!");
            return;
        }

        String strAmount = InputUtil.input("Jumlah");
        if (strAmount.equals("x"))
            return;

        // Parsing jumlah dipusatkan di InputUtil
        double amount;
        try {
            amount = Double.parseDouble(strAmount.trim());
            if (amount <= 0) {
                presenter.showError("Jumlah tidak valid!");
                return;
            }
        } catch (NumberFormatException e) {
            presenter.showError("Jumlah tidak valid!");
            return;
        }

        presenter.showAddSuccess(useCase.addTransaction(description, amount, type));
    }

    private void searchTransaction() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchTransactions(keyword), keyword);
        }
    }

    private void sortTransaction() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.equals("x"))
            return;

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedTransactions(useCase.sortTransactions(option));
    }

    private void showBalance() {
<<<<<<< HEAD
        presenter.showBalance(useCase.getBalance());
=======
        presenter.showCurrentBalance(useCase.getBalance());
>>>>>>> 25ed260698a420d91dda95444012d41bf4a61474
    }

    private void removeTransaction() {
        System.out.println("[Hapus Transaksi]");
        String strId = InputUtil.input("ID Transaksi (x Jika Batal)");
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

        if (useCase.removeTransaction(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.AMOUNT_ASC;
            case "2" -> SortOption.AMOUNT_DESC;
            case "3" -> SortOption.INCOME_FIRST;
            case "4" -> SortOption.EXPENSE_FIRST;
            default -> null;
        };
    }
}
