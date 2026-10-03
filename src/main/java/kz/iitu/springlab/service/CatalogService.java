package kz.iitu.springlab.service;

import kz.iitu.springlab.annotation.RequiresRole;
import kz.iitu.springlab.audit.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    @Lazy
    private CatalogService self; // Self-reference injection to go through the proxy

    public String findById(long id) {
        return "Item no. " + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        return List.of("Item A", "Item B", "Item C").subList(0, Math.min(limit, 3));
    }

    // ИНДИВИДУАЛЬНОЕ ЗАДАНИЕ (Вариант 6): Защита метода роли ADMIN
    @RequiresRole("ADMIN")
    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        return "Removed item " + id;
    }

    public List<String> processAllItems(int limit) {
        return this.findAll(limit);
    }

    // FIXED: Using 'self' proxy reference instead of 'this'
    public String removeTwice(long id) {
        String first  = self.remove(id);      // Goes through proxy -> triggers audit aspect & role check
        String second = self.remove(id + 1);  // Goes through proxy -> triggers audit aspect & role check
        return first + "; " + second;
    }
}