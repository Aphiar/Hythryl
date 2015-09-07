package mineward.core.npc;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class NPCManager {

	public static void Vegetate(Entity entity) {
		net.minecraft.server.v1_8_R3.Entity e = ((CraftEntity) entity)
				.getHandle();
		NBTTagCompound tag = new NBTTagCompound();
		e.c(tag);
		tag.setBoolean("NoAI", true);
		tag.setInt("Fire", 0);
		e.f(tag);
	}

}
