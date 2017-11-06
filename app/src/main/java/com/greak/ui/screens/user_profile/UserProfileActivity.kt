package com.greak.ui.screens.user_profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import com.greak.R
import com.greak.data.models.SteemAccount
import com.greak.ui.base.BaseActivity
import com.greak.ui.common.ToolbarInitializer

class UserProfileActivity : BaseActivity() {

    private var steemAccount: SteemAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment_container_with_toolbar)
        ButterKnife.bind(this)

        initArguments(savedInstanceState)
        initPostFragment()

        ToolbarInitializer.initToolbarWithTitle(this, steemAccount!!.name, true)
    }

    private fun initArguments(savedInstanceState: Bundle?) {
        val extras = savedInstanceState ?: intent.extras
        steemAccount = extras.getParcelable(EXTRA_CHANNEL)
    }

    private fun initPostFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, UserProfilePageFragment.newInstance(steemAccount))
                .commit()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_CHANNEL, steemAccount)
        super.onSaveInstanceState(outState)
    }

    companion object {

        private val EXTRA_CHANNEL = "channel"

        fun startActivity(context: Context, steemAccount: SteemAccount) {
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(EXTRA_CHANNEL, steemAccount)
            context.startActivity(intent)
        }
    }
}