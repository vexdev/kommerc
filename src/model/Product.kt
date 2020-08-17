package com.vexdev.model

import java.math.BigDecimal
import java.util.*

data class Product(
    val sku: Int,
    val name: String,
    val price: BigDecimal,
    val currency: Currency,
    val discounts: List<Discount>
)