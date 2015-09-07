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
				Field fieldGoals = e.getClass()
						.getDeclaredField("goalSelector");
				Field fieldTargets = e.getClass().getDeclaredField(
						"targetSelector");
				fieldGoals.setAccessible(true);
				fieldTargets.setAccessible(true);
				PathfinderGoalSelector goalSelector = (PathfinderGoalSelector) fieldGoals
						.get(e);
				PathfinderGoalSelector targetSelector = (PathfinderGoalSelector) fieldTargets
						.get(e);
				Field goalbList = goalSelector.getClass().getDeclaredField("b");
				Field goalcList = goalSelector.getClass().getDeclaredField("c");
				Field targetbList = targetSelector.getClass().getDeclaredField(
						"b");
				Field targetcList = targetSelector.getClass().getDeclaredField(
						"c");
				goalbList.setAccessible(true);
				goalcList.setAccessible(true);
				targetbList.setAccessible(true);
				targetcList.setAccessible(true);
				List<?> gbl = (List<?>) goalbList.get(goalSelector);
				List<?> gcl = (List<?>) goalcList.get(goalSelector);
				List<?> tbl = (List<?>) targetbList.get(targetSelector);
				List<?> tcl = (List<?>) targetcList.get(targetSelector);
				gbl.clear();
				gcl.clear();
				tbl.clear();
				tcl.clear();
				goalbList.set(goalSelector, gbl);
				goalcList.set(goalSelector, gcl);
				targetbList.set(targetSelector, tbl);
				targetcList.set(targetSelector, tcl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
