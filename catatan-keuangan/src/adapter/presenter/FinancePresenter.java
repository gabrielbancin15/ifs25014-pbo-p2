
package adapter.presenter;
 
import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;
 
public class FinancePresenter {
    private String format(Transaction t) {
        String typeStr = t.getType() == TransactionType.INCOME ? "Pemasukan" : "Pengeluaran";
        return String.format("%d | %s | Rp %.0f | %s", t.getId(), t.getDescription(), t.getAmount(), typeStr);
    }
 
    private void printBalance(double balance) {
        System.out.printf("Saldo: Rp %.0f%n", balance);
    }
 
    private void printList(List<Transaction> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            for (Transaction t : list) {
                System.out.println(format(t));
            }
        }
    }
 
    public void showTransactions(List<Transaction> list, double balance) {
        printList(list, "Daftar Transaksi:", "- Belum ada transaksi!");
        printBalance(balance);
    }
 
    public void showSearchResults(List<Transaction> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Transaksi tidak ditemukan!");
    }
 
    public void showSortedTransactions(List<Transaction> list) {
        printList(list, "Daftar Transaksi (Terurut):", "- Belum ada transaksi!");
    }
 
    public void showBalance(double balance) {
        System.out.printf("Saldo saat ini: Rp %.0f%n", balance);
    }
 
    public void showAddSuccess(Transaction t) {
        System.out.printf("Berhasil menambah transaksi: %s%n", format(t));
    }
 
    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }
 
    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus transaksi dengan ID: %d.%n", id);
    }
 
    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }
<<<<<<< HEAD
 
    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }
 
    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }
 
    public void showInvalidAmount() {
        System.out.println("[!] Jumlah tidak valid!");
=======

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showCurrentBalance(double balance) {
        printBalance(balance);
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
>>>>>>> 25ed260698a420d91dda95444012d41bf4a61474
    }
}
