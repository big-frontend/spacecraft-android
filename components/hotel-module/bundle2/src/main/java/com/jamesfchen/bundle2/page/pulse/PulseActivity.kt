package com.jamesfchen.bundle2.page.pulse

import android.app.Activity
import android.os.Bundle
import com.jamesfchen.bundle2.R
import com.jamesfchen.bundle2.databinding.ActivityPulseBinding
import java.util.ArrayList

class PulseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPulseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.memberSendGood.setOnClickListener { binding.heartLayout.addFavor() }

        val be: ArrayList<_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity?> = object : ArrayList<_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity?>() {
            init {
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_full_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_full_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_full_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_full_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_full_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_read_24dp, "启动1"))
                add(_root_ide_package_.com.jamesfchen.bundle2.page.pulse.HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_read_24dp, "启动1"))
            }
        }
        binding.hbvHeartbeat.setDatas(be)
        binding.hbvHeartbeat.startAnimation(4000)
        binding.hbvHeartbeat.setOnClickListener { binding.hbvHeartbeat.startAnimation(2000) }
    }
}