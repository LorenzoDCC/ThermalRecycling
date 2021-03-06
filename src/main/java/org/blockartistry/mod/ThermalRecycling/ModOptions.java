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

package org.blockartistry.mod.ThermalRecycling;

import java.util.HashMap;

import org.blockartistry.mod.ThermalRecycling.support.SupportedMod;

import net.minecraftforge.common.config.Configuration;

public final class ModOptions {

	protected static final String CATEGORY_RECYCLE_ENABLE = "recycle.enable";
	protected static final String CATEGORY_RECYCLE_RECIPIES_CONTROL = "recycle.recipe.control";
	protected static final String CATEGORY_LOGGING_CONTROL = "logging";
	protected static final String CONFIG_ENABLE_RECIPE_LOGGING = "Enable Recipe Logging";

	protected static HashMap<SupportedMod, Boolean> enableModProcessing = new HashMap<SupportedMod, Boolean>();
	protected static boolean enableRecipeLogging = true;

	public static void load(Configuration config) {

		enableRecipeLogging = config
				.getBoolean(CONFIG_ENABLE_RECIPE_LOGGING,
						CATEGORY_LOGGING_CONTROL, enableRecipeLogging,
						"Enables/disables logging of recipes to the Forge log during startup");

		for (SupportedMod mod : SupportedMod.values()) {

			boolean b = config
					.getBoolean(
							mod.getModId(),
							CATEGORY_RECYCLE_ENABLE,
							true,
							String.format(
									"Enable/Disable whether recycling recipes for items from [%s] are added",
									mod.getName()));

			enableModProcessing.put(mod, b);
		}
	}

	public static boolean getEnableRecipeLogging() {
		return enableRecipeLogging;
	}

	public static boolean getModProcessingEnabled(SupportedMod mod) {
		Boolean result = enableModProcessing.get(mod);
		return result == null ? false : result;
	}
}
