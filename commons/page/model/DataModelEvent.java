/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package commons.page.model;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 * 
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472558 $ $Date: 2006-11-08 18:36:53 +0100 (Mi, 08 Nov 2006) $
 */
public class DataModelEvent extends java.util.EventObject {
	private static final long serialVersionUID = 1823115573192262656L;
	// FIELDS
	private int _index;
	private Object _data;

	public DataModelEvent(DataModel _model, int _index, Object _data) {
		super(_model);
		this._index = _index;
		this._data = _data;
	}

	// METHODS
	public DataModel getDataModel() {
		return (DataModel) getSource();
	}

	public Object getRowData() {
		return _data;
	}

	public int getRowIndex() {
		return _index;
	}
}
