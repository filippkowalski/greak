package com.greak.data.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.greak.ui.screens.post.PostHtmlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hugo.weaving.DebugLog;

/**
 * Created by Filip Kowalski on 25.05.17.
 */
public class UserActionsPreferences {

	private static final String PREFERENCE_KEY = ".user_actions";
	private static final String KEY_SYNCHRONIZATION_TIME = "synchronization_time";
	private static final String KEY_HTML_STYLING = "html_styling";
	private static final String KEY_FOLLOWED_CHANNELS = "followed_channels";
	private static final String KEY_VOTES = "VOTES";

	public static void setSynchronizationTime(Context context, long synchronizationTime) {
		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putLong(KEY_SYNCHRONIZATION_TIME, synchronizationTime);
		preferencesEditor.apply();
	}

	public static void setHtmlStyling(Context context, String htmlStyling) {
		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putString(KEY_HTML_STYLING, htmlStyling);
		preferencesEditor.apply();
	}

	public static String getHtmlStyling(Context context) {
		return getSharedPreferences(context).getString(KEY_HTML_STYLING, PostHtmlUtils.Companion.getDEFAULT_STYLING());
	}

	@DebugLog
	public static void setFollowedChannels(Context context, List<Long> channelIds) {
		Set<String> followedChannels = getFollowedChannelsFromPrefs(context);
		for (Long channelId : channelIds) {
			followedChannels.add(String.valueOf(channelId));
		}

		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putStringSet(KEY_FOLLOWED_CHANNELS, followedChannels);
		preferencesEditor.apply();
	}

	public static void removeFollowedChannel(Context context, long channelId) {
		Set<String> followedChannels = getFollowedChannelsFromPrefs(context);
		followedChannels.remove(String.valueOf(channelId));

		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putStringSet(KEY_FOLLOWED_CHANNELS, followedChannels);
		preferencesEditor.apply();
	}

	public static Set<Long> getFollowedChannels(Context context) {
		Set<String> channelIds = getFollowedChannelsFromPrefs(context);
		Set<Long> followedChannels = new HashSet<>();

		for (String channelId : channelIds) {
			followedChannels.add(Long.valueOf(channelId));
		}
		return followedChannels;
	}

	private static Set<String> getFollowedChannelsFromPrefs(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getStringSet(KEY_FOLLOWED_CHANNELS, new HashSet<String>());
	}

	@DebugLog
	public static void setVotes(Context context, List<String> postLinks) {
		Set<String> likedPost = getVotesFromPrefs(context);
		for (String postId : postLinks) {
			likedPost.add(String.valueOf(postId));
		}

		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putStringSet(KEY_VOTES, likedPost);
		preferencesEditor.apply();
	}

	public static void removeVote(Context context, String postLink) {
		Set<String> likedPosts = getVotesFromPrefs(context);

		likedPosts.remove(postLink);

		SharedPreferences.Editor preferencesEditor = getSharedPreferences(context).edit();
		preferencesEditor.putStringSet(KEY_VOTES, likedPosts);
		preferencesEditor.apply();
	}

	public static Set<Long> getVotes(Context context) {
		Set<String> postIds = getVotesFromPrefs(context);
		Set<Long> likedPosts = new HashSet<>();

		for (String postId : postIds) {
			likedPosts.add(Long.valueOf(postId));
		}
		return likedPosts;
	}

	public static void cleanPrefs(Context context) {
		getSharedPreferences(context).edit().clear().apply();
	}

	private static Set<String> getVotesFromPrefs(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getStringSet(KEY_VOTES, new HashSet<String>());
	}

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
	}
}
