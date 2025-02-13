package com.example.submissionpertama.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.submissionpertama.R
import com.example.submissionpertama.data.Result
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity
import com.example.submissionpertama.databinding.ActivityDetailEventBinding
import com.example.submissionpertama.ui.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private var favoriteEventEntity: FavoriteEventEntity? = null
    private var isFavoriteEvent: Boolean = false
    private val detailEventViewModel: DetailEventViewModel by viewModels {
        ViewModelFactory.getInstance(application)!!
    }

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

        favoriteEventEntity = FavoriteEventEntity()

        supportActionBar?.hide()

        detailEventViewModel.fetchDetailEvent(id).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        setDetailEventData(result.data)
                    }

                    is Result.Error -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            detailEventViewModel.getFavoriteEventById(id).observe(this) { isFavorite ->
                isFavoriteEvent = isFavorite != null
                binding.ibFavorite.setImageResource(
                    if (isFavoriteEvent) R.drawable.baseline_favorite_24
                    else R.drawable.baseline_favorite_border_24
                )
            }
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
        val quota =
            getString(R.string.remaining_quota, (detailEvent.quota - detailEvent.registrants))
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
        binding.ibFavorite.setOnClickListener {
            favoriteEventEntity.let { favoriteEvent ->
                favoriteEvent?.id = detailEvent.id
                favoriteEvent?.name = detailEvent.name
                favoriteEvent?.imageLogo = detailEvent.imageLogo
            }

            if (isFavoriteEvent){
                detailEventViewModel.delete(favoriteEventEntity as FavoriteEventEntity)
                Toast.makeText(this, "BERHASIL MENGHAPUS DARI FAVORIT", Toast.LENGTH_SHORT).show()
            } else {
                detailEventViewModel.insert(favoriteEventEntity as FavoriteEventEntity)
                Toast.makeText(this, "BERHASIL DI TAMBAHKAN KE FAVORIT", Toast.LENGTH_SHORT).show()
            }

        }
    }
}