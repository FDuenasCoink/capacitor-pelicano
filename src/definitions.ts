/// <reference types="@capacitor/cli" />
import type { PluginListenerHandle } from "@capacitor/core";

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    Pelicano?: {
      warnToCritical?: number;
      maxCritical?: number;
      maximumPorts?: number;
      logLevel?: number;
    }
  }
}

export interface CoinEvent {
  error?: { code: number, message: string };
  value: number;
}

export interface CoinEventWarning {
  message: string;
  code: number;
}

export interface DeviceStatus {
  version: string;
  device: number;
  errorType: number;
  errorCode: number;
  message: string;
  aditionalInfo: string;
  priority: number;
  date: string;
}

export interface ResponseStatus {
  statusCode: number;
  message: string;
}

const channels = [50, 100, 200, 500, 1000] as const;

export type Channels = typeof channels[number];

export interface ChannelData {
  channel: number;
  active: boolean;
}

export interface ChannelInfo extends ResponseStatus {
  channels: { [key in Channels]?: boolean };
}

export interface UsageResponse extends ResponseStatus {
  quantity: number;
  usagePercent: number;
}

export interface PelicanoPlugin {
  /**
   * Function to get actual use of device.
   */
  getUsage(): Promise<UsageResponse>;
  /**
   * Function to clean the validator (take about 7 seconds)
   */
  cleanDevice(): Promise<ResponseStatus>;
  /**
   * Funtion to activate/deactivate channels
   */
  modifyChannel(channelData: ChannelData): Promise<ChannelInfo>;
  /**
   * Function use to test the device.
   */
  checkDevice(): Promise<ResponseStatus>;
  /**
   * Function use to test the connection.
   */
  connect(): Promise<ResponseStatus>;
  /**
   * Check device status.
   */
  testStatus(): Promise<DeviceStatus>;
  /**
   * Setup Azkoyen connection.
   */
  init(): Promise<void>;
  /**
   * Start the coin reader.
   */
  startReader(): Promise<ResponseStatus>;
  /**
   * Stop the coin reader.
   */
  stopReader(): Promise<ResponseStatus>;
  /**
   * Function to reset de device.
   */
  reset(): Promise<ResponseStatus>;
  /**
   * Listens for coin read.
   */
  addListener(eventName: 'coinInsert', listenerFunc: (event: CoinEvent) => void): Promise<PluginListenerHandle> & PluginListenerHandle;
  addListener(eventName: 'coinInsertWarning', listenerFunc: (event: CoinEventWarning) => void): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Removes all listeners
   */
  removeAllListeners(): Promise<void>;
}

export interface PelicanoPluginElectron extends PelicanoPlugin {
  removeListener?(event: string): void & Promise<void>;
  removeAllListeners(type?: string): void & Promise<void>;
  addListener(eventName: 'coinInsert', listenerFunc: (event: CoinEvent) => void): Promise<PluginListenerHandle> & PluginListenerHandle & string;
  addListener(eventName: 'coinInsertWarning', listenerFunc: (event: CoinEventWarning) => void): Promise<PluginListenerHandle> & PluginListenerHandle & string;
}