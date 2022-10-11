package com.uharris.frontendtask.offers

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.domain.DeleteOfferUseCase
import com.uharris.frontendtask.domain.GetLocalOffersUseCase
import com.uharris.frontendtask.domain.GetOffersUseCase
import com.uharris.frontendtask.domain.SaveOfferUseCase
import com.uharris.frontendtask.utils.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject internal constructor(
    private val getLocalOffers: GetLocalOffersUseCase,
    private val getListOffers: GetOffersUseCase,
    private val saveOfferUseCase: SaveOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase
) : ViewModel(), LifecycleObserver {

    private val _offersViewModelMutableState = MutableLiveData<State>()
    val offersViewModelState: LiveData<State> = _offersViewModelMutableState

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var offers: List<Offer>? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var total: Int? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var amount: Int? = null

    fun getOffers() {
        viewModelScope.launch {
            _offersViewModelMutableState.value = State.Loading
            if (offers.isNullOrEmpty()) {
                _offersViewModelMutableState.value = when (val result = getListOffers(null)) {
                    is AppResult.Success -> {
                        offers = result.data
                        getLocalOffers(null).let {
                            amount = it.fold(0) { acc, offer ->
                                val sum = acc + offer.price
                                sum
                            } / 100
                            total = it.size
                        }
                        State.ShowOffers(result.data, total, amount)
                    }
                    is AppResult.Error -> State.Error(result.exception.message)
                }
            } else {
                _offersViewModelMutableState.value = State.ShowOffers(offers, total, amount)
            }
        }
    }

    fun saveOffer(offer: Offer) {
        viewModelScope.launch {
            saveOfferUseCase(offer)
            updateList(offer)
            _offersViewModelMutableState.value = State.ShowOffers(offers, total, amount)
        }
    }

    fun removeOffer(offer: Offer) {
        viewModelScope.launch {
            deleteOfferUseCase(offer)
            updateList(offer, false)
            _offersViewModelMutableState.value = State.ShowOffers(offers, total, amount)
        }
    }

    private fun updateList(offer: Offer, status: Boolean = true) {
        offers?.find { it.id == offer.id }?.isInBasket = status
        total = total?.plus(if (status) 1 else -1)
        amount = amount?.plus(if (status) offer.price / 100 else (offer.price * -1) / 100)
    }

    sealed class State {
        object Loading : State()
        class ShowOffers(val offers: List<Offer>?, val total: Int?, val amount: Int?) : State()
        class Error(val error: String?) : State()
    }
}