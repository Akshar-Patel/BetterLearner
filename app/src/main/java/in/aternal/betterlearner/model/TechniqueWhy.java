package in.aternal.betterlearner.model;

import android.os.Parcel;
import com.squareup.moshi.Json;
import java.util.List;

public class TechniqueWhy implements android.os.Parcelable {

  public static final Creator<TechniqueWhy> CREATOR = new Creator<TechniqueWhy>() {
    @Override
    public TechniqueWhy createFromParcel(Parcel source) {
      return new TechniqueWhy(source);
    }

    @Override
    public TechniqueWhy[] newArray(int size) {
      return new TechniqueWhy[size];
    }
  };
  @Json(name = "benefits")
  private
  List<Benefit> mBenefitList;

  public TechniqueWhy() {
  }


  TechniqueWhy(Parcel in) {
    this.mBenefitList = in
        .createTypedArrayList(Benefit.CREATOR);
  }

  public List<Benefit> getBenefitList() {
    return mBenefitList;
  }

  public void setBenefitList(
      List<Benefit> benefitList) {
    mBenefitList = benefitList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.mBenefitList);
  }

  public static class Benefit implements android.os.Parcelable {

    public static final Creator<Benefit> CREATOR = new Creator<Benefit>() {
      @Override
      public Benefit createFromParcel(Parcel source) {
        return new Benefit(source);
      }

      @Override
      public Benefit[] newArray(int size) {
        return new Benefit[size];
      }
    };
    @Json(name = "desc")
    String desc;

    public Benefit() {
    }


    Benefit(Parcel in) {
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
