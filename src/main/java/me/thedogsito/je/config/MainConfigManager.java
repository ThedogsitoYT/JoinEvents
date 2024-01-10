package me.thedogsito.je.config;

import me.thedogsito.je.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MainConfigManager {
    private CustomConfig configFile;
    private Main plugin;

    private String PrefixMsg;
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

    public MainConfigManager(Main plugin) {
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml", null, plugin);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = configFile.getConfig();
        PrefixMsg = config.getString("Config.Prefix");

        WelcomeMessageText = config.getStringList("Config.Join.WelcomeMessage.Text");
        MessageWithPermission = config.getStringList("Config.Join.TextForRank.Permission");
        MessageWithPermissionText = config.getStringList("Config.Join.TextForRank.EnteredMessages");
        MessageWithPermissionLeaving = config.getStringList("Config.Join.TextForRank.LeavingMessages");
        SoundPermission = config.getStringList("Config.Join.TextForRank.Sounds");

        WelcomeMessageEnabled = config.getBoolean("Config.Join.WelcomeMessage.Enabled");
        TitleEnabled = config.getBoolean("Config.Join.Titles.Enabled");
        MessageWithPermissionEnabled = config.getBoolean("Config.Join.TextForRank.Enabled");

        Title = config.getString("Config.Join.Titles.Title").replace("%prefix%", getPrefixMsg());
        SubTitle = config.getString("Config.Join.Titles.SubTitle").replace("%prefix%", getPrefixMsg());
        NotPermission = config.getString("Messages.Errors.NotPermission").replace("%prefix%", getPrefixMsg());
        ErrorArgumentOfGet = config.getString("Messages.Errors.ErrorArgumentOfGet").replace("%prefix%", getPrefixMsg());
        NotExist = config.getString("Messages.Errors.NotExist").replace("%prefix%", getPrefixMsg());
        Reload = config.getString("Messages.Reload").replace("%prefix%", getPrefixMsg());
        SetHub = config.getString("Messages.SetHub").replace("%prefix%", getPrefixMsg());
        TeleportingHub = config.getString("Messages.Hub.HubTeleporting").replace("%prefix%", getPrefixMsg());
        HubTeleported = config.getString("Messages.Hub.HubTeleported").replace("%prefix%", getPrefixMsg());
        NotExistingHub = config.getString("Messages.NotExistingHub").replace("%prefix%", getPrefixMsg());

        HubTeleportingSeconds = config.getInt("Config.Commands.Hub.SecondsWait");
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

    public String getPrefixMsg() {
        return PrefixMsg;
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

