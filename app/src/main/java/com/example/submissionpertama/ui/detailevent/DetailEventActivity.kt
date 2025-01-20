package com.example.submissionpertama.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionpertama.R
import com.example.submissionpertama.data.response.EventItem
import com.example.submissionpertama.databinding.ActivityDetailEventBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra("event_id", 0)

        val detailEventViewModel = ViewModelProvider(this).get(DetailEventViewModel::class.java)
        detailEventViewModel.fetchDetailEvent(id)

        supportActionBar?.hide()

        detailEventViewModel.detailEvent.observe(this) { detailEvent ->
            setDetailEventData(detailEvent)
        }

        detailEventViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailEventViewModel.errorMessage.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
            binding.ivBanner.visibility = View.GONE
            binding.tvTitleEventDetail.visibility = View.GONE
            binding.tvOwnerName.visibility = View.GONE
            binding.tvBeginTime.visibility = View.GONE
            binding.tvQuota.visibility = View.GONE
            binding.tvDescription.visibility = View.GONE
            binding.tvDescriptionEvent.visibility = View.GONE
            binding.btnRegister.visibility = View.GONE
        } else {
            binding.progressBarDetail.visibility = View.GONE
            binding.ivBanner.visibility = View.VISIBLE
            binding.tvTitleEventDetail.visibility = View.VISIBLE
            binding.tvOwnerName.visibility = View.VISIBLE
            binding.tvBeginTime.visibility = View.VISIBLE
            binding.tvQuota.visibility = View.VISIBLE
            binding.tvDescription.visibility = View.VISIBLE
            binding.tvDescriptionEvent.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.VISIBLE
        }
    }

    private fun setDetailEventData(detailEvent: EventItem) {
        Glide.with(this)
            .load(detailEvent.mediaCover)
            .into(binding.ivBanner)
        binding.tvTitleEventDetail.text = detailEvent.name
        binding.tvOwnerName.text = detailEvent.ownerName
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val beginTime = inputFormat.parse(detailEvent.beginTime)
        val formattedDate = outputFormat.format(beginTime as Date)
        binding.tvBeginTime.text = formattedDate
        val quota = getString(R.string.remaining_quota, detailEvent.quota)
        binding.tvQuota.text = quota
        binding.tvDescriptionEvent.text = HtmlCompat.fromHtml(
            detailEvent.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.btnRegister.setOnClickListener {
            val intentOpenLink = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(detailEvent.link)
            }
            startActivity(intentOpenLink)
        }
    }
}