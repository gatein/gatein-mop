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

import org.chromattic.api.annotations.NodeMapping;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.Create;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ObjectType;

import java.util.Map;
import java.util.Collection;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NodeMapping(name = "mop:uicontainer")
public abstract class UIContainerImpl extends UIComponentImpl implements UIContainer {

  @Create
  public abstract UIContainerImpl createContainer();

  @Create
  public abstract UIBodyImpl createInsertion();

  @Create
  public abstract UIWindowImpl createWindow();

  @OneToMany
  public abstract Map<String, UIComponentImpl> getComponents();

  public ObjectType<? extends UIContainer> getObjectType() {
    return ObjectType.CONTAINER;
  }

  public UIComponent getChild(String componentName) {
    Map<String, UIComponentImpl> children = getComponents();
    return children.get(componentName);
  }

  public <T extends UIComponent> T addChild(ObjectType<T> componentType, String name) {
    UIComponentImpl child;
    if (componentType == ObjectType.WINDOW) {
      child = createWindow();
    } else if (componentType == ObjectType.CONTAINER) {
      child = createContainer();
    } else if (componentType == ObjectType.BODY) {
      child = createInsertion();
    } else {
      throw new UnsupportedOperationException();
    }
    Map<String, UIComponentImpl> children = getComponents();
    children.put(name, child);
    return componentType.cast(child);
  }

  public Collection<? extends UIComponent> getChildren() {
    return getComponents().values();
  }
}
