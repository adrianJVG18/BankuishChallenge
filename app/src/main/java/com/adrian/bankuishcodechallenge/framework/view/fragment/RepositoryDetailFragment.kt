package com.adrian.bankuishcodechallenge.framework.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.adrian.bankuishcodechallenge.R
import com.adrian.bankuishcodechallenge.adapter.model.RepositoryUi
import com.adrian.bankuishcodechallenge.databinding.FragmentRepositoryDetailBinding
import com.adrian.bankuishcodechallenge.framework.utils.formatTo
import com.adrian.bankuishcodechallenge.framework.utils.viewBinding
import com.squareup.picasso.Picasso
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private var repositoryUi: RepositoryUi = RepositoryUi()

    private val binding: FragmentRepositoryDetailBinding by viewBinding(
        FragmentRepositoryDetailBinding::bind
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgument()
        setUpViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpViews() {
        with(binding) {
            repositoryNameText.text = repositoryUi.name
            descriptionText.text = repositoryUi.description
            repositoryAuthorText.text = repositoryUi.author
            ownerUrlText.text = repositoryUi.ownerUrl
            issuesCountText.text = "${repositoryUi.issues}"
            watchersCountText.text = "${repositoryUi.watchers}"
            Picasso.get()
                .load(repositoryUi.avatarUrl)
                .placeholder(R.drawable.baseline_person_24)
                .into(avatarImageview)
        }
    }

    private fun getArgument() {
        if (arguments != null) {
            repositoryUi = RepositoryUi(
                name = requireArguments().getString(RepositoryUi.NAME) ?: "",
                author = requireArguments().getString(RepositoryUi.AUTHOR) ?: "",
                description = requireArguments().getString(RepositoryUi.DESCRIPTION) ?: "",
                creationDate = requireArguments().getString(RepositoryUi.CREATION_DATE) ?: "",
                updateDate = requireArguments().getString(RepositoryUi.UPDATED_DATE) ?: "",
                ownerUrl = requireArguments().getString(RepositoryUi.OWNER_URL) ?: "",
                avatarUrl = requireArguments().getString(RepositoryUi.AVATAR_URL) ?: "",
                watchers = requireArguments().getInt(RepositoryUi.WATCHERS),
                issues = requireArguments().getInt(RepositoryUi.ISSUES)
            )
        } else {
            toasting("error al obtener argumentos")
        }
    }

    private fun toasting(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}