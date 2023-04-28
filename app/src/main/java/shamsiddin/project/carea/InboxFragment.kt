package shamsiddin.project.carea

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.Adapters.InboxElementsAdapter
import shamsiddin.project.carea.DataClasses.InboxElementsData
import shamsiddin.project.carea.databinding.FragmentInboxBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class InboxFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentInboxBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInboxBinding.inflate(inflater, container, false)
        val ChatsAdapter = InboxElementsAdapter(Chats())
        val CallsAdapter = InboxElementsAdapter(Calls())
        binding.inboxElements.adapter = ChatsAdapter

        binding.callsText.setOnClickListener {
            binding.callsText.setTextColor(Color.parseColor("#000000"))
            binding.callsLine.setBackgroundResource(R.drawable.underline_black)
            binding.chatsLine.setBackgroundResource(R.drawable.underline_default)
            binding.chatsText.setTextColor(Color.parseColor("#9A9A9A"))
            binding.inboxElements.adapter = CallsAdapter
        }

        binding.chatsText.setOnClickListener {
            binding.callsText.setTextColor(Color.parseColor("#9A9A9A"))
            binding.callsLine.setBackgroundResource(R.drawable.underline_default)
            binding.chatsLine.setBackgroundResource(R.drawable.underline_black)
            binding.chatsText.setTextColor(Color.parseColor("#000000"))
            binding.inboxElements.adapter = ChatsAdapter
        }
        return binding.root
    }

    private fun Calls(): MutableList<InboxElementsData> {
        val list = mutableListOf<InboxElementsData>()
        list.add(InboxElementsData(R.drawable.bmwlogo, "BMW Store", R.drawable.ic_phone, "+998 99 777 70 70"))
        list.add(InboxElementsData(R.drawable.mercedes, "Mercedes-Benz", R.drawable.ic_phone, "+998 99 666 60 60"))
        list.add(InboxElementsData(R.drawable.hondalogo, "Honda Motor", R.drawable.ic_phone, "+998 99 999 90 90"))
        list.add(InboxElementsData(R.drawable.teslalogo2, "Tesla Motor", R.drawable.ic_phone, "+998 99 222 22 22"))
        list.add(InboxElementsData(R.drawable.volvologo, "Volvo Official", R.drawable.ic_phone, "+998 99 888 80 80"))
        list.add(InboxElementsData(R.drawable.toyotalogo, "Toyota Store", R.drawable.ic_phone, "+998 99 555 50 50"))
        list.add(InboxElementsData(R.drawable.bugattilogo, "Bugatti Official", R.drawable.ic_phone, "+998 99 111 11 11"))
        list.add(InboxElementsData(R.drawable.volkswagenlogo, "Volkswagen Official", R.drawable.ic_phone, "+998 99 333 33 33"))
        list.add(InboxElementsData(R.drawable.nissanlogo, "Nissan Official", R.drawable.ic_phone, "+998 99 333 33 33"))
        return list
    }

    private fun Chats(): MutableList<InboxElementsData>{
        val chatslist = mutableListOf<InboxElementsData>()
        chatslist.add(InboxElementsData(R.drawable.bmwlogo, "BMW Store", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.mercedes, "Mercedes-Benz", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.hondalogo, "Honda Motor", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.teslalogo2, "Tesla Motor", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.volvologo, "Volvo Official", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.toyotalogo, "Toyota Store", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.bugattilogo, "Bugatti Official", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.volkswagenlogo, "Volkswagen Official", R.drawable.ic_message, "last seen recently"))
        chatslist.add(InboxElementsData(R.drawable.nissanlogo, "Nissan Official", R.drawable.ic_message, "last seen recently"))
        return chatslist
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InboxFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}