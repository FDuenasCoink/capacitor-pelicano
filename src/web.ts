import { WebPlugin } from '@capacitor/core';

import type { ChannelData, ChannelInfo, DeviceStatus, PelicanoPluginBase, ResponseStatus, UsageResponse } from './definitions';
import { Validator } from './lib/Validator';
import { Logger } from './lib/logger';

export class PelicanoPluginWeb extends WebPlugin implements PelicanoPluginBase {
  private static readonly COINK_EVENT = 'coinInsert';
  private logger = new Logger('PELICANO');
  private validator = new Validator({
    title: 'Pelicano',
    coins: [
      { value: 50 },
      { value: 100 },
      { value: 200 },
      { value: 500 },
      { value: 1000 },
    ],
  });

  async reset(): Promise<ResponseStatus> {
    this.logger.log('reset');
    return {
      statusCode: 200,
      message: 'web simulator',
    }
  }

  async modifyChannel(channelData: ChannelData): Promise<ChannelInfo> {
    this.logger.log('modifyChannel');
    const channels =  this.validator.modifyChannel(channelData);
    return {
      statusCode: 200,
      message: 'web simulator',
      channels,
    };
  }

  async checkDevice(): Promise<ResponseStatus> {
    this.logger.log('checkDevice');
    return {
      statusCode: 200,
      message: 'web simulator',
    }
  }

  async connect(): Promise<ResponseStatus> {
    this.logger.log('connected');
    return {
      statusCode: 200,
      message: 'web simulator',
    }
  }

  async testStatus(): Promise<DeviceStatus> {
    this.logger.log('tes status simulated');
    return {
      version: '1',
      device: 1,
      errorType: 0,
      errorCode: 0,
      message: "web simulated response",
      aditionalInfo: "",
      priority: 0,
      date: new Date().toString(),
    }
  }

  async init() {
    this.logger.log('setup web');
    return;
  }

  async startReader() {
    this.logger.log('start reader');
    this.validator.mount();
    this.validator.resetChannels();
    this.validator.onCoinInsert((event) => {
      this.notifyListeners(PelicanoPluginWeb.COINK_EVENT, event);
    });
    return {
      statusCode: 200,
      message: "web simulator"
    };
  }

  async stopReader() {
    this.logger.log('stop reader');
    this.validator.unmount();
    return {
      statusCode: 200,
      message: "web simulator"
    };
  }

  async getUsage(): Promise<UsageResponse> {
    this.logger.log('get usage simulated web');
    return {
      statusCode: 200,
      message: 'web simulated response',
      quantity: 1000,
      usagePercent: 10,
    }
  }

  async cleanDevice(): Promise<ResponseStatus> {
    this.logger.log('get usage simulated web');
    return {
      statusCode: 200,
      message: 'web simulated response',
    }
  }
}
