package com.virgil.aft.business.view.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuwujing on 14/11/6.
 */
public class CountBean implements Parcelable{

    public int count=0;

    public CountBean(){

    }
    public CountBean(int count){
        this.count=count;
    }
    public static final Creator<CountBean> CREATOR=new Creator<CountBean>() {
        @Override
        public CountBean createFromParcel(Parcel source) {
            CountBean bean=new CountBean();
            bean.count= source.readInt();
            return bean;
        }

        @Override
        public CountBean[] newArray(int size) {
            return new CountBean[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
    }
}
