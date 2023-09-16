package com.coink.plugins.pelicano;

import com.getcapacitor.JSObject;

interface CoinNotifier {
  void onInsertCoin(JSObject data);

  void onInsertCoinWarning(JSObject data);
}
