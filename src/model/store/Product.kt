package com.vexdev.model.store

import java.math.BigDecimal

data class Product(
    val sku: Int,
    val name: String,
    val price: BigDecimal,
    val discounts: List<Discount>
)