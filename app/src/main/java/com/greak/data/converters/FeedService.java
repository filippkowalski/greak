package com.greak.data.converters;

import android.support.annotation.NonNull;

import com.greak.common.utils.SteemCurrencyCalculator;
import com.greak.common.utils.StringUtils;
import com.greak.data.models.Category;
import com.greak.data.models.Post;
import com.greak.data.models.PostWithoutPhoto;
import com.greak.data.models.SteemAccount;
import com.greak.ui.common.StringParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.apis.database.models.state.Comment;
import eu.bittrade.libs.steemj.apis.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.util.CondenserUtils;
import io.reactivex.Observable;

public class FeedService {

	private SteemJ steemJ;
	private AccountName accountName;
	private int entryId;
	private short limit;

	private RewardFund rewardFund;
	private Price currentMedianHistoryPrice;

	public FeedService(AccountName accountName, int entryId, short limit) {
		this.accountName = accountName;
		this.entryId = entryId;
		this.limit = limit;
	}

	public Observable<List<Post>> getData() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(prepareFeed()));
	}

	private List<Post> prepareFeed() throws SteemResponseException, SteemCommunicationException, JSONException {
		steemJ = new SteemJ(); // TODO improve this solution
		rewardFund = steemJ.getRewardFund(RewardFundType.POST);
		currentMedianHistoryPrice = steemJ.getCurrentMedianHistoryPrice();

		List<CommentFeedEntry> feedEntries = steemJ.getFeed(accountName, entryId, limit);
		return commentFeedEntriesToPosts(feedEntries);
	}

	private List<Post> commentFeedEntriesToPosts(List<CommentFeedEntry> commentFeedEntries) throws SteemResponseException, SteemCommunicationException, JSONException {
		List<Post> posts = new ArrayList<>();
		for (CommentFeedEntry commentFeedEntry : commentFeedEntries) {
			Post post = commentFeedEntryToPost(commentFeedEntry);
			posts.add(post);
		}
		return posts;
	}

	private Post commentFeedEntryToPost(CommentFeedEntry commentFeedEntry) throws SteemResponseException, SteemCommunicationException, JSONException {
		Comment comment = commentFeedEntry.getComment();
		String coverPhotoUrl = getCoverPhoto(commentFeedEntry);

		Post post = getPostInstance(coverPhotoUrl);
		post.setCoverPhoto(coverPhotoUrl);
		post.setContent(comment.getBody());
		post.setTitle(comment.getTitle());
		post.setCommentsCount(commentFeedEntry.getReblogBy().size());
		TimePointSec created = comment.getCreated();
		post.setDateCreated(created.getDateTime());
		post.setCategory(new Category(comment.getCategory()));
		post.setReadTime(calculateReadingTimeInMinutes(comment.getBody()));
		post.setMoneyEarned(SteemCurrencyCalculator.getEarnedMoney(comment, rewardFund, currentMedianHistoryPrice));

		AccountName accountName = comment.getAuthor();
		ExtendedAccount extendedAccount = getExtendedAccount(accountName);
		JSONObject profileJson = new JSONObject(extendedAccount.getJsonMetadata());

		SteemProfileConverter steemProfileConverter = new SteemProfileConverter(accountName);
		SteemAccount steemAccount = steemProfileConverter.getSteemAccount(profileJson);
		steemAccount.setPostCount((int) extendedAccount.getPostCount());

		post.setSteemAccount(steemAccount);
		return post;
	}

	@NonNull
	private Post getPostInstance(String coverPhotoUrl) {
		if (coverPhotoUrl != null) {
			return new Post();
		} else {
			return new PostWithoutPhoto();
		}
	}

	private String getCoverPhoto(CommentFeedEntry commentFeedEntry) {
		List<String> urls = CondenserUtils.extractLinksFromContent(commentFeedEntry.getComment().getBody());
		return StringParser.extractFirstFoundImageUrl(urls);
	}

	private ExtendedAccount getExtendedAccount(AccountName accountName) throws SteemResponseException, SteemCommunicationException {
		List<ExtendedAccount> accounts = steemJ.getAccounts(Collections.singletonList(accountName));
		return accounts.get(0);
	}

	// TODO this calculation should be improved, according to Medium.com. for every image 12 seconds should be added
	private int calculateReadingTimeInMinutes(String body) {
		return (int) Math.ceil(StringUtils.countWords(body) / StringUtils.AVERAGE_WORD_PER_MINUTE);
	}

}