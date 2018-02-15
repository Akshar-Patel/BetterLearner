package in.aternal.betterlearner.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.squareup.moshi.Json;

public class TechniqueWhat implements android.os.Parcelable {
  @Json(name = "desc")
  String desc;

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.desc);
  }

  public TechniqueWhat() {
  }

  protected TechniqueWhat(Parcel in) {
    this.desc = in.readString();
  }

  public static final Creator<TechniqueWhat> CREATOR = new Creator<TechniqueWhat>() {
    @Override
    public TechniqueWhat createFromParcel(Parcel source) {
      return new TechniqueWhat(source);
    }

    @Override
    public TechniqueWhat[] newArray(int size) {
      return new TechniqueWhat[size];
    }
  };
}
