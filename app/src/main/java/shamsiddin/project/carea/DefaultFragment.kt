package shamsiddin.project.carea

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.DataClasses.Titles
import shamsiddin.project.carea.databinding.FragmentDefaultBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class DefaultFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentDefaultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())

        if (mySharedPreferences.getCarList().isEmpty()){
            mySharedPreferences.setCarsData(Cars())
        }

        if (mySharedPreferences.getCategories().isEmpty()){
            mySharedPreferences.setCategories(Categories())
        }

        binding = FragmentDefaultBinding.inflate(inflater, container, false)
        parentFragmentManager.beginTransaction()
            .replace(R.id.changeable, HomeFragment())
            .commit()
        binding.navigationmenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_orders -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, OrdersFragment())
                        .commit()
                }
                R.id.menu_home -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, HomeFragment())
                        .commit()
                }
                R.id.menu_profile -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, ProfileFragment())
                        .commit()
                }
                R.id.menu_inbox -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, InboxFragment())
                        .commit()
                }
                R.id.menu_wallet -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.changeable, WalletFragment())
                        .commit()
                }
            }
            true
        }
        return binding.root
    }

    private fun Cars(): MutableList<CarsData> {
        var CarsList = mutableListOf<CarsData>()
        CarsList.add(CarsData(R.drawable.bmw_x8_m, "BMW X8 M", "155 000$", "4.9", "BMW", false))
        CarsList.add(CarsData(R.drawable.bmw_8_series, "BMW 8 Series", "155 000$", "4.7", "Mercedes", false))
        CarsList.add(CarsData(R.drawable.bmw_x1_specs, "BMW M4 Series", "155 000$", "4.5", "Toyota", false))
        CarsList.add(CarsData(R.drawable.bmw_i8, "BMW i8 Specs", "155 000$", "4.4", "Tesla", false))
        CarsList.add(CarsData(R.drawable.bmw_x6, "BMW X6 M", "155 000$", "4.8", "Honda", false))
        CarsList.add(CarsData(R.drawable.bmw_m4_series, "BMW M4 Series", "155 000$", "4.5", "Volvo", false))
        return CarsList
    }

    private fun Categories(): MutableList<Titles> {
        val s = mutableListOf<Titles>()
        s.add(Titles("All", true))
        s.add(Titles("Mercedes", false))
        s.add(Titles("Tesla", false))
        s.add(Titles("BMW", false))
        s.add(Titles("Toyota", false))
        s.add(Titles("Volvo", false))
        s.add(Titles("Bugatti", false))
        s.add(Titles("Honda", false))
        return s
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DefaultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}