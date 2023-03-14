# MinecraftTeleportPlugin

A simple plugin which adds some items, commands and events to teleport players.

Supported versions:

- 1.16.5

## Development

### plugin.yml

ビルド時に自動生成されます。[build.gradle.kts](build.gradle.kts) の以下の箇所で設定できます。
書き方は https://github.com/Minecrell/plugin-yml の Bukkit kotlin-dsl を見てください。

```kotlin
configure<BukkitPluginDescription> {
    // 内容
}
```

### タスク

#### プラグインのビルド `build`

`build/libs` フォルダに `.jar` を生成します。

#### テストサーバーの起動 `buildAndLaunchServer`

`:25565` でテストサーバーを起動します。

## References

### Official Documents

- [spigot api javadoc](https://hub.spigotmc.org/javadocs/spigot/index.html)

### Unofficial Documents

- [Spigot Event List | sya-ri](https://spigot-event-list.s7a.dev/)

### Articles

- [【改】Bukkit・Spigotプラグインを作る方法＜イベント編＞ | てれるんぶろぐ](https://tererun.hatenablog.com/entry/bukkit-spigot-plugin-4)

### AI

- [phind: AI search engine](https://www.phind.com/)
- [GitHub Copilot](https://copilot.github.com/)
- [ChatGPT](https://openai.com/blog/chatgpt)
