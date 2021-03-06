package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandTriggerChars extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerChars(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-chars";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is using too many chars";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {"[violation #]", "<commands>"};
    }
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tch", "t-ch" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_chars.put(vNumber, cmds);
	}

    @Override
    public HashMap<Integer, List<String>> getTriggers()
    {
        return settings.trigger_chars;
    }

    @Override
    public void showGui(Player player)
    {
        showGui(player, "Chars", settings.trigger_chars);
    }
}
