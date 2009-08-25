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
package org.gatein.mop.core.util;

import org.gatein.mop.api.Key;
import org.gatein.mop.api.Attributes;
import org.gatein.mop.api.ValueType;

import java.util.Date;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractAttributes implements Attributes {

  protected abstract Object get(String name);

  protected abstract void set(String name, Object o);

  private <T> T getObject(String name, ValueType<T> type, T defaultValue) {
    if (name == null) {
      throw new NullPointerException();
    }
    Object o = get(name);
    if (o == null) {
      return defaultValue;
    } else {
      return type.cast(o);
    }
  }

  public final Object getObject(String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    Object o = get(name);
    if (o == null) {
      return null;
    } else {
      ValueType.get(o);
      return o;
    }
  }

  public final <T> void setObject(String name, T value) {
    if (name == null) {
      throw new NullPointerException();
    }
    if (value == null) {
      set(name, null);
    } else {
      ValueType.get(value);
      set(name, value);
    }
  }

  public final ValueType<?> getType(String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    Object o = get(name);
    if (o == null) {
      return null;
    } else {
      return ValueType.get(o);
    }
  }

  public final String getString(String name) {
    return getObject(name, ValueType.STRING, null);
  }

  public final void setString(String name, String value) {
    setObject(name, value);
  }

  public final Boolean getBoolean(String name) {
    return getObject(name, ValueType.BOOLEAN, null);
  }

  public final void setBoolean(String name, Boolean value) {
    setObject(name, value);
  }

  public final Integer getInteger(String name) {
    return getObject(name, ValueType.INTEGER, null);
  }

  public final void setInteger(String name, Integer value) {
    setObject(name, value);
  }

  public final Date getDate(String name) {
    return getObject(name, ValueType.DATE, null);
  }

  public final void setDate(String name, Date value) {
    setObject(name, value);
  }

  public final Double getDouble(String name) {
    return getObject(name, ValueType.DOUBLE, null);
  }

  public final void setDouble(String name, Double value) {
    setObject(name, value);
  }

  public final <T> T getValue(Key<T> key, T defaultValue) {
    return getObject(key.getName(), key.getType(), defaultValue);
  }

  public final <T> T getValue(Key<T> key) {
    return getObject(key.getName(), key.getType(), null);
  }

  public final <T> void setValue(Key<T> key, T value) {
    setObject(key.getName(), value);
  }
}
