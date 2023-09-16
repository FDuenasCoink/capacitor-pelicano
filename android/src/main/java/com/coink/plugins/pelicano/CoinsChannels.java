package com.coink.plugins.pelicano;

import android.util.Pair;

import com.getcapacitor.JSObject;

import java.util.HashMap;
import java.util.Map;

public class CoinsChannels extends JSObject {

  private final Map<Integer, int[]> channelsBits = new HashMap<>();
  private final boolean[][] channelBitState = {
    {true, true, true, true, true, true, true, true},
    {true, true, true, true, true, true, true, true},
  };

  public CoinsChannels() {
    super();
    channelsBits.put(50, new int[]{5, 16});
    channelsBits.put(100, new int[]{4, 15});
    channelsBits.put(200, new int[]{3, 14});
    channelsBits.put(500, new int[]{2, 13});
    channelsBits.put(1000, new int[]{1, 12});
    reset();
  }

  public void setChannel(int channel, boolean active) {
    int[] channelBits = channelsBits.get(channel);
    if (channelBits == null) return;
    for (int ch : channelBits) {
      int channelIdx = 0;
      int offset = 1;
      if (ch > channelBitState[channelIdx].length) {
        offset += channelBitState[channelIdx].length;
        channelIdx = 1;
      }
      int idx = ch - offset;
      channelBitState[channelIdx][idx] = active;
    }
    put(Integer.toString(channel), active);
  }

  public Pair<Integer, Integer> getValue() {
    int mask1 = convertBitsToInt(channelBitState[0]);
    int mask2 = convertBitsToInt(channelBitState[1]);
    return new Pair<>(mask1, mask2);
  }

  private int convertBitsToInt(boolean[] bits) {
    int result = 0;
    for (boolean bit : bits) {
      result = (result << 1) | (bit ? 1 : 0);
    }
    return result;
  }

  public void reset() {
    for (int value : channelsBits.keySet()) {
      String key = Integer.toString(value);
      put(key, true);
    }
  }

}
