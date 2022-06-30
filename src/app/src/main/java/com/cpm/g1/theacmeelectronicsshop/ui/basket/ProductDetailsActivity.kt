package com.cpm.g1.theacmeelectronicsshop.ui.basket

import android.os.Bundle
import android.view.MenuItem
import com.cpm.g1.theacmeelectronicsshop.ui.auth.AuthenticatedUserActivity

class ProductDetailsActivity: AuthenticatedUserActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val details = ProductDetailsFragment.newInstance(intent.extras!!)
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, details)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}