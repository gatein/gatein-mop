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
package org.gatein.mop.core.api.workspace;

import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.Attributes;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.core.util.AbstractAttributes;
import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.Properties;
import org.chromattic.common.AbstractFilterIterator;

import java.util.Map;
import java.util.Set;
import java.util.AbstractSet;
import java.util.Iterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class WorkspaceObjectImpl implements WorkspaceObject {

  /** . */
  private final Set<String> keys = new AbstractSet<String>() {
    @Override
    public Iterator<String> iterator() {
      Map<String, Object> properties = getProperties();
      return new AbstractFilterIterator<String, String>(properties.keySet().iterator()) {
        protected String adapt(String internal) {
          if (internal.startsWith("mop:")) {
            return internal.substring(4);
          } else {
            return null;
          }
        }
      };
    }
    public int size() {
      Map<String, Object> properties = getProperties();
      int count = 0;
      for (String key : properties.keySet()) {
        if (key.startsWith("mop:")) {
          count++;
        }
      }
      return count;
    }
  };

  /** . */
  private final AbstractAttributes attributes = new AbstractAttributes() {
    @Override
    protected Object get(String name) {
      Map<String, Object> properties = getProperties();
      return properties.get("mop:" + name);
    }

    @Override
    protected void set(String name, Object o) {
      Map<String, Object> properties = getProperties();
      properties.put("mop:" + name, o);
    }

    public Set<String> getKeys() {
      return keys;
    }
  };

  public Attributes getAttributes() {
    return attributes;
  }

  @Name
  public abstract String getName();

  @Id
  public abstract String getObjectId();

  @Properties
  public abstract Map<String, Object> getProperties();

  @Override
  public String toString() {
    ObjectType<?> objectType = getObjectType();
    Class<? extends WorkspaceObject> javaType = objectType.getJavaType();
    String typeName = javaType.getSimpleName();
    String name = getName();
    String id = getObjectId();
    return typeName + "[name=" + name + ",id=" + id + "]";
  }

  static boolean contains(CustomizationContext container, CustomizationContext contained) {
    if (container == null) {
      throw new NullPointerException("No null container accepted");
    }
    if (contained == null) {
      throw new NullPointerException("No null contained accepted");
    }

    //
    if (container == contained) {
      return true;
    }
    if (container instanceof WorkspaceImpl) {
      if (contained instanceof SiteImpl) {
        SiteImpl site = (SiteImpl)contained;
        return contains(container, site.getWorkspace());
      } else if (contained instanceof PageImpl) {
        PageImpl page = (PageImpl)contained;
        return contains(container, page.getSite());
      }  else if (contained instanceof UIWindowImpl) {
        UIWindowImpl window = (UIWindowImpl)contained;
        return contains(container, window.getPage());
      }
    }
    if (container instanceof SiteImpl) {
      if (contained instanceof PageImpl) {
        PageImpl page = (PageImpl)contained;
        return contains(container, page.getSite());
      }  else if (contained instanceof UIWindowImpl) {
        UIWindowImpl window = (UIWindowImpl)contained;
        return contains(container, window.getPage());
      }
    }
    if (container instanceof PageImpl) {
      if (contained instanceof UIWindowImpl) {
        UIWindowImpl window = (UIWindowImpl)contained;
        return contains(container, window.getPage());
      }
    }
    return false;
  }
}
