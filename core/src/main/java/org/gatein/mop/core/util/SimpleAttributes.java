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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class SimpleAttributes extends AbstractAttributes {

  private final Map<String, Object> map;

  public SimpleAttributes(Map<String, Object> map) {
    this.map = map;
  }

  public SimpleAttributes() {
    this(new HashMap<String, Object>());
  }

  public Set<String> getKeys() {
    return map.keySet();
  }

  protected Object get(String name) {
    return map.get(name);
  }

  protected void set(String name, Object o) {
    if (o == null) {
      map.remove(name);
    } else {
      map.put(name, o);
    }
  }
}
