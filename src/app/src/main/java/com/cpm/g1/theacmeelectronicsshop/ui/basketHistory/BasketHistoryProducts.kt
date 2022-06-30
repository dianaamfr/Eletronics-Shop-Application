package com.cpm.g1.theacmeelectronicsshop.ui.basketHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cpm.g1.theacmeelectronicsshop.utils.ConfigHTTP
import com.cpm.g1.theacmeelectronicsshop.R
import com.cpm.g1.theacmeelectronicsshop.dataClasses.basket.Basket
import com.cpm.g1.theacmeelectronicsshop.httpService.GetProductsList

const val LIST_ADDRESS: String = "http://${ConfigHTTP.BASE_ADDRESS}:3000/api/products/list?"

class BasketHistoryProducts : Fragment() {
    private lateinit var productTransActivity: ProductTransactionActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productTransActivity = activity as ProductTransactionActivity
        productTransActivity.basket = (arguments?.getSerializable("basket") as Basket?)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_basket_history_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val productsList = view.findViewById<ListView>(R.id.basket_sv)
        productsList.adapter = (activity as ProductTransactionActivity).adapterProducts
        val requestURI = buildProductListURI()
        val mainActivity = activity as ProductTransactionActivity
        Thread(GetProductsList(mainActivity, requestURI)).start()
        setTotal(view)
    }

    /**
     * Set the total value of the purchase in the view.
     */
    private fun setTotal(view: View){
        view.findViewById<TextView>(R.id.total).text = (activity as ProductTransactionActivity).basket.total.toString() + " €"
    }

    /**
     * Function that adds the products as queries to the request of a list of
     * products.
     */
    private fun buildProductListURI(): String{
        var uri = LIST_ADDRESS

        for (product in productTransActivity.basket.products){
            uri += "prod=" + product.id + "&"
        }
        return uri
    }


    companion object {
        @JvmStatic
        fun newInstance(basket: Basket) =
            BasketHistoryProducts().apply {
                arguments = Bundle().apply {
                    putSerializable("basket", basket)
                }
            }

        fun newInstance(bundle: Bundle): BasketHistoryProducts{
            val basket = bundle.getSerializable("basket") as Basket
            return newInstance(basket)
        }
    }

}
