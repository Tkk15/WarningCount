package pomona.albert.model;

public class PersonBeingWarned {
	private int mID;

	private String mLicenseNO;

	private String mLicensePlate;
	
	private String mDescription;

	
	
	
	public PersonBeingWarned(int iD, String licenseNO, String licensePlate, String description) {
		super();
		mID = iD;
		mLicenseNO = licenseNO;
		mLicensePlate = licensePlate;
		mDescription = description;
	}

	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("PersonBeingWarned [ID=");
		sb.append(mID).append(", LicenseNO=").append(mLicenseNO).append(", LicensePlate=")
		.append(mLicensePlate).append(", Description=")
		.append(mDescription).append("]");
		return sb.toString();
	}




	public String getLicenseNO() {
		return mLicenseNO;
	}

	public void setLicenseNO(String licenseNO) {
		mLicenseNO = licenseNO;
	}

	public String getLicensePlate() {
		return mLicensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		mLicensePlate = licensePlate;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public int getID() {
		return mID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
		result = prime * result + mID;
		result = prime * result + ((mLicenseNO == null) ? 0 : mLicenseNO.hashCode());
		result = prime * result + ((mLicensePlate == null) ? 0 : mLicensePlate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonBeingWarned other = (PersonBeingWarned) obj;
		if (mDescription == null) {
			if (other.mDescription != null)
				return false;
		} else if (!mDescription.equals(other.mDescription))
			return false;
		if (mID != other.mID)
			return false;
		if (mLicenseNO == null) {
			if (other.mLicenseNO != null)
				return false;
		} else if (!mLicenseNO.equals(other.mLicenseNO))
			return false;
		if (mLicensePlate == null) {
			if (other.mLicensePlate != null)
				return false;
		} else if (!mLicensePlate.equals(other.mLicensePlate))
			return false;
		return true;
	}
	
	
	
	
	
	
}
