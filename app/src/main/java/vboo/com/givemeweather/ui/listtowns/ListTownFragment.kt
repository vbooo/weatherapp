package vboo.com.givemeweather.ui.listtowns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_town.*
import vboo.com.givemeweather.R

/**
 * This fragment displays the list of cities the user has been added
 */
@AndroidEntryPoint
class ListTownFragment : Fragment() {

    private val viewModel: ListTownViewModel by viewModels()
    private lateinit var viewAdapter: ListCityAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_town, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()

        // We observe the listCity LiveData contained in viewModel, and display his value when it's available
        viewModel.listCity.observe(viewLifecycleOwner,
            Observer {
                viewAdapter.listCity = it
            })

        setUpAdapter()

        viewModel.eventCityClicked.observe(viewLifecycleOwner,
            Observer {
                val action = ListTownFragmentDirections.actionFirstFragmentToSecondFragment(it)
                findNavController().navigate(action)
            })
    }

    private fun setUpAdapter() {
        viewAdapter = ListCityAdapter(requireContext(), viewModel)

        // Set our recycler view which will contain our Order list
        fragment_list_town_recycler_view.apply {
            setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = viewAdapter
            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
            this.addItemDecoration(dividerItemDecoration)
        }
    }
}