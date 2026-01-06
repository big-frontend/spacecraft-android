package com.electrolytej.main.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.electrolytej.main.R
import com.electrolytej.main.databinding.FragmentBrowserBinding
import com.electrolytej.util.dp
import com.electrolytej.widget.recyclerview.layoutmanager.FixedGridLayoutManager
import com.electrolytej.widget.recyclerview.layoutmanager.StackPageLayoutManager
import com.electrolytej.widget.viewpager2.transformer.StackPageTransformer
import com.electrolytej.widget.viewpager2.transformer.StereoPagerTransformer
import com.electrolytej.widget.viewpager2.transformer.StereoPagerVerticalTransformer
import com.electrolytej.widget.viewpager2.transformer.TransAlphScaleFormer
import kotlin.math.ceil

class BrowserFragment : Fragment() {
    lateinit var binding: FragmentBrowserBinding

    private var preset: PagerTransformerPresets.Preset = PagerTransformerPresets.Preset.COOL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bt.setOnClickListener {
            findNavController().navigate(R.id.action_search)
        }
        val options = RequestOptions().format(DecodeFormat.PREFER_RGB_565)
        Glide.with(this)
            .load("https://nb-ssp.oss-cn-beijing.aliyuncs.com/1648699553998828f6f50-79c1-11ec-92c0-01612323b7a1.png")
            .apply(options)
            .into(binding.ivP)

        val a = Adapter()
        a.setDataList(mutableListOf(1, 2, 3, 4, 5, 6, 6, 7))
        binding.pager.adapter = a
//        effect1()
        effect2()
        val a1 = Adapter()
        a1.setDataList(mutableListOf(1, 2, 3, 4, 5, 6, 6, 7))
        binding.rv.adapter = a1
        val fixedGridLayoutManager = FixedGridLayoutManager()
        FixedGridLayoutManager().setTotalColumnCount(3)
        val stackPageLayoutManager = StackPageLayoutManager(requireContext()).apply {
            maxVisibleCount = 3
            scaleStep = 0.06f
            stackYOffsetPx = 0

            // 每层一个绝对 offset 示例（不累加）
            setItemSpacingPerLayerPx(intArrayOf(0, 40, 90), StackPageLayoutManager.SpacingMode.ABSOLUTE)

            // 或者累加模式
            // setItemSpacingPerLayerPx(intArrayOf(0, 24, 52), StackPageLayoutManager.SpacingMode.CUMULATIVE)
        }
        binding.rv.layoutManager  = stackPageLayoutManager
    }

    private fun effect1() {
        // 长按按钮循环切换预设，方便你快速对比效果
        binding.bt.setOnLongClickListener {
            val preset = when (preset) {
                PagerTransformerPresets.Preset.CALM -> PagerTransformerPresets.Preset.COOL
                PagerTransformerPresets.Preset.COOL -> PagerTransformerPresets.Preset.INSANE
                PagerTransformerPresets.Preset.INSANE -> PagerTransformerPresets.Preset.CALM
            }
            binding.pager.setPageTransformer(PagerTransformerPresets.create(preset))
            true
        }
        binding.pager.setPageTransformer(PagerTransformerPresets.create(preset))

        // 为了让 transformer 看到左右页，建议配合关闭裁剪
        binding.pager.clipToPadding = false
        binding.pager.clipChildren = false
    }

    private fun effect2() {
        binding.pager.offscreenPageLimit = 3

        val compositePageTransformer = CompositePageTransformer()

        val stackTransformer = StackPageTransformer(binding.pager.offscreenPageLimit).apply {
            // CUMULATIVE：你传入的是每一层的“增量”，最终会做 1..layer 累加
            // 例如下面：
            // - 第二张总 offset = 28dp
            // - 第三张总 offset = 28 + 18 = 46dp
            // - 第四张总 offset = 28 + 18 + 12 = 58dp
            setSpacingMode(StackPageTransformer.SpacingMode.CUMULATIVE)
//            setItemSpacingPerLayerDp(28f, 18f, 12f)
            // 为了让 transformer 看到左右页，建议配合关闭裁剪

            // 落地 ABSOLUTE：每一层的 offset 是“绝对值”，不做累加
//            setSpacingMode(StackPageTransformer.SpacingMode.ABSOLUTE)
            // 第二张/第三张/第四张分别露出多少（dp）：按视觉喜好调整
//            setItemSpacingPerLayerDp(18f, 28f, 38f)
//            setItemSpacingPerLayerDp(24f, 40f, 56f)

            setItemSpacingDp(28f)
        }
//        compositePageTransformer.addTransformer(stackTransformer)
//        compositePageTransformer.addTransformer(ZoomOutPageTransformer())
//        compositePageTransformer.addTransformer(StereoPagerTransformer(200f.dp.toFloat()))
//        compositePageTransformer.addTransformer(StereoPagerVerticalTransformer(200f.dp.toFloat()))
        compositePageTransformer.addTransformer(TransAlphScaleFormer())

        // 自动根据“最深层”的偏移给 pager 留出露边空间（只设置 paddingEnd，不动 paddingStart）
        // 贴边：不再额外加 basePadding
        val desiredPaddingEnd =
            ceil(stackTransformer.getMaxStackOffsetPx()).toInt().coerceAtLeast(0)
        binding.pager.apply {
            clipToPadding = false
            clipChildren = false
            updatePadding(right = desiredPaddingEnd)
        }
        binding.pager.setPageTransformer(compositePageTransformer)

    }
}