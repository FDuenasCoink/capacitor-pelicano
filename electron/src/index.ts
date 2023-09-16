import type { CoinResult, IValidator, UnsubscribeFunc} from '@fduenascoink/oink-addons';
import { Pelicano as PelicanoAddon } from '@fduenascoink/oink-addons';
import { EventEmitter } from 'events';

import type { ChannelData, ChannelInfo, DeviceStatus, PelicanoPlugin, ResponseStatus, UsageResponse } from '../../src/definitions';

import { CoinChannels } from './channels';
import { PluginError, getCapacitorElectronConfig } from './utils';

export class Pelicano extends EventEmitter implements PelicanoPlugin  {
  private static readonly COIN_EVENT = "coinInsert";
  private static readonly COIN_WARNING_EVENT = "coinInsertWarning";
  
  pelicano: IValidator;
  private channels = new CoinChannels();
  private unsubscribeFn?: UnsubscribeFunc;

  constructor() {
    super();
    const config = getCapacitorElectronConfig('Pelicano');
    this.pelicano = new PelicanoAddon({
      maxCritical: config.maxCritical ?? 3,
      warnToCritical: config.warnToCritical ?? 10,
      maximumPorts: config.maximumPorts ?? 10,
      logLevel: config.logLevel ?? 1,
      logPath: 'logs/pelicano.log',
    });
  }
  
  async init(): Promise<void> {
    await this.connect();
    await this.checkDevice();
  }

  async connect(): Promise<ResponseStatus> {
    const response = this.pelicano.connect();
    const status = response.statusCode;
    if (status !== 200) {
      throw new PluginError(response.message, response.statusCode);
    }
    return response;
  }

  async checkDevice(): Promise<ResponseStatus> {
    const response = this.pelicano.checkDevice();
    const status = response.statusCode;
    if (status !== 200) {
      throw new PluginError(response.message, response.statusCode);
    }
    return response;
  }

  getUsage(): Promise<UsageResponse> {
    // TODO: hay que modificar el addon para implementar la funcion getInsertCoin.
    throw new Error('Method not implemented.');
  }

  async cleanDevice(): Promise<ResponseStatus> {
    const response = this.pelicano.cleanDevice();
    const status = response.statusCode;
    if (status !== 205) {
      throw new PluginError(response.message, response.statusCode);
    }
    return response;
  }

  async testStatus(): Promise<DeviceStatus> {
    const checkDevice = this.pelicano.checkDevice();
    const status = checkDevice.statusCode;
    if (status !== 200) {
      if (status === 301 || status === 302) {
        this.pelicano.cleanDevice();
      } else {
        throw new PluginError(checkDevice.message, checkDevice.statusCode);
      }
    }
    const testStatus = this.pelicano.testStatus();
    return {
      ...testStatus,
      date: new Date().toString(),
    };
  }

  async startReader(): Promise<ResponseStatus> {
    this.unsubscribeFn?.();
    const response = this.pelicano.startReader();
    const status = response.statusCode;
    if (status !== 201 && status !== 202) {
      throw new PluginError(response.message, response.statusCode);
    }
    this.channels.reset();
    this.unsubscribeFn = this.pelicano.onCoin((coin) => this.notifyCoin(coin));
    return response;
  }

  async stopReader(): Promise<ResponseStatus> {
    this.unsubscribeFn?.();
    const response = this.pelicano.stopReader();
    const status = response.statusCode;
    if (status !== 200) {
      throw new PluginError(response.message, response.statusCode);
    }
    this.channels.reset();
    return response;
  }

  async modifyChannel(channelData: ChannelData): Promise<ChannelInfo> {
    const { channel, active } = channelData;
    this.channels.setChannel(channel, active);
    const { mask1, mask2 } = this.channels.getValue();
    const response = this.pelicano.modifyChannels(mask1, mask2);
    const status = response.statusCode;
    if (status !== 203) {
      throw new PluginError(response.message, response.statusCode);
    }
    const channels = this.channels.state;
    return { ...response, channels };
  }

  async reset(): Promise<ResponseStatus> {
    this.unsubscribeFn?.();
    const response = this.pelicano.resetDevice();
    const status = response.statusCode;
    if (status !== 204) {
      throw new PluginError(response.message, response.statusCode);
    }
    return response;
  }
  
  async notifyCoin(coin: CoinResult) {
    const status = coin.statusCode;
    if (status === 303) return;

    if (coin.remaining > 1) {
      const remaining = this.pelicano.getLostCoins();
      Object.entries(remaining).forEach(([coinValue, quantity]) => {
        for (let i = 0; i < quantity; i++) {
          this.emit(Pelicano.COIN_EVENT, { value: Number(coinValue) });
        }
      });
    }

    if (status === 302 || status === 404) return;

    if (status === 401) {
      const warning = { code: coin.statusCode, message: coin.message };
      this.emit(Pelicano.COIN_WARNING_EVENT, warning);
      return;
    }

    if (status !== 202) {
      const error = { code: coin.statusCode, message: coin.message };
      const value = 0;
      const event = { value, error };
      this.emit(Pelicano.COIN_EVENT, event);
      this.unsubscribeFn?.();
      return
    }

    const value = coin.coin;
    if (value === 0) return;
    this.emit(Pelicano.COIN_EVENT, { value });
  }

  // @ts-ignore
  addListener(event: string | symbol, listener: (...args: any[]) => void): any {
    return super.addListener(event, listener);
  }

  // @ts-ignore
  removeAllListeners(event?: string | symbol): any {
    return super.removeAllListeners(event);
  }

  // @ts-ignore
  removeListener(event: string | symbol, listener: (...args: any[]) => void): any {
    return super.removeListener(event, listener);
  }
  
}