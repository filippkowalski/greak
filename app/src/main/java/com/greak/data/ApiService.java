package com.greak.data;

import java.util.List;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.apis.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

public class ApiService {

	public void getFeed() throws SteemResponseException, SteemCommunicationException {
		SteemJConfig myConfig = SteemJConfig.getInstance();
		myConfig.setDefaultAccount(new AccountName("mdfk"));

		SteemJ steemJ = new SteemJ();
		List<CommentFeedEntry> feed = steemJ.getFeed(new AccountName("dez1337"), 0, (short) 100);
	}
}
