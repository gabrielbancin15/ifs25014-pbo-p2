package domain.entity;

import java.util.Comparator;

public enum SortOption {
    TITLE_ASC(Comparator.comparing(Todo::getTitle)),
    TITLE_DESC(Comparator.comparing(Todo::getTitle).reversed()),
    // Selesai dahulu: isFinished() true lebih dulu (dibalik karena false < true)
    STATUS_FINISHED_FIRST(Comparator.comparing(Todo::isFinished).reversed()),
    // Belum selesai dahulu: isFinished() false lebih dulu
    STATUS_UNFINISHED_FIRST(Comparator.comparing(Todo::isFinished));

    private final Comparator<Todo> comparator;

    SortOption(Comparator<Todo> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Todo> comparator() {
        return comparator;
    }
}
