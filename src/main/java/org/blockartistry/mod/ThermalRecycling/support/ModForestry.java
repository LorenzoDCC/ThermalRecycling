/*
 * This file is part of ThermalRecycling, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.ThermalRecycling.support;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.blockartistry.mod.ThermalRecycling.data.ItemInfo;
import org.blockartistry.mod.ThermalRecycling.data.ItemScrapData;
import org.blockartistry.mod.ThermalRecycling.data.ScrapValue;
import org.blockartistry.mod.ThermalRecycling.support.recipe.RecipeDecomposition;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackHelper;

public class ModForestry extends ModPlugin {

	static final String[] recipeIgnoreList = new String[] { "Forestry:log1",
			"Forestry:log2", "Forestry:log3", "Forestry:log4", "Forestry:log5",
			"Forestry:log6", "Forestry:log7", "Forestry:log8",
			"Forestry:fireproofLog1", "Forestry:fireproofLog2",
			"Forestry:fireproofLog3", "Forestry:fireproofLog4",
			"Forestry:fireproofLog5", "Forestry:fireproofLog6",
			"Forestry:fireproofLog7", "Forestry:fireproofLog8",
			"Forestry:planks", "Forestry:planks2", "Forestry:fireproofPlanks1",
			"Forestry:fireproofPlanks2", "Forestry:slabs1", "Forestry:slabs2",
			"Forestry:slabs3", "Forestry:slabs4", "Forestry:fences",
			"Forestry:fences2", "Forestry:stairs", "Forestry:stamps",
			"Forestry:letters", "Forestry:crate", "Forestry:waxCast",
			"Forestry:apiculture", "Forestry:arboriculture",
			"Forestry:lepidopterology", "Forestry:soil",
			"Forestry:honeyedSlice", "Forestry:beeCombs", "Forestry:apatite",
			"Forestry:fertilizerCompound", "Forestry:fertilizerBio",
			"Forestry:carton" };

	static final String[] scrapValuesNone = new String[] { "Forestry:log1",
			"Forestry:log2", "Forestry:log3", "Forestry:log4", "Forestry:log5",
			"Forestry:log6", "Forestry:log7", "Forestry:log8",
			"Forestry:fireproofLog1", "Forestry:fireproofLog2",
			"Forestry:fireproofLog3", "Forestry:fireproofLog4",
			"Forestry:fireproofLog5", "Forestry:fireproofLog6",
			"Forestry:fireproofLog7", "Forestry:fireproofLog8",
			"Forestry:waxCapsule", "Forestry:refractoryEmpty",
			"Forestry:beeDroneGE", "Forestry:propolis", "Forestry:sapling",
			"Forestry:phosphor", "Forestry:beeswax", "Forestry:refractoryWax",
			"Forestry:fruits", "Forestry:honeyDrop", "Forestry:honeydew",
			"Forestry:royalJelly", "Forestry:waxCast", "Forestry:beeCombs",
			"Forestry:woodPulp", "Forestry:oakStick", "Forestry:carton",
			"Forestry:planks", "Forestry:planks2", "Forestry:fireproofPlanks1",
			"Forestry:fireproofPlanks2", "Forestry:slabs1", "Forestry:slabs2",
			"Forestry:slabs3", "Forestry:slabs4", "Forestry:fences",
			"Forestry:fences2", "Forestry:stairs", "Forestry:stamps",
			"Forestry:letters", "Forestry:crate", "Forestry:waxCast",
			"Forestry:leaves", "Forestry:stained",
			"Forestry:fertilizerCompound", "Forestry:fertilizerBio" };

	static final String[] scrapValuesPoor = new String[] {
			"Forestry:beeLarvaeGE", "Forestry:pollen", "Forestry:apatite", };

	static final String[] scrapValuesStandard = new String[] {
			"Forestry:butterflyGE", "Forestry:beePrincessGE",
			"Forestry:beeQueenGE", };

	static final String[] scrapValuesSuperior = new String[] {};

	public ModForestry() {
		super(SupportedMod.FORESTRY);
	}

	protected void registerForestryRecipes(Map<Object[], Object[]> entry) {
		for (Entry<Object[], Object[]> e : entry.entrySet()) {
			if (e.getValue().length == 1
					&& e.getValue()[0] instanceof ItemStack) {
				ItemStack stack = (ItemStack) e.getValue()[0];
				if (!ItemInfo.isRecipeIgnored(stack))
					recycler.useRecipe(
							new RecipeDecomposition(stack, e.getKey())).save();
			}
		}
	}

	@Override
	public void apply() {

		registerRecipesToIgnore(recipeIgnoreList);
		registerScrapValues(scrapValuesNone, ScrapValue.NONE);
		registerScrapValues(scrapValuesPoor, ScrapValue.POOR);
		registerScrapValues(scrapValuesStandard, ScrapValue.STANDARD);
		registerScrapValues(scrapValuesSuperior, ScrapValue.SUPERIOR);

		// Scan the item registry looking for "crated" things - we want
		// to blacklist recipes and set scrap value to POOR. Should
		// get something for the effort of making these crates.
		for (Object o : Item.itemRegistry.getKeys()) {
			String itemName = (String) o;
			if (itemName.startsWith("Forestry:crated")) {
				ItemStack stack = ItemStackHelper.getItemStack(itemName);
				ItemScrapData data = ItemInfo.get(stack);
				data.setIgnoreRecipe(true);
				data.setScrubFromOutput(true);
				data.setValue(ScrapValue.POOR);
				ItemInfo.put(data);
			}
		}

		// Dig into the Forestry crafting data and extract additional recipes
		registerForestryRecipes(forestry.api.recipes.RecipeManagers.carpenterManager
				.getRecipes());
		registerForestryRecipes(forestry.api.recipes.RecipeManagers.fabricatorManager
				.getRecipes());

		pulverizer.setEnergy(1200).append("Forestry:saplingGE", 8)
				.output(Blocks.dirt).save();

		pulverizer.setEnergy(1200).append("Forestry:sapling", 8)
				.output(Blocks.dirt).save();

		// Machine casings
		sawmill.append("Forestry:impregnatedCasing").output(Blocks.planks, 32)
				.save();
		pulverizer.append("Forestry:sturdyMachine").output("ingotBronze", 8)
				.save();
		pulverizer.append("Forestry:hardenedMachine").output("ingotBronze", 8)
				.secondaryOutput(Items.diamond, 4).save();

		// Survivalist tools
		furnace.append("Forestry:bronzePickaxe", "Forestry:kitPickaxe")
				.output("ingotBronze", 3).save();
		furnace.append("Forestry:bronzeShovel", "Forestry:kitShovel")
				.output("ingotBronze").save();

		// Misc
		pulverizer.setEnergy(200).append("Forestry:canEmpty")
				.output("nuggetTin", 2).secondaryOutput("nuggetTin").chance(10)
				.save();
	}
}