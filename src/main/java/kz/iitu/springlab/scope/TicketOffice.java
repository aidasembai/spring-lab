package kz.iitu.springlab.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TicketOffice {

    private final Ticket injectedDirectly;
    private final ObjectProvider<Ticket> prototypeTicketProvider;

    public TicketOffice(Ticket injectedDirectly, ObjectProvider<Ticket> prototypeTicketProvider) {
        this.injectedDirectly = injectedDirectly;
        this.prototypeTicketProvider = prototypeTicketProvider;
    }

    public Map<String, Object> demo() {
        return Map.of(
                "injectedDirectly", injectedDirectly.getId(),
                "viaProvider", Map.of(
                        "call1", prototypeTicketProvider.getObject().getId(),
                        "call2", prototypeTicketProvider.getObject().getId()
                )
        );
    }
}
