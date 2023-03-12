import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask.JarUrl
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.6.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.ben-manes.versions") version "0.41.0"
    id("com.palantir.git-version") version "0.12.3"
    id("dev.s7a.gradle.minecraft.server") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    // id("org.jmailen.kotlinter") version "3.13.0" apply false
}

val gitVersion: Closure<String> by extra

val pluginVersion: String by project.ext

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
    maven(url = "https://jitpack.io")
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

val shadowImplementation: Configuration by configurations.creating
configurations["implementation"].extendsFrom(shadowImplementation)

dependencies {
    shadowImplementation(kotlin("stdlib"))
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
    implementation("com.github.MockBukkit:MockBukkit:v1.16-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:$pluginVersion-R0.1-SNAPSHOT")
}

// plugin.yml
configure<BukkitPluginDescription> {
    main = "com.github.r4ai.Main"
    version = gitVersion()
    apiVersion = "1." + pluginVersion.split(".")[1]
    commands {
        register("hello") {
            usage = "/<command>"
            description = "Hello, World!"
            aliases = listOf("hi")
        }
        register("teleport_near_player") {
            description = "Teleportation command"
            aliases = listOf("tpnear")
            permission = "teleportplugin.teleport"
            permissionMessage = "Permission denied. You need `teleportplugin.teleport` permission."
            usage = "/teleport <pos-x> <pos-y> <pos-z>"
        }
        register("teleport_plugin") {
            description = "Teleportation command"
            aliases = listOf("tpplugin")
            permission = "teleportplugin.teleport"
            permissionMessage = "Permission denied. You need `teleportplugin.teleport` permission."
            usage = "/<command> item|exec|teleport"
        }
    }
}

tasks.withType<ShadowJar> {
    configurations = listOf(shadowImplementation)
    archiveClassifier.set("")
    relocate("kotlin", "com.github.r4ai.libs.kotlin")
    relocate("org.intellij.lang.annotations", "com.github.r4ai.libs.org.intellij.lang.annotations")
    relocate("org.jetbrains.annotations", "com.github.r4ai.libs.org.jetbrains.annotations")
}

tasks.named("build") {
    dependsOn("shadowJar")
}

task<LaunchMinecraftServerTask>("buildAndLaunchServer") {
    dependsOn("build")
    doFirst {
        copy {
            from(buildDir.resolve("libs/${project.name}.jar"))
            into(buildDir.resolve("MinecraftServer/plugins"))
        }
    }

    jarUrl.set(JarUrl.Paper(pluginVersion))
    jarName.set("server.jar")
    serverDirectory.set(buildDir.resolve("MinecraftServer"))
    nogui.set(true)
    agreeEula.set(true)
    serverArgument.set(listOf("--port", "25566"))
}

task<SetupTask>("setup")
