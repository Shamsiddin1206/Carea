package shamsiddin.project.carea

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import shamsiddin.project.carea.Adapters.CarsAdapter
import shamsiddin.project.carea.Adapters.DealsCategoriesAdapter
import shamsiddin.project.carea.Adapters.OfferCarsAdapter
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.DataClasses.OfferCarsInfo
import shamsiddin.project.carea.DataClasses.Titles
import shamsiddin.project.carea.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var categories: MutableList<Titles>
    lateinit var mySharedPreferences: MySharedPreferences
    lateinit var ChoosedCarsList: MutableList<CarsData>
    lateinit var CarsList: MutableList<CarsData>
    lateinit var offerCarsAdapter: OfferCarsAdapter
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        CarsList = mySharedPreferences.getCarList()
        categories = mySharedPreferences.getCategories()
        if (mySharedPreferences.GetSelectedCarsList().isEmpty()){
            ChoosedCarsList = mutableListOf()
        }else{
            ChoosedCarsList = mySharedPreferences.GetSelectedCarsList()
        }
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Related to ViewPager
        setOnboardingItems()
        setIndicators()
        CheckIndicator(0)

        //Related to Top Deals RecyclerView
        val dealsCategoriesAdapter = DealsCategoriesAdapter(categories, object : DealsCategoriesAdapter.ChoosedTopic{
            @SuppressLint("NotifyDataSetChanged")
            override fun Topic(string: String) {
                if (string=="All"){
                    SetCategoriesStatus("All")
                    val carsAdapter = CarsAdapter(CarsList, object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    carsAdapter.notifyDataSetChanged()
                    binding.carsrecyclerview.adapter = carsAdapter
                }
                if (string=="BMW"){
                    SetCategoriesStatus("BMW")
                    val carsAdapter = CarsAdapter(BMWCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Mercedes"){
                    SetCategoriesStatus("Mercedes")
                    val carsAdapter = CarsAdapter(MercedesCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Tesla"){
                    SetCategoriesStatus("Tesla")
                    val carsAdapter = CarsAdapter(TeslaCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Toyota"){
                    SetCategoriesStatus("Toyota")
                    val carsAdapter = CarsAdapter(ToyotaCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Volvo"){
                    SetCategoriesStatus("Volvo")
                    val carsAdapter = CarsAdapter(VolvoCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Bugatti"){
                    SetCategoriesStatus("Bugatti")
                    val carsAdapter = CarsAdapter(BugattiCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }

                if (string=="Honda"){
                    SetCategoriesStatus("Honda")
                    val carsAdapter = CarsAdapter(HondaCars(), object : CarsAdapter.OnPressed{
                        override fun onPressed(carsData: CarsData) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                                .commit()
                        }
                    }, object :CarsAdapter.OnSelected{
                        override fun onSelect(carsData: CarsData) {
                            if (carsData.status){
                                ChoosedCarsList.add(carsData)
                                SetStatus(carsData, true)
                            }else{
                                ChoosedCarsList.remove(carsData)
                                SetStatus(carsData, false)
                            }
                            mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
                        }
                    }, requireContext())
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.carsrecyclerview.layoutManager = layoutManager
                    binding.carsrecyclerview.adapter = carsAdapter
                }
            }
        })
        binding.dealsRecyclerview.adapter = dealsCategoriesAdapter

        //Related to Cars RecyclerView
        val carsAdapter = CarsAdapter(CarsList, object : CarsAdapter.OnPressed{
            override fun onPressed(carsData: CarsData) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, CarDetailsFragment.newInstance(carsData))
                    .commit()
            }
        }, object :CarsAdapter.OnSelected{
            override fun onSelect(carsData: CarsData) {
                if (carsData.status){
                    ChoosedCarsList.add(carsData)
                    SetStatus(carsData, true)
                }else{
                    ChoosedCarsList.remove(carsData)
                    SetStatus(carsData, false)
                }
                mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
            }
        }, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.carsrecyclerview.layoutManager = layoutManager
        binding.carsrecyclerview.adapter = carsAdapter

        //Related to Offer Cars
        binding.SeeAllSO.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SpecialOffersFragment())
                .commit()
        }

        //Related to Search
        binding.search.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SearchFragment())
                .commit()
        }

        binding.selected.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, WishListFragment())
                .commit()
        }

        mySharedPreferences.setCarsData(CarsList)
        mySharedPreferences.SetSelectedCarsList(ChoosedCarsList)
        return binding.root


    }


    private fun MercedesCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Mercedes"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun BMWCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="BMW"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun TeslaCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Tesla"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun ToyotaCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Toyota"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun VolvoCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Volvo"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun BugattiCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Bugatti"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun HondaCars():MutableList<CarsData>{
        val list = mutableListOf<CarsData>()
        for (i in 0..CarsList.size-1){
            if (CarsList[i].toifa=="Honda"){
                list.add(CarsList[i])
            }
        }
        return list
    }

    private fun setOnboardingItems() {
        offerCarsAdapter = OfferCarsAdapter(
            listOf(
                OfferCarsInfo("20%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.mercedescar),
                OfferCarsInfo("30%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.bmw_8_series),
                OfferCarsInfo("20%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.mercedescar),
                OfferCarsInfo("10%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.bmw_i8),
                OfferCarsInfo("20%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.mercedescar),
                OfferCarsInfo("20%", "Week Deals!", "Get a new car discount, only valid this week", R.drawable.mercedescar)
            ) as MutableList<OfferCarsInfo>
        )
        binding.offersViewpager.adapter = offerCarsAdapter
        binding.offersViewpager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    CheckIndicator(position)
                }
            }
        )
        (binding.offersViewpager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setIndicators(){
        val indicators = arrayOfNulls<ImageView>(offerCarsAdapter.itemCount)
        val layoutParams: LinearLayoutCompat.LayoutParams =
            LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices){
            indicators[i] = ImageView(requireContext())
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator2
                    )
                )
                it.layoutParams = layoutParams
                binding.indicatorLayout.addView(it)
            }
        }
    }

    private fun SetStatus(carsData: CarsData, davr:Boolean){
        for (i in 0..CarsList.size-1){
            if (carsData !in CarsList){
                if (carsData==CarsList[i]){
                    CarsList[i].status = davr
                    mySharedPreferences.setCarsData(CarsList)
                }
            }
        }
    }

    private fun CheckIndicator(position: Int){
        val childCount = binding.indicatorLayout.childCount
        for (i in 0..childCount-1){
            val imageView = binding.indicatorLayout.getChildAt(i) as ImageView
            if (i==position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator
                    )
                )
            } else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator2
                    )
                )
            }
        }
    }

    private fun SetCategoriesStatus(s:String){
        for (i in 0..categories.size-1){
            categories[i].status = categories[i].title == s
        }
        mySharedPreferences.setCategories(categories)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}