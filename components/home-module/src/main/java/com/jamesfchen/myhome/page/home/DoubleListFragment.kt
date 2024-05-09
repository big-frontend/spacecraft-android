package com.jamesfchen.myhome.page.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jamesfchen.mvp.RxFragment
import com.jamesfchen.myhome.GalleryActivity
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentDoubleListBinding

class DoubleListFragment : RxFragment<DoubleListPresenter>(), DoubleListContract.View {
    lateinit var binding: FragmentDoubleListBinding
    override fun createPresenter(): DoubleListPresenter {
        return DoubleListPresenter()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoubleListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = NavOptions.Builder() //                .setEnterAnim(R.anim.)
            //                .setExitAnim(R.anim.)
            //                .setPopEnterAnim(R.anim.)
            //                .setPopExitAnim(R.anim.)
            .build()
        val navController = NavController(requireActivity())
        //        navController.navigate(R.id.action_step_two);
//        navController.navigate(R.id.fragment_flow_step_one_dest);
//        navController.navigate(R.id.action_step_two, null, options);
        navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination?, arguments: Bundle? ->
            val currentDestination = navController.currentDestination
            val graph = navController.graph
        }
        Navigation.setViewNavController(view, navController)
        view.findViewById<View>(R.id.bt_navigate_destination).setOnClickListener { view1: View? ->
//            Navigation.findNavController(
//                view1!!
//            ).navigate(R.id.fragment_flow_step_one_dest, null, options)
        }
        //        view.findViewById(R.id.bt_navigate_action).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_step_two)
//        );
        val abl = view.findViewById<AppBarLayout>(R.id.abl)
        abl.addOnOffsetChangedListener { appBarLayout, verticalOffset -> }
        val ctbl = view.findViewById<CollapsingToolbarLayout>(R.id.ctbl)

        binding.rv.adapter = MyAdapter()
        binding.rv.layoutManager = LinearLayoutManager(binding.rv.context, RecyclerView.VERTICAL, false)
        val layoutParams = binding.rv.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior as AppBarLayout.ScrollingViewBehavior?
        behavior!!.overlayTop = 120
        binding.lv.isNestedScrollingEnabled = true
        binding.lv.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return 23
            }

            override fun getItem(position: Int): Any {
                return Any()
            }

            override fun getItemId(position: Int): Long {
                return 0
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                Log.d("ParallaxActivity", "ListView:getView:position$position")
                val myViewHolder: MyViewHolder
                if (convertView == null) {
                    val itemView: View = LayoutInflater.from(activity)
                        .inflate(R.layout.item_image_and_text, parent, false)
                    myViewHolder = MyViewHolder(itemView)
                    itemView.tag = myViewHolder
                    convertView = itemView
                } else {
                    myViewHolder = convertView.tag as MyViewHolder
                }
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                myViewHolder.tvText.text = "ListView:position:$position"
                return convertView
            }
        }
    }

    inner class MyAdapter :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_and_text, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tvText.text = "asfasdfas"
            holder.tvText.setOnClickListener { v: View? ->
                val i = Intent(activity, GalleryActivity::class.java)
                startActivity(i)
            }
        }

        override fun getItemCount(): Int {
            return 13
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvText: TextView = itemView.findViewById(R.id.tv_text)
    }
}