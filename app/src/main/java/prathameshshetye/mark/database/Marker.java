package prathameshshetye.mark.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by p.shetye on 5/1/15.
 */
public class Marker implements Parcelable {
    private byte [] mImage;
    private float mLatitude;
    private float mLongitude;
    private String mName;
    private String mDescrip;
    private int mID;

    public Marker(byte[] mImage,
                  float mLatitude,
                  float mLongitude,
                  String mName,
                  String mDescrip) {
        this.mImage = mImage;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mName = mName;
    }

    public Marker(int mID,
                  float mLatitude,
                  float mLongitude,
                  String mName,
                  String mDescrip,
                  byte[] mImage) {
        this.mImage = mImage;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mName = mName;
        this.mDescrip = mDescrip;
        this.mID = mID;
    }

    public Marker(float mLatitude, float mLongitude, String mName, String mDescrip) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mName = mName;
        this.mDescrip = mDescrip;
        this.mImage = null;
    }

    public Marker() {
        this.mImage = null;
        this.mLatitude = 0;
        this.mLongitude = 0;
        this.mName = "";
        this.mDescrip = "";
        this.mID = 0;
    }
    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] mImage) {
        this.mImage = mImage;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float mLatitude) {
        this.mLatitude = mLatitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescrip() {
        return mDescrip;
    }

    public void setDescrip(String mDescrip) {
        this.mDescrip = mDescrip;
    }

    public int getID() {
        return mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(mLatitude);
        parcel.writeFloat(mLongitude);
        parcel.writeString(mName);
        parcel.writeString(mDescrip);
        parcel.writeInt(mID);
        if (mImage == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(mImage.length);
            parcel.writeByteArray(mImage);
        }
    }

    public static final Parcelable.Creator<Marker> CREATOR
            = new Parcelable.Creator<Marker>() {
        public Marker createFromParcel(Parcel in) {
            return new Marker(in);
        }

        public Marker[] newArray(int size) {
            return new Marker[size];
        }
    };

    private Marker(Parcel in) {
        mLatitude = in.readFloat();
        mLongitude = in.readFloat();
        mName = in.readString();
        mDescrip = in.readString();
        mID = in.readInt();
        int size = in.readInt();
        if (size != 0) {
            mImage = new byte[size];
            in.readByteArray(mImage);
        }
    }
}
