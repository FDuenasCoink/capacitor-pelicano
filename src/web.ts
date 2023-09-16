import { WebPlugin } from '@capacitor/core';

import type { PelicanoPlugin } from './definitions';

export class PelicanoWeb extends WebPlugin implements PelicanoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
