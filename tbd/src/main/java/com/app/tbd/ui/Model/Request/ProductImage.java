package com.app.tbd.ui.Model.Request;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductImage implements Parcelable {

	private int id;
	private String url;
	private String caption;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String imageUrl) {
		this.url = imageUrl;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(url);
		// dest.writeByte((byte) (checked ? 1 : 0));
	}

	/*public static final Creator<ProductImage> CREATOR = new Product.Creator<ProductImage>() {
		public ProductImage createFromParcel(Parcel in) {
			return new ProductImage(in);
		}

		public ProductImage[] newArray(int size) {
			return new ProductImage[size];
		}
	};*/

	private ProductImage(Parcel in) {
		id = in.readInt();
		url = in.readString();
		// checked = in.readByte() != 0;
	}

	public ProductImage() {

	}
}