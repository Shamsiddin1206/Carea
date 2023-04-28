package shamsiddin.project.carea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import shamsiddin.project.carea.DataClasses.LoginData
import shamsiddin.project.carea.databinding.ActivityMainBinding
import shamsiddin.project.carea.databinding.FragmentLoginBinding
import shamsiddin.project.carea.databinding.FragmentRegistrationBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegistrationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var logininfo: MutableList<LoginData>
    lateinit var main_binding: ActivityMainBinding
    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        main_binding = ActivityMainBinding.inflate(inflater, container, false)
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        if (mySharedPreferences.getLoginData().isNotEmpty()){
            logininfo = mySharedPreferences.getLoginData()
        }else{
            logininfo = mutableListOf()
        }

        binding.signIn2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .commit()
        }
        binding.signUp.setOnClickListener {
            if (!binding.regEmailText.text.isNullOrEmpty()){
                if (!binding.regParolText.text.isNullOrEmpty()){
                    logininfo.add(LoginData(binding.regEmailText.text.toString(), binding.regParolText.text.toString()))
                    mySharedPreferences.setLoginData(logininfo)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, DefaultFragment())
                        .commit()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, HomeFragment())
                        .commit()
                }else{
                    Toast.makeText(requireContext(), "Enter Password", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Enter Email", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}