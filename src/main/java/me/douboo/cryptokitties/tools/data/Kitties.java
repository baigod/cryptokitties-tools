package me.douboo.cryptokitties.tools.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.douboo.cryptokitties.tools.vo.Auction;

public class Kitties {

	public static final Map<Integer, Auction> sales = new ConcurrentHashMap<Integer, Auction>();
}
