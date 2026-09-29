package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1) // Срабатывает первым в цепочке
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // Аннотация @annotation(audited) позволяет перехватывать методы с @Audited
    // и сразу получать доступ к объекту аннотации
    @Before("@annotation(audited)")
    public void auditMethod(JoinPoint jp, Audited audited) {
        String action = audited.action();

        if (audited.logArguments()) {
            log.info("[AUDIT] Action: {} | Method: {} | Args: {}",
                    action,
                    jp.getSignature().toShortString(),
                    Arrays.toString(jp.getArgs()));
        } else {
            log.info("[AUDIT] Action: {} | Method: {}",
                    action,
                    jp.getSignature().toShortString());
        }
    }
}