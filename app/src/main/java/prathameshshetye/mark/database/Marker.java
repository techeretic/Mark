package prathameshshetye.mark.database;

/**
 * Created by p.shetye on 5/1/15.
 */
public class Marker {
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
}
