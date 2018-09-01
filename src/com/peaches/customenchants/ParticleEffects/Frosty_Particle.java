package com.peaches.customenchants.ParticleEffects;

import com.peaches.customenchants.Support.Version;
import com.peaches.customenchants.Support.nms.*;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Frosty_Particle {

    private static final Frosty_Particle instance = new Frosty_Particle();

    public static Frosty_Particle getInstance() {
        return instance;
    }

    public void display(Player p)
    {
        if (Version.getVersion().equals(Version.v1_8_R1)) {
            NMS_v1_8_R1.sendParticle(
                    net.minecraft.server.v1_8_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_8_R2)) {
            NMS_v1_8_R2.sendParticle(
                    net.minecraft.server.v1_8_R2.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_8_R3)) {
            NMS_v1_8_R3.sendParticle(
                    net.minecraft.server.v1_8_R3.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 4.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_9_R1)) {
            NMS_v1_9_R1.sendParticle(
                    net.minecraft.server.v1_9_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_9_R2)) {
            NMS_v1_9_R2.sendParticle(
                    net.minecraft.server.v1_9_R2.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_10_R1)) {
            NMS_v1_10_R1.sendParticle(
                    net.minecraft.server.v1_10_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if (Version.getVersion().equals(Version.v1_11_R1)) {
            NMS_v1_11_R1.sendParticle(
                    net.minecraft.server.v1_11_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    10);
        }
        if(Version.getVersion().equals(Version.v1_13_R1)){
            p.spawnParticle(Particle.SNOW_SHOVEL, p.getLocation().add(0.0D, 2.6D, 0.0D),  10);

        }
    }
}
