package com.capgemini.tasktracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capgemini.tasktracker.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    /* TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

     */

    lateinit var taskVM: TaskViewModel
    lateinit var UserIdEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton:Button
    lateinit var registerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize the viewModel
        taskVM = ViewModelProvider(this)[TaskViewModel::class.java]
        //observe liveData

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
            UserIdEditText.setText(name)}


        registerButton.setOnClickListener{
                findNavController().navigate(R.id.registerFragment)
        }
        loginButton.setOnClickListener{
            var username = UserIdEditText.text.toString()
            var pwd = passwordEditText.text.toString()

            /*if(username.isNotEmpty() && pwd.isNotEmpty())
            {
                CoroutineScope(Dispatchers.IO).launch {
                    taskVM.getUser(username, pwd).observe(this@LoginFragment, Observer { user ->
                        val dataBundle = bundleOf("username" to username, "password" to pwd)
                        CoroutineScope(Dispatchers.IO).launch {
                            var user = taskVM.getUser(user.username, user.password)
                            if (user != null) {
                                findNavController().navigate(R.id.taskList, dataBundle)
                            } else
                                Snackbar.make(requireView(), "Invalid credentials", Snackbar.LENGTH_LONG ).show()
                        }
                    })
                }
            }*/
            if(username.isNotEmpty() && pwd.isNotEmpty())
            {
                val dataBundle = bundleOf("username" to username, "password" to pwd)

                var user = taskVM.getUser(username, pwd)
                if (user != null) {
                    findNavController().navigate(R.id.taskList, dataBundle)
                } else
                    Snackbar.make(requireView(), "User does not exist. Please Sign Up!", Snackbar.LENGTH_LONG ).show()
            }
            else
            {
                Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}