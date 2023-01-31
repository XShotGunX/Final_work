package com.example.finalwork.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.finalwork.R
import com.example.finalwork.model.model.StateFragments
import com.example.finalwork.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModels()

    private lateinit var addFragment: AddFragment
    private lateinit var viewFragment: ViewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment = AddFragment.newInstance()
        viewFragment = ViewFragment.newInstance()
        postViewModel.currentFragment.observe(this) {
            when(it){
                StateFragments.View -> postViewModel.goToView(supportFragmentManager, viewFragment)
                StateFragments.Add -> postViewModel.goToAdd(supportFragmentManager, addFragment)
            }
        }
    }
}