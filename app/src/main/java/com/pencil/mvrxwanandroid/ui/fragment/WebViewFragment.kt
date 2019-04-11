package com.pencil.mvrxwanandroid.ui.fragment

<<<<<<< HEAD
import androidx.navigation.fragment.navArgs
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.views.basicRow


class WebViewFragment : BaseFragment() {


    val args : WebViewFragmentArgs by navArgs()


    override fun epoxyController() = simpleController {

        (0..100).forEach {
            basicRow {
                id(it)
                title(args.title + it)
            }
        }

    }



=======
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pencil.mvrxwanandroid.R


class WebViewFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }
>>>>>>> 8fab216a2c6ea07c9f4cf30ab73f08fc133a172d


}
