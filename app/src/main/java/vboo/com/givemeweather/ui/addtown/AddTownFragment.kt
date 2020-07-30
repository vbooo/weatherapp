package vboo.com.givemeweather.ui.addtown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_town_fragment.*
import vboo.com.givemeweather.R
import vboo.com.weatherlib.domain.model.City


/**
 * This fragment handles the cities that the user can add as favourite
 */
@AndroidEntryPoint
class AddTownFragment : Fragment() {

    private val viewModel: AddTownViewModel by viewModels()
    private lateinit var viewAdapter: ListAddCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_town_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = AddTownFragmentDirections.actionAddTownFragmentToListTownFragment()
            findNavController().navigate(action)
        }

        viewModel.loadData()

        // We observe the listCity LiveData contained in viewModel, and display his value when it's available
        viewModel.listCity.observe(viewLifecycleOwner,
            Observer {
                viewAdapter.listCity = it
            })

        viewModel.cityFavouriteUpdated.observe(viewLifecycleOwner,
            Observer {
                displaySnackBar(it)
            })

        setUpAdapter()
    }

    private fun setUpAdapter() {
        viewAdapter = ListAddCityAdapter(requireContext(), viewModel)

        // Set our recycler view which will contain our Order list
        add_town_fragment_recycler_view.apply {
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

    private fun displaySnackBar(city: City?) {
        val root = add_town_fragment
        city?.let {
            if (city.isFavourite) {
                Snackbar.make(root, R.string.cityPutAsFavourite, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                 Snackbar.make(root, R.string.cityRemoveOfFavourite, Snackbar.LENGTH_SHORT)
                    .show()
            }

        } ?: kotlin.run {
            Snackbar.make(root, R.string.error_occured, Snackbar.LENGTH_SHORT)
                .show()
        }

    }

}