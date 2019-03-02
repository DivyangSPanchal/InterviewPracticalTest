package practicaltest.com.practical.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_selected_user_list.*
import practicaltest.com.practical.R
import practicaltest.com.practical.base.ActivityBase
import practicaltest.com.practical.databinding.ActivitySelectedUserListBinding
import practicaltest.com.practical.databinding.ItemUserListBinding
import practicaltest.com.practical.model.ModelUserList
import practicaltest.com.practical.viewmodel.ViewModelActivitySelectedUserList


class ActivitySelectedUserList : ActivityBase<ViewModelActivitySelectedUserList>() {

    override val viewModel: ViewModelActivitySelectedUserList
        get() = ViewModelProviders.of(this).get(ViewModelActivitySelectedUserList::class.java)
    private lateinit var mContext: Context;
    private var adapterUserList: AdapterUserList? = null
    private lateinit var bindingObj: ActivitySelectedUserListBinding
    private lateinit var linearLayoutManager: LinearLayoutManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingObj = DataBindingUtil.setContentView(this, R.layout.activity_selected_user_list)
        bindingObj.vmObj = this.viewModel
        mContext = this

        getIntentData()
        initComponent()
        setUpToolbar()
        setUpRecyclerAdapter()
    }

    private fun getIntentData() {
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra("selectedUser")) {
                viewModel.selectedUserList = intent.getSerializableExtra("selectedUser") as ArrayList<ModelUserList>
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun initComponent() {
        linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rvUsers.setLayoutManager(linearLayoutManager);
        rvUsers.setNestedScrollingEnabled(false);
    }

    private fun setUpToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.selected_user_list)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
    }

    private fun setUpRecyclerAdapter() {
        if (viewModel.selectedUserList != null && !(viewModel.selectedUserList).isEmpty()) {
            if (adapterUserList == null) {
                adapterUserList = AdapterUserList()
                rvUsers.adapter = adapterUserList
            }
        }
    }

    internal inner class AdapterUserList : RecyclerView.Adapter<AdapterUserList.MyViewHolder>() {
        lateinit var itemUserListBinding: ItemUserListBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            itemUserListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_user_list, parent, false)
            return MyViewHolder(itemUserListBinding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val user = viewModel.selectedUserList.get(position)
            if (user != null) {
                holder.bind(user, position)
            }
        }

        override fun getItemCount(): Int {
            return viewModel.selectedUserList.size
        }

        internal inner class MyViewHolder(var itemUserListBinding: ItemUserListBinding) : RecyclerView.ViewHolder(itemUserListBinding.getRoot()) {

            fun bind(user: ModelUserList, position: Int) {
                itemUserListBinding.user = user
            }
        }
    }
}
