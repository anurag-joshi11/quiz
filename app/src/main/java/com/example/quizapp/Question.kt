package com.example.quizapp

import android.os.Parcel
import android.os.Parcelable

// Data class representing a quiz question. Implements Parcelable so it can be passed between activities.
data class Question(
    val question: String, // The text of the question
    val options: List<String>, // A list of possible answer choices
    val correctAnswerIndex: Int // The index (position) of the correct answer in the options list
) : Parcelable {

    // Secondary constructor to create a Question object from a Parcel (used for passing data between activities)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Read the question string from the parcel, default to empty string if null
        parcel.createStringArrayList() ?: arrayListOf(), // Read the options list from the parcel, default to empty list if null
        parcel.readInt() // Read the correct answer index from the parcel
    )

    // Writes the object's data to the parcel (needed for Parcelable implementation)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question) // Store the question text in the parcel
        parcel.writeStringList(options) // Store the options list in the parcel
        parcel.writeInt(correctAnswerIndex) // Store the index of the correct answer in the parcel
    }

    // Describes the content type of the Parcelable object (usually 0, unless special behavior is needed)
    override fun describeContents(): Int = 0

    // Companion object that helps in creating Question objects from a Parcel
    companion object CREATOR : Parcelable.Creator<Question> {
        // Creates a Question instance from a Parcel (used when retrieving the object)
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        // Creates an array of Question objects (used when passing multiple questions)
        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
