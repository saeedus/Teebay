/*
 * Created by Saeedus Salehin on 19/7/25, 12:06 PM.
 */

package com.sazim.teebay.products.presentation.navigation

sealed class ProductNavRoutes(val route: String) {
    data object MyProductsScreen : ProductNavRoutes("my_products")
    data object AllProductsScreen : ProductNavRoutes("all_products")
    data object AddProductTitleScreen : ProductNavRoutes("add_product_title")
    data object AddProductCategoryScreen : ProductNavRoutes("add_product_category")
    data object AddProductDescScreen : ProductNavRoutes("add_product_description")
    data object AddProductPhotoUploadScreen : ProductNavRoutes("add_product_photo_upload")
    data object AddProductPriceSelectionScreen : ProductNavRoutes("add_product_price_selection")
    data object AddProductSummaryScreen : ProductNavRoutes("add_product_summary")
    data object EditProductScreen : ProductNavRoutes("edit_product")
    data object MyDealsScreen : ProductNavRoutes("my_deals")
    data object ProductDetailScreen : ProductNavRoutes("product_details")
}