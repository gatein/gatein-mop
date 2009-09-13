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
package org.gatein.mop.api.workspace;

import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ui.UIWindow;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.ui.UIBody;
import org.gatein.mop.api.workspace.link.PageLink;
import org.gatein.mop.api.workspace.link.URLLink;

import java.util.Set;
import java.util.HashSet;

/**
 * A type safe interface for the type of a workspace object.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ObjectType<O extends WorkspaceObject> {

  /** . */
  public static final ObjectType<WorkspaceObject> ANY = new ObjectType<WorkspaceObject>(WorkspaceObject.class);

  /** . */
  public static final ObjectType<Workspace> WORKSPACE = new ObjectType<Workspace>(Workspace.class);

  /** . */
  public static final ObjectType<Site> SITE = new ObjectType<Site>(Site.class);

  /** . */
  public static final ObjectType<Site> PORTAL_SITE = new ObjectType<Site>(Site.class, SITE);

  /** . */
  public static final ObjectType<Site> GROUP_SITE = new ObjectType<Site>(Site.class, SITE);

  /** . */
  public static final ObjectType<Site> USER_SITE = new ObjectType<Site>(Site.class, SITE);

  /** . */
  public static final ObjectType<Site> SHARED_SITE = new ObjectType<Site>(Site.class, SITE);

  /** . */
  public static final ObjectType<Page> PAGE = new ObjectType<Page>(Page.class);

  /** . */
  public static final ObjectType<Navigation> NAVIGATION = new ObjectType<Navigation>(Navigation.class);

  /** . */
  public static final ObjectType<UIComponent> COMPONENT = new ObjectType<UIComponent>(UIComponent.class);

  /** . */
  public static final ObjectType<UIBody> BODY = new ObjectType<UIBody>(UIBody.class, COMPONENT);

  /** . */
  public static final ObjectType<UIContainer> CONTAINER = new ObjectType<UIContainer>(UIContainer.class, COMPONENT);

  /** . */
  public static final ObjectType<UIWindow> WINDOW = new ObjectType<UIWindow>(UIWindow.class, COMPONENT);

  /** . */
  public static final ObjectType<PageLink> PAGE_LINK = new ObjectType<PageLink>(PageLink.class);

  /** . */
  public static final ObjectType<URLLink> URL_LINK = new ObjectType<URLLink>(URLLink.class);

  /** . */
  private final Class<O> javaType;

  /** . */
  private final Set<ObjectType<?>> superTypes;

  private ObjectType(Class<O> javaType, ObjectType<?>... superTypes) {
    for (ObjectType<?> superType : superTypes) {
      if (!superType.javaType.isAssignableFrom(javaType)) {
        throw new AssertionError();
      }
    }

    //
    Set<ObjectType<?>> tmp = new HashSet<ObjectType<?>>();
    for (ObjectType<?> superType : superTypes) {
      tmp.add(superType);
    }

    //
    this.javaType = javaType;
    this.superTypes = tmp;
  }

  public Class<O> getJavaType() {
    return javaType;
  }

  public boolean isAssignableFrom(ObjectType<?> other) {
    if (other == null) {
      throw new NullPointerException();
    }
    if (this == ANY) {
      return true;
    }
    return other == this || other.superTypes.contains(this);
  }

  public O cast(Object o) {
    if (o == null) {
      return null;
    } if (javaType.isInstance(o)) {
      return javaType.cast(o);
    } else {
      throw new ClassCastException();
    }
  }

  @Override
  public String toString() {
    return "ObjectType[" + javaType + "]";
  }
}
