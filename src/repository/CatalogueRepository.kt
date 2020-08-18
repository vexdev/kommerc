package com.vexdev.repository

import com.vexdev.model.store.Product

/**
 * Provides access to the full catalogue of products in the ecommerce.
 */
interface CatalogueRepository {

    fun getCatalogue(): Map<Int, Product>

}