/*
 *
 * Copyright 2003 (C) Chris Ward <frugal@purplewombat.co.uk>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	   See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pcgen.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import pcgen.cdom.enumeration.ListKey;
import pcgen.core.bonus.Bonus;
import pcgen.core.bonus.BonusObj;
import pcgen.core.prereq.Prerequisite;
import pcgen.rules.context.LoadContext;
import pcgen.util.TestHelper;
import plugin.lsttokens.testsupport.TokenRegistration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Equipment Modifer Test 
 */
public class EquipmentModifierTest
{
	/**
	 * Starts the system plugins.
	 */
	@BeforeEach
	public void setUp() throws Exception
	{
		TestHelper.loadPlugins();
	}

	@AfterEach
	public void tearDown()
	{
		TokenRegistration.clearTokens();
	}

	/**
	 * Test +13
	 */
	@Test
	public void test885958A()
	{
		LoadContext context = Globals.getContext();

		final EquipmentModifier eqMod = new EquipmentModifier();
		final BonusObj aBonus =
				Bonus.newBonus(context, "WEAPON|DAMAGE|((%CHOICE)MIN(STR))");
		eqMod.addToListFor(ListKey.BONUS, aBonus);

		final Equipment e = new Equipment();
		e.addAssociation(eqMod, "+13");
		
		for (BonusObj bonusObj : eqMod.getBonusList(e))
		{
			assertEquals("((+13)MIN(STR))", bonusObj.getValue());
		}
		assertEquals("((%CHOICE)MIN(STR))", aBonus.getValue());
	}

	/**
	 * Test -2 and +13
	 */
	@Test
	public void test885958B()
	{
		LoadContext context = Globals.getContext();

		final EquipmentModifier eqMod = new EquipmentModifier();
		final BonusObj aBonus =
				Bonus.newBonus(context, "WEAPON|TOHIT|-2|PREVARGT:%CHOICE,STR");

		final Equipment e = new Equipment();
		
		e.addAssociation(eqMod, "+13");
		eqMod.addToListFor(ListKey.BONUS, aBonus);

		for (BonusObj bonusObj : eqMod.getBonusList(e))
		{
			assertEquals("-2", bonusObj.getValue());

			final Prerequisite prereq = bonusObj.getPrerequisiteList().get(0);
			assertEquals("+13", prereq.getKey());
			assertEquals("STR", prereq.getOperand());
		}
		assertEquals("-2", aBonus.getValue());
		final Prerequisite prereq = aBonus.getPrerequisiteList().get(0);
		assertEquals("%CHOICE", prereq.getKey());
	}

	/**
	 * Test the expansion of the %CHOICE in a prereq for a bonus. Note as the
	 * options for the choice are processed in reverse order, we have to check the
	 * values in reverse order.
	 */
	@Test
	public void testChoice()
	{
		LoadContext context = Globals.getContext();

		final EquipmentModifier eqMod = new EquipmentModifier();
		final BonusObj aBonus =
				Bonus.newBonus(context, "WEAPON|TOHIT|-2|PREVARGT:%CHOICE,STR");

		final Equipment e = new Equipment();

		e.addAssociation(eqMod, "+1");
		e.addAssociation(eqMod, "+2");
		eqMod.addToListFor(ListKey.BONUS, aBonus);

		final List<BonusObj> list = eqMod.getBonusList(e);
		for (int j = list.size() - 1; j > 0; j--)
		{
			final BonusObj bonusObj = list.get(j);
			assertEquals("-2", bonusObj.getValue());

			final Prerequisite prereq = bonusObj.getPrerequisiteList().get(0);
			assertEquals("+" + (j + 1), prereq.getKey());
			assertEquals("STR", prereq.getOperand());
		}
		assertEquals("-2", aBonus.getValue());
		final Prerequisite prereq = aBonus.getPrerequisiteList().get(0);
		assertEquals("%CHOICE", prereq.getKey());
	}
}
