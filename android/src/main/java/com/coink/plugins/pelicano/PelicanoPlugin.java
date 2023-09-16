package com.coink.plugins.pelicano;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Pelicano")
public class PelicanoPlugin extends Plugin implements CoinNotifier {

    private static final String COIN_EVENT = "coinInsert";
    private static final String COIN_WARNING_EVENT = "coinInsertWarning";
    private static final  String TAG = "PelicanoPlugin";

    private Pelicano implementation;

    @Override
    public void load() {
        super.load();
        implementation = new Pelicano(this);
    }

    @PluginMethod()
    public void connect(PluginCall call) {
        try {
            PelicanoResponse result = implementation.connect();
            call.resolve(result);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void checkDevice(PluginCall call) {
        try {
            PelicanoResponse response = implementation.checkDevice();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void getUsage(PluginCall call) {
        try {
            PelicanoResponse response = implementation.getUsage();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void cleanDevice(PluginCall call) {
        try {
            PelicanoResponse response = implementation.cleanDevice();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void testStatus(PluginCall call) {
        try {
            PelicanoResponse response = implementation.testStatus();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void modifyChannel(PluginCall call) {
        int channel = call.getInt("channel", 0);
        boolean active = call.getBoolean("active", true);
        try {
            PelicanoResponse response = implementation.modifyChannel(channel, active);
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void init(PluginCall call) {
        try {
            implementation.connect();
            implementation.checkDevice();
            call.resolve();
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void startReader(PluginCall call) {
        try {
            PelicanoResponse response = implementation.startReader(this);
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void stopReader(PluginCall call) {
        try {
            PelicanoResponse response = implementation.stopReader();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @PluginMethod()
    public void reset(PluginCall call) {
        try {
            PelicanoResponse response = implementation.reset();
            call.resolve(response);
        } catch (PelicanoException e) {
            String message = e.getMessage();
            String code = e.getCode();
            call.reject(message, code);
        }
    }

    @Override
    public void onInsertCoin(JSObject data) {
        notifyListeners(COIN_EVENT, data);
    }

    @Override
    public void onInsertCoinWarning(JSObject data) {
        notifyListeners(COIN_WARNING_EVENT, data);
    }
}
