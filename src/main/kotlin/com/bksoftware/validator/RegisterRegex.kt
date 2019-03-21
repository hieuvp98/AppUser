package com.bksoftware.validator

import java.util.regex.Pattern

class RegisterRegex {
    companion object {
        fun checkEmail(email:String):Boolean{
            val regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
            val pattern = Pattern.compile(regex)
            return pattern.matcher(email).matches()
        }
        fun checkPhone(phoneNumber:String):Boolean{
            val regex = "^[0-9]{9,10}$"
            return Pattern.compile(regex).matcher(phoneNumber).matches()
        }
        fun checkUsername(username:String):Boolean{
            val regex = "^[a-zA-Z0-9]{6,20}$"
            return Pattern.compile(regex).matcher(username).matches()
        }
        fun checkName(name:String):Boolean{
            val regex = "^[a-zA-Z ]$"
            return Pattern.compile(regex).matcher(name).matches()
        }
    }
}