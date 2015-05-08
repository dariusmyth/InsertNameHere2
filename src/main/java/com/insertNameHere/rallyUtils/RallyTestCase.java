package com.insertNameHere.rallyUtils;

import com.insertNameHere.models.AbstractModel;



public class RallyTestCase extends AbstractModel {

	String _rallyAPIMajor, _rallyAPIMinor;
	String _ref, _objectVersion, _refObjectName;
	String FormattedID;
	String Name;
	String _type;

	public RallyTestCase(String _rallyAPIMajor, String _rallyAPIMinor, String _ref, String _objectVersion, String _refObjectName, String formattedID,
			String name, String _type) {
		this._rallyAPIMajor = _rallyAPIMajor;
		this._rallyAPIMinor = _rallyAPIMinor;
		this._ref = _ref;
		this._objectVersion = _objectVersion;
		this._refObjectName = _refObjectName;
		FormattedID = formattedID;
		Name = name;
		this._type = _type;
	}

	public String get_rallyAPIMajor() {
		return _rallyAPIMajor;
	}

	public void set_rallyAPIMajor(String _rallyAPIMajor) {
		this._rallyAPIMajor = _rallyAPIMajor;
	}

	public String get_rallyAPIMinor() {
		return _rallyAPIMinor;
	}

	public void set_rallyAPIMinor(String _rallyAPIMinor) {
		this._rallyAPIMinor = _rallyAPIMinor;
	}

	public String get_ref() {
		return _ref;
	}

	public void set_ref(String _ref) {
		this._ref = _ref;
	}

	public String get_objectVersion() {
		return _objectVersion;
	}

	public void set_objectVersion(String _objectVersion) {
		this._objectVersion = _objectVersion;
	}

	public String get_refObjectName() {
		return _refObjectName;
	}

	public void set_refObjectName(String _refObjectName) {
		this._refObjectName = _refObjectName;
	}

	public String getFormattedID() {
		return FormattedID;
	}

	public void setFormattedID(String formattedID) {
		FormattedID = formattedID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

}