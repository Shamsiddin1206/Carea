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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var loginInfo: MutableList<LoginData>
    lateinit var main_binding: ActivityMainBinding
    lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        main_binding = ActivityMainBinding.inflate(inflater, container, false)

        if (mySharedPreferences.getLoginData().isNotEmpty()){
            loginInfo = mySharedPreferences.getLoginData()
        }else{
            loginInfo = mutableListOf()
        }

        binding.signIn.setOnClickListener {
            var index = 0
            if (loginInfo.isNotEmpty()){
                if (!binding.emailText.text.isNullOrEmpty()){
                    if (!binding.passwordText.text.isNullOrEmpty()){
                        for (i in 0..loginInfo.size-1){
                            if (binding.passwordText.text.toString() == loginInfo[i].password){
                                if (binding.emailText.text.toString() == loginInfo[i].login){
                                    index++
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.fragmentContainerView, DefaultFragment())
                                        .commit()
                                    parentFragmentManager.beginTransaction()
                                        .add(R.id.changeable, HomeFragment())
                                        .commit()
                                }
                            }
                        }
                        if (index==0){
                            Toast.makeText(requireContext(), "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(), "Enter Password", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Enter Email", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "You have not registered yet", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signUp2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, RegistrationFragment())
                .commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}