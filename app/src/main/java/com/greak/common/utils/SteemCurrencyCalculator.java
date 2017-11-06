package com.greak.common.utils;

import java.math.BigInteger;

import eu.bittrade.libs.steemj.apis.database.models.state.Comment;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

public class SteemCurrencyCalculator {

	public static double getEarnedMoney(Comment comment, RewardFund rewardFund, Price currentMedianHistoryPrice) throws SteemResponseException, SteemCommunicationException {
		BigInteger rewardBalance = BigInteger.valueOf(rewardFund.getRewardBalance().getAmount());
		BigInteger recentClaims = rewardFund.getRecentClaims();
		BigInteger steemPrice = BigInteger.valueOf(currentMedianHistoryPrice.getBase().getAmount());
		BigInteger voteShares = BigInteger.valueOf(comment.getVoteRshares());

		BigInteger earnedMoney = voteShares
				.multiply(rewardBalance)
				.divide(recentClaims)
				.multiply(steemPrice)
				.divide(BigInteger.valueOf(10000));

		return earnedMoney.doubleValue() / 100;
	}
}
