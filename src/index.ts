import { registerPlugin } from '@capacitor/core';

import type { PelicanoPluginElectron } from './definitions';

const Pelicano = registerPlugin<PelicanoPluginElectron>('Pelicano', {
  web: () => import('./web').then(m => new m.PelicanoPluginWeb()),
  electron: () => (window as any).CapacitorCustomPlatform.plugins.Pelicano,
});

export * from './definitions';
export { Pelicano };
