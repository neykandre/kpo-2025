package hse.studying.orders.controller;

import hse.studying.orders.domain.Order;
import hse.studying.orders.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService svc;

    @PostMapping
    public ResponseEntity<Order> create(@RequestParam Long userId, @RequestBody Order req) {
        return ResponseEntity.ok(svc.createOrder(userId, req.getAmount(), req.getDescription()));
    }

    @GetMapping
    public List<Order> list(@RequestParam Long userId) {return svc.listOrders(userId);}

    @GetMapping("/{id}")
    public ResponseEntity<Order> one(@RequestParam Long userId, @PathVariable Long id) {
        return svc.getOrder(userId, id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
