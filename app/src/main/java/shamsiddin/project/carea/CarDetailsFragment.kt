package shamsiddin.project.carea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.databinding.FragmentCarDetailsBinding

private const val ARG_PARAM1 = "param1"

class CarDetailsFragment : Fragment() {
    private var param1: CarsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as CarsData
        }
    }

    lateinit var carsData: CarsData
    lateinit var binding: FragmentCarDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        carsData = param1!!
        //Related to implemented car details
        binding.imageCarDetails.setImageResource(param1!!.carrasm)
        binding.nameCarDetails.text = param1!!.carname
        binding.reytingCarDetails.text = param1!!.carreyting
        binding.priceamountCarDetails.text = param1!!.carprice

        //Related to return to HomeFragment
        binding.backFromCarDetails.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HomeFragment())
                .commit()
        }

        //Related to Order stuff
        binding.makeorderCarDetails.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MakeAnOrderFragment.newInstance(param1!!))
                .commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: CarsData) =
            CarDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}