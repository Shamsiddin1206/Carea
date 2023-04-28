package shamsiddin.project.carea

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.DataClasses.OrderedCars
import shamsiddin.project.carea.databinding.FragmentMakeAnOrderBinding

private const val ARG_PARAM1 = "param1"

class MakeAnOrderFragment : Fragment() {
    private var param1: CarsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as CarsData
        }
    }

    lateinit var OrderedList: MutableList<OrderedCars>
    lateinit var binding: FragmentMakeAnOrderBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeAnOrderBinding.inflate(inflater, container, false)
        val mySharedPreferences = MySharedPreferences.newInstance(requireContext())
        var CurrentBalance = SetUpNumbers(mySharedPreferences.getCardBalance()).toInt()

        if (mySharedPreferences.getOrderedCarList().isNotEmpty()){
            OrderedList = mySharedPreferences.getOrderedCarList()
        }else{
            OrderedList = mutableListOf()
        }

        Log.d("JJJ", "onCreateView: ${param1!!.carprice}")
        binding.orderName.text = param1!!.carname
        binding.orderPrice.text = param1!!.carprice
        binding.ownBalance.text = setString(mySharedPreferences.getCardBalance()) + " $"
        binding.orderImage.setImageResource(param1!!.carrasm)
        binding.totalAmount.text = setString(SetUTotalMoney()) + " $"
        binding.justcarAmount.text = param1!!.carprice

        binding.backFromMakeAnOrder.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, DefaultFragment())
                .commit()
        }

        //Related to Shipping Address
        binding.editAdress.setOnClickListener {
            binding.shippingAdressLayout.visibility = View.VISIBLE
            binding.mainScreenOfOrdering.visibility = View.GONE
        }
        CheckAddressRadioStatus()
        ClickAddressSettings()
        binding.backFromShippingAdress.setOnClickListener {
            binding.shippingAdressLayout.visibility = View.GONE
            binding.mainScreenOfOrdering.visibility = View.VISIBLE
        }
        binding.applyChoosedlocation.setOnClickListener {
            FinalAddress()
            binding.shippingAdressLayout.visibility = View.GONE
            binding.mainScreenOfOrdering.visibility = View.VISIBLE
        }


        //Related to Shipping Choice
        binding.shippingDetails.setOnClickListener {
            binding.mainScreenOfOrdering.visibility = View.GONE
            binding.ChooseShippingLayout.visibility = View.VISIBLE
            CheckShippingRadioButtons()
        }
        ClickChoiceRadioButtons()
        binding.backFromShippingChoice.setOnClickListener {
            binding.mainScreenOfOrdering.visibility = View.VISIBLE
            binding.ChooseShippingLayout.visibility = View.GONE
        }
        binding.applyChoosedtransport.setOnClickListener {
            FinalChoice()
            binding.totalAmount.text = setString(SetUTotalMoney()) + " $"
            binding.mainScreenOfOrdering.visibility = View.VISIBLE
            binding.ChooseShippingLayout.visibility = View.GONE
        }


        //Related to Confirmation
        binding.continueToPayment.setOnClickListener {
            binding.shadow.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(requireContext(),R.anim.pop_up)
            binding.paymentsettings.visibility = View.VISIBLE
            binding.requiresAmount.text = setString(SetUTotalMoney()) + " $"
            binding.paymentsettings.startAnimation(anim)
        }
        binding.confirmPayment.setOnClickListener {
            binding.paymentsettings.visibility = View.GONE
            if (SetUpNumbers(binding.ownBalance.text.toString()).toInt()>=SetUpNumbers(binding.requiresAmount.text.toString()).toInt()){
                CurrentBalance -= SetUpNumbers(SetUTotalMoney()).toInt()
                OrderedList.add(OrderedCars(param1!!.carrasm, param1!!.carname, param1!!.carprice))
                mySharedPreferences.setOrderedCarList(OrderedList)
                mySharedPreferences.setCardBalance(CurrentBalance.toString())
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.pop_up)
                binding.successfullyDone.visibility = View.VISIBLE
                binding.successfullyDone.startAnimation(anim)
                binding.doneGalochka.alpha = 0f
                binding.doneGalochka.visibility = View.VISIBLE
                binding.doneGalochka.animate().setDuration(5000).alpha(1f).withEndAction{
                    binding.shadow.visibility = View.GONE
                    binding.successfullyDone.visibility = View.GONE
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, DefaultFragment())
                        .commit()
                }
            }
        }

        return binding.root
    }

    //Related to Shipping Address
    private fun CheckAddressRadioStatus(){
        if (binding.mainLocationtitle.text == "Home"){
            binding.homeRadiobutton.isChecked = true
            binding.officeRadiobutton.isChecked = false
            binding.parentsRadiobutton.isChecked = false
        }
        if (binding.mainLocationtitle.text == "Office"){
            binding.homeRadiobutton.isChecked = false
            binding.officeRadiobutton.isChecked = true
            binding.parentsRadiobutton.isChecked = false
        }
        if (binding.mainLocationtitle.text == "Parent`s house"){
            binding.homeRadiobutton.isChecked = false
            binding.officeRadiobutton.isChecked = false
            binding.parentsRadiobutton.isChecked = true
        }
    }

    private fun ClickAddressSettings(){
        binding.homeRadiobutton.setOnClickListener {
            binding.homeRadiobutton.isChecked = true
            binding.officeRadiobutton.isChecked = false
            binding.parentsRadiobutton.isChecked = false
        }
        binding.officeRadiobutton.setOnClickListener {
            binding.homeRadiobutton.isChecked = false
            binding.officeRadiobutton.isChecked = true
            binding.parentsRadiobutton.isChecked = false
        }
        binding.parentsRadiobutton.setOnClickListener {
            binding.homeRadiobutton.isChecked = false
            binding.officeRadiobutton.isChecked = false
            binding.parentsRadiobutton.isChecked = true
        }
    }

    private fun FinalAddress(){
        if (binding.homeRadiobutton.isChecked){
            binding.mainLocationtitle.text = binding.shippingadressHome.text
            binding.mainLocation.text = binding.shippingadressHomelocation.text
        }
        if (binding.officeRadiobutton.isChecked){
            binding.mainLocationtitle.text = binding.shippingadressOffice.text
            binding.mainLocation.text = binding.shippingadressOfficelocation.text
        }
        if (binding.parentsRadiobutton.isChecked){
            binding.mainLocationtitle.text = binding.shippingadressParents.text
            binding.mainLocation.text = binding.shippingadressParentslocation.text
        }
    }

    //Related to Shipping Choice
    private fun CheckShippingRadioButtons(){
        if (binding.mainshippingTitle.text == "Truck"){
            binding.truckRadiobutton.isChecked = true
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = false
        }
        if (binding.mainshippingTitle.text == "Train"){
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = true
            binding.shipRadiobutton.isChecked = false
        }
        if (binding.mainshippingTitle.text == "Plane"){
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = true
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = false
        }
        if (binding.mainshippingTitle.text == "Ship"){
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = true
        }
    }

    private fun ClickChoiceRadioButtons(){
        binding.truckRadiobutton.setOnClickListener {
            binding.truckRadiobutton.isChecked = true
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = false
        }
        binding.planeRadiobutton.setOnClickListener {
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = true
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = false
        }
        binding.trainRadiobutton.setOnClickListener {
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = true
            binding.shipRadiobutton.isChecked = false
        }
        binding.shipRadiobutton.setOnClickListener {
            binding.truckRadiobutton.isChecked = false
            binding.planeRadiobutton.isChecked = false
            binding.trainRadiobutton.isChecked = false
            binding.shipRadiobutton.isChecked = true
        }
    }

    private fun FinalChoice(){
        if (binding.truckRadiobutton.isChecked){
            binding.mainshippingImage.setImageResource(R.drawable.track)
            binding.mainshippingTitle.text = binding.truckTitle.text
            binding.mainshippingPrice.visibility = View.VISIBLE
            binding.shippingNext.visibility = View.GONE
            binding.mainshippingPrice.text = binding.truckPrice.text
            binding.shippingAmount.text = binding.truckPrice.text
        }
        if (binding.trainRadiobutton.isChecked){
            binding.mainshippingImage.setImageResource(R.drawable.train)
            binding.mainshippingTitle.text = binding.trainTitle.text
            binding.mainshippingPrice.visibility = View.VISIBLE
            binding.shippingNext.visibility = View.GONE
            binding.mainshippingPrice.text = binding.trainPrice.text
            binding.shippingAmount.text = binding.trainPrice.text
        }
        if (binding.planeRadiobutton.isChecked){
            binding.mainshippingImage.setImageResource(R.drawable.plane)
            binding.mainshippingTitle.text = binding.planeTitle.text
            binding.mainshippingPrice.visibility = View.VISIBLE
            binding.shippingNext.visibility = View.GONE
            binding.mainshippingPrice.text = binding.planePrice.text
            binding.shippingAmount.text = binding.planePrice.text
        }
        if (binding.shipRadiobutton.isChecked){
            binding.mainshippingImage.setImageResource(R.drawable.ship)
            binding.mainshippingTitle.text = binding.shipTitle.text
            binding.mainshippingPrice.visibility = View.VISIBLE
            binding.shippingNext.visibility = View.GONE
            binding.mainshippingPrice.text = binding.shipPrice.text
            binding.shippingAmount.text = binding.shipPrice.text
        }
    }

    @SuppressLint("SetTextI18n")
    private fun SetUTotalMoney():String{
        var a:String = ""
        if (binding.shippingAmount.text!="-"){
            a = (SetUpNumbers(param1!!.carprice).toInt() +
                SetUpNumbers(binding.taxAmount.text.toString()).toInt()
                + SetUpNumbers(binding.shippingAmount.text.toString()).toInt()).toString()
        }else{
        a = (SetUpNumbers(param1!!.carprice).toInt() +
                SetUpNumbers(binding.taxAmount.text.toString()).toInt()).toString()
    }
        return a
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
        fun newInstance(param1: CarsData) =
            MakeAnOrderFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}