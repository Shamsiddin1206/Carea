package shamsiddin.project.carea

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import shamsiddin.project.carea.databinding.FragmentWalletBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WalletFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentWalletBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        if (mySharedPreferences.getCardBalance().isNotEmpty()){
            binding.cardBalance.text = setString(mySharedPreferences.getCardBalance())+" $"
        }else{
            mySharedPreferences.setCardBalance(binding.cardBalance.text.toString().dropLast(2))
        }
        binding.topup.setOnClickListener {
            binding.topup.visibility = View.GONE
            binding.topupDetails.visibility = View.VISIBLE
        }
        binding.proceed.setOnClickListener {
            if (!binding.sumAmount.text.isNullOrEmpty()){
                if (!binding.kartaDetails.text.isNullOrEmpty() && binding.kartaDetails.text!!.length==16){
                    binding.shadow.visibility = View.VISIBLE
                    binding.successfullyTopeuped.visibility = View.VISIBLE
                    binding.doneGalochka.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.pop_up)
                    binding.successfullyTopeuped.startAnimation(anim)
                    binding.doneGalochka.alpha = 0f
                    binding.doneGalochka.animate().setDuration(5000).alpha(1f).withEndAction{
                        val a = SetUpNumbers(binding.cardBalance.text.toString())
                        binding.cardBalance.text = setString((a.toString().toInt() + binding.sumAmount.text.toString().trim().toInt()).toString())+" $"
                        binding.topup.visibility = View.VISIBLE
                        binding.topupDetails.visibility = View.GONE
                        binding.shadow.visibility = View.GONE
                        binding.successfullyTopeuped.visibility = View.GONE
                        mySharedPreferences.setCardBalance(binding.cardBalance.text.toString().dropLast(2))
                    }
                }else{
                    Toast.makeText(requireContext(), "Enter card number fully", Toast.LENGTH_SHORT).show()
                }
            }else{
                binding.topup.visibility = View.VISIBLE
                binding.topupDetails.visibility = View.GONE
            }
        }
        return binding.root
    }

    private fun SetUpNumbers(s:String): String{
        var a:String = ""
        for (i in 0..s.length-1){
            if (s[i].isDigit()){
                a+=s[i]
            }
        }
        return a
    }

    private fun setString(s:String):String{
        val arr = SetUpNumbers(s).toMutableList()
        var a = 1
        var str = ""
        if (arr.size%3==0){
        }else{
            while (arr.size%3>0){
                str+=arr[0].toString()
                arr.removeAt(0)
            }
            str+=" "
        }
        Log.d("OOO", "setString: ${str}, ${arr.joinToString()}")
        for (i in 0..arr.size-1){
            str += arr[i].toString()
            if (a==3){
                str += " "
                a=0
            }
            a++
        }
        return str
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WalletFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}