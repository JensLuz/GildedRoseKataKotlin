package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        val legendary = arrayOf("Sulfuras, Hand of Ragnaros")
        val increasingQualityItems = arrayOf("Aged Brie", "Backstage passes to a TAFKAL80ETC concert")
        val conjured = arrayOf("Conjured Mana Cake")
        items.forEach {
            processQuality(it, legendary, increasingQualityItems, conjured)
            processSellIn(it, legendary, increasingQualityItems)
        }
    }

    // Process quality related rules
    private fun processQuality(item: Item, legendary: Array<String>, increasingQualityItems: Array<String>, conjured: Array<String>) {
        // Quality rules for standard items
        if (item.name !in increasingQualityItems) {
            degradeStandardItemQuality(item, legendary, conjured)
        // quality rules for quality increasing items
        } else if (item.name in increasingQualityItems) {
            increaseSpecialItemQuality(item)
        }
        qualityMinMaxCheck(item, legendary)
    }

    // Process sellIn related rules
    private fun processSellIn(item: Item, legendary: Array<String>, increasingQualityItems: Array<String>) {
        // Standard sellIn degradation of non-legendary items
        if (item.name !in legendary) item.sellIn -= 1
        // Below zero degradation
        if (item.sellIn < 0) {
            // Items degrade twice as fast when sellIn < 0
            if (item.name !in increasingQualityItems && item.name !in legendary) item.quality -= 1
            // Backstage pass quality is always zero after expiration
            if (item.name == "Backstage passes to a TAFKAL80ETC concert") item.quality = 0
        }
        qualityMinMaxCheck(item, legendary)
    }

    // Quality value cannot go below zero
    // Quality value cannot go over 50 unless legendary item
    private fun qualityMinMaxCheck(item: Item, legendary: Array<String>) {
        if (item.quality < 0 ) item.quality = 0
        if (item.quality > 50 && item.name !in legendary) item.quality = 50
    }

    private fun degradeStandardItemQuality(item: Item, legendary: Array<String>, conjured: Array<String>) {
        if (item.quality > 0) {
            // Standard quality degradation of non-legendary items
            if (item.name !in legendary) item.quality -= 1
            // Conjured items degrade faster
            if (item.name in conjured) item.quality -= 1
        }
    }
    private fun increaseSpecialItemQuality(item: Item) {
        item.quality = item.quality + 1
        // Backstage pass item has special quality increase rules
        if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
            if (item.sellIn < 11) item.quality += 1
            if (item.sellIn < 6) item.quality += 1
        }
    }
}

