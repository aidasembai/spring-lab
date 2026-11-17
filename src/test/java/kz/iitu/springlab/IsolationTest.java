package kz.iitu.springlab;

import kz.iitu.springlab.entity.Account;
import kz.iitu.springlab.repository.AccountRepository;
import kz.iitu.springlab.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class IsolationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    void testReadCommittedBehavior() {
        // Создаем тестовый аккаунт с начальным балансом 1000
        Account account = new Account();
        account.setOwnerName("Test User");
        account.setBalance(BigDecimal.valueOf(1000));
        account = accountRepository.save(account);
        Long accountId = account.getId();

        // Запускаем транзакцию с READ_COMMITTED
        executeReadCommitted(accountId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public void executeReadCommitted(Long accountId) {
        // 1. Первое чтение
        Account firstRead = accountRepository.findById(accountId).orElseThrow();
        System.out.println(">>> [READ_COMMITTED] 1-е чтение баланса: " + firstRead.getBalance());

        // Изменяем баланс из другой условной транзакции прямо во время работы
        updateAccountDirectly(accountId, BigDecimal.valueOf(5000));

        // 2. Второе чтение в рамках той же транзакции
        Account secondRead = accountRepository.findById(accountId).orElseThrow();
        System.out.println(">>> [READ_COMMITTED] 2-е чтение баланса: " + secondRead.getBalance());

        // При READ_COMMITTED значение изменится (Non-Repeatable Read)
    }

    // Вспомогательный метод для симуляции параллельного изменения
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateAccountDirectly(Long accountId, BigDecimal newBalance) {
        Account acc = accountRepository.findById(accountId).orElseThrow();
        acc.setBalance(newBalance);
        accountRepository.save(acc);
        System.out.println(">>> [PARALLEL UPDATE] Баланс обновлен на: " + newBalance);
    }
}
