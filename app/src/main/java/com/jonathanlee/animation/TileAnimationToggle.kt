package com.jonathanlee.animation

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

class TileAnimationToggle : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        val contentResolver = applicationContext.contentResolver
        val currentState = SystemAnimationHelper.getAnimation(contentResolver)
        if (currentState) {
            SystemAnimationHelper.setAnimation(contentResolver, status = false)
            Toast.makeText(applicationContext, "Animation Disabled", Toast.LENGTH_SHORT).show()
            qsTile.state = Tile.STATE_INACTIVE
        } else {
            SystemAnimationHelper.setAnimation(contentResolver, status = true)
            Toast.makeText(applicationContext, "Animation Enabled", Toast.LENGTH_SHORT).show()
            qsTile.state = Tile.STATE_ACTIVE
        }
    }

    private fun updateTile() {
        val contentResolver = applicationContext.contentResolver
        val currentState = SystemAnimationHelper.getAnimation(contentResolver)
        if (currentState) {
            if (qsTile.state == Tile.STATE_ACTIVE) {
                qsTile.state = Tile.STATE_INACTIVE
            }
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
    }
}