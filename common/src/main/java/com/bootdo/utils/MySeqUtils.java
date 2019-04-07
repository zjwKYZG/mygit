package com.bootdo.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 生成全局唯一序列号工具类
 * 
 * @author created by zjw on 2019年2月22日 下午3:18:13
 */
public class MySeqUtils {

	private static AtomicLong pay_seq = new AtomicLong(0L);
	private static String pay_seq_prefix = "P";
	private static AtomicLong trans_seq = new AtomicLong(0L);
	private static String trans_seq_prefix = "T";
	private static AtomicLong refund_seq = new AtomicLong(0L);
	private static String refund_seq_prefix = "R";
	private static String node = "00";

	public static String getPay() {
		return getSeq(pay_seq_prefix, pay_seq);
	}

	public static String getTrans() {
		return getSeq(trans_seq_prefix, trans_seq);
	}

	public static String getRefund() {
		return getSeq(refund_seq_prefix, refund_seq);
	}

	private static String getSeq(String prefix, AtomicLong seq) {
		prefix += node;
		return String.format("%s%s%06d", prefix, DateUtils.getSeqString(), (int) seq.getAndIncrement() % 1000000);
	}
}