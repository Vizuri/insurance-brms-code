/*
 *	Copyright 2015 Vizuri, a business division of AEM Corporation
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */

package com.vizuri.insurance.domain;

public class Question {

	private int id;
	private String description;
    private String answerType;
    private String group;
    private String mappedObject;
    private String mappedProperty;
    private Boolean required;
    private Boolean enabled;
    private int parentId;
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getMappedObject() {
		return mappedObject;
	}
	public void setMappedObject(String mappedObject) {
		this.mappedObject = mappedObject;
	}
	public String getMappedProperty() {
		return mappedProperty;
	}
	public void setMappedProperty(String mappedProperty) {
		this.mappedProperty = mappedProperty;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", answerType=" + answerType + ", group=" + group + ", mappedObject=" + mappedObject + ", mappedProperty=" + mappedProperty
				+ ", required=" + required + ", enabled=" + enabled + ", parentId=" + parentId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answerType == null) ? 0 : answerType.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((mappedObject == null) ? 0 : mappedObject.hashCode());
		result = prime * result
				+ ((mappedProperty == null) ? 0 : mappedProperty.hashCode());
		result = prime * result + parentId;
		result = prime * result
				+ ((required == null) ? 0 : required.hashCode());
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
		Question other = (Question) obj;
		if (answerType == null) {
			if (other.answerType != null)
				return false;
		} else if (!answerType.equals(other.answerType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id != other.id)
			return false;
		if (mappedObject == null) {
			if (other.mappedObject != null)
				return false;
		} else if (!mappedObject.equals(other.mappedObject))
			return false;
		if (mappedProperty == null) {
			if (other.mappedProperty != null)
				return false;
		} else if (!mappedProperty.equals(other.mappedProperty))
			return false;
		if (parentId != other.parentId)
			return false;
		if (required == null) {
			if (other.required != null)
				return false;
		} else if (!required.equals(other.required))
			return false;
		return true;
	}
	
	
    
}
