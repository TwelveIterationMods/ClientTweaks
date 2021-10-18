package net.blay09.mods.clienttweaks;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(ClientTweaks.MOD_ID)
public class ForgeClientTweaks {

    public ForgeClientTweaks() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientTweaks::initialize);
    }

}
