package kz.iitu.springlab.audit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    // Здесь можно подключить репозиторий аудита, если создашь табличку в БД,
    // либо просто выводить в консоль для демонстрации.

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAudit(String message) {
        System.out.println("Audit log saved: " + message);
        // Если потребуется, здесь можно выбросить исключение или сохранить в БД
    }
}