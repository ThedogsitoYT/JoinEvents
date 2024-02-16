package me.thedogsito.je.config;

import me.thedogsito.je.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MainConfigManager {
    private CustomConfig configFile;
    private Main plugin;

    private String Prefix;
    private List<String> WelcomeMessageText;
    private Boolean WelcomeMessageEnabled;
    private List<String> MessageWithPermission;
    private List<String> MessageWithPermissionText;
    private List<String> MessageWithPermissionLeaving;
    private List<String> SoundPermission;
    private String Title;
    private String SubTitle;
    private Boolean TitleEnabled;
    private Boolean MessageWithPermissionEnabled;
    private String NotPermission;
    private String NotExist;
    private String ErrorArgumentOfGet;
    private String Reload;
    private String SetHub;
    private Integer HubTeleportingSeconds;
    private String TeleportingHub;
    private String HubTeleported;
    private String NotExistingHub;
    private Integer WarpTeleportingSeconds;
    private String WarpTeleporting;
    private String WarpTeleported;
    private String NotExistingWarp;
    private String WarpError;
    private String SetWarp;
    private String DelWarp;

    public MainConfigManager(Main plugin) {
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml", null, plugin);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = configFile.getConfig();
        Prefix = config.getString("Config.Prefix");

        WelcomeMessageText = config.getStringList("Config.Join.WelcomeMessage.Text");
        MessageWithPermission = config.getStringList("Config.Join.TextForRank.Permission");
        MessageWithPermissionText = config.getStringList("Config.Join.TextForRank.EnteredMessages");
        MessageWithPermissionLeaving = config.getStringList("Config.Join.TextForRank.LeavingMessages");
        SoundPermission = config.getStringList("Config.Join.TextForRank.SoundsJoin");

        WelcomeMessageEnabled = config.getBoolean("Config.Join.WelcomeMessage.Enabled");
        TitleEnabled = config.getBoolean("Config.Join.Titles.Enabled");
        MessageWithPermissionEnabled = config.getBoolean("Config.Join.TextForRank.Enabled");

        Title = config.getString("Config.Join.Titles.Title").replace("%prefix%", getPrefix());
        SubTitle = config.getString("Config.Join.Titles.SubTitle").replace("%prefix%", getPrefix());
        NotPermission = config.getString("Messages.Errors.NotPermission").replace("%prefix%", getPrefix());
        ErrorArgumentOfGet = config.getString("Messages.Errors.ErrorArgumentOfGet").replace("%prefix%", getPrefix());
        NotExist = config.getString("Messages.Errors.NotExist").replace("%prefix%", getPrefix());
        Reload = config.getString("Messages.Reload").replace("%prefix%", getPrefix());
        SetHub = config.getString("Messages.SetHub").replace("%prefix%", getPrefix());
        SetWarp = config.getString("Messages.SetWarp");
        DelWarp = config.getString("Messages.DelWarp");
        TeleportingHub = config.getString("Messages.Hub.HubTeleporting").replace("%prefix%", getPrefix());
        HubTeleported = config.getString("Messages.Hub.HubTeleported").replace("%prefix%", getPrefix());
        NotExistingHub = config.getString("Messages.Hub.NotExistingHub").replace("%prefix%", getPrefix());
        WarpTeleporting = config.getString("Messages.Warps.WarpTeleporting").replace("%prefix%", getPrefix());
        WarpTeleported = config.getString("Messages.Warps.WarpTeleported").replace("%prefix%", getPrefix());
        WarpError = config.getString("Messages.Warps.WarpError").replace("%prefix%", getPrefix());
        NotExistingWarp = config.getString("Messages.Warps.NotExistingWarp").replace("%prefix%", getPrefix());

        HubTeleportingSeconds = config.getInt("Config.Commands.Hub.SecondsWait");
        WarpTeleportingSeconds = config.getInt("Config.Commands.Warps.SecondsWait");
    }

    public String getWarpError() {
        return WarpError;
    }

    public String getDelWarp() {
        return DelWarp;
    }

    public String getSetWarp() {
        return SetWarp;
    }

    public String getWarpTeleporting() {
        return WarpTeleporting;
    }

    public String getWarpTeleported() {
        return WarpTeleported;
    }

    public String getNotExistingWarp() {
        return NotExistingWarp;
    }

    public String getNotExistingHub() {
        return NotExistingHub;
    }

    public String getHubTeleporting() {
        return TeleportingHub;
    }

    public String getHubTeleported() {
        return HubTeleported;
    }

    public Integer getHubTeleportingSeconds() {
        return HubTeleportingSeconds;
    }

    public Integer getWarpTeleportingSeconds() {
        return WarpTeleportingSeconds;
    }

    public String getSetHub() {
        return SetHub;
    }

    public CustomConfig getConfigFile() {
        return configFile;
    }

    public Boolean isTitleEnabled() {
        return TitleEnabled;
    }

    public Main getPlugin() {
        return plugin;
    }

    public String getTitle() {
        return Title;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void reloadConfig() {
        configFile.reloadConfig();
        loadConfig();
    }

    public List<String> getSoundPermission() {
        return SoundPermission;
    }

    public String getPrefix() {
        return Prefix;
    }

    public List<String> getMessageWithPermissionLeaving() {
        return MessageWithPermissionLeaving;
    }

    public Boolean getMessageWithPermissionEnabled() {
        return MessageWithPermissionEnabled;
    }

    public List<String> getWelcomeMessageText() {
        return WelcomeMessageText;
    }

    public Boolean isWelcomeMessageEnabled() {
        return WelcomeMessageEnabled;
    }

    public Boolean getWelcomeMessageEnabled() {
        return WelcomeMessageEnabled;
    }

    public List<String> getMessageWithPermission() {
        return MessageWithPermission;
    }

    public List<String> getMessageWithPermissionText() {
        return MessageWithPermissionText;
    }

    public Boolean isMessageWithPermissionEnabled() {
        return MessageWithPermissionEnabled;
    }

    public String getNotPermission() {
        return NotPermission;
    }

    public String getNotExist() {
        return NotExist;
    }

    public String getErrorArgumentOfGet() {
        return ErrorArgumentOfGet;
    }

    public String getReload() {
        return Reload;
    }
}

