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
package org.gatein.mop.core.content.portlet;

import org.gatein.mop.api.content.ContentType;

import java.util.Collection;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

/**
 * A set of preferences.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public final class Preferences {

  /** . */
  public static final ContentType<Preferences> CONTENT_TYPE = new ContentType<Preferences>("application/portlet", Preferences.class);

  /** . */
  final Map<String, Preference> state;

  /** . */
  private final Map<String, Preference> entries;

  public Preferences() {
    this.state = new HashMap<String, Preference>();
    this.entries = Collections.unmodifiableMap(this.state);
  }

  public Preferences(Map<String, Preference> state) {
    if (state == null) {
      throw new NullPointerException();
    }

    //
    this.state = new HashMap<String, Preference>(state);
    this.entries = Collections.unmodifiableMap(this.state);
  }

  public Collection<Preference> getEntries() {
    return entries.values();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Preferences) {
      Preferences that = (Preferences)obj;
      return state.equals(that.state);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return state.hashCode();
  }

  @Override
  public String toString() {
    return "Preferences[state=" + state.toString() + "]";
  }
}
