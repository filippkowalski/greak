package com.greak.ui.screens.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chrono.src.common.utils.VisibilityUtils
import com.greak.R
import com.greak.common.utils.StatsConstants
import com.greak.data.converters.LoginService
import com.greak.data.database.UserInstance
import com.greak.data.database.UserManager
import com.greak.ui.analytics.FabricAnalyticsManager
import com.greak.ui.common.resolvers.OnLoginListener
import com.greak.ui.screens.login.SignInDialogFragment
import com.greak.ui.screens.main.MainActivity
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException
import eu.bittrade.libs.steemj.exceptions.SteemResponseException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), OnLoginListener {
    private var userManager: UserManager? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FabricAnalyticsManager.logEvent(StatsConstants.PROFILE, null)

        userManager = UserManager(context)
        val isUserLoggedIn = UserInstance.getInstance().isLogged

        initProfileData(isUserLoggedIn)
        initFontSize()
        initMyVotesListener()
        initLoginOrLogout(isUserLoggedIn)
    }

    private fun initProfileData(isUserLoggedIn: Boolean) {
        val account = UserInstance.getInstance().account
        if (!isUserLoggedIn) {
            VisibilityUtils.hide(line_profile_notifications, line_profile_my_votes)
        } else if (account != null) {
            text_profile_username.text = account.username
        }
    }

    private fun initFontSize() {
        text_font_size.text = userManager!!.fontSize.toString()
        button_font_size_subtract.setOnClickListener { changeFontSize(false) }
        button_font_size_add.setOnClickListener { changeFontSize(true) }
    }

    private fun initMyVotesListener() {
//        text_my_votes.setOnClickListener {
//            VotesActivity.startActivity(activity)
//        }
    }

    private fun changeFontSize(add: Boolean) {
        val fontSize = getFontSize(add)

        text_font_size.text = fontSize.toString()
        userManager!!.fontSize = fontSize

        FabricAnalyticsManager.logEvent(StatsConstants.CHANGED_FONT_SIZE, fontSize)
    }

    private fun getFontSize(add: Boolean): Int {
        val fontSize = Integer.parseInt(text_font_size.text.toString())
        return if (add && fontSize < 30) {
            fontSize + 1
        } else if (!add && fontSize > 12) {
            fontSize - 1
        } else {
            fontSize
        }
    }

    private fun initLoginOrLogout(isUserLoggedIn: Boolean) {
        text_profile_login_or_logout.text = getLoginOrLogoutText(isUserLoggedIn)
        text_profile_login_or_logout.setOnClickListener(getLoginOrLogoutListener(isUserLoggedIn))
    }

    private fun getLoginOrLogoutText(isUserLoggedIn: Boolean): String {
        return if (isUserLoggedIn) {
            getString(R.string.sign_out)
        } else {
            getString(R.string.sign_in)
        }
    }

    private fun getLoginOrLogoutListener(isUserLoggedIn: Boolean): View.OnClickListener {
        return if (isUserLoggedIn) {
            View.OnClickListener {
                val userManager = UserManager(context)
                userManager.logout()
                restartActivity()
            }
        } else {
            View.OnClickListener {
                SignInDialogFragment.newInstance().show(childFragmentManager, null)
            }
        }
    }

    override fun onUserLogin(username: String, password: String) {
        val service = LoginService(context, username, password)
        try {
            service.data
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { onUserLoggedIn(true) }
                    .onErrorReturn {
                        onUserLoggedIn(false)
                        false
                    }
                    .subscribe()
        } catch (e: SteemResponseException) {
            onUserLoggedIn(false)
        } catch (e: SteemCommunicationException) {
            onUserLoggedIn(false)
        }

        restartActivity()
    }

    private fun restartActivity() {
        activity.finish()
        MainActivity.startActivity(activity)
    }

    private fun onUserLoggedIn(loggedInSuccessfully: Boolean) {
        if (loggedInSuccessfully) {
            Toast.makeText(context, R.string.logged_in_successfully, Toast.LENGTH_SHORT).show()
            restartActivity()
        } else {
            Toast.makeText(context, R.string.failed_to_login, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
