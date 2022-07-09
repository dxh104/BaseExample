package com.dxh.base_library_module.base;

public class BaseResult<T> {
    public String code;
    public String msg;
    public T data;
    public String reason;
    public T result;
    public String message;

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}

//public class BaseResult<T extends Parcelable> implements Parcelable {
//    public String code;
//    public String msg;
//    public T data;
//
//    @Override
//    public String toString() {
//        return "BaseResult{" +
//                "code='" + code + '\'' +
//                ", msg='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.code);
//        dest.writeString(this.msg);
//        dest.writeParcelable(this.data, flags);
//    }
//
//    public BaseResult() {
//    }
//
//    protected BaseResult(Parcel in) {
//        this.code = in.readString();
//        this.msg = in.readString();
//
//        String dataName = in.readString();
//        try {
//            this.data = in.readParcelable(Class.forName(dataName).getClassLoader());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static final Parcelable.Creator<BaseResult> CREATOR = new Parcelable.Creator<BaseResult>() {
//        @Override
//        public BaseResult createFromParcel(Parcel source) {
//            return new BaseResult(source);
//        }
//
//        @Override
//        public BaseResult[] newArray(int size) {
//            return new BaseResult[size];
//        }
//    };
//}