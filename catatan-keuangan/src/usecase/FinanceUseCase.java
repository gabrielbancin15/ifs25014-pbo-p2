package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.List;
import java.util.Locale;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction addTransaction(String description, double amount, TransactionType type) {
        return repository.save(description, amount, type);
    }

    public boolean removeTransaction(int id) {
        return repository.deleteById(id);
    }

    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(t -> t.getDescription().toLowerCase(Locale.ROOT).contains(lowerKeyword))
                .toList();
    }

    public List<Transaction> sortTransactions(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    public double getBalance() {
        return repository.findAll().stream()
                .mapToDouble(t -> t.getType() == TransactionType.INCOME ? t.getAmount() : -t.getAmount())
                .sum();
    }
}
