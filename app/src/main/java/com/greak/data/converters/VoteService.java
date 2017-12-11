package com.greak.data.converters;

import org.json.JSONException;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.Observable;

public class VoteService {

	private final AccountName accountName;
	private final String permlink;
	private SteemJ steemJ;

	public VoteService(AccountName accountName, String permlink) {
		this.accountName = accountName;
		this.permlink = permlink;
	}

	public Observable<Boolean> votePost() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(vote()));
	}


	public Observable<Boolean> unvotePost() throws SteemResponseException, SteemCommunicationException {
		return Observable.create(e -> e.onNext(unvote()));
	}

	private Boolean vote() throws SteemResponseException, SteemCommunicationException, JSONException, SteemInvalidTransactionException {
		steemJ = new SteemJ();
		steemJ.vote(accountName, new Permlink(permlink), (short) 50);
		return true;
	}

	private Boolean unvote() throws SteemResponseException, SteemCommunicationException, JSONException, SteemInvalidTransactionException {
		steemJ = new SteemJ();
		steemJ.cancelVote(accountName, new Permlink(permlink));
		return true;
	}
}