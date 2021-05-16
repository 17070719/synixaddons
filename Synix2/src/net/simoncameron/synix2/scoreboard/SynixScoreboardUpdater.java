package net.simoncameron.synix2.scoreboard;
import me.qiooip.lazarus.config.Config;
import me.qiooip.lazarus.games.king.KillTheKingManager;
import me.qiooip.lazarus.Lazarus;
import me.qiooip.lazarus.scoreboard.PlayerScoreboard;
import me.qiooip.lazarus.scoreboard.task.ScoreboardUpdater;
import me.qiooip.lazarus.userdata.Userdata;
import me.qiooip.lazarus.userdata.UserdataManager;
import me.qiooip.lazarus.utils.Tasks;
import net.simoncameron.synix2.placeholders.InternalPlaceholderParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SynixScoreboardUpdater implements ScoreboardUpdater {

    private ScoreboardManager scoreboardManager;

    private ScheduledThreadPoolExecutor executor;
    private ScheduledFuture<?> updater;
    private final Lazarus instance;
   

    public SynixScoreboardUpdater(ScoreboardManager manager) {
        this.scoreboardManager = manager;
        this.instance = Lazarus.getInstance();
        Tasks.syncLater(this::setupTasks, 10L);
    }

    public void cancel() {
       if(this.updater != null) this.updater.cancel(true);
       if(this.executor != null) this.executor.shutdownNow();
    }

    private void setupTasks() {
        this.executor = new ScheduledThreadPoolExecutor(2, Tasks.newThreadFactory("Scoreboard Thread - %d"));
        this.executor.setRemoveOnCancelPolicy(true);
        this.updater = this.executor.scheduleAtFixedRate(this, 0L, 100L, TimeUnit.MILLISECONDS);
    }
    
    
   @Override
    public void run() {
	   try {
        for (Player player : Bukkit.getOnlinePlayers()) {
            System.out.println("Scoreboard plugin run");
            
        	instance.getScoreboardManager().setUpdater(this);
        	
            System.out.println("Player" + player.getName());
            
            PlayerScoreboard scoreboard = null; 
            
            do {
             scoreboard = instance.getScoreboardManager().getPlayerScoreboard(player);
            }
            while (scoreboard == null);
            System.out.println("Got Scoreboard ???");
            if (scoreboard == null) return;
            System.out.println("Got Scoreboard");
            scoreboard.clear();
            Userdata data = instance.getUserdataManager().getUserdata(player);
            if (data == null) return;
            System.out.println("Got Data");
            System.out.println(data.getSettings().isScoreboard());
            if (!data.getSettings().isScoreboard()) {
            	 System.out.println("Score board update");
                scoreboard.update();
                continue;
            }
            for (String string : scoreboardManager.getNormalScoreboard().getLines(player))
            {
            	 System.out.println("Score board add : " + string);
               //  System.out.println("Placeholder: " + InternalPlaceholderParser.parsePlaceholders(string, player));
                scoreboard.add( string,"");
                scoreboard.addLine(ChatColor.GRAY);
                
            }
           // System.out.println("Scoreboard Update");
           // scoreboard.update();
           
            if (!scoreboard.isEmpty()) {
                scoreboard.addLinesAndFooter();
                scoreboard.setUpdate(true);
            }
            System.out.println("Scoreboard Update");
            scoreboard.update();
        }
	   }
	   catch(Throwable t) {
		   t.printStackTrace();
	   }
    }

}
