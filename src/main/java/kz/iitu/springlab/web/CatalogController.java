package kz.iitu.springlab.web;

import kz.iitu.springlab.service.CatalogService;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Task 4.1: Наблюдение за прокси-объектом
    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
                "className",   catalogService.getClass().getName(),
                "superClass",  catalogService.getClass().getSuperclass().getSimpleName(),
                "isAopProxy", String.valueOf(AopUtils.isAopProxy(catalogService)),
                "isCglib",    String.valueOf(AopUtils.isCglibProxy(catalogService))
        );
    }

    // Task 4.2: Вызов сервисного метода с самовызовом findAll()
    @GetMapping("/process-all")
    public List<String> processAll(@RequestParam(defaultValue = "5") int limit) {
        return catalogService.processAllItems(limit);
    }

    // Task 4.3: Вызов сервисного метода с самовызовом remove()
    @DeleteMapping("/item-twice/{id}")
    public String removeTwiceItem(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/items")
    public List<String> getItems(@RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    @DeleteMapping("/item/{id}")
    public String removeItem(@PathVariable long id) {
        return catalogService.remove(id);
    }
}