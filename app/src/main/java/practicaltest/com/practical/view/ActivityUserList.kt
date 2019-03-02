package practicaltest.com.practical.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_user_list.*
import practicaltest.com.practical.R
import practicaltest.com.practical.base.ActivityBase
import practicaltest.com.practical.databinding.ActivityUserListBinding
import practicaltest.com.practical.databinding.ItemUserListBinding
import practicaltest.com.practical.model.ModelUserList
import practicaltest.com.practical.utility.UDF
import practicaltest.com.practical.viewmodel.ViewModelActivityUserList


class ActivityUserList : ActivityBase<ViewModelActivityUserList>() {

    override val viewModel: ViewModelActivityUserList
        get() = ViewModelProviders.of(this).get(ViewModelActivityUserList::class.java)
    private lateinit var mContext: Context;
    private var adapterUserList: AdapterUserList? = null
    private lateinit var bindingObj: ActivityUserListBinding
    private val reqCode = 101
    private lateinit var linearLayoutManager: LinearLayoutManager;
    private var previousItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingObj = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        bindingObj.vmObj = this.viewModel
        mContext = this

        initComponent()
        setUpToolbar()
        initiateUserlistApiCall()
        setUpEvents()
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
        toolbar.setTitle(R.string.user_list)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
    }

    private fun initiateUserlistApiCall() {
        if (UDF.isOnline(mContext)) {
            viewModel.callApiUserList("${viewModel.nextSince}")
        } else {
            displaySnackbarWithAction()
        }
    }

    private fun setUpRecyclerAdapter() {
        if (viewModel.userList != null && !(viewModel.userList).isEmpty()) {
            if (adapterUserList == null) {
                adapterUserList = AdapterUserList()
                rvUsers.adapter = adapterUserList
            } else {
                adapterUserList?.notifyDataSetChanged()
            }
        }
    }

    private fun setUpEvents() {
        viewModel.getParsingDoneEvent().observe(this, Observer { parsingDone ->
            if (parsingDone != null) {
                if (parsingDone) {
                    setUpRecyclerAdapter()
                }
            }
        })
        fab.hide()
        fab.setOnClickListener { view ->
            val intent = Intent(mContext, ActivitySelectedUserList::class.java)
            intent.putExtra("selectedUser", viewModel.selectedUserList as ArrayList<ModelUserList>)
            startActivity(intent)
            overridePendingTransition(R.anim.pull_from_right, R.anim.stay)
        }

        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView!!.layoutManager?.itemCount
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.callApiUserList("$viewModel.nextSince")
                }

                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() !=View.VISIBLE) {
                    if(viewModel.selectedUserList != null && !viewModel.selectedUserList.isEmpty()) {
                        fab.show();
                    }
                }
            }
        })
    }

    private fun displaySnackbarWithAction() {
        Snackbar.make(
                bindingObj.root,
                mContext.resources.getString(R.string.message_no_connection),
                Snackbar.LENGTH_INDEFINITE
        ).setAction(
                mContext.resources.getString(R.string.retry),
                {
                    initiateUserlistApiCall()
                }).show()
    }

    internal inner class AdapterUserList : RecyclerView.Adapter<AdapterUserList.MyViewHolder>() {
        lateinit var itemUserListBinding: ItemUserListBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            itemUserListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_user_list, parent, false)
            return MyViewHolder(itemUserListBinding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val user = viewModel.userList.get(position)
            if (user != null) {
                holder.bind(user, position)
            }
        }

        override fun getItemCount(): Int {
            return viewModel.userList.size
        }

        internal inner class MyViewHolder(var itemUserListBinding: ItemUserListBinding) : RecyclerView.ViewHolder(itemUserListBinding.getRoot()) {

            fun bind(user: ModelUserList, position: Int) {
                itemUserListBinding.user = user
                itemUserListBinding.cbSelection.visibility = View.VISIBLE
                itemUserListBinding.cbSelection.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked) {
                        if(!(viewModel.selectedUserList as ArrayList<ModelUserList>).contains(viewModel.userList.get(adapterPosition))) {
                            (viewModel.selectedUserList as ArrayList<ModelUserList>).add(viewModel.userList.get(adapterPosition))
                            viewModel.userList.get(adapterPosition).isChecked = true
                        }
                    }else{
                        if((viewModel.selectedUserList as ArrayList<ModelUserList>).contains(viewModel.userList.get(adapterPosition))) {
                            (viewModel.selectedUserList as ArrayList<ModelUserList>).remove(viewModel.userList.get(adapterPosition))
                            viewModel.userList.get(adapterPosition).isChecked = false
                        }
                    }

                    if(!(viewModel.selectedUserList as ArrayList<ModelUserList>).isEmpty()){
                        fab.show()
                    }else{
                        fab.hide()
                    }
                }
            }
        }
    }
}
