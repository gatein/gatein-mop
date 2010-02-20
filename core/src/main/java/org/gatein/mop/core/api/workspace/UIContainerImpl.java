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

import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.PrimaryType;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ObjectType;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:uicontainer")
public abstract class UIContainerImpl extends UIComponentImpl implements UIContainer
{

   @Create
   public abstract UIContainerImpl createContainer();

   @Create
   public abstract UIBodyImpl createInsertion();

   @Create
   public abstract UIWindowImpl createWindow();

   @OneToMany
   public abstract Map<String, UIComponentImpl> getComponentMap();

   @OneToMany
   public abstract List<UIComponentImpl> getComponentList();

   public ObjectType<? extends UIContainer> getObjectType()
   {
      return ObjectType.CONTAINER;
   }

   public UIComponent get(String componentName)
   {
      Map<String, UIComponentImpl> children = getComponentMap();
      return children.get(componentName);
   }

   public <T extends UIComponent> T add(ObjectType<T> componentType, String name)
   {
      UIComponentImpl child;
      if (componentType == ObjectType.WINDOW)
      {
         child = createWindow();
      }
      else if (componentType == ObjectType.CONTAINER)
      {
         child = createContainer();
      }
      else if (componentType == ObjectType.BODY)
      {
         child = createInsertion();
      }
      else
      {
         throw new UnsupportedOperationException();
      }
      Map<String, UIComponentImpl> children = getComponentMap();
      children.put(name, child);
      return componentType.cast(child);
   }

   public <T extends UIComponent> T add(int index, ObjectType<T> componentType, String name)
   {
      UIComponentImpl child;
      if (componentType == ObjectType.WINDOW)
      {
         child = createWindow();
      }
      else if (componentType == ObjectType.CONTAINER)
      {
         child = createContainer();
      }
      else if (componentType == ObjectType.BODY)
      {
         child = createInsertion();
      }
      else
      {
         throw new UnsupportedOperationException();
      }
      child.setName(name);
      List<UIComponentImpl> children = getComponentList();
      children.add(index, child);
      return componentType.cast(child);
   }

   public List<UIComponent> getComponents() {
      // We have to do that
      return (List)components;
   }

   // List<UIComponent> implementation **********************************************************************************

   private final List<UIComponentImpl> components = new List<UIComponentImpl>() {

      public int size()
      {
         return getComponentList().size();
      }

      public boolean isEmpty()
      {
         return getComponentList().isEmpty();
      }

      public boolean contains(Object o)
      {
         return getComponentList().contains(o);
      }

      public Iterator<UIComponentImpl> iterator()
      {
         return getComponentList().iterator();
      }

      public Object[] toArray()
      {
         return new Object[0];
      }

      public <T> T[] toArray(T[] a)
      {
         return getComponentList().toArray(a);
      }

      public boolean add(UIComponentImpl uiComponent)
      {
         return getComponentList().add(uiComponent);
      }

      public boolean remove(Object o)
      {
         return getComponentList().remove(o);
      }

      public boolean containsAll(Collection<?> c)
      {
         return getComponentList().containsAll(c);
      }

      public boolean addAll(Collection<? extends UIComponentImpl> c)
      {
         return getComponentList().addAll(c);
      }

      public boolean addAll(int index, Collection<? extends UIComponentImpl> c)
      {
         return getComponentList().addAll(index, c);
      }

      public boolean removeAll(Collection<?> c)
      {
         return getComponentList().removeAll(c);
      }

      public boolean retainAll(Collection<?> c)
      {
         return getComponentList().retainAll(c);
      }

      public void clear()
      {
         getComponentList().clear();
      }

      public UIComponentImpl get(int index)
      {
         return getComponentList().get(index);
      }

      public UIComponentImpl set(int index, UIComponentImpl element)
      {
         return getComponentList().set(index, element);
      }

      public void add(int index, UIComponentImpl element)
      {
         getComponentList().add(index, element);
      }

      public UIComponentImpl remove(int index)
      {
         return getComponentList().remove(index);
      }

      public int indexOf(Object o)
      {
         return getComponentList().indexOf(o);
      }

      public int lastIndexOf(Object o)
      {
         return getComponentList().lastIndexOf(o);
      }

      public ListIterator<UIComponentImpl> listIterator()
      {
         return getComponentList().listIterator();
      }

      public ListIterator<UIComponentImpl> listIterator(int index)
      {
         return getComponentList().listIterator(index);
      }

      public List<UIComponentImpl> subList(int fromIndex, int toIndex)
      {
         return getComponentList().subList(fromIndex, toIndex);
      }
   };
}
