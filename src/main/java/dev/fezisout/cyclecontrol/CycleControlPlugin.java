package dev.fezisout.cyclecontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CycleControlPlugin extends JavaPlugin implements Listener {
    private static final double MINIMUM_CYCLE_MINUTES = 1.0;
    private static final double MAXIMUM_CYCLE_MINUTES = 1440.0;

    private final Map<UUID, Boolean> originalDaylightCycleValues = new HashMap<>();
    private BukkitTask clockTask;
    private double cycleMinutes;
    private double clockAccumulator;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        cycleMinutes = getConfig().getDouble("cycle-minutes", 40.0);
        if (!isValidDuration(cycleMinutes)) {
            getLogger().warning("Invalid cycle-minutes in config.yml; using 40 minutes.");
            cycleMinutes = 40.0;
        }

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getWorlds().forEach(this::startManaging);

        clockTask = Bukkit.getScheduler().runTaskTimer(this, this::advanceClocks, 1L, 1L);

        getLogger().info("The complete day-night cycle now lasts " + formatMinutes(cycleMinutes) + " minutes.");
    }

    @Override
    public void onDisable() {
        if (clockTask != null) {
            clockTask.cancel();
        }

        Bukkit.getWorlds().forEach(this::stopManaging);
        originalDaylightCycleValues.clear();
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        startManaging(event.getWorld());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        stopManaging(event.getWorld());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "The complete day-night cycle is "
                    + ChatColor.YELLOW + formatMinutes(cycleMinutes) + " minutes"
                    + ChatColor.GOLD + " (" + formatMinutes(cycleMinutes / 2.0) + " minutes per half)." );
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        final double requestedMinutes;
        try {
            requestedMinutes = Double.parseDouble(args[0]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.RED + "Please enter a number of minutes.");
            return true;
        }

        if (!isValidDuration(requestedMinutes)) {
            sender.sendMessage(ChatColor.RED + "The cycle must be between 1 and 1440 minutes.");
            return true;
        }

        cycleMinutes = requestedMinutes;
        clockAccumulator = 0.0;
        getConfig().set("cycle-minutes", cycleMinutes);
        saveConfig();

        sender.sendMessage(ChatColor.GREEN + "The complete day-night cycle is now "
                + formatMinutes(cycleMinutes) + " minutes ("
                + formatMinutes(cycleMinutes / 2.0) + " minutes each for day and night)." );
        return true;
    }

    private void advanceClocks() {
        // A Minecraft cycle contains 24,000 time units. At 20 TPS, there are
        // 1,200 server ticks per real-world minute.
        clockAccumulator += 20.0 / cycleMinutes;
        long timeUnits = (long) clockAccumulator;
        if (timeUnits == 0L) {
            return;
        }

        clockAccumulator -= timeUnits;
        Bukkit.getWorlds().forEach(world -> world.setFullTime(world.getFullTime() + timeUnits));
    }

    private boolean isValidDuration(double minutes) {
        return Double.isFinite(minutes)
                && minutes >= MINIMUM_CYCLE_MINUTES
                && minutes <= MAXIMUM_CYCLE_MINUTES;
    }

    private String formatMinutes(double minutes) {
        if (minutes == Math.rint(minutes)) {
            return Long.toString((long) minutes);
        }
        return Double.toString(minutes);
    }

    private void startManaging(World world) {
        originalDaylightCycleValues.computeIfAbsent(world.getUID(), ignored -> {
            Boolean currentValue = world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
            return currentValue == null || currentValue;
        });
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    private void stopManaging(World world) {
        Boolean originalValue = originalDaylightCycleValues.remove(world.getUID());
        if (originalValue != null) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, originalValue);
        }
    }
}
