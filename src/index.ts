import { registerPlugin } from '@capacitor/core';

import type { PelicanoPlugin } from './definitions';

const Pelicano = registerPlugin<PelicanoPlugin>('Pelicano', {
  web: () => import('./web').then(m => new m.PelicanoWeb()),
});

export * from './definitions';
export { Pelicano };
