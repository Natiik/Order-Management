package com.order.ordermanagement.controller;

import com.order.ordermanagement.controller.model.request.OrderRequest;
import com.order.ordermanagement.object.Order;
import com.order.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController extends BaseController {
    private final OrderService orderService;

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> create(@RequestBody OrderRequest request) {
        try {
            Order order = orderService.create(getCurrentUser(), request.items(), request.comment());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> edit(@PathVariable UUID orderId, @RequestBody OrderRequest request) {
        try {
            Order order = orderService.editOrder(getCurrentUser(), orderId, request.items(), request.comment());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','USER' )")
    public ResponseEntity<?> findByid(@PathVariable UUID id) {
        try {
            Order order = orderService.findById(getCurrentUser(), id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGER','USER' )")
    public ResponseEntity<?> findByAll() {
        try {
            List<Order> all = orderService.findAll(getCurrentUser());
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            return handleException(e);
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            orderService.delete(getCurrentUser(), id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return handleException(e);
        }
    }

}
