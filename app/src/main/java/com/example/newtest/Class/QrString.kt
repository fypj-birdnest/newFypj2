package com.example.newtest.Class

import java.io.Serializable

data class QrString(var tvalue: String?,var brand:String?,var authen:String?,var collagen:String?,var saliva:String?,var acidity:String?,var country:String?) : Serializable{


        init {
                this.tvalue = tvalue
                this.authen = authen
                this.collagen = collagen
                this.saliva = saliva
                this.brand = brand
                this.acidity = acidity
                this.country = country


        }

}