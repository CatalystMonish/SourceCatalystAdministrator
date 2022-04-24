package com.catalystmedia.sourcecatalystadministrator.models

class Registrations {
    private var userEmail:String =""
    private var verified:Boolean = false


constructor()
    constructor(userEmail:String, verified:Boolean){
        this.userEmail = userEmail
        this.verified = verified

    }


    fun getUserEmail():String{
        return userEmail
    }

    fun setUserEmail(userEmail: String){
        this.userEmail = userEmail
    }

    fun getVerified():Boolean{
        return verified
    }

    fun setVerified(verified: Boolean){
        this.verified = verified
    }
}
