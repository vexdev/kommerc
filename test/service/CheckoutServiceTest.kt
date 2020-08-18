package service

import com.vexdev.repository.MockCatalogueRepository
import com.vexdev.repository.MockCatalogueRepository.Companion.MK_SKU
import com.vexdev.repository.MockCatalogueRepository.Companion.ROLEX_SKU
import com.vexdev.repository.MockCatalogueRepository.Companion.SWATCH_SKU
import com.vexdev.service.CheckoutService
import java.math.BigDecimal
import kotlin.test.*

class CheckoutServiceTest {

    lateinit var tested: CheckoutService

    @BeforeTest
    fun setup() {
        tested = CheckoutService(MockCatalogueRepository())
    }

    @Test
    fun noProductReturnsZero() {
        val actual = tested.checkout(emptyList())
        assertEquals(BigDecimal.ZERO, actual)
    }

    @Test
    fun simpleSingleProductWorks() {
        val actual = tested.checkout(listOf(SWATCH_SKU))
        assertEquals(BigDecimal(50), actual)
    }

    @Test
    fun multipleSimpleProductsWork() {
        val actual = tested.checkout(listOf(SWATCH_SKU, SWATCH_SKU))
        assertEquals(BigDecimal(100), actual)
    }

    @Test
    fun singleBulkDiscountedElementWorks() {
        val actual = tested.checkout(listOf(ROLEX_SKU))
        assertEquals(BigDecimal(100), actual)
    }

    @Test
    fun bulkDiscountWorks() {
        val actual = tested.checkout(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU))
        assertEquals(BigDecimal(200), actual)
    }

    @Test
    fun bulkDiscountWorksTwice() {
        val actual = tested.checkout(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU))
        assertEquals(BigDecimal(400), actual)
    }

    @Test
    fun bulkDiscountWorksTwicePlusOne() {
        val actual = tested.checkout(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU))
        assertEquals(BigDecimal(500), actual)
    }

    @Test
    fun bulkDiscountWorksMixingDiscounts() {
        val actual = tested.checkout(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, MK_SKU, MK_SKU))
        assertEquals(BigDecimal(320), actual)
    }

    @Test
    fun bigOrderWorks() {
        val actual = tested.checkout(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, MK_SKU, MK_SKU, MK_SKU, SWATCH_SKU))
        assertEquals(BigDecimal(450), actual)
    }

}