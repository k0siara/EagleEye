package com.patrykkosieradzki.eagleeye.ui.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hadilq.liveevent.LiveEvent
import com.patrykkosieradzki.eagleeye.BR
import com.patrykkosieradzki.eagleeye.R
import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.domain.exceptions.ApiException
import com.patrykkosieradzki.eagleeye.domain.usecases.CheckSessionUseCase
import com.patrykkosieradzki.eagleeye.ui.DesktopActivity
import com.patrykkosieradzki.eagleeye.ui.LauncherActivity
import com.patrykkosieradzki.eagleeye.ui.error.DialogFragmentFactory
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import kotlin.reflect.KClass

@Suppress("TooManyFunctions")
abstract class BaseFragment<STATE : ViewState, VM : BaseViewModel<STATE>, VDB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    vmKClass: KClass<VM>
) : Fragment() {

    protected lateinit var binding: VDB

    val viewModel: VM by lazy {
        getViewModel(clazz = vmKClass)
    }

    val appConfiguration: AppConfiguration by inject()
    val checkSessionUseCase: CheckSessionUseCase by inject()
    open val requireSession = true

    var onBackEvent: () -> Unit = {
        try {
            findNavController().navigateUp()
        } catch (e: IllegalStateException) {
            Timber.e("Fragment $this is not a NavHostFragment or within a NavHostFragment")
        }
    }

    /**
     * final - use BaseViewModel.onResumeWithValidSession instead
     */
    final override fun onResume() {
        super.onResume()
        if (requireSession) {
            viewModel.checkSessionOnResume(checkSessionUseCase)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                setVariable(BR.vm, viewModel)
            }
        return RelativeLayout(requireContext()).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
//            id = R.id.loader_container
            addView(binding.root, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
//            loader = LottieAnimationView(requireContext()).apply {
//                isClickable = true
//                isFocusable = true
//                visibility = View.GONE
//                setBackgroundColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.progress_bar_background
//                    )
//                )
//                setAnimation(R.raw.loader_animation)
//                repeatMode = LottieDrawable.RESTART
//                repeatCount = LottieDrawable.INFINITE
//                playAnimation()
//            }
//            addView(loader, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            inProgress.observe(viewLifecycleOwner) {
//                loader?.goneIfWithAnimation(!it)
            }
            showErrorEvent.observe(viewLifecycleOwner) {
                showError(it)
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                onBackEvent.invoke()
            }
            setupViews(view)
            initialize()
        }
    }

    open fun setupViews(view: View) {}

    protected fun showError(error: ErrorEvent) = when (error.throwable) {
        is ApiException -> {
            showApiException(error.throwable, error.isInitialState)
        }
        else -> if (appConfiguration.debug) showErrorDialog(
            error.throwable?.message ?: "Error"
        ) else showErrorDialog(
            getString(R.string.general_error_message)
        )
    }

    private fun showApiException(error: ApiException, isInitialError: Boolean) {
        showErrorDialog(error.errorMessage) {
            when {
                error.shouldLogout -> goToLoginScreen()
                isInitialError -> {
                    try {
                        findNavController().popBackStack()
                    } catch (e: IllegalStateException) {
                        Timber.e("Fragment $this is not a NavHostFragment or within a NavHostFragment")
                    }
                }
            }
        }
    }

    protected fun showErrorDialog(message: String, actionOnDismiss: () -> Unit = {}) {
        Timber.e("showErrorDialog:$message")
        val dialog = DialogFragmentFactory.newErrorInstance(message)
        dialog.callback = {
            actionOnDismiss()
            viewModel.updateViewStateToSuccess()
        }
        dialog.show(childFragmentManager)
    }

    protected fun goToLoginScreen() {
        if (requireActivity() is LauncherActivity) {
            Timber.d("Already in Login Screen")
            return
        }
        val intent = Intent(requireActivity(), LauncherActivity::class.java).apply {
            flags = prepareFlags()
        }
        startActivity(intent)
        requireActivity().finish()
    }

    protected fun goToDesktop() {
        if (requireActivity() is DesktopActivity) {
            Timber.d("Already in Desktop")
            return
        }
        val intent = Intent(requireActivity(), DesktopActivity::class.java).apply {
            flags = prepareFlags()
        }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun prepareFlags(): Int {
        return Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
}

interface ViewState {
    val inProgress: Boolean
    fun toSuccess(): ViewState
}

inline val <T> MutableLiveData<T>.readOnly: LiveData<T>
    get() = this

inline val <T> LiveData<T>.valueNN
    get() = this.value!!

fun <T> LiveEvent<T>.fireEvent(event: T) {
    this.value = event
}

fun LiveEvent<Unit>.fireEvent() {
    this.value = Unit
}

fun EditText.observeText(update: (String) -> Unit) {
    this.addTextChangedListener { update(it.toString()) }
}

fun Fragment.navigateTo(directions: NavDirections) {
    findNavController().navigate(directions)
}

val Fragment.appCompatActivity: AppCompatActivity
    get() = requireActivity() as AppCompatActivity