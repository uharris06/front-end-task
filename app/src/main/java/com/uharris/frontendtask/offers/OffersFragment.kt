package com.uharris.frontendtask.offers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uharris.frontendtask.R
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.databinding.FragmentOffersBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class OffersFragment : Fragment(), OffersAdapter.OfferListener {

    private lateinit var offersAdapter: OffersAdapter
    private var _binding: FragmentOffersBinding? = null

    private val viewModel: OffersViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.offersRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            offersAdapter = OffersAdapter(this@OffersFragment).also {
                this.adapter = it
            }
        }
        loadObservables()
        viewModel.getOffers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadObservables() {
        viewModel.offersViewModelState.observe(viewLifecycleOwner) {
            onStateChanged(it)
        }
    }

    private fun onStateChanged(state: OffersViewModel.State) {
        when(state) {
            is OffersViewModel.State.Loading ->{}
            is OffersViewModel.State.ShowOffers -> {
                offersAdapter.apply {
                    submitList(null)
                    submitList(state.offers)
                }
                binding.apply {
                    amountBasketTextView.text = getString(R.string.amount, state.amount)
                    totalBasketTextView.text = getString(R.string.total, state.total)
                }
            }
            is OffersViewModel.State.Error -> {
                Snackbar.make(requireActivity().findViewById(R.id.coordinator_layout),
                    "There is a problem with your internet connection or with server",
                    Snackbar.LENGTH_LONG).show()

            }
        }
    }

    override fun addOffer(offer: Offer) {
        viewModel.saveOffer(offer)
    }

    override fun removeOffer(offer: Offer) {
        viewModel.removeOffer(offer)
    }
}