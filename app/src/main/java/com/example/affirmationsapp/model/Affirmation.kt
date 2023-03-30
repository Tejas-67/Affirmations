package com.example.affirmationsapp.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Affirmation")
data class Affirmation(

    @ColumnInfo(name="name") var text: String?,
    @ColumnInfo(name="image", typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray?,
    @PrimaryKey(autoGenerate = true) var id: Int=0
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createByteArray(),
        parcel.readInt()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Affirmation

        if (text != other.text) return false
        if (!image.contentEquals(other.image)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + id
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeByteArray(image)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Affirmation> {
        override fun createFromParcel(parcel: Parcel): Affirmation {
            return Affirmation(parcel)
        }

        override fun newArray(size: Int): Array<Affirmation?> {
            return arrayOfNulls(size)
        }
    }
}
