package kz.iitu.springlab.notify;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("consoleNotifier")
@Order(1)
public class ConsoleNotifier implements Notifier {

    @Override
    public String channel() {
        return "CONSOLE";
    }

    @Override
    public String send(String message) {
        return "Console: " + message;
    }
}
