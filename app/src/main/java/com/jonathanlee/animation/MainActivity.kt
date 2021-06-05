package com.jonathanlee.animation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jonathanlee.animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val OFF = "Off"
        private const val ON = "On"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun areSystemAnimationsEnabled(): Boolean {
        return SystemAnimationHelper.getAnimation(contentResolver)
    }

    private fun isPermissionGiven(): Boolean {
        return SystemAnimationHelper.isPermissionGiven(this)
    }

    private fun setAnimation(on: Boolean) {
        if (isPermissionGiven()) {
            SystemAnimationHelper.setAnimation(contentResolver, on)
            when {
                SystemAnimationHelper.isSetAnimationOn() == true -> {
                    binding.tvInfo.text = ON
                }
                SystemAnimationHelper.isSetAnimationOn() == false -> {
                    binding.tvInfo.text = OFF
                }
                else -> {
                    return
                }
            }
        } else {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            binding.switchAnimation.isChecked = false
        }
    }

    private fun initView() {
        if (areSystemAnimationsEnabled()) {
            binding.apply {
                tvInfo.text = ON
                switchAnimation.isChecked = true
            }
        } else {
            binding.apply {
                tvInfo.text = OFF
                switchAnimation.isChecked = false
            }
        }
        binding.switchAnimation.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setAnimation(true)
            } else {
                setAnimation(false)
            }
        }
        binding.buttonCheck.setOnClickListener {
            if (isPermissionGiven()) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}