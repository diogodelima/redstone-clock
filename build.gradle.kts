plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "com.diogo"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.30")
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.8.6")
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("me.devnatan:inventory-framework-platform-bukkit:3.0.8")
    implementation("org.jetbrains:annotations:23.0.0")

    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

tasks.withType<Jar> {
    destinationDirectory = File("C:\\Users\\diogo\\Desktop\\Desenvolvimento\\Feltz\\Factions\\plugins")
}

bukkit {
    name = "feltz-redstone-clock"
    prefix = "feltz-redstone-clock"
    version = "${project.version}"
    main = "com.diogo.redstoneclock.RedstoneClockPlugin"
    depend = listOf("DecentHolograms", "InventoryFramework")
    commands {
        register("redstoneclock")
    }
}