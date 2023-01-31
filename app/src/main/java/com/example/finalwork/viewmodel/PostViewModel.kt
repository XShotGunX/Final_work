package com.example.finalwork.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.finalwork.R
import com.example.finalwork.model.api.PostApi
import com.example.finalwork.model.database.Post
import com.example.finalwork.model.database.PostDatabase
import com.example.finalwork.model.model.StateFragments
import com.example.finalwork.view.AddFragment
import com.example.finalwork.view.ViewFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {
    private val _postsLiveData: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val postLiveData: LiveData<List<Post>> = _postsLiveData

    private val _currentFragment: MutableLiveData<StateFragments> = MutableLiveData(StateFragments.View)
    val currentFragment: LiveData<StateFragments> = _currentFragment

    var database: PostDatabase? = null

    fun goToView(fragmentManager: FragmentManager, viewFragment: ViewFragment){
        fragmentManager.beginTransaction()
            .replace(R.id.container, viewFragment)
            .commit()
    }

    fun goToAdd(fragmentManager: FragmentManager, addFragment: AddFragment){
        fragmentManager.beginTransaction()
            .replace(R.id.container, addFragment)
            .commit()
    }

    fun getPostsFromServer(postApi: PostApi, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val response = postApi.getPosts()
            putToDB(response.body()!!, context)
        }
    }

    fun putToDB(posts: List<Post>, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            database!!.getDao().insertPosts(*posts.toTypedArray())
        }
    }

    fun getPostsFromDb(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            _postsLiveData.postValue(database!!.getDao().getPosts())
        }
    }

    fun putPostToDb(post: Post,context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            database!!.getDao().insertPost(post)
        }
    }

    fun changeFragment(stateFragments: StateFragments){
        _currentFragment.postValue(stateFragments)
    }
}