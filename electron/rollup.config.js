import commonjs from '@rollup/plugin-commonjs';
import resolve from '@rollup/plugin-node-resolve';

export default {
  input: 'electron/build/electron/src/index.js',
  output: [
    {
      file: 'electron/dist/plugin.js',
      format: 'cjs',
      sourcemap: true,
      inlineDynamicImports: true,
      exports: 'named',
    },
  ],
  external: [
    '@capacitor/core',
    '@fduenascoink/pelicano-addon',
    'electron',
    'path',
    'fs',
    'events',
  ],
  plugins: [
    resolve(), 
    commonjs({
      ignoreDynamicRequires: true,
      dynamicRequireTargets: [
        'node_modules/@fduenascoink/capacitor-pelicano/electron/dist/plugin.js'
      ],
    }),
  ],
};