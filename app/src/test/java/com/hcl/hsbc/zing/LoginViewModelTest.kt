package com.hcl.hsbc_zing

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hcl.hsbc_zing.viewmodel.loginviewmodel.LoginViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    @Before
    fun setup(){
        loginViewModel = LoginViewModel()
    }

    @Test
    fun testInsertData(){

    }

}