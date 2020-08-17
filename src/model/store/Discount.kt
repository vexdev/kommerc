package com.vexdev.model.store

import java.math.BigDecimal

sealed class Discount

/**
 * This [Discount] is applied when a user buys a given [quantity] of products or a multiple of that.
 * It works by taking the total value of the products to which it's applied and replacing it with the given amount.
 */
data class BulkDiscount(
    val quantity: Int,
    val amount: BigDecimal
) : Discount()