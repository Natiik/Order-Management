package com.order.ordermanagement.controller.model.request;

import java.util.List;

public record OrderRequest(List<ItemCount> items, String comment) {
}
