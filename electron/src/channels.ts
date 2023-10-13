export class CoinChannels {
  private channelBitState = [
    [1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1],
  ];

  private readonly channelsBits: { [key: number]: number[] }  = {
    50: [5, 16],
    100: [4, 15],
    200: [3, 14],
    500: [2, 13],
    1000: [1, 12],
  }

  state: { [key: number]: boolean } = {
    50: true,
    100: true,
    200: true,
    500: true,
    1000: true
  };

  setChannel(channel: number, active: boolean) {
    const channelBits = this.channelsBits[channel];
    if (!channelBits) return;
    this.channelBitState.forEach((channel, i) => {
      channel.forEach((_, j) => {
        const pos = (i * channel.length) + j + 1;
        if (channelBits.includes(pos)) {
          this.channelBitState[i][j] = Number(active);
        }
      });
    });
    this.state[channel] = active;
  }

  getValue() {
    const mask1 = this.convertBitsToInt(this.channelBitState[0]);
    const mask2 = this.convertBitsToInt(this.channelBitState[1]);
    return { mask1, mask2 };
  }


  reset() {
    this.channelBitState.forEach((bits, i) => {
      bits.forEach((_, j) => {
        this.channelBitState[i][j] = 1;
      });
    });
    Object.keys(this.state).forEach(key => {
      this.state[Number(key)] = true;
    });
  }

  private convertBitsToInt(bits: number[]) {
    return parseInt(bits.join(''), 2);
  }
}