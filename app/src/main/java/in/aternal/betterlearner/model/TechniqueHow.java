package in.aternal.betterlearner.model;

import android.os.Parcel;
import com.squareup.moshi.Json;
import java.util.List;

public class TechniqueHow implements android.os.Parcelable {

  public static final Creator<TechniqueHow> CREATOR = new Creator<TechniqueHow>() {
    @Override
    public TechniqueHow createFromParcel(Parcel source) {
      return new TechniqueHow(source);
    }

    @Override
    public TechniqueHow[] newArray(int size) {
      return new TechniqueHow[size];
    }
  };
  @Json(name = "steps")
  List<Step> mStepList;

  public TechniqueHow() {
  }

  protected TechniqueHow(Parcel in) {
    this.mStepList = in
        .createTypedArrayList(Step.CREATOR);
  }

  public List<Step> getStepList() {
    return mStepList;
  }

  public void setStepList(List<Step> stepList) {
    mStepList = stepList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.mStepList);
  }

  public static class Step implements android.os.Parcelable {

    public static final Creator<Step> CREATOR = new Creator<Step>() {
      @Override
      public Step createFromParcel(Parcel source) {
        return new Step(source);
      }

      @Override
      public Step[] newArray(int size) {
        return new Step[size];
      }
    };
    @Json(name = "desc")
    String desc;

    public Step() {
    }

    protected Step(Parcel in) {
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
}
