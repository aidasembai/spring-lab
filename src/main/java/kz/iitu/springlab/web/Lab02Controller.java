package kz.iitu.springlab.web;

import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab02Controller {

    private final NotificationService notificationService;
    private final TicketOffice ticketOffice;

    public Lab02Controller(NotificationService notificationService, TicketOffice ticketOffice) {
        this.notificationService = notificationService;
        this.ticketOffice = ticketOffice;
    }

    @GetMapping("/notify")
    public String notifyPrimary(@RequestParam(defaultValue = "hello") String msg) {
        return notificationService.sendPrimary(msg);
    }

    @GetMapping("/notify/all")
    public List<String> notifyAllNotifiers(@RequestParam(defaultValue = "hello") String msg) {
        return notificationService.sendAll(msg);
    }

    @GetMapping("/scopes")
    public Map<String, Object> getScopesDemo() {
        return ticketOffice.demo();
    }
}