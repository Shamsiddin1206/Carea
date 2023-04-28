package shamsiddin.project.carea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shamsiddin.project.carea.databinding.FragmentLoadingSplashBinding
import shamsiddin.project.carea.databinding.FragmentMainSplashBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainSplashFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentMainSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainSplashBinding.inflate(inflater, container, false)
        binding.splashbackground.alpha = 0f
        binding.splashtitle.alpha = 0f
        binding.splashtitle.animate().setDuration(3000).alpha(1f).withEndAction {
            binding.splashbackground.animate().setDuration(5000).alpha(1f).withEndAction {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, LoginFragment())
                    .commit()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainSplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}