package com.greak.data.converters;

import com.greak.data.models.Category;
import com.greak.data.models.Post;
import com.greak.data.models.SteemAccount;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.Observable;

public class UserDetailsService {

	private SteemJ steemJ;
	private AccountName accountName;
	private int offset;
	private int limit;

	public UserDetailsService(AccountName accountName, int offset, int limit) {
		this.accountName = accountName;
		this.offset = offset;
		this.limit = limit;
	}

	public Observable<List<Post>> getData() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(prepareData()));
	}

	private List<Post> prepareData() throws SteemResponseException, SteemCommunicationException, JSONException {
		steemJ = new SteemJ(); // TODO improve this solution
		List<Post> posts = new ArrayList<>();

		DiscussionQuery discussionQuery = new DiscussionQuery();
		discussionQuery.setParentAuthor(accountName);
		Map<Integer, AppliedOperation> accountHistory = steemJ.getAccountHistory(accountName, offset, limit);
		for (int i = 0; i < accountHistory.size(); i++) {
			Operation op = accountHistory.get(i).getOp();
			if (op instanceof CommentOperation) {
				CommentOperation commentOperation = (CommentOperation) op;
				if (!commentOperation.getTitle().isEmpty()) {
					// TODO
					// this if clause is because comments can also be shown here, this might be the way to
					// prevent it but let's see later if there is a better and cleaner solution
					Post post = new Post();
					post.setContent(commentOperation.getBody());
					post.setTitle(commentOperation.getTitle());
					// TODO temporary setters
					post.setCategory(new Category("test", "#000000", "#ffffff"));
					post.setCommentsCount(123);
					post.setMoneyEarned(64.32);
					post.setReadTime(3);
					post.setDateCreated("2000-12-12'T'10:11:12");
					// TODO end of temporary setters
					SteemAccount steemAccount = new SteemAccount();
					steemAccount.setName(accountName.getName());
					post.setSteemAccount(steemAccount);
					posts.add(post);
				}
			}
		}
		return posts;
	}
}