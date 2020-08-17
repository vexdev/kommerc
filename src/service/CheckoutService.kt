package com.vexdev.service

import com.vexdev.repository.CatalogueRepository
import java.math.BigDecimal

class CheckoutService(private val catalogueRepository: CatalogueRepository) {

    fun checkout(skus: List<Int>): BigDecimal = TODO()

}