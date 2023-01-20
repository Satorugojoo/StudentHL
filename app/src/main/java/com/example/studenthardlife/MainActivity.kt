package com.example.studenthardlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenthardlife.databinding.FragmentTaskOverviewBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { FragmentTaskOverviewBinding.inflate(layoutInflater) }
    private val dbHandler by lazy { DBHandler(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}