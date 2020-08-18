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
            sku = ROLEX_SKU,
            name = "Rolex",
            price = BigDecimal(100),
            discounts = listOf(BulkDiscount(3, BigDecimal(200)))
        ),
        Product(
            sku = MK_SKU,
            name = "Michael Kors",
            price = BigDecimal(80),
            discounts = listOf(BulkDiscount(2, BigDecimal(120)))
        ),
        Product(
            sku = SWATCH_SKU,
            name = "Swatch",
            price = BigDecimal(50),
            discounts = emptyList()
        ),
        Product(
            sku = CASIO_SKU,
            name = "Casio",
            price = BigDecimal(30),
            discounts = emptyList()
        )
    )

    companion object {
        const val ROLEX_SKU = 1
        const val MK_SKU = 2
        const val SWATCH_SKU = 3
        const val CASIO_SKU = 4
    }

}