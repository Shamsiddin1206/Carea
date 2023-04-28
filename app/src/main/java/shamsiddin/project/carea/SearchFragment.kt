package shamsiddin.project.carea

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import shamsiddin.project.carea.Adapters.CarsAdapter
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.databinding.FragmentSearchBinding

private const val ARG_PARAM1 = "param1"

class SearchFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    lateinit var selectedcars:MutableList<CarsData>
    lateinit var carsAdapter: CarsAdapter
    lateinit var listofcars: MutableList<CarsData>
    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        selectedcars = mutableListOf()
        selectedcars = mySharedPreferences.GetSelectedCarsList()
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        listofcars = mutableListOf()
        listofcars = mySharedPreferences.getCarList()
        Log.d("FFF", "onCreateView: ${listofcars[0].carname}")
        carsAdapter = CarsAdapter(listofcars, object : CarsAdapter.OnPressed{
            override fun onPressed(carsData: CarsData) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                    .commit()
            }
        }, object :CarsAdapter.OnSelected{
            override fun onSelect(carsData: CarsData) {
                if (carsData.status){
                    selectedcars.add(carsData)
                }else{
                    selectedcars.remove(carsData)
                }
            }
        }, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.foundElementsSearch.layoutManager = layoutManager
        binding.foundElementsSearch.adapter=carsAdapter
        binding.searchSearch.addTextChangedListener{
            val carfilter = mutableListOf<CarsData>()
            binding.foundSearch.visibility = View.VISIBLE
            binding.searchresultSearch.visibility = View.GONE
            if (it!!.isNotEmpty()){
                for (i in 0..listofcars.size-1){
                    if (listofcars[i].carname.trim().toLowerCase().contains(it.trim().toString().toLowerCase())){
                        carfilter.add(listofcars[i])
                    }
                }
                carsAdapter = CarsAdapter(carfilter, object : CarsAdapter.OnPressed{
                    override fun onPressed(carsData: CarsData) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                            .commit()
                    }
                }, object :CarsAdapter.OnSelected{
                    override fun onSelect(carsData: CarsData) {
                        if (carsData.status==true){
                            selectedcars.add(carsData)
                        }else{
                            selectedcars.remove(carsData)
                        }
                    }
                }, requireContext())
                val layoutManager = GridLayoutManager(requireContext(), 2)
                binding.foundElementsSearch.layoutManager = layoutManager
                binding.foundElementsSearch.adapter=carsAdapter
                if (carfilter.isEmpty() && it.isNotEmpty()){
                    binding.searchresultSearch.visibility = View.VISIBLE
                    binding.foundSearch.visibility = View.GONE
                    return@addTextChangedListener
                }
            }else{
                carsAdapter = CarsAdapter(listofcars, object : CarsAdapter.OnPressed{
                    override fun onPressed(carsData: CarsData) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                            .commit()
                    }
                }, object :CarsAdapter.OnSelected{
                    override fun onSelect(carsData: CarsData) {
                        if (carsData.status==true){
                            selectedcars.add(carsData)
                        }else{
                            selectedcars.remove(carsData)
                        }
                    }
                }, requireContext())
                val layoutManager = GridLayoutManager(requireContext(), 2)
                binding.foundElementsSearch.layoutManager = layoutManager
                binding.foundElementsSearch.adapter=carsAdapter
            }
        }
        mySharedPreferences.SetSelectedCarsList(selectedcars)
        return binding.root
    }
    private fun RefreshSearch(s:String):Int{
        if (s.length<1){
            return 0
        }
        return s.length
    }

    private fun CheckSelected(carsData: CarsData){
        if (carsData in selectedcars){
            selectedcars.remove(carsData)
        }else{
            selectedcars.add(carsData)
        }
    }

    private fun CheckWords(){

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}