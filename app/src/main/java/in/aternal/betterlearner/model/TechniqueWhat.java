package in.aternal.betterlearner.model;

import android.os.Parcel;
import com.squareup.moshi.Json;

public class TechniqueWhat implements android.os.Parcelable {

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
  @Json(name = "desc")
  private
  String desc;

  public TechniqueWhat() {
  }

  TechniqueWhat(Parcel in) {
    this.desc = in.readString();
  }

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
}
