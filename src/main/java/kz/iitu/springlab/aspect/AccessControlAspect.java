package kz.iitu.springlab.aspect;

import kz.iitu.springlab.annotation.RequiresRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AccessControlAspect {

    @Before("@annotation(requiresRole)")
    public void checkAccess(JoinPoint joinPoint, RequiresRole requiresRole) {
        String requiredRole = requiresRole.value();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new SecurityException("No HTTP request found in current context");
        }

        HttpServletRequest request = attributes.getRequest();
        String userRole = request.getHeader("X-User-Role");

        if (userRole == null || !userRole.equalsIgnoreCase(requiredRole)) {
            throw new SecurityException("Access Denied: Required role '" + requiredRole + "', but got '" + userRole + "'");
        }
    }
}