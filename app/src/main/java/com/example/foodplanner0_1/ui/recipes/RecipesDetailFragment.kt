package com.example.foodplanner0_1.ui.recipes

import androidx.fragment.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.doOnLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.foodplanner0_1.databinding.FragmentRecipesDetailBinding
import com.example.foodplanner0_1.ui.recipes.data.RecipesListItem
import kotlinx.coroutines.launch
import java.io.File


class RecipesDetailFragment : Fragment() {}
/*


private var _binding: FragmentRecipesDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: RecipesDetailFragmentArgs by navArgs()

    private val recipesDetailViewModel: RecipesDetailViewModel by viewModels {
        RecipesDetailViewModelFactory(args.recipesId)
    }

    private val selectRecipe = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { uri: Uri? ->
        uri?.let { parseContactSelection(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentRecipesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recipeTitle.doOnTextChanged { text, _, _, _ ->
                recipesDetailViewModel.updateCrime { oldRecipes ->
                    oldRecipes.copy(title = text.toString())
                }
            }




            val selectSuspectIntent = selectSuspect.contract.createIntent(
                requireContext(),
                null
            )
            crimeSuspect.isEnabled = canResolveIntent(selectSuspectIntent)



            val captureImageIntent = takePhoto.contract.createIntent(
                requireContext(),
                null
            )
            crimeCamera.isEnabled = canResolveIntent(captureImageIntent)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipesDetailViewModel.recipes.collect { RecipesListItem ->
                    RecipesListItem?.let { updateUi(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(recipesListItem: RecipesListItem) {
        binding.apply {
            if (crimeTitle.text.toString() != recipesListItem.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeDate.setOnClickListener {
                findNavController().navigate(
                    RecipesDetailFragmentDirections.selectDate(crime.date)
                )
            }

            crimeSolved.isChecked = crime.isSolved

            crimeReport.setOnClickListener {
                val reportIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getCrimeReport(crime))
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject)
                    )
                }
                val chooserIntent = Intent.createChooser(
                    reportIntent,
                    getString(R.string.send_report)
                )
                startActivity(chooserIntent)
            }

            crimeSuspect.text = crime.suspect.ifEmpty {
                getString(R.string.crime_suspect_text)
            }

            updatePhoto(crime.photoFileName)
        }
    }

    private fun getCrimeReport(crime: Crime): String {
        val solvedString = if (crime.isSolved) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }

        val suspectText = if (crime.suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crime.suspect)
        }

        return getString(
            R.string.crime_report,
            crime.title, dateString, solvedString, suspectText
        )
    }

    private fun parseContactSelection(contactUri: Uri) {
        val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

        val queryCursor = requireActivity().contentResolver
            .query(contactUri, queryFields, null, null, null)

        queryCursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val suspect = cursor.getString(0)
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(suspect = suspect)
                }
            }
        }
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }
}

/*

In case of Camera:

 crimeCamera.setOnClickListener {
                photoName = "IMG_${Date()}.JPG"
                val photoFile = File(
                    requireContext().applicationContext.filesDir,
                    photoName
                )
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.bignerdranch.android.criminalintent.fileprovider",
                    photoFile
                )

                takePhoto.launch(photoUri)
            }


Structure might be relevant lateron:

 crimeSuspect.setOnClickListener {
                selectSuspect.launch(null)
            }




*/