package in.aternal.betterlearner.model;

import android.os.Parcel;
import com.squareup.moshi.Json;

public class Technique implements android.os.Parcelable {

  @Json(name = "id")
  int mId;
  @Json(name = "name")
  String mName;
  @Json(name = "desc")
  String mDesc;
  @Json(name = "what")
  TechniqueWhat mTechniqueWhat;

  public TechniqueWhy getTechniqueWhy() {
    return mTechniqueWhy;
  }

  public void setTechniqueWhy(TechniqueWhy techniqueWhy) {
    mTechniqueWhy = techniqueWhy;
  }

  @Json(name = "why")
  TechniqueWhy mTechniqueWhy;

  public TechniqueHow getTechniqueHow() {
    return mTechniqueHow;
  }

  public void setTechniqueHow(TechniqueHow techniqueHow) {
    mTechniqueHow = techniqueHow;
  }

  @Json(name = "how")
  TechniqueHow mTechniqueHow;

  public Technique() {
  }

  public TechniqueWhat getTechniqueWhat() {
    return mTechniqueWhat;
  }

  public void setTechniqueWhat(TechniqueWhat techniqueWhat) {
    mTechniqueWhat = techniqueWhat;
  }

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
    dest.writeParcelable(this.mTechniqueWhy, flags);
    dest.writeParcelable(this.mTechniqueHow, flags);
  }

  protected Technique(Parcel in) {
    this.mId = in.readInt();
    this.mName = in.readString();
    this.mDesc = in.readString();
    this.mTechniqueWhat = in.readParcelable(TechniqueWhat.class.getClassLoader());
    this.mTechniqueWhy = in.readParcelable(TechniqueWhy.class.getClassLoader());
    this.mTechniqueHow = in.readParcelable(TechniqueHow.class.getClassLoader());
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
