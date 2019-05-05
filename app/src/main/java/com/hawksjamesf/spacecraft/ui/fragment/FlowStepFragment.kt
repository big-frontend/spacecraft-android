package com.hawksjamesf.spacecraft.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hawksjamesf.spacecraft.R

/**
 * Presents how multiple steps flow could be implemented.
 */
class FlowStepFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val flowStepNumber = arguments?.getInt("flowStepNumber")

        // TODO STEP 8 - Use type-safe arguments - remove previous line!
//        val safeArgs = FlowStepFragmentArgs.fromBundle(arguments)
//        val flowStepNumber = safeArgs.flowStepNumber
        // TODO END STEP 8

        return when (flowStepNumber) {
            2 -> inflater.inflate(R.layout.fragment_flow_step_two, container, false)
            else -> inflater.inflate(R.layout.fragment_flow_step_one, container, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.bt_next)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_next)
        )

    }
}