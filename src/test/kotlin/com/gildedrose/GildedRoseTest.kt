package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    // Standard rule from GildedRoseRequirements.txt
    // sellIn & quality value goes down when updateQuality is executed
    @Test
    fun sellInAndQualityGoesDownOnUpdate() {
        val items = listOf(Item("Any", 10, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Any", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(9, app.items[0].quality)
    }

    // Special rules from GildedRoseRequirements.txt
    // Once the sell by date has passed, Quality degrades twice as fast
    @Test
    fun qualityGoesDownFasterWhenItemExpired() {
        val items = listOf(Item("Any", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Any", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(8, app.items[0].quality)
    }

    // The Quality of an item is never negative
    @Test
    fun qualityCannotBeNegative() {
        val items = listOf(Item("Any", 10, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Any", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }

    // "Aged Brie" actually increases in Quality the older it gets
    @Test
    fun agedBrieSellInGoesDownAndQualityGoesUp() {
        val items = listOf(Item("Aged Brie", 10, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(11, app.items[0].quality)
    }

    @Test
    fun agedBrieQualityCannotExceed50() {
        val items = listOf(Item("Aged Brie", 10, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)
    }

    // The Quality of an item is never more than 50
    @Test
    fun qualityCannotBeOver50() {
        val items = listOf(Item("Aged Brie", 10, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)
    }

    // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    // "Sulfuras" is a legendary item and as such its Quality is 80 and it never alters.
    @Test
    fun sulfurasNeverExpiresOrLosesQuality() {
        val items = listOf(Item("Sulfuras, Hand of Ragnaros", 25, 80))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name)
        assertEquals(25, app.items[0].sellIn)
        assertEquals(80, app.items[0].quality)
    }

    // 	- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    //	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    //	Quality drops to 0 after the concert
    @Test
    fun backstagePassesQualityIncreaseMoreThan10DaysBeforeExpiration() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 12, 25))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(11, app.items[0].sellIn)
        assertEquals(26, app.items[0].quality)
    }

    @Test
    fun backstagePassesQualityIncrease10DaysBeforeExpiration() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 9, 25))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(8, app.items[0].sellIn)
        assertEquals(27, app.items[0].quality)
    }

    @Test
    fun backstagePassesQualityIncrease5DaysBeforeExpiration() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 4, 25))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(3, app.items[0].sellIn)
        assertEquals(28, app.items[0].quality)
    }

    @Test
    fun backstagePassesQualityToZeroWhenExpired() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 0, 25))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }

    @Test
    fun backstagePassesQualityCannotDropBelowZero() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }

    @Test
    fun backstagePassesQualityCannotExceed50() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 4, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(3, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)
    }

    // "Conjured" items degrade in Quality twice as fast as normal items
    @Test
    fun conjuredItemsDegradeTwiceAsFast() {
        val items = listOf(Item("Conjured Mana Cake", 12, 25))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Conjured Mana Cake", app.items[0].name)
        assertEquals(11, app.items[0].sellIn)
        assertEquals(23, app.items[0].quality)
    }

    @Test
    fun itemsQualityCannotDropBelowZero() {
        val items = listOf(Item("Conjured Mana Cake", 12, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("Conjured Mana Cake", app.items[0].name)
        assertEquals(11, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }
}


