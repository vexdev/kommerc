package com.vexdev.repository

import com.vexdev.model.store.BulkDiscount
import com.vexdev.model.store.Product
import java.math.BigDecimal

/**
 * Simple mock repository returning the same hardcoded values provided in the given example.
 */
class MockCatalogueRepository : CatalogueRepository {

    override fun getCatalogue(): List<Product> = listOf(
        Product(
            sku = 1,
            name = "Rolex",
            price = BigDecimal(100),
            discounts = listOf(BulkDiscount(3, BigDecimal(200)))
        ),
        Product(
            sku = 2,
            name = "Michael Kors",
            price = BigDecimal(80),
            discounts = listOf(BulkDiscount(2, BigDecimal(120)))
        ),
        Product(
            sku = 3,
            name = "Swatch",
            price = BigDecimal(50),
            discounts = emptyList()
        ),
        Product(
            sku = 4,
            name = "Casio",
            price = BigDecimal(30),
            discounts = emptyList()
        )
    )

}