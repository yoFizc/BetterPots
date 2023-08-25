package dev.fiz.betterpots;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class BetterPots extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("betterpots").setExecutor(new BetterPotsCommand(this));
        getCommand("betterpots").setTabCompleter(new BetterPotsCommand(this));
    }


    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (e.getEntityType() == EntityType.SPLASH_POTION) {
            Projectile projectile = e.getEntity();

            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {

                Vector velocity = projectile.getVelocity();

                velocity.setY(velocity.getY() - getConfig().getDouble("PotSpeed"));
                projectile.setVelocity(velocity);

            }
        }
    }

    @EventHandler
    public void onSplash(PotionSplashEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) e.getEntity().getShooter();

            if (shooter.isSprinting() && e.getIntensity(shooter) > 0.5D) {
                e.setIntensity(shooter, 1.0D);
            }
        }
    }

}
