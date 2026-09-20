package kz.iitu.springlab.web;

import kz.iitu.springlab.lifecycle.LifecycleDemo;
import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/lab02")
public class Lab02Controller {

    private final NotificationService notificationService;
    private final LifecycleDemo lifecycleDemo;
    private final TicketOffice ticketOffice;

    public Lab02Controller(NotificationService notificationService,
                           LifecycleDemo lifecycleDemo,
                           TicketOffice ticketOffice) {
        this.notificationService = notificationService;
        this.lifecycleDemo = lifecycleDemo;
        this.ticketOffice = ticketOffice;
    }

    @GetMapping("/notify/primary")
    public String notifyPrimary(@RequestParam(defaultValue = "Hello") String msg) {
        return notificationService.viaPrimary(msg);
    }

    @GetMapping("/notify/console")
    public String notifyConsole(@RequestParam(defaultValue = "Hello") String msg) {
        return notificationService.viaConsole(msg);
    }

    @GetMapping("/notify/all")
    public List<String> notifyAll(@RequestParam(defaultValue = "Hello") String msg) {
        return notificationService.viaAll(msg);
    }

    @GetMapping("/notify/names")
    public Set<String> notifyNames() {
        return notificationService.names();
    }

    @GetMapping("/lifecycle")
    public List<String> lifecycle() {
        return lifecycleDemo.events();
    }

    @GetMapping("/scope")
    public Map<String, Object> scope() {
        return ticketOffice.demo();
    }
}