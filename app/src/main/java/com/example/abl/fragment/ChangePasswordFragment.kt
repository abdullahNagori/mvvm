package com.example.abl.fragment

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.base.BaseDialogFragment
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentChangePasswordBinding
import com.example.abl.model.ChangePasswordModel
import com.example.abl.model.GenericMsgResponse
import com.example.abl.model.OtpResponse
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.ValidationHelper
import java.lang.Exception
import java.util.regex.Pattern
import javax.inject.Inject

class ChangePasswordFragment : BaseDockFragment(){


    lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        myDockActivity?.getUserViewModel()?.apiListener = this

        binding.btnSubmit.setOnClickListener {
            auth()
        }

        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun showBanner(text: String, type: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")

    }

    private fun auth() {
        if(binding.edOldPassword.text.toString().isNullOrEmpty()){
            binding.parentEdOldPassword.error = getString(R.string.error_empty_field)
            return
        }
        if(binding.edNewPassword.text.toString().isNullOrEmpty()){
            binding.parentEdNewPassword.error = getString(R.string.error_empty_field)
            return
        }
        if(binding.edConfirmPassword.text.toString().isNullOrEmpty()){
            binding.parentEdConfirmPassword.error = getString(R.string.error_empty_field)
            return
        }

        if((binding.edConfirmPassword.text.toString()) != (binding.edNewPassword.text.toString())){
            binding.parentEdConfirmPassword.error = getString(R.string.error_different_fields)
            return
        }

        if(!isValidPassword(binding.edNewPassword.text.toString())){
            showPasswordchangingInstructions(getString(R.string.error_wrong_password_pattern))
            return
        }
        if(binding.edNewPassword.length()<8) {
            showPasswordchangingInstructions(getString(R.string.error_wrong_password_pattern))
            return
        }
        changePassword(ChangePasswordModel(sharedPrefManager.getLoginID().toString(),binding.edOldPassword.text.toString(),
        binding.edNewPassword.text.toString(), binding.edConfirmPassword.text.toString()))
    }

    private fun changePassword(changePasswordModel: ChangePasswordModel){
        myDockActivity?.getUserViewModel()?.changePassword(changePasswordModel)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when(tag){
            Constants.CHANGE_PASSWORD -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val changePasswordEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, GenericMsgResponse::class.java)

                        validationhelper.navigateToLogin()
                }
                catch (e: Exception){

                }
            }
        }
    }

    fun isValidPassword(str: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+_=;*])(?=\\S+$).{4,}$"
        val p = Pattern.compile(regex)
        return p.matcher(str).matches()
    }
}