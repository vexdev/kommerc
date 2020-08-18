package com.vexdev.service

import com.vexdev.model.store.BulkDiscount
import com.vexdev.model.store.Discount
import com.vexdev.model.store.Product
import com.vexdev.repository.CatalogueRepository
import java.math.BigDecimal

class CheckoutService(private val catalogueRepository: CatalogueRepository) {

    @OptIn(ExperimentalStdlibApi::class)
    fun checkout(skus: List<Int>): BigDecimal {
        val prodCatalogue = catalogueRepository.getCatalogue()
        val total = getTotalBeforeReductions(skus, prodCatalogue)
        val discounts = resolveDiscounts(skus, prodCatalogue)
        val reduction = getTotalReduction(skus, discounts, prodCatalogue)
        return total + reduction
    }

    @ExperimentalStdlibApi
    private fun getTotalBeforeReductions(
        skus: List<Int>,
        prodCatalogue: Map<Int, Product>
    ): BigDecimal = skus
        .map { prodCatalogue.getSku(it).price }
        .reduceOrNull { a, b -> a + b }
        ?: BigDecimal.ZERO

    private fun resolveDiscounts(
        skus: List<Int>,
        prodCatalogue: Map<Int, Product>
    ): Map<Int, List<Discount>> = skus
        .asSequence()
        .distinct()
        .map { it to prodCatalogue.getSku(it).discounts }
        .toMap()

    @ExperimentalStdlibApi
    private fun getTotalReduction(
        skus: List<Int>,
        discounts: Map<Int, List<Discount>>,
        prodCatalogue: Map<Int, Product>
    ): BigDecimal = discounts.entries
        .flatMap { (sku, discounts) -> discounts.map { getReduction(sku, skus, it, prodCatalogue) } }
        .reduceOrNull { a, b -> a + b }
        ?: BigDecimal.ZERO

    /**
     * Given a discount, applies it to the given list of products, returning the final reduction.
     */
    private fun getReduction(
        sku: Int,
        skus: List<Int>,
        discount: Discount,
        prodCatalogue: Map<Int, Product>
    ): BigDecimal =
        // This when is exhaustive because Discount is a sealed class.
        when (discount) {
            is BulkDiscount -> computeBulkDiscount(sku, skus, discount, prodCatalogue.getSku(sku).price)
        }

    private fun computeBulkDiscount(
        sku: Int,
        skus: List<Int>,
        discount: BulkDiscount,
        itemPrice: BigDecimal
    ): BigDecimal {
        val bulkElements = skus.count { it == sku }
        val discountApplications = bulkElements / discount.quantity
        val removedPrice = (itemPrice * BigDecimal(discountApplications) * BigDecimal(discount.quantity)).negate()
        val newPrice = BigDecimal(discountApplications) * discount.amount
        return removedPrice + newPrice
    }

    private fun Map<Int, Product>.getSku(sku: Int) = this[sku] ?: throw MissingProductException(sku)


    class MissingProductException(sku: Int) :
        Exception("Missing product with sku $sku")

}