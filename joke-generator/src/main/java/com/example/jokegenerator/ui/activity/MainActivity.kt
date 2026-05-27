package com.example.jokegenerator.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.jokegenerator.data.api.RetrofitClient
import com.example.jokegenerator.data.repository.JokeRepository
import com.example.jokegenerator.databinding.ActivityMainBinding
import com.example.jokegenerator.ui.viewmodel.JokeState
import com.example.jokegenerator.ui.viewmodel.JokeViewModel
import com.example.jokegenerator.ui.viewmodel.JokeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { JokeRepository(RetrofitClient.jokeApiService) }
    private val viewModel: JokeViewModel by viewModels {
        JokeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Get Joke Button
        binding.getJokeButton.setOnClickListener {
            viewModel.getRandomJoke()
        }

        // Safe Mode Switch
        binding.safeModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSafeMode(isChecked)
        }

        // Category Spinner
        viewModel.categories.observe(this) { categories ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categories
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter

            binding.categorySpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.setSelectedCategory(categories[position])
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
            }
        }

        // Share Button
        binding.shareButton.setOnClickListener {
            val jokeText = binding.jokeText.text.toString()
            if (jokeText.isNotEmpty()) {
                shareJoke(jokeText)
            } else {
                Toast.makeText(this, "No joke to share yet!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        viewModel.jokeState.observe(this) { state ->
            when (state) {
                is JokeState.Idle -> {
                    binding.jokeText.text = "Tap 'Get Joke' to start laughing!"
                    binding.progressBar.visibility = View.GONE
                    binding.getJokeButton.isEnabled = true
                }
                is JokeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.jokeText.text = ""
                    binding.getJokeButton.isEnabled = false
                    binding.categoryInfo.text = ""
                }
                is JokeState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.jokeText.text = state.joke.content
                    binding.categoryInfo.text = "📁 ${state.joke.category.uppercase()} | Type: ${state.joke.type.uppercase()}"
                    binding.getJokeButton.isEnabled = true
                }
                is JokeState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.jokeText.text = "Error: ${state.message}"
                    binding.getJokeButton.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun shareJoke(jokeText: String) {
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            putExtra(android.content.Intent.EXTRA_TEXT, jokeText)
            type = "text/plain"
        }
        startActivity(android.content.Intent.createChooser(shareIntent, "Share Joke"))
    }
}
