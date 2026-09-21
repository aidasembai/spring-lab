package kz.iitu.springlab.notify;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final Notifier primaryNotifier;
    private final Notifier consoleNotifier;
    private final List<Notifier> allNotifiersList;
    private final Map<String, Notifier> allNotifiersMap;

    public NotificationService(
            Notifier primaryNotifier,
            @Qualifier("consoleNotifier") Notifier consoleNotifier,
            List<Notifier> allNotifiersList,
            Map<String, Notifier> allNotifiersMap
    ) {
        this.primaryNotifier = primaryNotifier;
        this.consoleNotifier = consoleNotifier;
        this.allNotifiersList = allNotifiersList;
        this.allNotifiersMap = allNotifiersMap;
    }

    public String sendPrimary(String message) {
        return primaryNotifier.send(message);
    }

    public String sendConsole(String message) {
        return consoleNotifier.send(message);
    }

    public List<String> sendAll(String message) {
        return allNotifiersList.stream()
                .map(n -> n.send(message))
                .toList();
    }

    public Map<String, Notifier> getAllNotifiersMap() {
        return allNotifiersMap;
    }
}