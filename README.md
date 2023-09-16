# capacitor-pelicano

Plugin to control pelicano validator from capacitor app

## Install

```bash
npm install capacitor-pelicano
npx cap sync
```

## API

<docgen-index>

* [`getUsage()`](#getusage)
* [`cleanDevice()`](#cleandevice)
* [`modifyChannel(...)`](#modifychannel)
* [`checkDevice()`](#checkdevice)
* [`connect()`](#connect)
* [`testStatus()`](#teststatus)
* [`init()`](#init)
* [`startReader()`](#startreader)
* [`stopReader()`](#stopreader)
* [`reset()`](#reset)
* [`addListener('coinInsert', ...)`](#addlistenercoininsert)
* [`addListener('coinInsertWarning', ...)`](#addlistenercoininsertwarning)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getUsage()

```typescript
getUsage() => Promise<UsageResponse>
```

Function to get actual use of device.

**Returns:** <code>Promise&lt;<a href="#usageresponse">UsageResponse</a>&gt;</code>

--------------------


### cleanDevice()

```typescript
cleanDevice() => Promise<ResponseStatus>
```

Function to clean the validator (take about 7 seconds)

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### modifyChannel(...)

```typescript
modifyChannel(channelData: ChannelData) => Promise<ChannelInfo>
```

Funtion to activate/deactivate channels

| Param             | Type                                                |
| ----------------- | --------------------------------------------------- |
| **`channelData`** | <code><a href="#channeldata">ChannelData</a></code> |

**Returns:** <code>Promise&lt;<a href="#channelinfo">ChannelInfo</a>&gt;</code>

--------------------


### checkDevice()

```typescript
checkDevice() => Promise<ResponseStatus>
```

Function use to test the device.

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### connect()

```typescript
connect() => Promise<ResponseStatus>
```

Function use to test the connection.

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### testStatus()

```typescript
testStatus() => Promise<DeviceStatus>
```

Check device status.

**Returns:** <code>Promise&lt;<a href="#devicestatus">DeviceStatus</a>&gt;</code>

--------------------


### init()

```typescript
init() => Promise<void>
```

Setup Azkoyen connection.

--------------------


### startReader()

```typescript
startReader() => Promise<ResponseStatus>
```

Start the coin reader.

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### stopReader()

```typescript
stopReader() => Promise<ResponseStatus>
```

Stop the coin reader.

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### reset()

```typescript
reset() => Promise<ResponseStatus>
```

Function to reset de device.

**Returns:** <code>Promise&lt;<a href="#responsestatus">ResponseStatus</a>&gt;</code>

--------------------


### addListener('coinInsert', ...)

```typescript
addListener(eventName: 'coinInsert', listenerFunc: (event: CoinEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listens for coin read.

| Param              | Type                                                                |
| ------------------ | ------------------------------------------------------------------- |
| **`eventName`**    | <code>'coinInsert'</code>                                           |
| **`listenerFunc`** | <code>(event: <a href="#coinevent">CoinEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('coinInsertWarning', ...)

```typescript
addListener(eventName: 'coinInsertWarning', listenerFunc: (event: CoinEventWarning) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'coinInsertWarning'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#coineventwarning">CoinEventWarning</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Removes all listeners

--------------------


### Interfaces


#### UsageResponse

| Prop               | Type                |
| ------------------ | ------------------- |
| **`quantity`**     | <code>number</code> |
| **`usagePercent`** | <code>number</code> |


#### ResponseStatus

| Prop             | Type                |
| ---------------- | ------------------- |
| **`statusCode`** | <code>number</code> |
| **`message`**    | <code>string</code> |


#### ChannelInfo

| Prop           | Type                                                                                        |
| -------------- | ------------------------------------------------------------------------------------------- |
| **`channels`** | <code>{ 50?: boolean; 100?: boolean; 200?: boolean; 500?: boolean; 1000?: boolean; }</code> |


#### ChannelData

| Prop          | Type                 |
| ------------- | -------------------- |
| **`channel`** | <code>number</code>  |
| **`active`**  | <code>boolean</code> |


#### DeviceStatus

| Prop                | Type                |
| ------------------- | ------------------- |
| **`version`**       | <code>string</code> |
| **`device`**        | <code>number</code> |
| **`errorType`**     | <code>number</code> |
| **`errorCode`**     | <code>number</code> |
| **`message`**       | <code>string</code> |
| **`aditionalInfo`** | <code>string</code> |
| **`priority`**      | <code>number</code> |
| **`date`**          | <code>string</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CoinEvent

| Prop        | Type                                            |
| ----------- | ----------------------------------------------- |
| **`error`** | <code>{ code: number; message: string; }</code> |
| **`value`** | <code>number</code>                             |


#### CoinEventWarning

| Prop          | Type                |
| ------------- | ------------------- |
| **`message`** | <code>string</code> |
| **`code`**    | <code>number</code> |


### Type Aliases


#### Channels

<code>typeof channels[number]</code>

</docgen-api>
