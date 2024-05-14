package com.hcl.hsbc.zing.view.favouritepayee

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hcl.hsbc.zing.R
import com.hcl.hsbc.zing.view.favouritepayee.adapters.RecyclerViewAdapter
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel
import com.hcl.hsbc.zing.viewmodel.favouritepayeeviewmodel.FavouritePayeeViewModel


class Favouritepayee : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var floatingActionButton2: FloatingActionButton
    lateinit var favouritePayeeViewModel: FavouritePayeeViewModel
    lateinit var context: Context
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favouritepayee)
        favouritePayeeViewModel = ViewModelProvider(this).get(FavouritePayeeViewModel::class.java)
        favouritePayeeInit()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredlist: ArrayList<FavouritePayeeSummaryModel> = ArrayList()
                for (item in adapter.getItems()) {
                    if (item.accountNumber.toLowerCase().contains(newText!!.toLowerCase())) {
                        filteredlist.add(item)
                    }
                }
                if (filteredlist.isEmpty()) {
                    Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.filterList(filteredlist)
                }
                return false
            }

        })

    }

    private fun favouritePayeeInit() {
        context = this@Favouritepayee
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        floatingActionButton2 = findViewById(R.id.floatingActionButton2)
        favouritePayeeViewModel.getFavouritePayee(context)!!
            .observe(this, Observer {
                if (it != null) {
                    adapter =
                        RecyclerViewAdapter(it, object : RecyclerViewAdapter.OnItemClickListener {
                            override fun onItemClick(item: FavouritePayeeSummaryModel?) {
                                Toast.makeText(context, item.toString(), Toast.LENGTH_LONG).show()
                            }

                            override fun editClick(item: FavouritePayeeSummaryModel?) {
                                item?.Id?.let { id ->
                                    showAlertDialog(
                                        item!!.accountNumber,
                                        item.payeeName,
                                        item.bankName,
                                        item.numberOfTimes,
                                        id,
                                        "edit"
                                    )
                                }
                            }

                            override fun deleteClick(item: FavouritePayeeSummaryModel?) {
                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                                builder
                                    .setMessage("")
                                    .setTitle("Are you sure want to delete")
                                    .setPositiveButton("Delete") { dialog, which ->
                                        favouritePayeeViewModel.deleteFavPayee(context,item?.accountNumber)
                                    }
                                    .setNegativeButton("No") { dialog, which ->
                                        dialog.dismiss()
                                    }

                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            }

                        })
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = adapter
                }
            })

        floatingActionButton2.setOnClickListener {
            showAlertDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAlertDialog(
        accountNumberFromArgument: String = "",
        payeeNameFromArgument: String = "",
        bankNameFromArgument: String = "",
        numberOfTimesFromArgument: Int = 0,
        Id: Int = 0,
        edit: String = ""
    ) {
        var times = numberOfTimesFromArgument
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val listOfbanks = listOf<String>(
            "HSBC",
            "DBS",
            "HDFC",
            "SBI",
            "BANK Of AMERICA",
            "BANK OF BARODA",
            "ICICI"
        )
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, listOfbanks)

        val view = layoutInflater.inflate(R.layout.add_favourite_payee, null)
        val add = view.findViewById<Button>(R.id.add)
        val accountnumber = view.findViewById<EditText>(R.id.accountnumber)
        val payeeName = view.findViewById<EditText>(R.id.payeename)
        val bankName: AutoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.bankname)
        bankName.setAdapter(adapter)
        val title = view.findViewById<TextView>(R.id.titleofdialog)
        if (!accountNumberFromArgument.isEmpty() || !payeeNameFromArgument.isEmpty() ||
            !bankNameFromArgument.isEmpty()
        ) {
            title.text = "Edit your Favourite Payee"
            accountnumber.text = accountNumberFromArgument.toEditable()
            payeeName.text = payeeNameFromArgument.toEditable()
            bankName.text = bankNameFromArgument.toEditable()
            add.text = "Edit"
        }
        builder.setView(view)
        add.setOnClickListener {
            if (edit.equals("edit")) {
                times = +1
                if (!accountnumber.text.toString().isEmpty() || !payeeName.text.toString()
                        .isEmpty() || !bankName.text.toString().isEmpty()
                ) {
                    favouritePayeeViewModel.editFavPayee(
                        context,
                        FavouritePayeeSummaryModel(
                            accountnumber.text.toString(),
                            payeeName.text.toString(),
                            bankName.text.toString(),
                            times
                        ),
                        Id
                    )
                    builder.dismiss()
                }
            } else if (!accountnumber.text.toString().isEmpty() || !payeeName.text.toString()
                    .isEmpty() || !bankName.text.toString().isEmpty()
            ) {
                favouritePayeeViewModel.insertData(
                    context,
                    accountnumber.text.toString(),
                    payeeName.text.toString(),
                    bankName.text.toString()
                )
                builder.dismiss()
            } else {
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_LONG)
                    .show()
            }
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}