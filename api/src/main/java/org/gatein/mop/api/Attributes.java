/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.api;

import java.util.Date;
import java.util.Set;

/**
 * <p>This interface extends a map to provide convenient method for dealing with attribute values in a type safe
 * manner when the interface client knows the type of an attribute and wants to deal with it accordingly.</p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Attributes {

  Set<String> getKeys();

  String getString(String name);

  void setString(String name, String value);

  Boolean getBoolean(String name);

  void setBoolean(String name, Boolean value);

  Integer getInteger(String name);

  void setInteger(String name, Integer value);

  Date getDate(String name);

  void setDate(String name, Date value);

  Double getDouble(String name);

  void setDouble(String name, Double value);

  Object getObject(String name);

  <T> void setObject(String name, T value);

  ValueType<?> getType(String name);

  <T> T getValue(Key<T> key);

  <T> T getValue(Key<T> key, T defaultValue);

  <T> void setValue(Key<T> key, T value);

}
