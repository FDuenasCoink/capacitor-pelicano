export interface PelicanoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
