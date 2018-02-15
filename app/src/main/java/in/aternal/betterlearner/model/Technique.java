package in.aternal.betterlearner.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.squareup.moshi.Json;

public class Technique implements android.os.Parcelable {
  @Json(name="id")
  int mId;
  @Json(name="name")
  String mName;
  @Json(name="desc")
  String mDesc;

  public TechniqueWhat getTechniqueWhat() {
    return mTechniqueWhat;
  }

  public void setTechniqueWhat(TechniqueWhat techniqueWhat) {
    mTechniqueWhat = techniqueWhat;
  }

  @Json(name="what")
  TechniqueWhat mTechniqueWhat;

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getDesc() {
    return mDesc;
  }

  public void setDesc(String desc) {
    mDesc = desc;
  }

  public Technique() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mId);
    dest.writeString(this.mName);
    dest.writeString(this.mDesc);
    dest.writeParcelable(this.mTechniqueWhat, flags);
  }

  protected Technique(Parcel in) {
    this.mId = in.readInt();
    this.mName = in.readString();
    this.mDesc = in.readString();
    this.mTechniqueWhat = in.readParcelable(TechniqueWhat.class.getClassLoader());
  }

  public static final Creator<Technique> CREATOR = new Creator<Technique>() {
    @Override
    public Technique createFromParcel(Parcel source) {
      return new Technique(source);
    }

    @Override
    public Technique[] newArray(int size) {
      return new Technique[size];
    }
  };
}
