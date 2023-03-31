package com.order.ordermanagement.controller;

import com.order.ordermanagement.object.Item;
import com.order.ordermanagement.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController extends BaseController {
    private final ItemService itemService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> save(@RequestBody Item item) {
        try {
            Item saved = itemService.save(item);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> findByid(@PathVariable UUID id) {
        try {
            Item item = itemService.findById(id);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            itemService.delete(id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return handleException(e);
        }
    }

}
