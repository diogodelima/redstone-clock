package com.diogo.redstoneclock.command;

import com.diogo.redstoneclock.model.redstoneclock.RedstoneClock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RedstoneClockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender.hasPermission("redstoneclock.admin")))
            return false;

        if (args.length == 0){
            sender.sendMessage("§cDigite '/redstoneclock give <jogador> <quantia>'");
            return false;
        }

        if (args[0].equalsIgnoreCase("give")){

            if (args.length != 3){
                sender.sendMessage("§cDigite '/redstoneclock give <jogador> <quantia>'");
                return false;
            }

            Player target = Bukkit.getPlayerExact(args[1]);
            int amount;

            if (target == null || !target.isOnline()){
                sender.sendMessage("§cJogador inválido");
                return false;
            }

            try {
                amount = Integer.parseInt(args[2]);
            }catch (Exception e){
                sender.sendMessage("§cQuantia inválida.");
                return false;
            }

            ItemStack item = RedstoneClock.display.clone();
            item.setAmount(amount);
            target.getInventory().addItem(item);
            sender.sendMessage("§aRelógio de Redstone enviado para o jogador §f" + target.getName() + " §acom sucesso.");
            return true;
        }

        sender.sendMessage("§cDigite '/redstoneclock give <jogador> <quantia>'");
        return false;
    }

}
