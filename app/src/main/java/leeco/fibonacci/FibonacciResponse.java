package leeco.fibonacci;

import android.os.Parcelable;

import android.os.Parcel;

/**
 * Created by fuyingang on 2017/7/27.
 */

public class FibonacciResponse implements Parcelable {

    private final long result;
    private final long timeInMillis;

    public FibonacciResponse(long result, long timeInMillis) {
        this.result = result;
        this.timeInMillis = timeInMillis;
    }

    public long getResult() {
        return result;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(result);
        parcel.writeLong(timeInMillis);
    }

    public static final Parcelable.Creator<FibonacciResponse> CREATOR = new Parcelable.Creator<FibonacciResponse>() {
        public FibonacciResponse createFromParcel(Parcel in) {
            return new FibonacciResponse(in.readLong(), in.readLong());
        }
        public FibonacciResponse[] newArray(int size) {
            return new FibonacciResponse[size];
        }
    };
}
