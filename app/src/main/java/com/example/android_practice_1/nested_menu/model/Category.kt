package com.example.android_practice_1.nested_menu.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.example.android_practice_1.BR



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Category() : Parcelable, BaseObservable() {

    @JsonProperty("id")
    var id: String = ""

    @JsonProperty("name")
    var name: String = ""

    // Change this to match exactly with your JSON field name
    @JsonProperty("child")
    var child: ArrayList<Category> = ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    @get:Bindable
    var isExpanded: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.expanded)
        }

    val hasChildren: Boolean
        @Bindable get() = child.isNotEmpty()

    // Update Parcelable implementation to match
    constructor(parcel: Parcel) : this() {
        id = parcel.readString() ?: ""
        name = parcel.readString() ?: ""
        child = parcel.createTypedArrayList(CREATOR) ?: ArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeTypedList(child)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category = Category(parcel)
        override fun newArray(size: Int): Array<Category?> = arrayOfNulls(size)
    }
}