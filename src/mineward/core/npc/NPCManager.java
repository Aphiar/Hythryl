package mineward.core.npc;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class NPCManager {

	public static void Vegetate(Entity entity) {
		try {
			if (((CraftEntity) entity).getHandle() instanceof EntityInsentient) {
				EntityInsentient e = (EntityInsentient) ((CraftEntity) entity)
						.getHandle();
				Field fieldGoals = EntityInsentient.class
						.getDeclaredField("goalSelector");
				Field fieldTargets = EntityInsentient.class
						.getDeclaredField("targetSelector");
				fieldGoals.setAccessible(true);
				fieldTargets.setAccessible(true);
				PathfinderGoalSelector goalSelector = (PathfinderGoalSelector) fieldGoals
						.get(e);
				PathfinderGoalSelector targetSelector = (PathfinderGoalSelector) fieldTargets
						.get(e);
				Field goalbList = PathfinderGoalSelector.class
						.getDeclaredField("b");
				Field goalcList = PathfinderGoalSelector.class
						.getDeclaredField("c");
				goalbList.setAccessible(true);
				goalcList.setAccessible(true);
				List<?> gbl = (List<?>) goalbList.get(goalSelector);
				List<?> gcl = (List<?>) goalcList.get(goalSelector);
				List<?> tbl = (List<?>) goalbList.get(targetSelector);
				List<?> tcl = (List<?>) goalcList.get(targetSelector);
				gbl.clear();
				gcl.clear();
				tbl.clear();
				tcl.clear();
				goalbList.set(goalSelector, gbl);
				goalcList.set(goalSelector, gcl);
				goalbList.set(targetSelector, tbl);
				goalcList.set(targetSelector, tcl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
