package com.vexdev.model

import java.math.BigDecimal

sealed class Discount

/**
 * This [Discount] is applied when a user buys a given [quantity] of products or a multiple of that.
 * It works by taking the total value of the products to which it's applied and replacing it with the given amount.
 */
data class BulkDiscount(
    val quantity: Int,
    /**
     * Note: As a [Discount] is a property of a [Product] which already defines the currency, we're going to
     * simply define an amount here. However this approach will not scale in case we will decide to open the ecommerce
     * to multiple currencies, in that case a different system must be provided, E.G. by defining a map of amounts by
     * currencies or by using a formula to compute the actual discount.
     */
    val amount: BigDecimal
) : Discount()