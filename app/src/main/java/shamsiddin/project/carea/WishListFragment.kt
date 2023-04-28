package shamsiddin.project.carea

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import shamsiddin.project.carea.Adapters.CarsAdapter
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.databinding.FragmentWishListBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WishListFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var allList: MutableList<CarsData>
    lateinit var mySharedPreferences: MySharedPreferences
    lateinit var list: MutableList<CarsData>
    lateinit var binding: FragmentWishListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        binding = FragmentWishListBinding.inflate(inflater, container, false)
        list = mySharedPreferences.GetSelectedCarsList()
        allList = mySharedPreferences.getCarList()

        val carsAdapter = CarsAdapter(list, object : CarsAdapter.OnPressed{
            override fun onPressed(carsData: CarsData) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                    .commit()
            }
        }, object :CarsAdapter.OnSelected{
            override fun onSelect(carsData: CarsData) {
                if (carsData.status){
                    list.add(carsData)
                    SetStatus(carsData, true)
                }else{
                    list.remove(carsData)
                    SetStatus(carsData, false)
                }
                mySharedPreferences.SetSelectedCarsList(list)
            }
        }, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wishlistrecycler.layoutManager = layoutManager
        binding.wishlistrecycler.adapter = carsAdapter

        binding.backFromCarWishlist.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, DefaultFragment())
                .commit()
        }
        return binding.root
    }

    private fun SetStatus(carsData: CarsData, davr:Boolean){
        for (i in 0..allList.size-1){
            if (carsData==allList[i] && allList[i].status!=davr){
                allList[i].status = davr
                mySharedPreferences.setCarsData(allList)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}