package shamsiddin.project.carea

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.DataClasses.LoginData
import shamsiddin.project.carea.DataClasses.OrderedCars
import shamsiddin.project.carea.DataClasses.Titles
import java.lang.reflect.Type

class MySharedPreferences private constructor(contexT: Context){

    val sharedPreferences = contexT.getSharedPreferences("data", 0)
    val edit = sharedPreferences.edit()
    val gson = Gson()

    companion object{
        private var instance:MySharedPreferences? = null
        fun newInstance(contexT: Context): MySharedPreferences {
            if (instance == null){
                instance = MySharedPreferences(contexT)
            }
            return instance!!
        }
    }

    fun getCarList(): MutableList<CarsData>{
        val data: String = sharedPreferences.getString("carsList", "")!!
        if (data == "") {
            return mutableListOf()
        }
        val typeToken = object :  TypeToken<MutableList<CarsData>>() {}.type
        return gson.fromJson(data, typeToken)
    }
    fun setCarsData(mutableList: MutableList<CarsData>) {
        edit.putString("carsList", gson.toJson(mutableList)).apply()
    }

    fun GetSelectedCarsList(): MutableList<CarsData>{
        val data: String = sharedPreferences.getString("Selected", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<CarsData>>(){}.type
        return gson.fromJson(data, typeToken)
    }
    fun SetSelectedCarsList(mutableList: MutableList<CarsData>){
        edit.putString("Selected", gson.toJson(mutableList)).apply()
    }

    fun setCardBalance(string: String){
        edit.putString("dollars", gson.toJson(string)).apply()
    }
    fun getCardBalance(): String{
        val data: String = sharedPreferences.getString("dollars", "")!!
        if (data == ""){
            return ""
        }
        val typeToken = object : TypeToken<String>(){}.type
        return gson.fromJson(data, typeToken)
    }
// Aziz
    fun setLoginData(mutableList: MutableList<LoginData>){
        edit.putString("Login", gson.toJson(mutableList)).apply()
    }
    fun getLoginData(): MutableList<LoginData>{
        val data: String = sharedPreferences.getString("Login", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<LoginData>>(){}.type
        return gson.fromJson(data, typeToken)
    }

    fun setOrderedCarList(mutableList: MutableList<OrderedCars>){
        edit.putString("Ordered", gson.toJson(mutableList)).apply()
    }
    fun getOrderedCarList(): MutableList<OrderedCars>{
        val data: String = sharedPreferences.getString("Ordered", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<OrderedCars>>(){}.type
        return gson.fromJson(data, typeToken)
    }

    fun setCategories(mutableList: MutableList<Titles>){
        edit.putString("Categories", gson.toJson(mutableList)).apply()
    }
    fun getCategories(): MutableList<Titles>{
        val data: String = sharedPreferences.getString("Categories", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<Titles>>(){}.type
        return gson.fromJson(data, typeToken)
    }

}