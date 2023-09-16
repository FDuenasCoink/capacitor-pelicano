package com.coink.plugins.pelicano;

import android.util.Pair;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginConfig;

import java.util.HashMap;
import java.util.Map;

import hardware.pelicano.CoinError_t;
import hardware.pelicano.CoinLost_t;
import hardware.pelicano.PelicanoControlClass;
import hardware.pelicano.Response_t;
import hardware.pelicano.TestStatus_t;

public class Pelicano implements Runnable {

    static {
        System.loadLibrary("Pelicano_Wrapper");
    }

    private static final String LOG_FILE = "Pelicano.log";
    private static final float MAX_VALIDATOR_USAGE = 750_000;

    private CoinNotifier notifier;
    private final CoinsChannels channels = new CoinsChannels();
    private final PelicanoControlClass pelicano = new PelicanoControlClass();
    private final CustomThread thread = new CustomThread(this);

    public Pelicano (Plugin plugin) {
        PluginConfig config = plugin.getConfig();
        int warnToCritical = config.getInt("warnToCritical", 10);
        int maxCritical = config.getInt("maxCritical", 1);
        int MaximumPorts = config.getInt("maximumPorts", 10);
        int logLevel = config.getInt("logLevel", 1);

        pelicano.setWarnToCritical(warnToCritical);
        pelicano.setMaxCritical(maxCritical);
        pelicano.setMaximumPorts(MaximumPorts);

        String packagePath = plugin.getActivity().getApplicationContext().getFilesDir().getAbsolutePath();
        String logPath = packagePath + "/" + LOG_FILE;
        pelicano.setPath(logPath);
        pelicano.setLogLvl(logLevel);

        pelicano.InitLog();
    }

    public PelicanoResponse connect() throws PelicanoException {
        thread.pause();
        Response_t response = pelicano.Connect();
        thread.resume();
        if (response.getStatusCode() != 200) {
            String message = response.getMessage();
            int code = response.getStatusCode();
            throw new PelicanoException(message, code);
        }
        return new PelicanoResponse(response);
    }

    public PelicanoResponse checkDevice() throws PelicanoException {
        thread.pause();
        Response_t response = pelicano.CheckDevice();
        thread.resume();
        if (response.getStatusCode() != 200) {
            String message = response.getMessage();
            int code = response.getStatusCode();
            throw new PelicanoException(message, code);
        }
        return new PelicanoResponse(response);
    }

    public PelicanoResponse getUsage() throws PelicanoException {
        thread.pause();
        Response_t response = pelicano.GetInsertedCoins();
        thread.resume();
        int code = response.getStatusCode();
        if (code != 206) {
            String message = response.getMessage();
            throw new PelicanoException(message, code);
        }
        long quantity = pelicano.getInsertedCoins();
        float usagePercent = (quantity * 100) / MAX_VALIDATOR_USAGE;
        PelicanoResponse data = new PelicanoResponse(response);
        data.put("quantity", quantity);
        data.put("usagePercent", usagePercent);
        return data;
    }

    public PelicanoResponse cleanDevice() throws PelicanoException {
        Response_t response = pelicano.CleanDevice();
        int status = response.getStatusCode();
        if (status != 205) {
            String message = response.getMessage();
            throw new PelicanoException(message, status);
        }
        return new PelicanoResponse(response);
    }

    public PelicanoResponse testStatus() throws PelicanoException {
        try {
            checkDevice();
        } catch (PelicanoException e) {
            String code = e.getCode();
            if (code.equals("301") || code.equals("302")) {
                cleanDevice();
            } else {
                throw e;
            }
        }
        thread.pause();
        TestStatus_t result = pelicano.TestStatus();
        thread.resume();
        return new PelicanoResponse(result);
    }

    public PelicanoResponse startReader(CoinNotifier notifier) throws PelicanoException {
        thread.stop();
        Response_t response = pelicano.StartReader();
        int status = response.getStatusCode();
        if (status != 201 && status != 202) {
            String message = response.getMessage();
            throw new PelicanoException(message, status);
        }
        channels.reset();
        this.notifier = notifier;
        thread.start();
        return new PelicanoResponse(response);
    }

    public PelicanoResponse stopReader() throws PelicanoException {
        thread.stop();
        Response_t response = pelicano.StopReader();
        int status = response.getStatusCode();
        if (status != 200) {
            String message = response.getMessage();
            throw new PelicanoException(message, status);
        }
        this.notifier = null;
        channels.reset();
        return new PelicanoResponse(response);
    }

    public PelicanoResponse modifyChannel(int channel, boolean active) throws PelicanoException {
        channels.setChannel(channel, active);
        Pair<Integer, Integer> values = channels.getValue();
        thread.pause();
        Response_t response = pelicano.ModifyChannels(values.first, values.second);
        thread.resume();
        int status = response.getStatusCode();
        if (status != 203) {
            String message = response.getMessage();
            throw new PelicanoException(message, status);
        }
        return new PelicanoResponse(response, channels);
    }

    public PelicanoResponse reset() throws PelicanoException {
        Response_t response = pelicano.ResetDevice();
        int status = response.getStatusCode();
        if (status != 204) {
            String message = response.getMessage();
            throw  new PelicanoException(message, status);
        }
        return new PelicanoResponse(response);
    }

    @Override
    public void run() {
        CoinError_t coin = pelicano.GetCoin();
        int status = coin.getStatusCode();

        if (status == 303) return;

        if (coin.getRemaining() > 1) {
            CoinLost_t remaining = pelicano.GetLostCoins();
            Map<Integer, Integer> coinList = new HashMap<>();
            coinList.put(50, remaining.getCoinCinc());
            coinList.put(100, remaining.getCoinCien());
            coinList.put(200, remaining.getCoinDosc());
            coinList.put(500, remaining.getCoinQuin());
            coinList.put(1000, remaining.getCoinMil());
            for (Map.Entry<Integer, Integer> entry : coinList.entrySet()) {
                int quantity = entry.getValue();
                int coinValue = entry.getKey();
                if (quantity == 0) continue;
                PelicanoEvent event = PelicanoEvent.fromValue(coinValue);
                for (int i = 0; i < quantity; i++) {
                    notifier.onInsertCoin(event);
                }
            }
        }

        if (status == 302 || status == 404) return;

        if (status == 401) {
            PelicanoEvent warning = PelicanoEvent.warning(coin);
            notifier.onInsertCoinWarning(warning);
            return;
        }

        if (status != 202) {
            PelicanoEvent error = PelicanoEvent.error(coin);
            notifier.onInsertCoin(error);
            thread.breakProcess();
            return;
        }

        int coinValue = coin.getCoin();
        if (coinValue == 0) return;

        PelicanoEvent event = new PelicanoEvent(coin);
        notifier.onInsertCoin(event);
    }

}