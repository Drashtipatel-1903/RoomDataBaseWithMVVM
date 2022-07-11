package com.example.roomdatabasedemo.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.adapters.UserDataAdapter
import com.example.roomdatabasedemo.dao.UserDao
import com.example.roomdatabasedemo.database.UserDatabase
import com.example.roomdatabasedemo.databinding.CustomDialogsBinding
import com.example.roomdatabasedemo.databinding.FragmentSignupBinding
import com.example.roomdatabasedemo.modal.UserEntity
import com.example.roomdatabasedemo.repository.UserRepository
import com.example.roomdatabasedemo.viewmodals.UserViewModal
import com.example.roomdatabasedemo.viewmodals.UserViewModalFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch


class SignupFragment : Fragment() , UserDataAdapter.DeleteItemClickListner , UserDataAdapter.UpdateItemClickListner
{

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding
    private var dataList: MutableList<UserEntity> = ArrayList()
    private lateinit var viewModal: UserViewModal
    private lateinit var repository: UserRepository
    private lateinit var adapter: UserDataAdapter
    private lateinit var userEntity: UserEntity
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val toolbar = view?.findViewById<MaterialToolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val binding: FragmentSignupBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        val db = Room.databaseBuilder(
            requireContext(),
            UserDatabase::class.java, "user_database"
        ).build()

        val userDao = UserDatabase.getInstance(requireContext()).userDao()
        repository = UserRepository(userDao)
        val factory = UserViewModalFactory(repository)

        viewModal = ViewModelProvider(this , factory)[UserViewModal::class.java]

        binding.btnAddRecords.setOnClickListener {
            val username = binding.edUsername.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            //addUserData(db.userDao())

            if(email.isNotEmpty() && password.isNotEmpty())
            {
                lifecycleScope.launch {
                    viewModal.insertUserData(
                        UserEntity(
                            0,
                            username = username,
                            email = email,
                            password = password
                        )
                    )
                    Toast.makeText(context, "User Record Added Successfully..!!!", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.edUsername.text?.clear()
                binding.edUsername.clearFocus()
                binding.edEmail.text?.clear()
                binding.edPassword.text?.clear()
            }
            else{
                Toast.makeText(context, "User Record Unsuccessfully..!!!", Toast.LENGTH_SHORT)
                    .show()
            }
            //binding.tvNoRecord.visibility = View.GONE
        }

        viewModal.getAllUserData().observe(viewLifecycleOwner , Observer {

            dataList.clear()
            dataList.addAll(it)
            Log.d("Tag" , dataList.toString())
            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            adapter = UserDataAdapter(dataList , this , this)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
            //adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onItemClickDeleteData(userEntity: UserEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are You Sure?")
        builder.setMessage("You want to delete this record")
        builder.setIcon(R.drawable.alert)
        builder.setPositiveButton("Yes"){dialogInterface , _->

                viewModal.deleteUserData(userEntity)
                adapter.notifyDataSetChanged()

                Toast.makeText(requireContext(), "User Deleted Successfully", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){dialogInterface , _->
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Cancel"){dialogInterface , _->
            dialogInterface.dismiss()
        }
        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onItemClickUpdateData(userEntity: UserEntity) {
        val updateDialog = Dialog(requireContext())
        val dialogBinding : CustomDialogsBinding = CustomDialogsBinding.inflate(LayoutInflater.from(requireContext()))
        updateDialog.setContentView(dialogBinding.root)
        updateDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.edUpdateUsername.setText(userEntity.username)
        dialogBinding.edUpdateEmail.setText(userEntity.email)

        dialogBinding.btnUpdate.setOnClickListener {
            lifecycleScope.launch {
                var user = dialogBinding.edUpdateUsername.text.toString()
                var email = dialogBinding.edUpdateEmail.text.toString()
                viewModal.updateUserData(UserEntity(username = user , email = email , id = userEntity.id))
                Toast.makeText(requireContext(), "User Data Updated", Toast.LENGTH_SHORT).show()
                updateDialog.dismiss()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.setTitle("Update Data")

        updateDialog.create()
        updateDialog.show()


    }

//    private fun addUserData(userDao: UserDao)
//    {
//
//        val username = binding!!.edUsername.text.toString()
//        val email = binding!!.edEmail.text.toString()
//        val password = binding!!.edPassword.text.toString()
//
//        if(email.isNotEmpty() && password.isNotEmpty())
//        {
//            lifecycleScope.launch {
//                val data = userDao.insertUserData(UserEntity(0,username=username , email = email , password = password))
//                Toast.makeText(context, "User Record Added Successfully..!!!", Toast.LENGTH_SHORT).show()
//                Log.d("tag" , data.toString())
//                binding!!.edUsername.text!!.clear()
//                binding!!.edEmail.text!!.clear()
//                binding!!.edPassword.text!!.clear()
//            }
//        }
//        else
//        {
//            Toast.makeText(context, "Something Went Wrong..Please Try Again!!", Toast.LENGTH_SHORT).show()
//        }
//    }
}