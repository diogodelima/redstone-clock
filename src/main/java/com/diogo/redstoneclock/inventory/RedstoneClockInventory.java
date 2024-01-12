package com.diogo.redstoneclock.inventory;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import com.diogo.redstoneclock.model.redstoneclock.service.RedClockFoundationService;
import com.diogo.redstoneclock.util.ItemBuilder;
import lombok.AllArgsConstructor;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.state.State;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;

@AllArgsConstructor
public class RedstoneClockInventory extends View {

    private final RedClockFoundationService redClockService;

    private final State<ItemStack> itemStackState = computedState(context -> {

        final RedstoneClock redstoneClock = (RedstoneClock) context.getInitialData();

        return new ItemBuilder(Material.REDSTONE_BLOCK)
                .setDisplayName("§4Relógio de Redstone")
                .setLore(Arrays.asList(
                        "§7Utilize os botões para alterar o delay",
                        "§7que o relógio de redstone irá utilizar.",
                        "",
                        "§7Delay: §f" + String.format("%.1f", redstoneClock.getDelay()) + "s"
                )).build();
    });

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {

        config
                .title("Relógio - Configuração")
                .size(5)
                .cancelOnPickup()
                .cancelOnDrop()
                .cancelOnDrag()
                .cancelOnClick();

    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {

        final RedstoneClock redstoneClock = (RedstoneClock) render.getInitialData();

        render.slot(19)
                    .watch(itemStackState)
                    .renderWith(() -> itemStackState.get(render));

        render.slot(10, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setSkull("http://textures.minecraft.net/texture/ac731c3c723f67d2cfb1a1192b947086fba32aea472d347a5ed5d7642f73b")
                .setDisplayName("§c§l+")
                .setLore(Collections.singletonList("§7Clique para aumentar o delay em 0.1s"))
                .build())
                .onClick(click -> {

                    Player player = click.getPlayer();

                    if (redstoneClock.isActive()){
                        player.sendMessage("§cDesligue o Relógio de Redstone para alterar o delay.");
                        return;
                    }

                    try {
                        redstoneClock.setDelay(redstoneClock.getDelay() + 0.1);
                        click.update();
                    } catch (Exception e) {
                        player.sendMessage("§cO delay máximo permitido no relógio de redstone é 1.5s");
                    }

                });

        render.slot(28, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                        .setSkull("http://textures.minecraft.net/texture/4e4b8b8d2362c864e062301487d94d3272a6b570afbf80c2c5b148c954579d46")
                        .setDisplayName("§c§l-")
                        .setLore(Collections.singletonList("§7Clique para diminuir o delay em 0.1s"))
                        .build())
                .onClick(click -> {

                    Player player = click.getPlayer();

                    if (redstoneClock.isActive()){
                        player.sendMessage("§cDesligue o Relógio de Redstone para alterar o delay.");
                        return;
                    }

                    try {
                        redstoneClock.setDelay(redstoneClock.getDelay() - 0.1);
                        click.update();
                    } catch (Exception e) {
                        player.sendMessage("§cO delay mínimo permitido no relógio de redstone é 0.7s");
                    }

                });

        render.slot(24, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setSkull("http://textures.minecraft.net/texture/94f90c7bd60bfd0dfc31808d0484d8c2db9959f68df91fbf29423a3da62429a6")
                .setDisplayName("§cConfiguração de Invasão")
                .setLore(Arrays.asList(
                        "§7Esta opção define o delay mínimo para",
                        "§7canhões de invasão (0.7s)."
                ))
                .build())
            .onClick(click -> {

                Player player = click.getPlayer();

                if (redstoneClock.isActive()){
                    player.sendMessage("§cDesligue o Relógio de Redstone para alterar o delay.");
                    return;
                }

                try {
                    redstoneClock.setDelay(0.7);
                    click.update();
                } catch (Exception e) {
                    throw new RuntimeException("Delay unexpected");
                }

            });

        render.slot(25, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                        .setSkull("http://textures.minecraft.net/texture/4030315d38fd52c454e7b9cd51cc3bd5d43786d166220685eaeff58991e6f7cb")
                        .setDisplayName("§cConfiguração de Counter")
                        .setLore(Arrays.asList(
                                "§7Esta opção define o delay mínimo para",
                                "§7canhões de Counter (1.2s)."
                        ))
                        .build())
                .onClick(click -> {

                    Player player = click.getPlayer();

                    if (redstoneClock.isActive()){
                        player.sendMessage("§cDesligue o Relógio de Redstone para alterar o delay.");
                        return;
                    }

                    try {
                        redstoneClock.setDelay(1.2);
                        click.update();
                    } catch (Exception e) {
                        throw new RuntimeException("Delay unexpected");
                    }

                });

        render.slot(21, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setSkull("http://textures.minecraft.net/texture/ebc37c9b37f4df36e4e50435fd41ecd6a7a711d11b91a6e507d285e8e85bd")
                .setDisplayName("§aConfirmar")
                .setLore(Arrays.asList(
                        "§7Clique para confirmar o delay e",
                        "§7ativar o relógio de redstone."
                )).build())
            .onClick(click -> {

                Player player = click.getPlayer();

                try {
                    redClockService.update(redstoneClock);
                    redstoneClock.setActive(true);
                    click.closeForPlayer();
                } catch (Exception e) {
                    player.sendMessage("§cVocê inseriu um delay inválido.");
                }

            });

        render.slot(22, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                        .setSkull("http://textures.minecraft.net/texture/3cc470ae2631efdfaf967b369413bc2451cd7a39465da7836a6c7a14e877")
                        .setDisplayName("§cDesligar")
                        .setLore(Arrays.asList(
                                "§7Clique para desligar o",
                                "§7relógio de redstone."
                        )).build())
                .onClick(click -> {

                    Player player = click.getPlayer();

                    try {
                        redstoneClock.setActive(false);
                        click.closeForPlayer();
                    } catch (Exception e) {
                        player.sendMessage("§cVocê inseriu um delay inválido.");
                    }

                });

    }
}
