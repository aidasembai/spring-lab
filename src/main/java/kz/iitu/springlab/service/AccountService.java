package kz.iitu.springlab.service;



import kz.iitu.springlab.audit.AuditService;

import kz.iitu.springlab.entity.Account;

import kz.iitu.springlab.repository.AccountRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



import java.math.BigDecimal;



@Service

public class AccountService {



    private final AccountRepository accountRepository;

    private final AuditService auditService; // внедряем сервис аудита



    public AccountService(AccountRepository accountRepository, AuditService auditService) {

        this.accountRepository = accountRepository;

        this.auditService = auditService;

    }



    @Transactional

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Account from = accountRepository.findById(fromId)

                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account to = accountRepository.findById(toId)

                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));



        from.debit(amount);

        to.credit(amount);



        accountRepository.save(from);

        accountRepository.save(to);



// Вызываем метод аудита

        auditService.logAudit("Transferred " + amount + " from " + from.getOwnerName() + " to " + to.getOwnerName());



// Для проверки отката по заданию (Task B):

// принудительно вызовем ошибку после успешного выполнения auditService

// if (true) { throw new RuntimeException("Force rollback after audit"); }

    }

}