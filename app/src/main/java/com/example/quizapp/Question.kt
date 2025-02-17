package com.example.quizapp

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents a quiz question.
 * Implements [Parcelable] to allow easy data transfer between activities.
 *
 * @property question The text of the question.
 * @property options List of possible answer choices.
 * @property correctAnswerIndex The index of the correct answer in [options].
 */
data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) : Parcelable {

    /**
     * Constructs a [Question] object from a [Parcel].
     */
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readInt()
    )

    /**
     * Writes the object data into a [Parcel].
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringList(options)
        parcel.writeInt(correctAnswerIndex)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Question> {
        /**
         * Creates a [Question] instance from a [Parcel].
         */
        override fun createFromParcel(parcel: Parcel): Question = Question(parcel)

        /**
         * Creates an array of [Question] objects.
         */
        override fun newArray(size: Int): Array<Question?> = arrayOfNulls(size)
    }
}
