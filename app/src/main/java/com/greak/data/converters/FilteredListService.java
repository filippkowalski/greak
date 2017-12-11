package com.greak.data.converters;

import android.support.annotation.NonNull;

import com.greak.common.utils.SteemCurrencyCalculator;
import com.greak.common.utils.StringUtils;
import com.greak.data.models.Category;
import com.greak.data.models.Post;
import com.greak.data.models.PostWithoutPhoto;
import com.greak.data.models.SteemAccount;
import com.greak.ui.screens.main.common.ListType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.apis.database.models.state.Discussion;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.Observable;

public class FilteredListService {

	@ListType
	private final int listType;
	private SteemJ steemJ;
	private RewardFund rewardFund;
	private Price currentMedianHistoryPrice;

	public FilteredListService(int listType) {
		this.listType = listType;
	}

	public Observable<List<Post>> getData() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(prepareData()));
	}

	private List<Post> prepareData() throws SteemResponseException, SteemCommunicationException, JSONException {
		steemJ = new SteemJ(); // TODO improve this solution
		rewardFund = steemJ.getRewardFund(RewardFundType.POST);
		currentMedianHistoryPrice = steemJ.getCurrentMedianHistoryPrice();

		List<Post> posts = new ArrayList<>();
		DiscussionQuery discussionQuery = new DiscussionQuery();
		discussionQuery.setLimit(50);
		List<Discussion> discussionList = steemJ.getDiscussionsBy(discussionQuery, getDiscussionSortType());
		for (Discussion discussion : discussionList) {
			String coverPhotoUrl = null; //TODO
			Post post = getPostInstance(coverPhotoUrl);
			post.setCoverPhoto(coverPhotoUrl);
			post.setContent(discussion.getBody());
			post.setTitle(discussion.getTitle());
			post.setCommentsCount(discussion.getRebloggedBy().size());
			TimePointSec created = discussion.getCreated();
			post.setDateCreated(created.getDateTime());
				post.setCategory(new Category(discussion.getCategory()));
			post.setReadTime(calculateReadingTimeInMinutes(discussion.getBody()));
			post.setMoneyEarned(SteemCurrencyCalculator.getEarnedMoney(discussion, rewardFund, currentMedianHistoryPrice));

			AccountName accountName = discussion.getAuthor();
			ExtendedAccount extendedAccount = getExtendedAccount(accountName);
			if (!extendedAccount.getJsonMetadata().isEmpty()) { // TODO meh, why some posts doesnt have those metadata?
				JSONObject profileJson = new JSONObject(extendedAccount.getJsonMetadata());

				SteemProfileConverter steemProfileConverter = new SteemProfileConverter(accountName);
				SteemAccount steemAccount = steemProfileConverter.getSteemAccount(profileJson);
				steemAccount.setPostCount((int) extendedAccount.getPostCount());

				post.setSteemAccount(steemAccount);
				posts.add(post);
			}
		}
		return posts;
	}

	private DiscussionSortType getDiscussionSortType() {
		switch (listType) {
			case ListType.TYPE_NEW:
				return DiscussionSortType.GET_DISCUSSIONS_BY_CREATED;
			case ListType.TYPE_TRENDING:
			default:
				return DiscussionSortType.GET_DISCUSSIONS_BY_TRENDING;
		}
	}

	// TODO below methods are duplicated in FeedService

	private ExtendedAccount getExtendedAccount(AccountName accountName) throws SteemResponseException, SteemCommunicationException {
		List<ExtendedAccount> accounts = steemJ.getAccounts(Collections.singletonList(accountName));
		return accounts.get(0);
	}

	// TODO this calculation should be improved, according to Medium.com. for every image 12 seconds should be added
	private int calculateReadingTimeInMinutes(String body) {
		return (int) Math.ceil(StringUtils.countWords(body) / StringUtils.AVERAGE_WORD_PER_MINUTE);
	}

	@NonNull
	private Post getPostInstance(String coverPhotoUrl) {
		if (coverPhotoUrl != null) {
			return new Post();
		} else {
			return new PostWithoutPhoto();
		}
	}
}