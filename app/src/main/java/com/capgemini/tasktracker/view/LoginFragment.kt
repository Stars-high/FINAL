package com.capgemini.tasktracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capgemini.tasktracker.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
class LoginFragment : Fragment() {

    lateinit var taskVM: TaskViewModel
    lateinit var UserIdEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton:Button
    lateinit var registerButton: Button
    lateinit var sharedPreferences: SharedPreferences

    var PREFS_KEY = "prefs"
    var UNAME_KEY = "uname"
    var PWD_KEY = "pwd"
    var uname=""
    var pwd=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initializing shared preferences
        sharedPreferences = activity!!.applicationContext.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        uname = sharedPreferences.getString(UNAME_KEY, "").toString()   //getting data from shared prefs
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize the viewModel
        taskVM = ViewModelProvider(this)[TaskViewModel::class.java]

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserIdEditText=view.findViewById(R.id.userIdT)
        passwordEditText=view.findViewById(R.id.passET)
        loginButton=view.findViewById(R.id.loginB)
        registerButton=view.findViewById(R.id.registerB)

        arguments?.let { val name= it.getString("username")
            UserIdEditText.setText(name)
        }

        registerButton.setOnClickListener{
                findNavController().navigate(R.id.registerFragment)
        }
        loginButton.setOnClickListener{
            var username = UserIdEditText.text.toString()
            var pwd = passwordEditText.text.toString()

            if(username.isNotEmpty() && pwd.isNotEmpty())
            {
                //val dataBundle = bundleOf("username" to username, "password" to pwd)
                if(taskVM.isTaken(username)){
                    if (taskVM.getUsername(username, pwd)==username && taskVM.getPassword(username, pwd)==pwd) {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString(UNAME_KEY, username)   //adding data to shared prefs to save them
                        editor.putString(PWD_KEY, pwd)

                        editor.apply()  //apply changes to shared prefs

                        findNavController().navigate(R.id.taskList)
                        Snackbar.make(requireView(), "Hi $username!", Toast.LENGTH_LONG ).show()
                    }
                    else{
                        Snackbar.make(requireView(), "Invalid Password", Toast.LENGTH_LONG ).show()
                    }
                }
                else{
                    Snackbar.make(requireView(), "Username does not exist. Please Sign Up!", Snackbar.LENGTH_LONG ).show()
                }
            }
            else
            {
                Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(uname!="" && pwd!=""){
            findNavController().navigate(R.id.taskList)
        }
    }
}