package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.cmd.CommandList;
import de.rob1n.prospam.cmd.CommandWithGui;
import de.rob1n.prospam.gui.Item;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CommandHelp extends Command implements CommandWithGui
{
	public CommandHelp(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "help";
	}

	@Override
	public String getDescription()
	{
		return "How to use the plugin";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {""};
    }

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		final CommandList cmdList = plugin.getCommandHandler().getCommandList();

        if(isPlayer(sender))
        { //show Inventory GUI
            Player player = (Player)sender;

            showGui(player);
        }
        else
        { //show in conventional way
            sender.sendMessage(ProSpam.prefixed("[Version: "+plugin.getDescription().getVersion()+" by prodaim] "+"List of Commands"));
            for(Command cmd: cmdList)
            {
                sender.sendMessage(ChatColor.GRAY+"/prospam " + ChatColor.LIGHT_PURPLE + StringUtils.join(cmd.getArgs(), " "));
                sender.sendMessage(ChatColor.ITALIC+cmd.getDescription()+ChatColor.RESET);
                //sender.sendMessage(ChatColor.GRAY+"---------------------------------------------"+ChatColor.RESET);
            }
        }
	}

    @SuppressWarnings("deprecation")
    public void showGui(Player player)
    {
        final Set<Item> items = new HashSet<Item>();

        //enable/disable item
        if(settings.enabled)
        {
            items.add(new Item(0, new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, DyeColor.GREEN.getDyeData()), "ProSpam is enabled", Item.NO_CLICK_ACTION));
            items.add(new Item(1, new ItemStack(Material.LEVER), "Disable ProSpam", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    CommandDisable commandDisable = new CommandDisable(plugin);
                    commandDisable.execute(player, new String[0]); //no gui, execute normally

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(0, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1, DyeColor.RED.getDyeData()), "ProSpam is disabled", Item.NO_CLICK_ACTION));
            items.add(new Item(1, new ItemStack(Material.LEVER), "Enable ProSpam", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    CommandEnable commandEnable = new CommandEnable(plugin);
                    commandEnable.execute(player, new String[0]); //no gui, execute normally

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //reload item
        items.add(new Item(3, new ItemStack(Material.PAPER), "Reload Config", "Reloads the settings from config.yml", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandReload commandReload = new CommandReload(plugin);
                commandReload.execute(player, new String[0]); //no gui, execute normally

                //reopen with new settings
                showGui(player);
            }
        }));

        //spacer items
        for(int i=9; i<=17; i++)
        {
            items.add(Item.getSpacerItem(i));
        }

        //manage filters item
        items.add(new Item(18, new ItemStack(Material.LEGACY_BOOK_AND_QUILL), "Manage filters", "All the filter settings", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandFilters commandFilters = new CommandFilters(plugin);
                commandFilters.showGui(player);
            }
        }));

        //spamStats item
        items.add(new Item(24, new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte)3), "Player spam stats", "Shows spam stats for each player", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandCounter commandCounter = new CommandCounter(plugin);
                commandCounter.showGui(player);
            }
        }));

        //whitelist item
        if(settings.whitelist_enabled)
        {
            items.add(new Item(26, new ItemStack(Material.MAP), "Disable whitelist", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    CommandWhitelistDisable commandWhitelistDisable = new CommandWhitelistDisable(plugin);
                    commandWhitelistDisable.execute(player, new String[0]); //no gui, execute normally

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(26, new ItemStack(Material.LEGACY_EMPTY_MAP), "Enable whitelist", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    CommandWhitelistEnable commandWhitelistEnable = new CommandWhitelistEnable(plugin);
                    commandWhitelistEnable.execute(player, new String[0]); //no gui, execute normally

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        plugin.getGuiManager().openInventoryGui(player, "ProSpam by prodaim. Commands", items);
    }
}
