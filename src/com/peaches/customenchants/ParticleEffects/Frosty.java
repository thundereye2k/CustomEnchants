package com.peaches.customenchants.ParticleEffects;

import com.peaches.customenchants.Support.Version;
import com.peaches.customenchants.Support.nms.*;
import org.bukkit.entity.Player;

public class Frosty {

    private static final Frosty instance = new Frosty();

    public static Frosty getInstance() {
        return instance;
    }

    public void display(Player p)
    {
        if (Version.getVersion().equals(Version.v1_8_R1)) {
            NMS_v1_8_R1.sendParticle(
                    net.minecraft.server.v1_8_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_8_R2)) {
            NMS_v1_8_R2.sendParticle(
                    net.minecraft.server.v1_8_R2.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_8_R3)) {
            NMS_v1_8_R3.sendParticle(
                    net.minecraft.server.v1_8_R3.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 4.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_9_R1)) {
            NMS_v1_9_R1.sendParticle(
                    net.minecraft.server.v1_9_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_9_R2)) {
            NMS_v1_9_R2.sendParticle(
                    net.minecraft.server.v1_9_R2.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_10_R1)) {
            NMS_v1_10_R1.sendParticle(
                    net.minecraft.server.v1_10_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
        if (Version.getVersion().equals(Version.v1_11_R1)) {
            NMS_v1_11_R1.sendParticle(
                    net.minecraft.server.v1_11_R1.EnumParticle.SNOW_SHOVEL,
                    p.getLocation().add(0.0D, 2.6D, 0.0D), 1.2F, 0.005F, 1.2F, 0.05F,
                    1);
        }
    }
}
