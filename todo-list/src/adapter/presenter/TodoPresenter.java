package adapter.presenter;

import domain.entity.Todo;
import java.util.List;

public class TodoPresenter {
    private String format(Todo t) {
        // Tampilkan status [✓] jika selesai, [ ] jika belum
        String status = t.isFinished() ? "[✓]" : "[ ]";
        return String.format("%d | %s %s", t.getId(), status, t.getTitle());
    }

    private void printList(List<Todo> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            for (Todo t : list) {
                System.out.println(format(t));
            }
        }
    }

    public void showTodos(List<Todo> list) {
        printList(list, "Daftar Todo:", "- Belum ada todo!");
    }

    public void showSearchResults(List<Todo> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Todo tidak ditemukan!");
    }

    public void showSortedTodos(List<Todo> list) {
        printList(list, "Daftar Todo (Terurut):", "- Belum ada todo!");
    }

    public void showAddSuccess(Todo t) {
        System.out.printf("Berhasil menambah todo: %s%n", format(t));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus todo.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus todo dengan ID: %d.%n", id);
    }

    public void showMarkFinishedSuccess() {
        System.out.println("Berhasil menandai todo sebagai selesai.");
    }

    public void showMarkUnfinishedSuccess() {
        System.out.println("Berhasil menandai todo sebagai belum selesai.");
    }

    public void showMarkFailed(int id) {
        System.out.printf("[!] Gagal mengubah/menandai todo dengan ID: %d.%n", id);
    }

    public void showEditSuccess() {
        System.out.println("Berhasil mengubah judul todo.");
    }

    public void showEditFailed(int id) {
        System.out.printf("[!] Gagal mengubah/menandai todo dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan urutan tidak valid!");
    }

    public void showError(String message) {
        System.out.println("[!] " + message);
    }
}
