package com.greak.ui.screens.channel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import com.greak.R
import com.greak.data.models.Channel
import com.greak.ui.base.BaseActivity
import com.greak.ui.common.ToolbarInitializer

class ChannelActivity : BaseActivity() {

    private var channel: Channel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment_container_with_toolbar)
        ButterKnife.bind(this)

        initArguments(savedInstanceState)
        initPostFragment()

        ToolbarInitializer.initToolbarWithTitle(this, channel!!.name, true)
    }

    private fun initArguments(savedInstanceState: Bundle?) {
        val extras = savedInstanceState ?: intent.extras
        channel = extras.getParcelable<Channel>(EXTRA_CHANNEL)
    }

    private fun initPostFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, ChannelPageFragment.newInstance(channel))
                .commit()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(EXTRA_CHANNEL, channel)
        super.onSaveInstanceState(outState)
    }

    companion object {

        private val EXTRA_CHANNEL = "channel"

        fun startActivity(context: Context, channel: Channel) {
            val intent = Intent(context, ChannelActivity::class.java)
            intent.putExtra(EXTRA_CHANNEL, channel)
            context.startActivity(intent)
        }
    }
}